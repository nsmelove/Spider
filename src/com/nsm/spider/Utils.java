package com.nsm.spider;

/**
 * Created by nsm on 2015/7/23.
 */
public class Utils {
    public static boolean checkUrl(String url){
        if(url != null && url.startsWith("http://")){
            return true;
        }
        return false;
    }
}
