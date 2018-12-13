package com.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class DBUtils {

    private static volatile SqlSessionFactory sessionFactory = null;
    final static String dbConfigPath = "MyBatisConfig.xml";

   static{
        Reader reader = null;
        try {
            reader =  Resources.getResourceAsReader(dbConfigPath);
            if(sessionFactory == null){
                synchronized (reader) {
                    if (sessionFactory == null) {
                        sessionFactory = new SqlSessionFactoryBuilder().build(reader);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SqlSessionFactory initSqlSession(){
        Reader reader = null;
        try {
            reader =  Resources.getResourceAsReader(dbConfigPath);
            if(sessionFactory == null){
                synchronized (reader) {
                    if (sessionFactory == null) {
                        sessionFactory = new SqlSessionFactoryBuilder().build(reader);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static SqlSession getSqlSession(){
       if(sessionFactory == null){
           sessionFactory =  initSqlSession();
       }
       return  sessionFactory.openSession();
    }

}
