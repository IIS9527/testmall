package org.linlinjava.litemall.util.service;


import cn.hutool.extra.ssh.JschUtil;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;

@Service
public class DyTaskService {


    public boolean taskPush(String beginTimeFrom, String duration, String number, String personAddress, Integer integral)  {

        try {
        String jsonUtil = JSONUtil.createObj().set("token", "asjdfbajskdfnkwe234123kljdfnkljsdgn2kwfdlknasdln").set("number", number).set("beginTimeFrom", beginTimeFrom).set("duration", duration).set("personAddress", personAddress).set("integral", integral).toString();
           System.out.println("ssssssss"+jsonUtil);
            Connection connection =Jsoup.connect("http://localhost:8999/litemall/setTask")
                   .header("Content-Type", "application/json")
                    .method(Connection.Method.POST)
                   .ignoreContentType(true);
          Connection.Response document =connection.requestBody(jsonUtil).execute();
           JSON  json =JSONUtil.parse(document.body());
           System.out.println(json);
           if (json.getByPath("code").toString().equals("200")){
               return true;
           }
           return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 创建HttpClient实例
//        try {
//            HttpClient httpClient = HttpClientBuilder.create().build();
//
//            // 创建POST请求
//            HttpPost request = new HttpPost("http://localhost:8999/litemall/setTask");
//
//            // 添加请求头
//            request.addHeader("Content-Type", "application/json");
//
//            // 添加请求体（JSON数据）
////            String jsonBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
//            request.setEntity(new StringEntity(JSONUtil.createObj().set("token", "asjdfbajskdfnkwe234123kljdfnkljsdgn2kwfdlknasdln").set("number", number).set("beginTimeFrom", beginTimeFrom).set("duration", duration).set("personAddress", personAddress).set("integral", integral).toString()));
//
//            // 发送请求并获取响应
//            HttpResponse response = httpClient.execute(request);
//
//            // 读取响应内容
//            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            String line;
//            StringBuilder result = new StringBuilder();
//            while ((line = reader.readLine()) != null) {
//                result.append(line);
//            }
//            JSON json =JSONUtil.parse(result.toString());
//            System.out.println(result);
//            System.out.println(json.getByPath("code").toString());
//            if (json.getByPath("code").toString().equals("200")){
//                return true;
//            }
//            System.out.println("Response: " + result.toString());
//        } catch (Exception e) {
//            // 打印响应内容
//            return false;
//        }
//        return false;
    }


    public Integer getDevices(){

        try {

            Document document =Jsoup.connect("http://localhost:8999/litemall/devices")
                    .ignoreContentType(true)
                    .get();
            System.out.println(document.body().text());
            JSON  json =JSONUtil.parse(document.body().text());
            if (json.getByPath("code").toString().equals("200")){
                return Integer.parseInt( json.getByPath("data").toString());
            }
            return 0;
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0;
        }


    }




}
