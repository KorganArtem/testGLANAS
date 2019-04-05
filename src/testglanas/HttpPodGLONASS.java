/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testglanas;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

/**
 *
 * @author korgan
 */
public class HttpPodGLONASS {
    private String sid = "";
    HttpClient httpclient = HttpClients.createDefault();
    HttpResponse response;
    public void newLogin(String login, String password) throws UnsupportedEncodingException, IOException{
        HttpPost httppost = new HttpPost("http://194.28.112.5:8026/ajax.html?svc=core/login"); //params={%22user%22:%22%D0%A0%D0%B0%D0%B9-%D0%A2%D1%83%D1%80%22,%22password%22:%2212345%22}
//         Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("params", "{\"user\":\"Рай-Тур\",\"password\":\"12345\"}"));
        //params.add(new BasicNameValuePair("password", "12345"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        System.out.println(params);

        //Execute and get the response.
        response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //InputStream instream = entity.getContent();
            try {
                JsonObject authResult = jsonMaker(EntityUtils.toString(entity));
                String ssid = authResult.get("ssid").toString().replaceAll("\"", "");
                System.out.println(ssid);
                sid=ssid;
            } finally {
               
            } 
        }
        
    }
    public void carList() throws UnsupportedEncodingException, IOException{
        HttpPost httppostCL = new HttpPost("http://194.28.112.5:8026/ajax.html?svc=core/search_items");
        List<NameValuePair> paramsGetList = new ArrayList<NameValuePair>(2);
        paramsGetList.add(new BasicNameValuePair("params", "{\"spec\":{\"itemsType\":\"avl_unit\",\"propName\":\"sys_name\",\"propValueMask\":\"*\",\"sortType\":\"sys_name\"},\"force\":1,\"flags\":1,\"from\":0,\"to\":300}"));
        paramsGetList.add(new BasicNameValuePair("sid", sid));
        httppostCL.setEntity(new UrlEncodedFormEntity(paramsGetList, "UTF-8"));
        System.out.println(paramsGetList);
        response = httpclient.execute(httppostCL);
        HttpEntity entityCl = response.getEntity();
        if (entityCl != null) {
            //InputStream instream = entity.getContent();
            try {
                JsonObject carList = jsonMaker(EntityUtils.toString(entityCl));
                JsonArray carlistJS = carList.getAsJsonArray("items");
                System.out.println(carlistJS.size());
                for(int ind=0; ind<carlistJS.size(); ind++){
                    System.out.println(carlistJS.get(ind));
                }
//                sarchAllItems(ssid);
                
            } finally {
                //instream.close();
            } 
        }
    }
    
    
    public void engineOff(long idItem) throws MalformedURLException, IOException{
         HttpPost httppost = new HttpPost("http://194.28.112.5:8026/ajax.html?svc=unit/exec_cmd"); //params={%22user%22:%22%D0%A0%D0%B0%D0%B9-%D0%A2%D1%83%D1%80%22,%22password%22:%2212345%22}
//         Request parameters and other properties.
        List<NameValuePair> paramsEngOff = new ArrayList<NameValuePair>(2);
        paramsEngOff.add(new BasicNameValuePair("params", "{itemId:"+idItem+",commandName:%22%D0%9E%D1%82%D0%BA%D0%BB%D1%8E%D1%87%D0%B8%D1%82%D1%8C%20%D0%B4%D0%B2%D0%B8%D0%B3%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%22,%22linkType%22:%22tcp%22,%20%22param%22:%22*!2Y%22,%20%22timeout%22:%2010}"));
        paramsEngOff.add(new BasicNameValuePair("sid", sid));
        httppost.setEntity(new UrlEncodedFormEntity(paramsEngOff, "UTF-8"));
        System.out.println(paramsEngOff);
        //Execute and get the response.
        response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //InputStream instream = entity.getContent();
            try {
                JsonObject authResult = jsonMaker(EntityUtils.toString(entity));
                System.out.println(authResult);
            } finally {
               
            } 
        }
//        String searchString = "https://hst-api.wialon.com/wialon/ajax.html?svc=unit/exec_cmd&&sid="+sid;
//        //params={%22itemId%22:"+idItem+",%22commandName%22:%22%D0%9E%D1%82%D0%BA%D0%BB%D1%8E%D1%87%D0%B8%D1%82%D1%8C%20%D0%B4%D0%B2%D0%B8%D0%B3%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%22,%22linkType%22:%22tcp%22,%20%22param%22:%22*!2Y%22,%20%22timeout%22:%2010}
//        System.out.println(searchString);
//        URL objGetList = new URL(searchString);
//        HttpURLConnection connectionGetList = (HttpURLConnection) objGetList.openConnection();
//
//        connectionGetList.setRequestMethod("GET");
//
//        BufferedReader inList = new BufferedReader(new InputStreamReader(connectionGetList.getInputStream()));
//        String inputLineList;
//        StringBuffer responseList = new StringBuffer();
//
//        while ((inputLineList = inList.readLine()) != null) {
//            responseList.append(inputLineList);
//        }
//        System.out.println(responseList.toString());
//        inList.close();
    }
    public void engineOn(long idItem) throws MalformedURLException, ProtocolException, IOException{
        String searchString = "https://hst-api.wialon.com/wialon/ajax.html?svc=unit/exec_cmd&params={%22itemId%22:"+idItem+",%22commandName%22:%22%D0%92%D0%BA%D0%BB%D1%8E%D1%87%D0%B8%D1%82%D1%8C%20%D0%B4%D0%B2%D0%B8%D0%B3%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%22,%22linkType%22:%22tcp%22,%22param%22:%22*!2N%22,%22timeout%22:%2010}&sid="+sid;
        System.out.println(searchString);
        URL objGetList = new URL(searchString);
        HttpURLConnection connectionGetList = (HttpURLConnection) objGetList.openConnection();

        connectionGetList.setRequestMethod("GET");

        BufferedReader inList = new BufferedReader(new InputStreamReader(connectionGetList.getInputStream()));
        String inputLineList;
        StringBuffer responseList = new StringBuffer();

        while ((inputLineList = inList.readLine()) != null) {
            responseList.append(inputLineList);
        }
        System.out.println(responseList.toString());
        inList.close();
    }
    public boolean engineState(long idItem) throws MalformedURLException, ProtocolException, IOException{
        HttpPost httppostCL = new HttpPost("http://194.28.112.5:8026/ajax.html?svc=unit/calc_last_message");
        List<NameValuePair> paramsGetState = new ArrayList<NameValuePair>(2);
        paramsGetState.add(new BasicNameValuePair("params", "{unitId:"+idItem+",sensors:[2]}"));
        paramsGetState.add(new BasicNameValuePair("sid", sid));
        httppostCL.setEntity(new UrlEncodedFormEntity(paramsGetState, "UTF-8"));
        System.out.println(paramsGetState);
        response = httpclient.execute(httppostCL);
        HttpEntity entityCl = response.getEntity();
        String stateEngin = "1";
        if (entityCl != null) {
            //InputStream instream = entity.getContent();
            try {
                JsonObject carState = jsonMaker(EntityUtils.toString(entityCl));
                //JsonArray carlistJS = carList.getAsJsonArray("items");
                System.out.println("STATE:"+carState.get("2"));
                stateEngin = carState.get("2").getAsString();
            } finally {
                //instream.close();
            } 
        }
        int state = Integer.parseInt(stateEngin);
        System.out.println(state);
        if(state==1)
            return true;
        return false;
    }
    private JsonObject jsonMaker(String inputString){
        JsonParser parser = new JsonParser();
        JsonObject jsonObj;
        jsonObj = (JsonObject) parser.parse(inputString);
        return jsonObj;
    }
}
