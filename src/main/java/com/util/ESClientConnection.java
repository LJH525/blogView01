package com.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class ESClientConnection {

    private volatile TransportClient client = null;

    public ESClientConnection(){
        try{
            Settings settings = Settings.builder()
                    .put("client.transport.sniff",true)
                    .put("cluster.name","blog-es")
                    .build();
            //注意此处必须填充settings参数，设置连接的集群的名字
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

           //此处 settings设置？
//            client = new PreBuiltTransportClient(Settings.EMPTY)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));
//            IndexResponse is = client.prepareIndex("blogIndex01","blog","").get();
//           CreateIndexResponse rs = client.admin().indices().prepareCreate("blogtest01").get();
            XContentBuilder mappings = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("settings")
                            .field("number_of_shards",5)
                            .field("number_of_replicas",2)
                         .endObject()
                        .startObject("mappings")
                            .startObject("blog")
                            .startObject("properties")
                                .startObject("blogOpendId").field("type","keyword").field("store",true).endObject()
                                .startObject("blogText").field("type","text").field("store",true).field("analyzer","ik_smart").field("search_analyzer","ik_smart").field("index",true).endObject()
                                .startObject("blogOrigtext").field("type","text").field("store",true).field("analyzer","ik_smart").endObject()
                                .startObject("blogNick").field("type","keyword").field("store",true).endObject()
                                .startObject("blogTimestamp").field("type","date").field("store",true).endObject()
                                .startObject("blogSelf").field("type","boolean").field("store",true).endObject()
                                .startObject("blogType").field("type","integer").field("store",true).endObject()
                                .startObject("blogLikecount").field("type","long").field("store",true).endObject()
                                .startObject("blogMcount").field("type","long").field("store",true).endObject()
                                .startObject("blogCount").field("type","long").field("store",true).endObject()
                                .startObject("blogCitycode").field("type","short").field("store",true).endObject()
                                .startObject("blogCountrycode").field("type","short").field("store",true).endObject()
                                .startObject("blogLocation").field("type","keyword").field("store",true).endObject()
                                .startObject("blogLat_lon").field("type","geo_point").field("store",true).endObject()
                            .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
               client.admin().indices().prepareCreate("blogview02").setSource(mappings).get();
//                client.prepareIndex("blogview01","blog")
//                        .setSource(mappings).get();
////            System.out.println(rs.isAcknowledged());
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
                    new ESClientConnection();
                }
            }
        }
        return client;
    }


    public static void main(String args[]){
        System.out.println(new ESClientConnection().getTransClient().transportAddresses().get(0).getPort());

    }

}
