package com.service.impl;

import com.bean.BlogUser;
import com.service.inter.IndexService;
import com.util.AddressToLat_Lon;
import com.util.DBUtils;
import com.util.ESClientConnection;
import com.util.MyStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServiceImpl implements IndexService {

    //自动注入，Es的连接对象
    //@Autowired
    private ESClientConnection client = new ESClientConnection() ;
    private BigInteger indexCount = BigInteger.valueOf(0);

    public void initIndex(String filePath){
        //语料的存储路径
        File file = new File(filePath);
        File listFiles[] = file.listFiles();
        SqlSession session =  DBUtils.getSqlSession();
        for (int i = 0; i <listFiles.length ; i++) {
            File tempFile = listFiles[i];
            if(tempFile.isFile() && tempFile.toURI().toString().contains("info.xml")) {//处理用户个人信息记录
               /* BlogUser blogUser = docUserXMLParse(tempFile);
                //构建数据库用户表,插入用户数据
                System.out.println(session.insert("BlogUser.insert" ,blogUser));
                //此处没有做事务处理
                session.commit();
                System.out.println(tempFile.getName());*/
            }else if(tempFile.isFile() && tempFile.toURI().toString().endsWith(".xml")){//处理用户帖子记录
                System.out.println(tempFile.getName()+"l");
                IndexRequestBuilder req = client.getTransClient().prepareIndex("blogview01","blog");
                docXMLParse(tempFile, req );

            }else if(tempFile.isDirectory()){
                initIndex(tempFile.getAbsolutePath());
            }
        }
    }

    /*
    * 微博帖子信息处理
    * */
    public  void docXMLParse(File file,IndexRequestBuilder req  ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file.getAbsolutePath());

            List<Node> openidList = document.selectNodes("/root/data/info/openid");//用户的唯一Id,与name对应
            List<Node> textList = document.selectNodes("/root/data/info/text");//微博内容
            List<Node> origtextList = document.selectNodes("/root/data/info/origtext");//原始内容
            List<Node> nickList = document.selectNodes("/root/data/info/nick");//发布人昵称
            List<Node> timestampList = document.selectNodes("/root/data/info/timestamp");//微博发布时间
            List<Node> selfList = document.selectNodes("/root/data/info/self");//是否是原创，0不是 ，1 是
            List<Node> typeList = document.selectNodes("/root/data/info/type");//微博类型
            List<Node> likecountList = document.selectNodes("/root/data/info/likecount");//微博赞数量
            List<Node> mcountList = document.selectNodes("/root/data/info/mcount");//微博被点评的次数
            List<Node> countList = document.selectNodes("/root/data/info/count");//微博转发的次数
            List<Node> cityCodeList = document.selectNodes("/root/data/info/city_code");//微博的城市
            List<Node> countryCodeList = document.selectNodes("/root/data/info/country_code");//微博国家
            List<Node> locationList = document.selectNodes("/root/data/info/location");//微博地点
            List<Node> longitudeList = document.selectNodes("/root/data/info/longitude");//微博经度
            List<Node> latitudeList = document.selectNodes("/root/data/info/latitude");//微博维度

            for (int i = 0; i < openidList.size(); i++) {
                indexCount = indexCount.add(BigInteger.valueOf(1));
                System.out.println("indexCount.add(BigInteger.valueOf(1)" + indexCount.toString());
//                client.getTransClient().prepareIndex("blogview01","blog",(i+1)+"")

                String  blogLat_lon = (latitudeList.get(i).getText()==null ? "0":latitudeList.get(i).getText()) + ","
                        + (longitudeList.get(i).getText()==null ? "0":longitudeList.get(i).getText());
                System.out.println(":::::::::::::::::::::::::::::::::::我是经纬度:"+blogLat_lon);
                String location = locationList.get(i).getText();
                if("0,0".equals(blogLat_lon)){
                    blogLat_lon = AddressToLat_Lon.getLatAndLon(location);
                }
                req.setId(indexCount.toString()).setSource(XContentFactory.jsonBuilder().startObject()
                        .field("blogOpendId", openidList.get(i).getText())
                        .field("blogText", textList.get(i).getText())
                        .field("blogOrigtext", origtextList.get(i).getText())
                        .field("blogNick", nickList.get(i).getText())
                        .field("blogTimestamp", sdf.format(new Date(Long.parseLong(timestampList.get(i).getText()) * 1000)))
                        .field("blogSelf", selfList.get(i).getText() == "1" ? true : false)
                        .field("blogType", typeList.get(i).getText())
                        .field("blogLikecount", likecountList.get(i).getText())
                        .field("blogMcount", mcountList.get(i).getText())
                        .field("blogCount", countList.get(i).getText())
                        .field("blogCitycode", MyStringUtils.isNumCode(cityCodeList.get(i).getText()))
                        .field("blogCountrycode", MyStringUtils.isNumCode(countryCodeList.get(i).getText()))
                        .field("blogLocation",location )
                        .field("blogLat_lon", blogLat_lon)//128.680025,35.227632
                        //.field("blogLatitude",)
                        .endObject()
                ).get();
                //测试代码
                System.out.println("openidList:" + openidList.get(i).getText());
                System.out.println(textList.get(i).getText());
                System.out.println(origtextList.get(i).getText());
                System.out.println(nickList.get(i).getText());
                System.out.println(timestampList.get(i).getText());
                System.out.println(selfList.get(i).getText());
                System.out.println(typeList.get(i).getText());
                System.out.println(likecountList.get(i).getText());
                System.out.println(mcountList.get(i).getText());
                System.out.println(countList.get(i).getText());
                System.out.println(cityCodeList.get(i).getText());
                System.out.println(countryCodeList.get(i).getText());
                System.out.println(locationList.get(i).getText());
                System.out.println(longitudeList.get(i).getText());
                System.out.println(latitudeList.get(i).getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {


            //TODO
        }

    }
    /*
    * 微博用户信息处理
    * */
    public  BlogUser  docUserXMLParse(File file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SAXReader reader = new SAXReader();
        Document document = null;
        BlogUser blogUser = new BlogUser();
        try {
            document = reader.read(file.getAbsolutePath());
            List<Node> birth_dayList = document.selectNodes("/root/data/birth_day");//出生的天
            List<Node> birth_monthList = document.selectNodes("/root/data/birth_month");//出生的月
            List<Node> birth_yearList = document.selectNodes("/root/data/birth_year");//出生的年
            String birthday = birth_yearList.get(0).getText()+"-"+birth_monthList.get(0).getText()+"-"+birth_dayList.get(0).getText();
            if(birthday.equals("0-0-0")){
                birthday ="1000-01-01";
            }
            List<Node> emailList = document.selectNodes("/root/data/email");//邮箱
            List<Node> nameList = document.selectNodes("/root/data/name");//姓名
            List<Node> nickList = document.selectNodes("/root/data/nick");//昵称
            List<Node> openidList = document.selectNodes("/root/data/openid");//用户唯一标示
            List<Node> regtimeList = document.selectNodes("/root/data/regtime");//注册时间
            long regtime = Long.parseLong(regtimeList.get(0).getText());
            List<Node> sexList = document.selectNodes("/root/data/sex");//性别
            List<Node> locationList = document.selectNodes("/root/data/location");//地点
            List<Node> homepageList = document.selectNodes("/root/data/homepage");//用户首页URL
            String homepage="http://t.qq.com/";
            if(homepageList.get(0).getText()==null){
                String urlStr =  file.getAbsolutePath();
                int end =  urlStr.lastIndexOf("\\");
                int start  = urlStr.substring(1,urlStr.lastIndexOf("\\")).lastIndexOf("\\");
                homepage = "http://t.qq.com/"+urlStr.substring(start,end);
            }else{
                homepage = homepageList.get(0).getText();
            }
            List<Node> isvipList = document.selectNodes("/root/data/isvip");//是否是VIP
            List<Node> levelList = document.selectNodes("/root/data/level");//用户等级
            List<Node> tweetnumList = document.selectNodes("/root/data/tweetnum");// 发文总数量
            List<Node> fansnumList = document.selectNodes("/root/data/fansnum");// 关注人数
            List<Node> favnumList = document.selectNodes("/root/data/favnum");// 粉丝数
            List<Node> tagList = document.selectNodes("/root/data/tag/name");//标签
            String tag ="";
            for ( Node node : tagList ) {
                tag += node.getText()+",";
            }

            java.util.Date dd =  sdf.parse(birthday);
            blogUser.setBirthday(new java.sql.Date(dd.getTime()));
            blogUser.setEmail(emailList.get(0).getText());
            blogUser.setUserName(nameList.get(0).getText());
            blogUser.setNick(nickList.get(0).getText());
            blogUser.setOpenid(openidList.get(0).getText());
            blogUser.setRegtime(new Timestamp(regtime*1000));
            blogUser.setSex(Byte.valueOf(sexList.get(0).getText()));
            blogUser.setLocation(locationList.get(0).getText());
            blogUser.setHomepage(homepage);
            blogUser.setIsvip(Byte.valueOf(isvipList.get(0).getText()));
            blogUser.setUserLevel(Byte.valueOf(levelList.get(0).getText()));
            blogUser.setFansnum(Integer.valueOf(fansnumList.get(0).getText()));
            blogUser.setFavnum(Integer.valueOf(favnumList.get(0).getText()));
            blogUser.setTweetnum(Integer.valueOf(tweetnumList.get(0).getText()));
            blogUser.setTag(tag);
            blogUser.setLatAndlon(AddressToLat_Lon.getLatAndLon(locationList.get(0).getText()));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return blogUser;

 /*
 备用字段
            List<Node> city_codeList = document.selectNodes("/root/data/city_code");//城市
            List<Node> compList = document.selectNodes("/root/data/comp");//
            List<Node> country_codeList = document.selectNodes("/root/data/country_code");//国家
            List<Node> eduList = document.selectNodes("/root/data/edu");//
            List<Node> expList = document.selectNodes("/root/data/exp");//
            List<Node> headList = document.selectNodes("/root/data/head");//
            List<Node> homecity_codeList = document.selectNodes("/root/data/homecity_code");//
            List<Node> homecountry_codeList = document.selectNodes("/root/data/homecountry_code");//
            List<Node> homeprovince_codeList = document.selectNodes("/root/data/homeprovince_code");//
            List<Node> hometown_codeList = document.selectNodes("/root/data/hometown_code");//
            List<Node> https_headList = document.selectNodes("/root/data/https_head");//
            List<Node> idolnumList = document.selectNodes("/root/data/idolnum");//
            List<Node> industry_codeList = document.selectNodes("/root/data/industry_code");//
            List<Node> introductionList = document.selectNodes("/root/data/introduction");//
            List<Node> isentList = document.selectNodes("/root/data/isent");//
            List<Node> ismyblackList = document.selectNodes("/root/data/ismyblack");//
            List<Node> ismyfansList = document.selectNodes("/root/data/ismyfans");//
            List<Node> ismyidolList = document.selectNodes("/root/data/ismyidol");//
            List<Node> isrealnameList = document.selectNodes("/root/data/isrealname");//
            List<Node> mutual_fans_numList = document.selectNodes("/root/data/mutual_fans_num");//
            List<Node> province_codeList = document.selectNodes("/root/data/province_code");//
            List<Node> send_private_flagList = document.selectNodes("/root/data/send_private_flag");//
            List<Node> verifyinfoList = document.selectNodes("/root/data/verifyinfo");//

            List<Node> Tcity_codeList = document.selectNodes("/root/data/tweetinfo/city_code");//
            List<Node> Tcountry_codeList = document.selectNodes("/root/data/tweetinfo/country_code");//
            List<Node> TemotiontypeList = document.selectNodes("/root/data/tweetinfo/emotiontype");//
            List<Node> TfromList = document.selectNodes("/root/data/tweetinfo/from");//
            List<Node> TfromurlList = document.selectNodes("/root/data/tweetinfo/fromurl");//
            List<Node> TgeoList = document.selectNodes("/root/data/tweetinfo/geo");//
            List<Node> TidList = document.selectNodes("/root/data/tweetinfo/id");//
            List<Node> TimageList = document.selectNodes("/root/data/tweetinfo/image");//
            List<Node> TlatitudeList = document.selectNodes("/root/data/tweetinfo/latitude");//
            List<Node> TlongitudeList = document.selectNodes("/root/data/tweetinfo/longitude");//
            List<Node> TlocationList = document.selectNodes("/root/data/tweetinfo/location");//
            List<Node> TorigtextList = document.selectNodes("/root/data/tweetinfo/origtext");//
            List<Node> Tprovince_codeList = document.selectNodes("/root/data/tweetinfo/province_code");//
            List<Node> TselfList = document.selectNodes("/root/data/tweetinfo/self");//
            List<Node> TstatusList = document.selectNodes("/root/data/tweetinfo/status");//
            List<Node> TtextList = document.selectNodes("/root/data/tweetinfo/text");//
            List<Node> TtimestampList = document.selectNodes("/root/data/tweetinfo/timestamp");//
            List<Node> TtypeList = document.selectNodes("/root/data/tweetinfo/type");//
*/
    }
    /*
    * 递归解析xml元素，返回Map对象
    * */
    private void parseXMLEle(Element root , Map map ){

        List<Element> data = root.elements();
        if(data.size() > 0){
            for (int i = 0; i < data.size() ; i++) {
                parseXMLEle (  data.get(i),map );
            }
        }else{
            map.put(root.getParent().getName()+"_"+root.getName(),root.getTextTrim());
            System.out.println(root.getParent().getName()+"_"+root.getName() + ":" +root.getText());
        }
    }


    @Override
    public void index(String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","ljh");
        map.put("age",20);
        map.put("birthday",new Date());
        map.put("message", "ljh test message");
        //prepareIndex(String index,String type, String id)
        //根据数据源创建索引，类型，id
        IndexResponse indexResponse = client.getTransClient().prepareIndex("test01","student",id).setSource(map).get();
        //获取索引的名称类型，id

    }



    @Override
    public void del(String id) throws Exception {

    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public void update(String id) throws Exception {

    }

    @Override
    public List<Object> mutiGet(String... ids) throws Exception {
        return null;
    }

    @Override
    public void bulk(String... ids) throws Exception {

    }

    @Override
    public void bulkProcesstor(String index, String type, String... ids) throws Exception {

    }
}
