package org.linlinjava.litemall.util.service;


import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DyTaskService {

    public boolean taskPush(String beginTimeFrom,String duration,String number,String roomId,String videoName,String roomAddress,Integer integral ){

        try {

           Document document =Jsoup.connect("http://localhost:8999/litemall/setTask")
                   .data("number",number)
                   .data("beginTimeFrom",beginTimeFrom)
                   .data( "duration",duration)
                   .data("roomId",roomId)
                   .data("videoName",videoName)
                   .data("roomAddress",roomAddress)
                   .data("integral",integral.toString())
                   .ignoreContentType(true).post();

           System.out.println(document.body().text());

           JSON  json =JSONUtil.parse(document.body().text());

           if (json.getByPath("code").toString().equals("200")){
               return true;
           }
           return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    public Integer  getResidualDevices(){

        try {

            Document document =Jsoup.connect("http://localhost:8999/litemall/getRemainderDeviceNumber")
                    .ignoreContentType(true)
                    .get();

            System.out.println(document.body().text());

            JSON  json =JSONUtil.parse(document.body().text());

            if (json.getByPath("code").toString().equals("200")){
                return Integer.parseInt( json.getByPath("data").toString());
            }
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }




}
