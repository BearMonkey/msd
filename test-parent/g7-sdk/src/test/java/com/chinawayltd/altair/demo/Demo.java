/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.chinawayltd.altair.demo;

import com.chinawayltd.altair.demo.constant.Constants;
import com.chinawayltd.altair.demo.constant.ContentType;
import com.chinawayltd.altair.demo.constant.HttpHeader;
import com.chinawayltd.altair.demo.constant.HttpSchema;
import com.chinawayltd.altair.demo.enums.Method;
import com.chinawayltd.altair.demo.util.MessageDigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用示例
 * 请替换APP_KEY,SECRET_KEY,BaseURL,CUSTOM_HEADERS_TO_SIGN_PREFIX为真实配置
 */
public class Demo {
    private static final Logger LOG = LoggerFactory
            .getLogger(Demo.class);


    //APP KEY
    private final static String ACCESS_ID = "123";
    // APP密钥
    private final static String SECRET_KEY = "123";
    //API域名
    private final static String BaseURL = "demo.dsp.chinawayltd.com/altair/rest/";

//    private final static String BaseURL = "openapi.huoyunren.com";

    /**
     * HTTP GET
     *
     * @throws Exception
     */
    @Test
    public void get_history_location() throws Exception {
        //请求path
        String path = "/v1/device/truck/history_location";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());


        Request request = new Request(Method.GET, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);


        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("plate_num", "部A11110");
        querys.put("from", "2017-11-25 00:00:00");
        querys.put("to", "2017-11-30 00:00:00");
        request.setQueries(querys);

        //调用服务端
        Response response = Client.execute(request);

//        System.out.println(response.getBody());
        LOG.info(response.getBody());
    }

    /**
     * HTTP GET
     *
     * @throws Exception
     */
    @Test
    public void get_current_location() throws Exception {
        //请求path
        String path = "/v1/device/truck/current_location";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
//        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
//        headers.put("X-G7-Ca-a-header1", "header1Value");
//        headers.put("X-G7-Ca-b-header2", "header2Value");
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());


        Request request = new Request(Method.GET, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);


        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("plate_num", "部A11110");
//        querys.put("from", "2017-11-25 00:00:00");
//        querys.put("to", "2017-11-30 00:00:00");
        request.setQueries(querys);

        //调用服务端
        Response response = Client.execute(request);

//        System.out.println(response.getBody());
        LOG.info(response.getBody());
    }

    /**
     * HTTP POST 字符串
     *
     * @throws Exception
     */
    @Test
    public void postJson() throws Exception {
        //请求path
        String path = "/v1/base/current/full_currents";
        //Body内容
        String body = "{\n" +
                "\"carnum\": \"川A12345\",\n" +
                "\"longitude\": \"104.07134\",\n" +
                "\"latitude\": \"30.54013\"\n" +
                "}";

        System.out.println(body);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);
//        headers.put("d-header1", "header1Value");
//        headers.put("X-G7-Ca-a-header1", "header1Value");
//        headers.put("X-G7-Ca-b-header2", "header2Value");
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());


        Request request = new Request(Method.POST_JSON, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);


        //请求的query
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("a-query1", "query1Value");
//        querys.put("b-query2", "query2Value");
//        request.setQueries(querys);

        request.setJsonStrBody(body);

        //调用服务端
        Response response = Client.execute(request);

//        System.out.println(response.getBody());
        LOG.info(response.getBody());
    }


    /**
     * HTTP DELETE
     *
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        //请求path
        String path = "/v1/base/current/full_currents";

        String body = "{\n" +
                "  \"carnum\": \"川A12345\",\n" +
                "  \"longitude\": \"104.07134\",\n" +
                "  \"latitude\": \"30.54013\"\n" +
                "}";

        Map<String, String> headers = new HashMap<String, String>();
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);
        headers.put("d-header1", "header1Value");
        headers.put("X-G7-Ca-a-header1", "header1Value");
        headers.put("X-G7-Ca-b-header2", "header2Value");
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());
        Request request = new Request(Method.DELETE, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQueries(querys);
        request.setJsonStrBody(body);

        //调用服务端
        Response response = Client.execute(request);

//        System.out.println(response.getBody());
        LOG.info(response.getBody());
    }


}
