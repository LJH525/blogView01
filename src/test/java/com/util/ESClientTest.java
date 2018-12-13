package com.util;

import org.junit.Test;


public class ESClientTest {

    //获取与es建立连接后的es对象，类似与数据库的连接对象
    @Test
    public void getTransClientTest(){
        System.out.println(new ESClientConnection().getTransClient());
    }
}
