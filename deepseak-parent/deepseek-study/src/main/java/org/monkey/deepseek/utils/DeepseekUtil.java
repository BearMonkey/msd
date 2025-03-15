package org.monkey.deepseek.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import org.monkey.deepseek.dto.DeepseekError;
import org.monkey.deepseek.dto.DeepseekReq;
import org.monkey.deepseek.dto.DeepseekResp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

import static java.net.Proxy.Type.HTTP;

/**
 * DeepseekUtil
 *
 * @author cc
 * @since 2025/2/8 13:55
 */
public class DeepseekUtil {
    public static final String BASE_URL = "https://api.deepseek.com";
    public static final String DEEPSEEK_API = "/chat/completions";
    public static final String OPEN_API = "";
    public static final String API_KEY = "sk-be07fa6ee29f4db7807b24dff10b7b06";

    public static void deepseek(DeepseekReq deepseekReq) {
        String url = BASE_URL + DEEPSEEK_API;
        System.out.println("请求url:" + url);
        String body = JSONObject.toJSONString(deepseekReq);
        System.out.println("请求参数:" + body);
        long t0 = System.currentTimeMillis();
        try (HttpResponse httpResponse = HttpUtil.createGet(url)
                .contentType("application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .body(body)
                .execute()) {
            String result = httpResponse.body();
            System.out.println("响应结果:" + result);
            System.out.println("响应耗时:" + (System.currentTimeMillis() - t0) + "ms");
            DeepseekResp deepseekResp = JSONObject.parseObject(result, DeepseekResp.class);
            if (deepseekResp == null) {
                System.out.println("响应结果转换失败");
                return;
            }
            DeepseekError error = deepseekResp.getError();
            if (error != null) {
                System.out.println("Deepseek返回失败:" + error.getMessage());
                return;
            }
            System.out.println("Deepseek返回成功");
//            System.out.println("===================收到响应==================");
//            System.out.println(result);
//            System.out.println("===========================================");

        }
    }
    public static void main(String[] args) {
        // XML 内容（注意 Java 字符串转义）
        String xmlContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<request>"
                + "<id>20250210124800$nrblfbaurg</id>"
                + "<order_code>7081202502101231489117950031</order_code>"
                + "<carriers_name>ZTO</carriers_name>"
                + "<transport_no>73544353312745</transport_no>"
                + "<ordermark>2</ordermark>"
                + "<order_type>201</order_type>"
                + "<transfercentercode>深圳转</transfercentercode>"
                + "<bignote>110- C8 BA4</bignote>"
                + "</request>";

        // 构建请求
        HttpRequest request = HttpRequest.post("https://napi.tudgo.com/thirdparty/zhongyuan/notify")
                .header("Content-Type", "multipart/form-data")
                .setHttpProxy("127.0.0.1", 8888)
                .header("accept", "*/*")
                .form("sign_type", "MD5")
                .form("input_charset", "UTF-8")
                .form("content", xmlContent)
                .form("sign", "asdf")
                .form("notify_type", "COSCO_STOCK_ORDER_CARRIERS")
//				.contentType("multipart/form-data");
//				.contentType("application/x-www-form-urlencoded")
                ;

        // 发送请求并获取响应
        HttpResponse response = request.execute();

        // 输出结果
        System.out.println("HTTP Status: " + response.getStatus());
        System.out.println("Response Body: " + response.body());
    }
}
