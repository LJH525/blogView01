package com.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class BlogUser {

     private Date birthday;
     private String email;
     private String userName;
     private String nick;
     private String openid;
     private java.sql.Timestamp regtime;
     private byte sex;
     private String location;
     private String homepage;
     private byte isvip;
     private byte userLevel;
     private int fansnum;
     private int favnum;
     private int tweetnum;
     private String  tag;
    private String latAndlon;

    public String getLatAndlon() {
        return latAndlon;
    }

    public void setLatAndlon(String latAndlon) {
        this.latAndlon = latAndlon;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Timestamp getRegtime() {
        return regtime;
    }

    public void setRegtime(Timestamp regtime) {
        this.regtime = regtime;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public byte getIsvip() {
        return isvip;
    }

    public void setIsvip(byte isvip) {
        this.isvip = isvip;
    }


    public int getFansnum() {
        return fansnum;
    }

    public void setFansnum(int fansnum) {
        this.fansnum = fansnum;
    }

    public int getFavnum() {
        return favnum;
    }

    public void setFavnum(int favnum) {
        this.favnum = favnum;
    }

    public int getTweetnum() {
        return tweetnum;
    }

    public void setTweetnum(int tweetnum) {
        this.tweetnum = tweetnum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(byte userLevel) {
        this.userLevel = userLevel;
    }
}
