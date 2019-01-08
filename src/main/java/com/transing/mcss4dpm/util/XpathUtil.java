package com.transing.mcss4dpm.util;


import com.transing.mcss4dpm.integration.bo.WeixinBrandBO;
import com.transing.mcss4dpm.integration.bo.WeixinCommentBO;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;


public class XpathUtil {

    public static HtmlCleaner htmlCleaner = new HtmlCleaner();

    public static boolean isJson(String content) {
        try {
            com.alibaba.fastjson.JSONArray jsonStr = com.alibaba.fastjson.JSONArray.parseArray(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Date> getWeixinPublishTime(String htmlContent) {
        List<Date> dateList = new ArrayList<>();
        TagNode tagNode = htmlCleaner.clean(htmlContent);
        List<Object> publishDateObj = getObjListByXpath(tagNode, "//*[@class='weui_media_extra_info']/text()");
        for (Object obj : publishDateObj) {
            String publishDate = obj.toString().trim();
            dateList.add(DateUtil.parseDate(publishDate));
        }
        return dateList;
    }

    public static void main(String[] args) {
        try {
            File fil = new File("C:\\Users\\Administrator\\Desktop\\dad.html");
            FileInputStream file = new FileInputStream(fil);
            InputStreamReader inputFileReader = new InputStreamReader(file, "utf-8");
            BufferedReader reader = new BufferedReader(inputFileReader);
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }

            WeixinBrandBO weixinBrand = new WeixinBrandBO();

            weixinBrand.setTaskId(2);
            weixinBrand.setPid(2);
            weixinBrand.setUrl("");

            getWeixinBrand2ByHtml(sb.toString(), weixinBrand);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Map<String, Object> getWeixinBrand2ByHtml(String htmlContent, WeixinBrandBO weixinBrandBO) {
        if (null == weixinBrandBO) {
            return null;
        }
        String url = weixinBrandBO.getUrl();
        long taskId = weixinBrandBO.getTaskId();
        long pid = weixinBrandBO.getPid();

        TagNode tagNode = htmlCleaner.clean(htmlContent);

        Object titleObj = getObjByXpath(tagNode, "//h2[@id='activity-name']/text()");

        Object publishDateObj = getObjByXpath(tagNode, "//em[@id='post-date']/text()");
        Object authorObj = getObjByXpath(tagNode, "//div[@id='meta_content']/em[2]/text()");
        Object sourceObj = getObjByXpath(tagNode, "//a[@id='post-user']/text()");
        Object weixinIdObj = getObjByXpath(tagNode, "//div[@id='js_profile_qrcode']/div[@class='profile_inner']/p[1]/span[@class='profile_meta_value']/text()");
        Object contentObj = getObjByXpath(tagNode, "//div[@id='js_content']//text()");
        Object imgUrlObj = getObjByXpath(tagNode, "//div[@id='js_content']//img/@data-src");
        Object pageViewsObj = getObjByXpath(tagNode, "//span[@id='readNum3']/text()");
        Object praiseNumObj = getObjByXpath(tagNode, "//span[@id='like3']/text()");

        String title = titleObj.toString().trim();
        String publishDate = publishDateObj.toString().trim();
        String authar = authorObj == null ? "" : authorObj.toString().trim();
        String source = sourceObj == null ? "" : sourceObj.toString().trim();
        String weixinId = weixinIdObj == null ? "" : weixinIdObj.toString().trim();
        String content = contentObj.toString().trim();
        String imgUrls = imgUrlObj.toString().trim();
        String pageViews = !"".equals(pageViewsObj) ? pageViewsObj.toString().trim() : "0";
        String pariseNum = !"".equals(praiseNumObj) ? praiseNumObj.toString().trim() : "0";
        if (pariseNum.equals("赞")) {
            pariseNum = "0";
        }
        if (pageViews.contains("+")) {
            pageViews = "100000";
        }
        weixinBrandBO.setAuthor(authar);
        weixinBrandBO.setSource(source);
        weixinBrandBO.setWeixinId(weixinId);
        weixinBrandBO.setContent(content);
        weixinBrandBO.setTitle(title);
        weixinBrandBO.setPublishTime(publishDate);
        weixinBrandBO.setImgUrl(imgUrls);
        weixinBrandBO.setPageViews(pageViews);
        weixinBrandBO.setPraiseNum(pariseNum);

        //评论
        List<WeixinCommentBO> weixinCommentBOList = new ArrayList<>();
        List<TagNode> liTagNodeList = getTagNodeListByXpathStr(tagNode, "//ul[@id=\"js_cmt_list\"]/li");
        for (TagNode liTagNode : liTagNodeList) {
            WeixinCommentBO weixinCommentBO = new WeixinCommentBO();
            //获取评论
            String commenter = getObjByXpath(liTagNode, "//div[@class='user_info']//*[@class='nickname']/text()").toString().trim();
            String avatar = getObjByXpath(liTagNode, "//div[@class='user_info']//*[@class='avatar']/@src").toString().trim();
            String commentTimeStr = getObjByXpath(liTagNode, "//p[@class='discuss_extra_info']/text()").toString().trim();
            String conent = getObjByXpath(liTagNode, "//div[@class='discuss_message']/div[@class='discuss_message_content']/text()").toString().trim();
            String praiseNumStr = getObjByXpath(liTagNode, "//div[@class='discuss_opr']/span/span[@class='praise_num']/text()").toString().trim();
            int praiseNum;
            Date commentTime;
            try {
                praiseNum = Integer.parseInt(praiseNumStr);
            } catch (Exception e) {
                praiseNum = 0;
            }
            try {
                commentTime = DateUtil.parseDate(commentTimeStr);
            } catch (Exception e) {
                commentTime = new Date();
            }
            weixinCommentBO.setDatetime(commentTime);
            weixinCommentBO.setLtimes(praiseNum);
            weixinCommentBO.setAuthor(commenter);
            weixinCommentBO.setContent(conent);
            weixinCommentBO.setIcon(avatar);
            weixinCommentBO.setParent(url);
            //获取评论回复
            List<TagNode> replyTagNodeList = getTagNodeListByXpathStr(liTagNode, "//div[@class='reply_result']");
            if (replyTagNodeList.size() > 0) {
                for (TagNode replyTagNode : replyTagNodeList) {
                    String replyConent = getObjByXpath(replyTagNode, "//div[@class='discuss_message']/div[@class='discuss_message_content']/text()").toString().trim();
                    String replyTimeStr = getObjByXpath(replyTagNode, "//p[@class='discuss_extra_info']/text()").toString().trim();
                    Date replyTime;
                    try {
                        replyTime = DateUtil.parseDate(replyTimeStr);
                    } catch (Exception e) {
                        replyTime = new Date();
                    }
                    if (replyTagNodeList.size() > 1) {
                        WeixinCommentBO weixinCommentBO2 = new WeixinCommentBO();

                        weixinCommentBO2.setLtimes(praiseNum);
                        weixinCommentBO2.setAuthor(commenter);
                        weixinCommentBO2.setContent(conent);
                        weixinCommentBO2.setDatetime(commentTime);
                        weixinCommentBO2.setIcon(avatar);

                        weixinCommentBO2.setReply(replyConent);
//                        weixinCommentBO2.setReplytime(replyTime);
                        weixinCommentBOList.add(weixinCommentBO2);
                    } else if (replyTagNodeList.size() == 1) {
                        weixinCommentBO.setReply(replyConent);
//                        weixinCommentBO.setReplytime(replyTime);
                        weixinCommentBOList.add(weixinCommentBO);
                    }
                }
            } else {
                weixinCommentBO.setReply("");
                weixinCommentBOList.add(weixinCommentBO);
            }

        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("weixinBrandBO", weixinBrandBO);
        returnMap.put("weixinCommentBOList", weixinCommentBOList);
        return returnMap;

    }


    public static List<TagNode> getTagNodeListByXpathStr(TagNode tagNode, String xpathStr) {
        List<TagNode> tagNodeList = new ArrayList<>();
        try {
            Object[] objArray = tagNode.evaluateXPath(xpathStr);
            if (null != objArray && objArray.length > 0) {
                for (Object obj : objArray) {
                    if (obj instanceof TagNode) {
                        tagNodeList.add((TagNode) obj);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tagNodeList;
    }

    public static Object getObjByXpath(TagNode tagNode, String xpathStr) {
        Object obj = "";
        try {
            Object[] objArray = tagNode.evaluateXPath(xpathStr);
            if (null != objArray && objArray.length > 0) {
                return objArray[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static List<Object> getObjListByXpath(TagNode tagNode, String xpathStr) {
        List<Object> objectList = new ArrayList<>();
        Object obj = "";
        try {
            Object[] objArray = tagNode.evaluateXPath(xpathStr);
            if (null != objArray && objArray.length > 0) {
                for (int i = 0; i < objArray.length; i++) {
                    objectList.add(objArray[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectList;
    }

}
