package org.monkey.msd.test.g7sdk;

import com.chinawayltd.altair.demo.Client;
import com.chinawayltd.altair.demo.Request;
import com.chinawayltd.altair.demo.Response;
import com.chinawayltd.altair.demo.constant.Constants;
import com.chinawayltd.altair.demo.constant.HttpHeader;
import com.chinawayltd.altair.demo.constant.HttpSchema;
import com.chinawayltd.altair.demo.enums.Method;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * G7sdkDemo
 *
 * @author cc
 * @since 2025/3/27 17:34
 */
@Slf4j
public class G7sdkDemo {

    //APP KEY
    private final static String ACCESS_ID = "123";
    // APP密钥
    private final static String SECRET_KEY = "123";
    //API域名
    private final static String BaseURL = "demo.dsp.chinawayltd.com/altair/rest/";

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
        log.info(response.getBody());
    }

    public static void main(String[] args) throws Exception {
        G7sdkDemo demo = new G7sdkDemo();
        demo.get_history_location();
    }
}
