package com.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;


public class ESClientTest {




    //获取与es建立连接后的es对象，类似与数据库的连接对象
    @Test
    public  void getTransClientTest(){
        System.out.println(new ESClient().getTransClient());
    }
}
