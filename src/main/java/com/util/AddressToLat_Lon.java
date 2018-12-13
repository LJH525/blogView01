package com.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
/*
    根据城市名获取经纬度，
    高德API https://lbs.amap.com/api/webservice/guide/api/georegeo
**/
public class AddressToLat_Lon {

    private static final String URL="https://restapi.amap.com/v3/geocode/geo";
    public static String  getLatAndLon(String address){
        System.out.println("address:"+address);
        String latitudeAndLongitude = "0,0";
        if(address==null || "".equals(address) || "未知".equals(address)){
            return latitudeAndLongitude;
        }
        CloseableHttpClient sc = HttpClients.createDefault();
        try {
            List<NameValuePair> list =new ArrayList();
            list.add(new BasicNameValuePair("address",address));
            list.add(new BasicNameValuePair("output","JSON"));
            list.add(new BasicNameValuePair("key","9d265b7bb78a66a7383505f94fbf3e3d"));
            UrlEncodedFormEntity ue = new UrlEncodedFormEntity(list,"utf-8");
            HttpPost pos = new HttpPost(URL);
            pos.setEntity(ue);
            CloseableHttpResponse rs = sc.execute(pos);
            HttpEntity et =  rs.getEntity();
            String json = EntityUtils.toString(et,"utf-8");
            JSONArray jsonArray = (JSONArray) JSON.parseObject(json).get("geocodes");
            if(jsonArray.size()>0){
                Object ob =  ((JSONObject)(jsonArray).get(0)).get("location");
                System.out.println( ob);
                latitudeAndLongitude = ob!=null ? ob.toString() : null;
            }else{
                latitudeAndLongitude = "0,0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        String [] point = latitudeAndLongitude.split(",");
        latitudeAndLongitude = point[1]+","+point[0];
        return latitudeAndLongitude;
    }
}
