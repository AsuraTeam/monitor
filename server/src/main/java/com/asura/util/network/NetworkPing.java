package com.asura.util.network;

import com.asura.resource.entity.CmdbResourceNetworkAddressEntity;
import com.asura.resource.service.CmdbResourceNetworkAddressService;
import com.asura.util.DateUtil;

import java.util.ArrayList;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 实现ping
 * @author zhaoyun
 * @version 1.0
 * @since 1.0
 */
public class NetworkPing extends Thread{

    private CmdbResourceNetworkAddressService service;
    private int networkId;
    private static ArrayList addList;
    private String networkPrefix;


    public NetworkPing(CmdbResourceNetworkAddressService service, int networkId, ArrayList addList,String networkPrefix){
        this.service = service;
        this.networkId = networkId;
        this.addList = addList;
        this.networkPrefix = networkPrefix;

    }



    public void run(){
        for(int i=1;i<254;i++) {
            CmdbResourceNetworkAddressEntity entity = new CmdbResourceNetworkAddressEntity();
            Ping ping = new Ping();
            int res = ping.execPing(this.networkPrefix + "." +i );

            entity.setIpSubffix(i);
            entity.setIpPrefixId(networkId);
            entity.setStatus(res);
            entity.setLastModifyTime(DateUtil.getTimeStamp());
            if (addList.contains(this.networkId + "" + i)) {
                service.update(entity);
            } else {
                service.save(entity);
            }

        }
        try {
            super.interrupt();
        } catch (Exception e) {

        }
    }


}
