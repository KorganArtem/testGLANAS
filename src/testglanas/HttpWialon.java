/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testglanas;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;

/**
 *
 * @author korgan
 */
public class HttpWialon {
    private String sid = "";
    public boolean login(String token) throws MalformedURLException, ProtocolException, IOException{
        String url = "https://hst-api.wialon.com/wialon/ajax.html?svc=token/login&params={\"token\":\""+token+"\"}";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JsonObject authResult = jsonMaker(response.toString());
        sid = authResult.get("eid").toString().replaceAll("\"", "");
        return true;
    }
    public void sarchAllItems() throws MalformedURLException, IOException{
       
        String searchString = "https://hst-api.wialon.com/wialon/ajax.html?svc=core/search_items&params={\"spec\":{\"itemsType\":\"avl_unit\",\"propName\":\"sys_name\",\"propValueMask\":\"*\",\"sortType\":\"sys_name\"},\"force\":1,\"flags\":1,\"from\":0,\"to\":0}&sid="+sid;
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
    public void engineOff(long idItem) throws MalformedURLException, IOException{
        String searchString = "https://hst-api.wialon.com/wialon/ajax.html?svc=unit/exec_cmd&params={%22itemId%22:"+idItem+",%22commandName%22:%22%D0%9E%D1%82%D0%BA%D0%BB%D1%8E%D1%87%D0%B8%D1%82%D1%8C%20%D0%B4%D0%B2%D0%B8%D0%B3%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%22,%22linkType%22:%22tcp%22,%20%22param%22:%22*!2Y%22,%20%22timeout%22:%2010}&sid="+sid;
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
        String searchString = "https://hst-api.wialon.com/wialon/ajax.html?svc=unit/calc_last_message&params={%22unitId%22:"+idItem+",%22sensors%22:[2]}&sid="+sid;
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
        String stateEngin = jsonMaker(responseList.toString()).get("2").toString();
        int state = Integer.parseInt(stateEngin);
        inList.close();
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
}//https://hst-api.wialon.com/wialon/ajax.html?svc=unit/calc_last_message&params={%22unitId%22:16260554,%22sensors%22:[2]}&sid=056bd357b24f833839ba1ae5536bf764
