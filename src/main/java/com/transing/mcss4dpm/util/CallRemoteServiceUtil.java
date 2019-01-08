package com.transing.mcss4dpm.util;

import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.jeeframework.util.httpclient.HttpClientHelper;
import com.jeeframework.util.httpclient.HttpResponse;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class CallRemoteServiceUtil {
    public static final int RESPONSE_CODE_SUCCESS = 0;//返回成功

    /**
     * 远程服务调用方法
     *
     * @param serviceURL 服务访问URL
     * @param method     访问方法  get /   post
     * @param postData   请求参数
     * @return
     */
    public static Object callRemoteService(String loggerName, String serviceURL, String method, Map<String, String> postData) {
        HttpClientHelper httpClientHelper = new HttpClientHelper();

        String getTermListStr = "{}";
        try {
            HttpResponse getTermListResponse = null;
            LoggerUtil.debugTrace(loggerName,"======url = "+ serviceURL +" param = "+ JSONObject.fromObject(postData));
            if (method.equalsIgnoreCase("get")) {
                getTermListResponse = httpClientHelper.doGet(serviceURL,
                        "utf-8", "utf-8", null,
                        null);
            } else {
                getTermListResponse = httpClientHelper.doPost(serviceURL, postData, "utf-8", "utf-8", null, null);
            }
            getTermListStr = getTermListResponse.getContent();
        } catch (HttpException e) {
            LoggerUtil.errorTrace(loggerName, e);
        } catch (IOException e) {
            LoggerUtil.errorTrace(loggerName, e);
        }

        try {
            net.sf.json.JSONObject getTermListJsonObject = net.sf.json.JSONObject.fromObject(getTermListStr);
            int code = getTermListJsonObject.getInt("code");
            if (code == RESPONSE_CODE_SUCCESS) {

                Object termListArrayObject = getTermListJsonObject.get("data");
                return termListArrayObject;
            }


        } catch (JSONException e) {
            System.out.println("XXXX: CallRemoteServiceUtil error : 访问远程接口" + serviceURL + "出错，跳出执行");
            LoggerUtil.errorTrace(loggerName, "访问远程接口出错，跳出执行。", e);
            return null;
        }
        return null;
    }

    public static void upload(File file, String serviceURL, Map<String, String> postData) {
        PostMethod filePost = new PostMethod(serviceURL);
        HttpClient client = new HttpClient();

        try {
            // 通过以下方法可以模拟页面参数提交
            for (String key : postData.keySet()) {
                filePost.setParameter(key, postData.get(key));
            }

            Part[] parts = {new FilePart(file.getName(), file)};
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                System.out.println("上传成功");
            } else {
                System.out.println("上传失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            filePost.releaseConnection();
        }
    }

    public static void upload2(File file, String serviceURL, Map<String, String> postData) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();

            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(serviceURL);

            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(file);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                    // 相当于<input type="file" name="file"/>
                    .addPart("file", bin);
            for (String key : postData.keySet()) {
                StringBody stringBody = new StringBody(postData.get(key), ContentType.create(
                        "text/plain", Consts.UTF_8));
                multipartEntityBuilder.addPart(key, stringBody);
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();

            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);

            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }

            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
