package com.transing.mcss.util;

import com.transing.mcss.integration.bo.WeixinBrandBO;
import com.transing.mcss.integration.bo.WeixinCommentBO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.formula.functions.T;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import javax.swing.text.html.HTML;
import java.io.*;
import java.util.*;


public class XpathUtil {

    public static HtmlCleaner htmlCleaner = new HtmlCleaner();

    public static List<Date> getWeixinPublishTime(String htmlContent) {
        List<Date> dateList=new ArrayList<>();
        TagNode tagNode = htmlCleaner.clean(htmlContent);
        List<Object> publishDateObj = getObjListByXpath(tagNode, "//*[@class='weui_media_extra_info']/text()");
        for(Object obj:publishDateObj){
            String publishDate = obj.toString().trim();
            dateList.add(DateUtil.parseDate(publishDate));
        }
        return dateList;
    }

    public static void main(String[] args) {
        try{
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

            Map weixinBrandBO = getWeixinBrandByHtml(sb.toString(),weixinBrand);
            System.out.println(weixinBrandBO);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @param htmlContent
     * @param weixinBrandBO  需要 提前set url,taskId,peojectId参数
     * @return 如果weixinBrandBO参数为空 则返回null
     */
    public static Map<String,Object> getWeixinBrandByHtml(String htmlContent, WeixinBrandBO weixinBrandBO){
        if(null == weixinBrandBO){
            return null;
        }

        String url = weixinBrandBO.getUrl();
        long taskId = weixinBrandBO.getTaskId();
        long pid = weixinBrandBO.getPid();

        TagNode tagNode = htmlCleaner.clean(htmlContent);

        Object titleObj = getObjByXpath(tagNode,"//h2[@id='activity-name']/text()");

        Object publishDateObj = getObjByXpath(tagNode,"//em[@id='post-date']/text()");
        Object authorObj = getObjByXpath(tagNode,"//a[@id='post-user']/text()");
        Object contentObj = getObjByXpath(tagNode,"//div[@id='js_content']//text()");
        Object imgUrlObj = getObjByXpath(tagNode,"//div[@id='js_content']//img/@data-src");
        Object pageViewsObj = getObjByXpath(tagNode,"//span[@id='readNum3']/text()");
        Object praiseNumObj = getObjByXpath(tagNode,"//span[@id='like3']/text()");

        String title = titleObj.toString().trim();
        String publishDate = publishDateObj.toString().trim();
        String authar = authorObj.toString().trim();
        String content = contentObj.toString().trim();
        String imgUrls = imgUrlObj.toString().trim();
        String pageViews = !"".equals(pageViewsObj)?pageViewsObj.toString().trim():"0";
        String pariseNum = !"".equals(praiseNumObj)?praiseNumObj.toString().trim():"0";
        if(pariseNum.equals("赞")){
            pariseNum="0";
        }

        weixinBrandBO.setAuthor(authar);
        weixinBrandBO.setContent(content);
        weixinBrandBO.setTitle(title);
        weixinBrandBO.setPublishTime(publishDate);
        weixinBrandBO.setImgUrl(imgUrls);
        weixinBrandBO.setPageViews(pageViews);
        weixinBrandBO.setPraiseNum(pariseNum);

        //评论
        List<WeixinCommentBO> weixinCommentBOList = new ArrayList<>();
        List<TagNode> liTagNodeList = getTagNodeListByXpathStr(tagNode,"//ul[@id='js_cmt_list']/li");
        for (TagNode liTagNode:liTagNodeList) {
            WeixinCommentBO weixinCommentBO = new WeixinCommentBO();
            weixinCommentBO.setUrl(url);
            weixinCommentBO.setTaskId(taskId);
            weixinCommentBO.setPid(pid);

            String nickName = getObjByXpath(liTagNode,"//div[@class='user_info/strong[@class='nickname']/text()']").toString().trim();
            String avatar = getObjByXpath(liTagNode,"//div[@class='user_info/img[@class='avatar']/@src']").toString().trim();
            String commentTime = getObjByXpath(liTagNode,"//p[@class='discuss_extra_info']/text()").toString().trim();
            String conent = getObjByXpath(liTagNode,"//div[@class='discuss_message']/div[@class='discuss_message_content']/text()").toString().trim();
            String praiseNumStr = getObjByXpath(liTagNode,"//div[@class='discuss_opr']/span/span[@class='praise_num']/text()").toString().trim();
            weixinCommentBO.setPraiseNum(praiseNumStr);

            weixinCommentBO.setCommenter(nickName);
            weixinCommentBO.setContent(conent);
            weixinCommentBO.setCommentTime(commentTime);
            weixinCommentBO.setCommentAvatar(avatar);

            List<TagNode> replyTagNodeList = getTagNodeListByXpathStr(liTagNode,"//div[@class='reply_result']");
            if(replyTagNodeList.size() > 0){
                for (TagNode replyTagNode:replyTagNodeList) {
                    String replyConent = getObjByXpath(replyTagNode,"//div[@class='discuss_message']/div[@class='discuss_message_content']/text()").toString().trim();
                    String replyTime = getObjByXpath(replyTagNode,"//p[@class='discuss_extra_info']/text()").toString().trim();

                    if(replyTagNodeList.size() > 1){
                        WeixinCommentBO weixinCommentBO2 = new WeixinCommentBO();
                        weixinCommentBO2.setUrl(url);
                        weixinCommentBO2.setTaskId(taskId);
                        weixinCommentBO2.setPid(pid);

                        weixinCommentBO2.setPraiseNum(praiseNumStr);
                        weixinCommentBO2.setCommenter(nickName);
                        weixinCommentBO2.setContent(conent);
                        weixinCommentBO2.setCommentTime(commentTime);
                        weixinCommentBO2.setCommentAvatar(avatar);

                        weixinCommentBO2.setReplyConent(replyConent);
                        weixinCommentBO2.setReplyTime(replyTime);

                        weixinCommentBOList.add(weixinCommentBO2);
                    }else if(replyTagNodeList.size() == 1){
                        weixinCommentBO.setReplyConent(replyConent);
                        weixinCommentBO.setReplyTime(replyTime);

                        weixinCommentBOList.add(weixinCommentBO);
                    }
                }
            }else{
                weixinCommentBO.setReplyTime("");
                weixinCommentBO.setReplyConent("");
                weixinCommentBOList.add(weixinCommentBO);
            }
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("weixinBrandBO",weixinBrandBO);
        returnMap.put("weixinCommentBOList",weixinCommentBOList);

        return returnMap;
    }

    public static List<TagNode> getTagNodeListByXpathStr(TagNode tagNode,String xpathStr){
        List<TagNode> tagNodeList = new ArrayList<>();
        try {
            Object [] objArray = tagNode.evaluateXPath(xpathStr);
            if(null != objArray && objArray.length > 0){
                for (Object obj :objArray) {
                    if(obj instanceof TagNode){
                        tagNodeList.add((TagNode) obj);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return tagNodeList;
    }

    public static Object getObjByXpath(TagNode tagNode, String xpathStr){
        Object obj = "";
        try {
            Object [] objArray = tagNode.evaluateXPath(xpathStr);
            if(null != objArray && objArray.length > 0) {
                return objArray[0];
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    public static List<Object> getObjListByXpath(TagNode tagNode, String xpathStr){
        List<Object> objectList=new ArrayList<>();
        Object obj = "";
        try {
            Object [] objArray = tagNode.evaluateXPath(xpathStr);
            if(null != objArray && objArray.length > 0) {
                for (int i = 0; i < objArray.length; i++) {
                    objectList.add(objArray[i]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objectList;
    }

}
