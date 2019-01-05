package com.service.impl;

import com.bean.BlogUser;
import com.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.File;

public class IndexServiceImplTest {

    @Test
    public void initIndex(){
        new IndexServiceImpl().initIndex("E:\\BaiduNetdiskDownload\\post1");
    }

  /*  @Test
    public void docXMLParse(){
        new IndexServiceImpl().docXMLParse(
              new File("D:\\postgrudationPoject\\blogDataset\\post1\\h1085223935\\2014-06-24 09.12.12.xml"));
    }*/

    @Test
    public void docUserXMLParse(){
        BlogUser db =  new IndexServiceImpl().docUserXMLParse(
                new File("D:\\postgrudationPoject\\blogDataset\\post1\\a1299628483\\info.xml"));

       SqlSession session =  DBUtils.getSqlSession();
       System.out.println(session.insert("BlogUser.insert" ,db));
       session.commit();


     /*  String str =  new File("D:\\postgrudationPoject\\blogDataset\\post1\\a1299628483\\info.xml").getAbsolutePath();
       int end =  str.lastIndexOf("\\");
       int satrt  = str.substring(1,str.lastIndexOf("\\")).lastIndexOf("\\");
       System.out.println(str.substring(satrt+2,end));
*/
    }
}
