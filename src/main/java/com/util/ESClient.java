package com.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class ESClient {

    private volatile TransportClient client = null;

    public ESClient(){
        try{
            Settings settings = Settings.builder()
                    .put("client.transport.sniff",true)
                    .put("cluster.name","elasticsearch")
                    .build();
           //此处 settings设置？
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9200));
        }catch (Exception e){
            e.printStackTrace();
            client.close();
        }
    }

    //获取与es建立连接后的es对象，类似与数据库的连接对象
    public TransportClient getTransClient(){

        if(client == null){
            synchronized (client){
                if(client ==null) {
                    new ESClient();
                }
            }
        }
        return client;
    }


    public static void main(String args[]){
        System.out.println(new ESClient().getTransClient().transportAddresses().get(0).getPort());

    }

}
