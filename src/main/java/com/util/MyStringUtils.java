package com.util;

public class MyStringUtils {

    //地点编码处理
    public static String isNumCode(String code){
        try{
            Integer.valueOf(code);
             return code;
        }catch (Exception e){
            return "-1";
        }
    }
}
