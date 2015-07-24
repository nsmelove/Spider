package com.nsm.spider;

import java.util.concurrent.*;

/**
 * Created by nsm on 2015/7/23.
 */
public class Manager {
    private static ExecutorService executor = Executors.newFixedThreadPool(Constant.THREAD_SIZE);
    private static LinkedBlockingQueue<LinkData> linkDataQueue = new LinkedBlockingQueue<LinkData>();
    public static boolean pushLinkData(LinkData linkData){
        return  linkDataQueue.offer(linkData);
    }
    public static LinkData popLinkData(){
        return  linkDataQueue.poll();
    }

    public static void startUp() {
        try {
            for(int i=0;i<Constant.THREAD_SIZE;i++){
                executor.execute(new Spider());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void stop(){
        executor.shutdownNow();
    }
}
