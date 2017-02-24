package com.asura.agent.configure;

import com.asura.agent.util.FileIoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationHome;

import java.io.File;
import java.util.ArrayList;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @since 1.0
 * @Date 2016-08-13 07:31:00
 * agent配置文件管理
 */
public class Configure {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static String path;
    private static String url;

    public Configure(){

        if(this.path==null){
            logger.info("设置配置文件路径");
            this.setPath();
        }

        // 设置api接口url
        if(this.getUrl()==null) {
            logger.info("设置url参数");
            this.setUrl(get("url"));
        }


    }



    /**
     * 设置配置文本的路径
     */
    public void setPath(){

        File file = new File(getJarPath());
        String p = file.getParent()+FileIoUtil.separator;
        this.path = p;
    }

    public static  String getPath(){
        return path;
    }
    /**
     * 获取配置文件的内容
     * @param name
     * @return
     */
    public static String get(String name){

        String config = getPath() + "../configure/agent.conf".replace("/",FileIoUtil.separator);
        ArrayList<String> conf = FileIoUtil.readTxtFile(config);
        for(String f:conf){
            if(f.startsWith(name)){
                String[] data = f.split(name+"=");
                return data[1];
            }
        }
        return "";
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Configure.url = url;
    }

    /**
     * 获取jar包的名字和绝对路径
     * @return
     */
    public static  String getJarPath() {
        // 获取jar的名字路径
        ApplicationHome home = new ApplicationHome(Configure.class);
        String path = home.getSource() == null ? "" : home.getSource().getAbsolutePath();
        return path;
    }
}
