package org.linlinjava.litemall.util.service;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.DefaultJavaScriptErrorListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Component
public class DyAddressService {

    private final Log logger = LogFactory.getLog(DyAddressService.class);

    public String getRoomId( String address){
        try  {

            HtmlPage page =getTimeByHtmlUnit(address);

            String reg = "(?<=reflow/).*?(?=\\?u_code)";
            String temp = "aAabcBc";
            Pattern pattern = Pattern.compile(reg);

            Matcher matcher = pattern.matcher(page.getBaseURI());
            if( matcher.find() ){
                System.out.println(matcher.group());
                return  matcher.group();
            }
            else {

                List<HtmlElement> p =  page.getBody().getElementsByAttribute("a","class","B3AsdZT9");


                reg = "(?<=room_id=).*?(?=&)";
                pattern = Pattern.compile(reg);
//                 matcher = pattern.matcher(roomid);
                if (matcher.find()){
                    System.out.println("ssssssssss"+matcher.group());
                    return  matcher.group();
                }
            }

            return null;

        }
        catch ( Exception e){
            return null;
        }

    }

    //    }
    public String getVideoName( String address){
        try  {

            HtmlPage page =getTimeByHtmlUnit(address);

            if (page == null) return null;


            UrlBuilder builder = UrlBuilder.ofHttp(page.getBaseURI(), CharsetUtil.CHARSET_UTF_8);


            String sec_uid = null;
            if (StrUtil.isEmptyIfStr( builder.getQuery().get("sec_user_id"))){

                sec_uid =  builder.getPathStr().split("/")[builder.getPathStr().split("/").length-1];
            }

            else {
                sec_uid =  builder.getQuery().get("sec_user_id").toString();
            }

            String url= "https://www.iesdouyin.com/web/api/v2/user/info/?sec_uid="+sec_uid+"&from_ssr=1";

            WebClient webClient = new WebClient(BrowserVersion.getDefault());

            JSONObject jsonObject =  JSONUtil.parseObj(webClient.getPage(url).getWebResponse().getContentAsString(StandardCharsets.UTF_8));



            return JSONUtil.parseObj (jsonObject.get("user_info")).get("nickname").toString();
        }

        catch ( Exception e){

            return null;
        }
    }

//    public String getXiGuaVideoName( String roomId) throws  Exception{
//        if (StringUtils.isBlank(roomId)) {
//            return null;
//        }
//
//
//        String url ="https://webcast-open.douyin.com/open/webcast/reflow/?webcast_app_id=247160&room_id=7360956915726748453";
//
//
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setActiveXNative(false);
//        webClient.getOptions().setRedirectEnabled(true);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setTimeout(6000);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//
//        // -----重点-----设置为我们自定义的错误处理类
//
////        webClient.setJavaScriptErrorListener(new MyJSErrorListener());
//
//        webClient.setJavaScriptTimeout(5000);
//        webClient.addRequestHeader("Content-Encoding", "br");
//        webClient.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-");
////        webClient.addRequestHeader("coockie","ttwid=1%7Cbw2fl4da1A6RPEptdVhTJb3-TjFVhpLlTu7yTA1rIDc%7C1711977532%7C4fe861baeb2a41e5a01ce502010f834d0d8717a80811faf9e773090235dd59ea; SEARCH_RESULT_LIST_TYPE=%22single%22; passport_csrf_token=5d6eda75a081ac61534e4336169cc2e3; passport_csrf_token_default=5d6eda75a081ac61534e4336169cc2e3; bd_ticket_guard_client_web_domain=2; s_v_web_id=verify_lv88q73c_C7QusO07_tEJ8_4Y1Q_9lPB_6HNBc07tI5ev; ttcid=5b818df11cbc48a5834b1a67a97ffe2c40; __live_version__=%221.1.1.9743%22; live_use_vvc=%22false%22; FORCE_LOGIN=%7B%22videoConsumedRemainSeconds%22%3A180%7D; home_can_add_dy_2_desktop=%220%22; stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1440%2C%5C%22screen_height%5C%22%3A900%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A36%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A10%2C%5C%22effective_type%5C%22%3A%5C%224g%5C%22%2C%5C%22round_trip_time%5C%22%3A50%7D%22; strategyABtestKey=%221713626500.546%22; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCSFRIYm1lNjB5d0ptd09Hb05WZDlLV1BEUWhLRGdySEtuZGVTUlc4aUJlL241ajNieHIxMjBHeW1VTitKQTZlQmVRV3NRQzhEdWlnc294TUlyKzF1aEE9IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoxfQ%3D%3D; download_guide=%221%2F20240420%2F0%22; webcast_local_quality=null; live_can_add_dy_2_desktop=%221%22; msToken=7RUQiDYR0Lc-QGUn7duBz03sXsm5-joVWVu5ooIF8HdStw_gWZubGzNS1npZWUjFEaXfF7tSv27kwQGF7zbtZ7CPzZglvOLgkwXSI5D-UZwTssUUMNb_3QF9h418EuM=; IsDouyinActive=false; msToken=ixMqD7gm_0oTL0MDcrBtgRI_uUP7iXundTZnSXPmNcojYyq89IHxAy_BR-pBoMeQS-zzPZ6V4n3g03gkw2IH8rZhIDan3hJI-wXbBuIZkkFKyP0SbC8U5adnh02ZXdAH; tt_scid=CFeFrc7KBd0mD2xp4.dQidMG0rt-wSBTPjaPcaj7dm3yyNjR4gimzbR0WugnCdWi1ee8");
//        webClient.addRequestHeader("Sec-Ch-Ua-Mobile", "?1");
//        webClient.addRequestHeader( "Sec-Ch-Ua-Platform","\"Android\"");
//        webClient.addRequestHeader("Sec-Ch-Ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"");
//      //  webClient.addRequestHeader("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36)");
//        webClient.addRequestHeader("Sec-Fetch-User","?1");
//
//
//       webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
//
//
////        HtmlPage tmpPage = HTMLParser.parseHtml(page.getWebResponse(), webClient.getCurrentWindow());
////
//        webClient.waitForBackgroundJavaScript(6000);
//        logger.info(webClient.getPage(url));
////
////             logger.info("sssssssssss{}"+page.getBaseURI());
////
////        logger.info(webClient.getPage(page.getBaseURI()));
////
////
////        logger.info(page);
//
//
//
//        return null;
//    }
//


    public HtmlPage getTimeByHtmlUnit(String url) throws IOException {

        if (StringUtils.isBlank(url)) {
            return null;
        }

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(6000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        // -----重点-----设置为我们自定义的错误处理类

//        webClient.setJavaScriptErrorListener(new MyJSErrorListener());

        webClient.setJavaScriptTimeout(5000);
        webClient.addRequestHeader("Content-Encoding", "br");
        webClient.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-");


        HtmlPage page =webClient.getPage(url);

        webClient.waitForBackgroundJavaScript(6000);

        return page;
    }


    /**
     * 忽略html unit打印的所有js加载报错信息
     */
    public static class MyJSErrorListener extends DefaultJavaScriptErrorListener {
        @Override
        public void scriptException(HtmlPage page, ScriptException scriptException) {
        }

        @Override
        public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
        }

        @Override
        public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {

        }

        @Override
        public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {

        }


    }

    /**
     *
     * @param personAddress
     * @return null || roomId
     */


    public  String getRoomIdByPersonAddress(String personAddress){

        Map<String,String> userAgent1 =  new HashMap<String,String>();
        userAgent1.put("Accept","charset=utf-8");
        userAgent1.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        userAgent1.put("Cache-Control","max-age=0");
        userAgent1.put("Content-Type","application/json; charset=utf-8");
        userAgent1.put("Sec-Ch-Ua","Microsoft Edge\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"");
        userAgent1.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.43");
        Document document1 = null;
        try {
            document1 = Jsoup.connect(personAddress).headers(userAgent1).followRedirects(false).timeout(2500).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (document1 == null){return null; }
        UrlBuilder urlBuilder =  UrlBuilder.of(document1.getElementsByTag("a").attr("href"));
        String sec_uid= urlBuilder.getQuery().get("sec_user_id").toString();

        Map<String,String> userAgent =  new HashMap<String,String>();
        userAgent.put("Host","weixin.qq.com");
        userAgent.put("cookie", "__ac_referer=__ac_blank; douyin.com; xg_device_score=7.658235294117647; ttwid=1%7CtXEFNxAkbbNuu5XAhosKIzchqO6roAyd5JVCGO3cIiM%7C1711472656%7C56dc31e5ed09a21c21b22658372e2486e2220f7caaac025a184571f5fa77519d; __live_version__=%221.1.1.9068%22; live_use_vvc=%22false%22; douyin.com; device_web_cpu_core=8; device_web_memory_size=8; architecture=amd64; dy_swidth=1536; dy_sheight=864; csrf_session_id=28804b8e2f2979cf68a9d2c8d1f06d04; FORCE_LOGIN=%7B%22videoConsumedRemainSeconds%22%3A180%2C%22isForcePopClose%22%3A1%7D; passport_csrf_token=9e04838afc9a6d6686ee4e3351d58d45; passport_csrf_token_default=9e04838afc9a6d6686ee4e3351d58d45; bd_ticket_guard_client_web_domain=2; s_v_web_id=verify_lu8mtuij_CPs2IGoc_exJT_4OJ4_BOem_kmnzipZOnwQC; d_ticket=b301872caf2b942c0a7b2ce6c0c92f817796d; n_mh=Mj5R-WMq0TuPIqQd7pXrRYzK0n6qjk7xUbCs_njXXTU; toutiao_sso_user=118294779a5513cb71f8d9a8ad7e1b8b; toutiao_sso_user_ss=118294779a5513cb71f8d9a8ad7e1b8b; passport_auth_status=cb5b9a5122c50d741242c12b48c0f00b%2C; passport_auth_status_ss=cb5b9a5122c50d741242c12b48c0f00b%2C; publish_badge_show_info=%220%2C0%2C0%2C1711473014470%22; _bd_ticket_crypt_doamin=2; _bd_ticket_crypt_cookie=1a61a4ee809350d56694280864d5cbdb; __security_server_data_status=1; xgplayer_device_id=34877104544; xgplayer_user_id=248144835877; volume_info=%7B%22isUserMute%22%3Afalse%2C%22isMute%22%3Atrue%2C%22volume%22%3A0.983%7D; pwa2=%220%7C0%7C3%7C0%22; webcast_local_quality=ld; download_guide=%223%2F20240327%2F1%22; webcast_leading_last_show_time=1711655627963; webcast_leading_total_show_times=2; live_can_add_dy_2_desktop=%221%22; FRIEND_NUMBER_RED_POINT_INFO=%22MS4wLjABAAAAmnOTojt3vkNLQwePh2OzWCa_sbj6NHS_j4atlUrtGqap9_IXEMGacraz1t7b5Bbc%2F1711728000000%2F1711655687067%2F0%2F0%22; stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1536%2C%5C%22screen_height%5C%22%3A864%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A8%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A10%2C%5C%22effective_type%5C%22%3A%5C%224g%5C%22%2C%5C%22round_trip_time%5C%22%3A50%7D%22; store-region=cn-fj; store-region-src=uid; msToken=z3DtPo2V1I43l2QYYbz5TRG-JWCsxnmqnYLGlfPyZMs2wEZL1TItlzynW9km97RXsYra5vDY0aSXF7r5r-OKG_uuKlKDsIVDzf9kSqeuRRdYmWsFoK_u0X1HiMM=; FOLLOW_NUMBER_YELLOW_POINT_INFO=%22MS4wLjABAAAAmnOTojt3vkNLQwePh2OzWCa_sbj6NHS_j4atlUrtGqap9_IXEMGacraz1t7b5Bbc%2F1711728000000%2F1711698419239%2F1711698413779%2F0%22; tt_scid=yppYIPWcyCPRazOgtOjSn9bBM9o1EsblAsTQrRI-fQaH-iAG78B5Hg2z2tqONHqa46f2; strategyABtestKey=%221711993021.46%22; FOLLOW_LIVE_POINT_INFO=%22MS4wLjABAAAAmnOTojt3vkNLQwePh2OzWCa_sbj6NHS_j4atlUrtGqap9_IXEMGacraz1t7b5Bbc%2F1712073600000%2F0%2F1711993021783%2F0%22; LOGIN_STATUS=0; xg_device_score=7.658235294117647; sso_uid_tt=632679d98273b34a5b0ef88c84024c52; sso_uid_tt_ss=632679d98273b34a5b0ef88c84024c52; sid_ucp_sso_v1=1.0.0-KGViYTAwZWRkNDlhMTQ5MmFjNTM1Yzk5NTJlN2NlYzE1ZTA4Yjc3ZjcKCRDA4auwBhjvMRoCbGYiIDExODI5NDc3OWE1NTEzY2I3MWY4ZDlhOGFkN2UxYjhi; ssid_ucp_sso_v1=1.0.0-KGViYTAwZWRkNDlhMTQ5MmFjNTM1Yzk5NTJlN2NlYzE1ZTA4Yjc3ZjcKCRDA4auwBhjvMRoCbGYiIDExODI5NDc3OWE1NTEzY2I3MWY4ZDlhOGFkN2UxYjhi; odin_tt=a43751dda14272259074c175158e76ae0a028280b65f32ee7079cb20ef7dad4c; sid_guard=d5bc735fc5a1bee8e60d8ffc6426a8d9%7C1711993024%7C21600%7CMon%2C+01-Apr-2024+23%3A37%3A04+GMT; uid_tt=653b257657e87e41aec2f5b244e2244a; uid_tt_ss=653b257657e87e41aec2f5b244e2244a; sid_tt=d5bc735fc5a1bee8e60d8ffc6426a8d9; sessionid=d5bc735fc5a1bee8e60d8ffc6426a8d9; sessionid_ss=d5bc735fc5a1bee8e60d8ffc6426a8d9; sid_ucp_v1=1.0.0-KGY0ZWIwNTk0Yzk0MDVlYWQ5MTBkYzA3NmM2MjJkYjA4NDhkYjFjZGMKCBDA4auwBhgNGgJobCIgZDViYzczNWZjNWExYmVlOGU2MGQ4ZmZjNjQyNmE4ZDk; ssid_ucp_v1=1.0.0-KGY0ZWIwNTk0Yzk0MDVlYWQ5MTBkYzA3NmM2MjJkYjA4NDhkYjFjZGMKCBDA4auwBhgNGgJobCIgZDViYzczNWZjNWExYmVlOGU2MGQ4ZmZjNjQyNmE4ZDk; __ac_nonce=0660af0c100715f3f59e7; __ac_signature=_02B4Z6wo00f01dbooUAAAIDBIIGoZwFe4HHWzKXAABOyWMcVzyFgDh9rmE45fj-D3sA1RWgZer0FACcx9-cOTPtyp2Rx89LxUbeQHbLXUXmwYUMAv2Uf4Qr.HHQBdXionN5IGpmeT55VnvVN3f; stream_player_status_params=%22%7B%5C%22is_auto_play%5C%22%3A1%2C%5C%22is_full_screen%5C%22%3A0%2C%5C%22is_full_webscreen%5C%22%3A0%2C%5C%22is_mute%5C%22%3A1%2C%5C%22is_speed%5C%22%3A1%2C%5C%22is_visible%5C%22%3A0%7D%22; home_can_add_dy_2_desktop=%221%22; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCSEpWZEhacEptWGw5Ykg5Z2RaamFVWU9uZjdRUjByTjRiSGt5NU1PeTl1RDd2a2tFR3Fzem90R2szWjBlL3pWc3haanl5dGdVeHMwWWJLMmhQMEVmOFU9IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoxfQ%3D%3D; msToken=9aeZrlDQA2qtTYIy5gNID182VZkOPdhq7qO2_3uiT0bpFy-8E5ABZMhjZeO8M53xAXxjr4fw2kcr6ZifH6ibeSYdkSI50ip1hTR56wwOsFjZVNV_mr6I79zkl9s=; IsDouyinActive=false");
        userAgent.put("Cache-Control","max-age=0");
        userAgent.put("Sec-Ch-Ua","\"Chromium\";v=\"112\", \"Microsoft Edge\";v=\"112\", \"Not:A-Brand\";v=\"99\"");
        userAgent.put("Sec-Ch-Ua-Mobile","0");
        userAgent.put("Sec-Ch-Ua-Platfo","\"Windows\"");
        userAgent.put("Upgrade-Insecure-Reques","1");
        userAgent.put("User-Agen","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.58");
        userAgent.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        userAgent.put("Sec-Fetch-Sit","none");
        userAgent.put("Sec-Fetch-Mod","navigate");
        userAgent.put("Sec-Fetch-Use","1");
        userAgent.put("Accept-Encoding","gzip, deflate");
        userAgent.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        Document document = null;
        try {
            document = Jsoup.connect("https://www.douyin.com/user/"+sec_uid).headers(userAgent).timeout(2500).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Elements e = document.getElementsByTag("script");
        for (Element script : e) {
            // 获取每个<script>元素内部内容
            String scriptContent = script.html();
            // 检查是否包含"myFunction"这个名字
            if (scriptContent.contains("roomIdStr")) {
                String[] findRoomidStr= scriptContent.split("roomIdStr\\\\\":\\\\\"");
                if (findRoomidStr.length>1){
                    System.out.println("sssssssssssssssssssssssss"+findRoomidStr[1]);
                    System.out.println(findRoomidStr[1].split("\\\\")[0]);
                    if ( findRoomidStr[1].split("\\\\")[0] != null &&!"0".equals(findRoomidStr[1].split("\\\\")[0]) && NumberUtil.isNumber(findRoomidStr[1].split("\\\\")[0])){
                        return     findRoomidStr[1].split("\\\\")[0];
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param personAddress
     * @return null || nickName
     */
    public  String getNickNameByPersonAddress(String personAddress){
        Map<String,String> userAgent =  new HashMap<String,String>();
        userAgent.put("Accept","charset=utf-8");
        userAgent.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        userAgent.put("Cache-Control","max-age=0");
        userAgent.put("Content-Type","application/json; charset=utf-8");
        userAgent.put("Sec-Ch-Ua","Microsoft Edge\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"");
        userAgent.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.43");
        Document document = null;
        try {
            document = Jsoup.connect(personAddress).headers(userAgent).followRedirects(false).timeout(2500).get();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (document == null){return null; }
        UrlBuilder urlBuilder =  UrlBuilder.of(document.getElementsByTag("a").attr("href"));
        logger.info("sec_uid:"+document.getElementsByTag("a").attr("href"));
        String sec_uid= urlBuilder.getQuery().get("sec_user_id").toString();
        try {
            Document document1 = Jsoup.connect("https://www.iesdouyin.com/web/api/v2/user/info/?sec_uid="+sec_uid+"&from_ssr=1").headers(userAgent).followRedirects(false).ignoreContentType(true).timeout(2500).get();
//            System.out.println(document1.getElementsByTag("body"));
            JSONObject jsonObject = JSONUtil.parseObj(document1.getElementsByTag("body").text());
//            System.out.println(jsonObject);
            String nickName = jsonObject.getJSONObject("user_info").get("nickname").toString();
            return     StrUtil.isEmptyIfStr(nickName) ? null : nickName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


     public String getXiGuaName(String roomid) {
//        ChromeOptions options = new ChromeOptions();

         EdgeOptions options =new EdgeOptions();

      // 设置允许弹框
//        options.addArguments("disable-infobars","disable-web-security");
      // 设置无gui 开发时还是不要加，可以看到浏览器效果
//        options.addArguments("--headless");
//        String driverPath =  "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe";
        String driverPath =  "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe";

        System.setProperty("webdriver.Edge.driver", driverPath);

        HashMap<String,String>  mobileEmulation = new HashMap<String,String>();
        mobileEmulation.put("deviceName","iPhone XR");
//        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        RemoteWebDriver driver=  new EdgeDriver(options);
        driver.get("https://webcast-open.douyin.com/open/webcast/reflow/?webcast_app_id=247160&room_id="+roomid);

        String   xiguaName =  driver.findElement(By.className("saas-reflow-room-anchor-name")).getText();

        driver.close();

         return xiguaName;

    }

//    public String getRoomIdByPersonAddress(String personAddress) {
//        ChromeOptions options = new ChromeOptions();
//        // 设置允许弹框
//        options.addArguments("disable-infobars","disable-web-security");
//        // 设置无gui 开发时还是不要加，可以看到浏览器效果
////            options.addArguments("--headless");
//        String driverPath =  "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe";
//
//
//        System.setProperty("webdriver.chrome.driver", driverPath);
//
////        HashMap<String,String>  mobileEmulation = new HashMap<String,String>();
////        mobileEmulation.put("deviceName","iPhone X");
////        options.setExperimentalOption("mobileEmulation", mobileEmulation);
//
//        RemoteWebDriver driver=  new ChromeDriver(options);
////        driver.get("https://webcast-open.douyin.com/open/webcast/reflow/?webcast_app_id=247160&room_id="+roomid);
//
//        driver.get(personAddress);
//
//        if (!driver.getCurrentUrl().equals(personAddress)){
//            System.out.println("into new");
//            System.out.println(driver.getTitle());
//
//
//            for (WebElement element : driver.findElement(By.tagName("body")).findElements(By.tagName("script"))) {
//                String   elementHtmlStr = element.getAttribute("innerHTML");
//                if (elementHtmlStr.contains("roomIdStr")){
////                    System.out.println(elementHtmlStr);
//                    String[] findRoomidStr= elementHtmlStr.split("roomIdStr\\\\\":\\\\\"");
//                    if (findRoomidStr.length>1){
//                        System.out.println("sssssssssssssssssssssssss"+findRoomidStr[1]);
//                        System.out.println(findRoomidStr[1].split("\\\\")[0]);
//                        if ( findRoomidStr[1].split("\\\\")[0] != null &&!"0".equals(findRoomidStr[1].split("\\\\")[0]) && NumberUtil.isNumber(findRoomidStr[1].split("\\\\")[0])){
//                             driver.close();
//                             return     findRoomidStr[1].split("\\\\")[0];
//                        }
//                    }
//
//                }
//
//            }
//
//        }
//        driver.close();
//
//        return null;
//
//    }
//
//
//

}
