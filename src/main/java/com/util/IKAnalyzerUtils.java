/*
package com.util;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class IKAnalyzerUtils {

    public void ana(){

    }

    public static void main1(String[] args) throws IOException {

        String text="基于java语言开发的轻量级的中文分词工具包";
        //创建分词对象
        Analyzer anal=new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
        //分词
        TokenStream ts=anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        while(ts.incrementToken()){
            System.out.print(term.toString()+"|");
        }
        reader.close();
        System.out.println();

    }


    public static void main(String[] args) throws IOException {
        String text="谷歌今天下班时小伙伴们打算把午饭剩下的米饭做蛋炒饭吃掉！可又都说自己不会做！于是最近比较喜欢强出头的我自告奋勇说：让我来～\uD83D\uDE04 我也不想暴露自己奇葩的厨艺 于是匆忙百度了下别人的做法现学现卖\uD83D\uDE1C她们吃了说还不错！吃的时候大姐头一高兴说告诉我们一个小秘密 说她去年年底报了一个初级厨师培训班！";
        StringReader sr=new StringReader(text);
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            System.out.print(lex.getLexemeText()+"|");
        }
    }

}
*/
