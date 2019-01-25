package com.service.impl;

import com.bean.BlogUser;
import com.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


public class IndexServiceImplTest {


    private static AtomicLong indexCount = new AtomicLong(0);

    public void initIndex(String filePath){
        //语料的存储路径
        File file = new File(filePath);
        File listFiles[] = file.listFiles();
        System.out.println(listFiles.length);
        //SqlSession session =  DBUtils.getSqlSession();
       /* for (int i = 0; i <listFiles.length ; i++) {
            File tempFile = listFiles[i];
            if(tempFile.isFile() && tempFile.toURI().toString().contains("info.xml")) {//处理用户个人信息记录

            }else if(tempFile.isFile() && tempFile.toURI().toString().endsWith(".xml")){//处理用户帖子记录

            }else if(tempFile.isDirectory()){
                System.out.println(tempFile.getAbsolutePath());
                initIndex(tempFile.getAbsolutePath());
            }
        }*/
    }



    @Test
    public void initIndex() throws Exception{
       /* DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = new Timestamp(0).toString();
        Date ss = df.parse(s);
        System.out.println(ss.getTime());*/
        //initIndex("D:\\postgrudationPoject\\blogDataset\\post1");

        File file = new File("E:\\BaiduNetdiskDownload\\post1");
        File listFiles[] = file.listFiles();
        IndexServiceImpl.initIndex(listFiles);
       /* int fileSum = listFiles.length;
        int threds = 2;
      //  ArrayList<File []> ls = new ArrayList<>();
        int k = fileSum/threds ;
        for(int i = 0 ; i <threds  ; ){
             File temfilels [] = new File[k];
             new Thread(){
                 @Override
                 public void run(){
                     for(int j = 0 ; j < k ;j++){
                         temfilels[j]= listFiles[i*k+j];
                     }
                     //ls.add(temfilels);
                     IndexServiceImpl.initIndex(temfilels);
                 }
             }.start();
        }*/

       //IndexServiceImpl.initIndex("D:\\postgrudationPoject\\blogDataset\\post1");

    }

  /*  @Test
    public void docXMLParse(){
        new IndexServiceImpl().docXMLParse(
              new File("D:\\postgrudationPoject\\blogDataset\\post1\\h1085223935\\2014-06-24 09.12.12.xml"));
    }*/

    @Test
    public void docUserXMLParse(){
      /*  BlogUser db =  new IndexServiceImpl().docUserXMLParse(
                new File("D:\\postgrudationPoject\\blogDataset\\post1\\a1299628483\\info.xml"));
*/
     /*   BlogUser db = new BlogUser();
        db.setRegtime(new Timestamp(0));
        db.setOpenid("122");
       SqlSession session =  DBUtils.getSqlSession();

       System.out.println(session.insert("BlogUser.insert" ,db));
       session.commit();*/


     /*  String str =  new File("D:\\postgrudationPoject\\blogDataset\\post1\\a1299628483\\info.xml").getAbsolutePath();
       int end =  str.lastIndexOf("\\");
       int satrt  = str.substring(1,str.lastIndexOf("\\")).lastIndexOf("\\");
       System.out.println(str.substring(satrt+2,end));
*/
    }
}
