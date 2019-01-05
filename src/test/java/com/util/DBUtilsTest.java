package com.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.BlogUser;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import sun.net.www.http.HttpClient;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DBUtilsTest {

    @Test
    public void getSqlSessionTest() throws Exception{

        SqlSession s  = DBUtils.getSqlSession();
//        List<BlogUser> ls = s.selectList("BlogUser.selectAll");
//        BlogUser us =  ls.get(0);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date dd =  sdf.parse("0-0-0");
//        new  java.sql.Date(dd.getTime());
//        System.out.println(new  java.sql.Date(dd.getTime()).toString());
//        System.out.println(sdf.parse("0-0-0").getMonth());
//        System.out.println(sdf.parse("0-0-0").getDay());
        System.out.println(s );
    }

    @Test
    public void getULR(){
        System.out.println( AddressToLat_Lon.getLatAndLon("中国 江苏 盐城"));
    }

    @Test
    public void tss(){
        System.out.print("1970-01-01 08:00:00.0".substring(0,19));
        //System.out.print("1000-01-01 00:00:00".length());
    }
}
