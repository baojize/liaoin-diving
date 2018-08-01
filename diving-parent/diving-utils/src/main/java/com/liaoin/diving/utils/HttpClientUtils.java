package com.liaoin.diving.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    public static String doPost(String url, Map<String, String> map) throws IOException {
        // 1.创建httpClient对象
        HttpClient httpClient = HttpClients.createDefault();
        // 2.创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 3.封装请求参数
        List<NameValuePair> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // 4.设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
        // 5.设置请求头信息
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Java");
        // 6.执行请求操作，获取响应对象
        HttpResponse response = httpClient.execute(httpPost);
        // 7.获取结果对象
        HttpEntity entity = response.getEntity();
        // 8.返回结果对象的字符串
        return EntityUtils.toString(entity, "UTF-8");
    }

    public static String doPost(String url, String json) throws IOException {
        // 1.创建httpClient对象
        HttpClient httpClient = HttpClients.createDefault();
        // 2.创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 4.设置参数到请求对象中
        httpPost.setEntity(new StringEntity(json));
        // 5.设置请求头信息
        httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
        httpPost.setHeader("User-Agent", "Java");
        // 6.执行请求操作，获取响应对象
        HttpResponse response = httpClient.execute(httpPost);
        // 7.获取结果对象
        HttpEntity entity = response.getEntity();
        // 8.返回结果对象的字符串
        return EntityUtils.toString(entity, "UTF-8");
    }

    public static String doGet(String url) throws IOException {
        // 1.创建httpClient对象
        HttpClient httpClient = HttpClients.createDefault();
        // 2.创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        // 3.设置请求头信息
        httpGet.setHeader("User-Agent", "Java");
        // 4.执行请求操作，获取响应对象
        HttpResponse response = httpClient.execute(httpGet);
        // 5.获取结果对象
        HttpEntity entity = response.getEntity();
        // 6.返回结果对象的字符串
        return EntityUtils.toString(entity, "UTF-8");
    }
}
