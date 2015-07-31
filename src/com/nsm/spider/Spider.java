package com.nsm.spider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by nsm on 2015/7/23.
 */
public class Spider implements Runnable{
    private LinkData linkData ;
    @Override
    public void run() {
        System.out.println("spider "+Thread.currentThread().getName()+" running !");
        while (true){
            linkData =Manager.popLinkData();
            if(linkData != null){
                String url = linkData.getUrl();
                Connection connection =Jsoup.connect(url);
                connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
                connection.timeout(Constant.CONNECT_TIMEOUT);
                try {
                    Document document =connection.get();
                    linkData.setTitle(document.title());
                    printLinkData(linkData);
                    DataBase.saveLinkData(linkData);
                    findTagA(document);
                }catch (IOException e){
                    System.out.println("invalid url:"+url);
                }

            }
        }
    }
    private void findTagA(Element element){
        Elements elements = element.children();
        for(Element childElement:elements){
            if(childElement.tagName().equals("a")){
                String url = childElement.attr("href");
                if(Utils.checkUrl(url)){
                    LinkData childLinkData = new LinkData();
                    childLinkData.setText(childElement.text());
                    childLinkData.setUrl(url);
                    Manager.pushLinkData(childLinkData);
                }
            }else {
                findTagA(childElement);
            }
        }
    }
    private void printLinkData(LinkData linkData){
        System.out.println("----------------------");
        System.out.println("text:"+linkData.getText());
        System.out.println("title:"+linkData.getTitle());
        System.out.println("url:"+linkData.getUrl());
    }
}
