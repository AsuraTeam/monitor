package com.asura.util.network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPing {
    private Logger logger = LoggerFactory.getLogger(ThreadPing.class);
    private Queue<String> allIp;
    private int fetchedNum = 0; // 已经取得的任务数量，每次从队列中取一个ip就加1
    private ArrayList upList;
    private int ipNumber;
    private ArrayList downList;
    private Ping ping = new Ping();

    public ThreadPing(ArrayList<String > list, ArrayList upList, ArrayList downList, int ipNumber) {
        // 首先创建一个队列用于存储所有ip地址
        allIp = new LinkedList<String>();
        for (String ip:list){
                allIp.offer(ip);
        }
        this.ipNumber = ipNumber;
        this.upList = upList;
        this.downList = downList;
    }

    public void startPing() {
        // 创建一个线程池，多个线程同时跑
        int threadNum = this.allIp.size()+10;
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.execute(new PingRunner());
        }
        executor.shutdown();
        try {
            while (!executor.isTerminated()) {
                if(downList.size()>ipNumber){
                    logger.info("获取到未使用数量 "+downList.size() + " 实际需求数量 "+ipNumber);
                    break;
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Fetched num is "+fetchedNum);
    }

    private class PingRunner implements Runnable {
        private String taskIp = null;
        @Override
        public void run() {
            try {
                while ((taskIp = getIp()) != null) {
                    InetAddress addr = InetAddress.getByName(taskIp);
                    if (addr.isReachable(3000)) {
                        upList.add(taskIp);
                    } else {
                        if(ping.execPing(taskIp) ==0) {
                            downList.add(taskIp);
//                            System.out.println("host ["+taskIp+"] is not reachable");
                        }else{
                            upList.add(taskIp);
                        }
                    }
                }
            } catch (SocketException e) {
//                System.out.println("host ["+taskIp+"] permission denied");
            } catch (Exception e) {
                if(ping.execPing(taskIp) ==0) {
                    downList.add(taskIp);
                    System.out.println("host ["+taskIp+"] is not reachable");
                }else{
                    upList.add(taskIp);
                }
            }
        }

        public String getIp() {
            String ip = null;
            synchronized (allIp) {
                ip = allIp.poll();
            }
            if (ip != null) {
                fetchedNum++;
            }
            return ip;
        }
    }
}


