package com.transing.mcss4dpm.util;

import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.jeeframework.util.validate.Validate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包: com.transing.crawl.util
 * 源文件:XpathUtil.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年07月03日
 */
public class ParseUtil {


    public static String getTagNodeHtmlString(Object tag2) {
        StringBuilder sb = new StringBuilder();

        if (tag2 instanceof TagNode) {
            TagNode tag = (TagNode) tag2;
            sb.append("<" + tag.getName());
            Map<String, String> attrs = tag.getAttributes();

            if (attrs != null && attrs.size() > 0) {
                for (String key : attrs.keySet()) {
                    sb.append(" ").append(key).append("=\"").append(attrs.get(key)).append("\" ");
                }
            }

            sb.append(">");

            List child = tag.getChildren();

            for (Object c : child) {
                sb.append(getTagNodeHtmlString(c));
            }

            sb.append("</" + tag.getName() + ">");

        } else {
            sb.append(tag2.toString());
        }

        return sb.toString();
    }


    public static TagNode getTagNode(String html) {
        String content = html;

        content = content.replaceAll("<script [\\s|\\S]*? </scritp>", "");

        if (!content.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
            content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + content;
        }

        try {
            return new HtmlCleaner().clean(content);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 正则处理
     *
     * @param regx
     * @param html
     * @return
     */
    public static List regexMatcher(String regx, String html) {
        List ret = new ArrayList<>();
        Integer curPattern = Pattern.DOTALL;
        Pattern p = Pattern.compile(regx, curPattern);

        Matcher m = p.matcher(html);// 开始编译
        while (m.find()) {
            String retContent = m.group(1);
            ret.add(retContent);
        }
        return ret;
    }

    /**
     * json结构数据的读取
     *
     * @param content
     * @return
     */
    public static void JSONReadArray(String content, String[] keys, int layer, List resultArray) throws Exception {
        if (Validate.isEmpty(content))
            return;

        if (layer < keys.length) {
            try {
                JSONObject contentJSON = JSONObject.fromObject(content);
                content = contentJSON.get(keys[layer]).toString();
                JSONReadArray(content, keys, layer + 1, resultArray);
            } catch (Exception e) {
                JSONArray jsonArray = JSONArray.fromObject(content);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONReadArray(jsonArray.getString(i), keys, layer + 1, resultArray);
                }
            }
        } else {
            try {
                JSONArray contentArray = JSONArray.fromObject(content);
                for (int i = 0; i < contentArray.size(); i++) {
                    resultArray.add(contentArray.get(i));
                }
            } catch (Exception e) {
                try {
                    JSONObject jsonObject = JSONObject.fromObject(content);
                    resultArray.add(jsonObject.getString(keys[layer - 1]));
                } catch (Exception ex) {
                    resultArray.add(content);
                }
            }
        }
    }


    /**
     * 判定页面中是否包含exception 的mark
     *
     * @param exceptionMark
     * @param html
     * @return
     */
    public static String checkHtmlException(List<String> exceptionMark, String html) {
        if (exceptionMark != null && exceptionMark.size() > 0) {
            for (String mark : exceptionMark) {
                LoggerUtil.debugTrace("mark=" + mark);
                if (html.indexOf(mark) > 0) {
                    LoggerUtil.debugTrace("mark==" + mark);
                    return mark;
                }
            }
        }
        return null;
    }

    /**
     * 字段的解析
     *
     * @param content
     * @param parseExpress
     * @return
     * @throws Exception
     */
    public static List parseValue(String content,
                                  String parseExpress, String analysisType, JSONObject fixedJson)
            throws Exception {

        List parseResult = new ArrayList<>();

        if (analysisType.equalsIgnoreCase("xpath")) {
            //xpath
            String[] xpathResultArray = xpathParse(content, parseExpress);
            if (xpathResultArray != null && xpathResultArray.length > 0) {
                parseResult = Arrays.asList(xpathResultArray);
                return parseResult;
            } else {
                return null;
            }
        } else if (analysisType.equalsIgnoreCase("regex") || ("正则表达式").equals(analysisType)) {
            //regex
            String[] regexResultArray = listtoArray(ParseUtil
                    .regexMatcher(parseExpress, content));
            if (regexResultArray != null && regexResultArray.length > 0) {
                parseResult = Arrays.asList(regexResultArray);
                return parseResult;
            } else {
                return null;
            }
        } else if (analysisType.equalsIgnoreCase("json")) {
            //json
            String[] expressions = parseExpress.split("\\.");
            if (Validate.isEmpty(parseExpress)) {
                expressions = new String[0];
            } else {
                if (content.indexOf("{") > -1)
                    content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
            }
            List resultList = new ArrayList();
            ParseUtil.JSONReadArray(content, expressions, 0, resultList);
            String[] resultArray = listtoArray(resultList);
            if (resultArray != null && resultArray.length > 0) {
                parseResult = Arrays.asList(resultArray);
                return parseResult;
            } else {
                return null;
            }
        } else {
            //固定值

            if (fixedJson != null && fixedJson.size() > 0) {
                Iterator<String> keys = fixedJson.keySet().iterator();
                StringBuffer jsStr = new StringBuffer("function processor(){");
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = fixedJson.getString(key);
                    value = contentScape(value);
                    jsStr.append("var " + key + "=\"" + value + "\"; ");
                }
                if (parseExpress.indexOf("return") < 0)
                    parseExpress = "return " + parseExpress;
                if (!parseExpress.endsWith(";"))
                    parseExpress += ";";
                jsStr.append(parseExpress);
                jsStr.append("}");
                String jsResult = jsParse(jsStr.toString(), "processor");
                List<String> list = new ArrayList<String>();
                list.add(jsResult);
                return list;
            } else {
                return null;
            }
        }
    }

    /**
     * Xpath 解析
     *
     * @param xpath 表达式
     * @param xpath 被解析内容
     */
    private static String[] xpathParse(String content, String xpath) throws Exception {
        TagNode doc = ParseUtil.getTagNode(content);
        Object[] nodeList = doc.evaluateXPath(xpath);

        if (nodeList == null || nodeList.length < 1) {
            return null;
        }

        String[] ret = new String[nodeList.length];

        for (int i = 0; i < nodeList.length; i++) {
            ret[i] = ParseUtil.getTagNodeHtmlString(nodeList[i]);
        }

        return ret;
    }

    /**
     * 将list 转成成string[]
     *
     * @param list
     * @return
     */
    private static String[] listtoArray(List list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        }

        String[] ret = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            ret[i] = String.valueOf(list.get(i));
        }

        return ret;
    }

    /**
     * js解析
     *
     * @param processorValue
     * @param methodName
     * @return
     */
    public static String jsParse(String processorValue, String methodName) {
        Context cx = Context.enter();
        Scriptable scope = cx.initStandardObjects();
        LoggerUtil.infoTrace("parseUtils", processorValue);
        try {
            Script script = cx
                    .compileString(processorValue, "js", 1, null);
            script.exec(cx, scope);
            Function func = (Function) scope.get(methodName, scope);
            Object suTmp = func
                    .call(Context.getCurrentContext(), scope, func,
                            new Object[0]);
            scope = Context.toObject(suTmp, scope);
            Object[] s = scope.getIds();
            if (s.length > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < s.length; i++) {
                    stringBuffer
                            .append(scope.get(s[i].toString(), scope)
                                    .toString());
                    stringBuffer.append(";");
                }
                return stringBuffer.toString()
                        .substring(0,
                                stringBuffer.toString().length() - 1);
            } else {
                return scope.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Context.exit();
        }
    }

    /**
     * 获取列表中所有的标签名称
     *
     * @param html
     * @return
     */
    public static List<String> getALLTagNodeName(String html) {
        List<String> nodeNames = new ArrayList<String>();

        TagNode tagNode = getTagNode(html);
        if (tagNode == null)
            return null;
        TagNode[] tagNodes = tagNode.getAllElements(true);

        for (TagNode node : tagNodes) {
            String tagName = node.getName();
            if (!nodeNames.contains(tagName))
                nodeNames.add(tagName);
        }
        return nodeNames;
    }

    /**
     * 转义
     * 将文本中的&lt;等转换成html标签
     *
     * @param content
     * @return
     */
    public static String getContentChangeHtmlElem(String content) {
        return StringEscapeUtils.unescapeHtml3(content);
    }

    /**
     * 内容编码
     * 将文本中的“等转换成字符串
     *
     * @param content
     * @return
     */
    public static String contentScape(String content) {
        content = content.replaceAll("([\r\n\t]+)", "");
        return StringEscapeUtils.escapeHtml4(content);
    }


    public static void main(String[] args) {
        String html = "&amp;";
        String content = contentScape(html);
        System.out.println(content);
    }
}
