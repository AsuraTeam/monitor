package com.asura.monitor.graph.thread;

import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.MergerDataUtil;
import com.asura.resource.entity.CmdbResourceServerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.asura.monitor.graph.util.FileRender.getSubDir;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class MergerThread extends Thread {

    private List<CmdbResourceServerEntity> ips;
    ArrayList<Integer> arrayList;

    public MergerThread(List<CmdbResourceServerEntity> ips, ArrayList<Integer> arrayList) {
        this.ips = ips;
        this.arrayList = arrayList;
    }

    public void run() {
        String ip;
        for (CmdbResourceServerEntity entity : this.ips) {
            ip = entity.getIpAddress();
            long start = System.currentTimeMillis() / 1000;
            ArrayList dir = getSubDir(ip);
            // 获取所有的类型
            Map<String, ArrayList> map = FileRender.getGraphName(dir, ip);
            executor(map, ip, start);
        }
    }

    void executor(Map<String, ArrayList> map, String ip, Long start){
        String groups;
        String name;
        for (Map.Entry<String, ArrayList> entity1 : map.entrySet()) {
            groups = entity1.getKey();
            ArrayList names = entity1.getValue();
            for (Integer day : arrayList) {
                ExecutorService executors = Executors.newFixedThreadPool(300);
                for (int i = 0; i < names.size(); i++) {
                    name = (String) names.get(i);
                    MergerDataUtil dataMergerUtil = new MergerDataUtil(groups, name, day, ip);
                    executors.execute(dataMergerUtil);
                }
                executors.shutdown();
                try {
                    while (!executors.isTerminated()) {
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                }
            }
        }
        System.out.println(ip + " end  " + (System.currentTimeMillis() / 1000 - start));
    }
}
