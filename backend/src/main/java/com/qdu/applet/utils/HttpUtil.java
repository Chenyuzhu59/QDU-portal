package com.qdu.applet.utils;

import com.qdu.applet.service.JwService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

public class HttpUtil {
    public static Document getDocument(String cookie, String url) {
        Connection connection = Jsoup.connect(url).
                header("cookie", cookie).
                method(Connection.Method.GET);
        Document document = sendDocument(connection);
        return document;
    }

    public static Document getDocument(String url) {
        Connection connection = Jsoup.connect(url).
                method(Connection.Method.GET);
        Document document = sendDocument(connection);
        return document;
    }

    public static Document postDocument(String cookie, String url, Map<String, String> data) {
        Connection connection = Jsoup.connect(url).
                header("cookie", cookie).
                method(Connection.Method.POST);

        //遍历添加参数
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                //添加参数
                connection.data(entry.getKey(), entry.getValue());
            }
        }

        Document document = sendDocument(connection);
        return document;
    }

    private static Document sendDocument(Connection connection) {
        Document document = null;
        try {
            Connection.Response response = connection.execute();
            document = response.parse();
        } catch (SocketTimeoutException e) {
            //请求失败，重复一次
            final Log logger = LogFactory.getLog(JwService.class);
            logger.info("请求失败，重复一次");
            Connection.Response response = connection.execute();
            document = response.parse();
        } catch (HttpStatusException e) {
            final Log logger = LogFactory.getLog(JwService.class);
            logger.info(e.getStatusCode() + " url:" + e.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return document;
        }
    }
}
