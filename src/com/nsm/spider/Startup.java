package com.nsm.spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by nsm on 2015/7/23.
 */
public class Startup {
    private static String defaultUrl="http://www.hao123.com/";
    //private static String defaultUrl="http://www.baidu.com/";
    public static void main(String[] args) {
        System.out.println("program startup !");
        String url = null;
        if(args.length > 0 &&Utils.checkUrl(args[0])){
           url = args[0];
        }else {
            url = defaultUrl;
        }
        LinkData linkData = new LinkData();
        linkData.setUrl(url);
        Manager.pushLinkData(linkData);
        Manager.startUp();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                String cmd = reader.readLine();
                if(cmd.equals("exit") || cmd.equals("quit") ||cmd.equals("stop")){
                    Manager.stop();
                    System.out.println("program shutdown !");
                    System.exit(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
