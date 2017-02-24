package com.asura.monitor.graph.util;

import com.asura.monitor.graph.entity.PushEntity;
import com.asura.util.DateUtil;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 文件写入类
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/07 09:19
 * @since 1.0
 */
public class FileWriter {

    private static Logger logger = Logger.getLogger(FileRender.class);

    private static DateUtil dateUtil = new DateUtil();

    // 默认用这个，如果需要更新，改掉就行
    public static String dataDir = System.getProperty("user.home");

    // 获取文件分割符号，window \ linux /
    public static String separator = System.getProperty("file.separator");


    /**
     * @param dir
     */
    static void makeDir(String dir) {
        File folder = new File(dir);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
    }

    /**
     * @param file
     */
    public static void makeDirs(String file) {
        String dir = file.substring(0, file.lastIndexOf(separator));
        makeDir(dir);
    }

    /**
     * @param file
     *         文件名
     * @param content
     *         文件内容
     */
    public static void writeFile(String file, String content, boolean append) {
//        file = file.replace("..", "");
//        makeDirs(file);
//        try {
//            java.io.FileWriter fileWriter = new java.io.FileWriter(file, append);
//            fileWriter.write(content + "\n");
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 防止等待磁盘
        FileThread thread = new FileThread(file, content, append);
        thread.start();
        try {
            for (int i=0 ; i< 1000 ; i++) {
                if(thread.isAlive()) {
                    Thread.sleep(2);
                }
            }
            if (thread.isAlive()) {
                thread.stop();
                logger.error("退出文件写入线程...");
            }
        }catch (Exception e){
            thread.interrupt();
            e.printStackTrace();
        }
    }

    /**
     *
     * @param type
     * @param ip
     * @param name
     * @return
     */
    public static String getGraphFile(String type, String ip, String name){
        // 拼接文件目录
        String dir = dataDir + separator + "graph" + separator +
                ip + separator +
                type + separator +
                dateUtil.getDate("yyyy") + separator +
                dateUtil.getDate("MM") + separator +
                dateUtil.getDate("dd") + separator +
                separator + name;
        return dir;
    }

    /**
     * 历史数据放到文件
     *
     * @param type
     * @param ip
     * @param name
     * @param value
     */
    public static void writeHistory(String type, String ip, String name, String value) {

        // 拼接文件目录
        String dir = dataDir + separator + "graph" + separator +
                ip + separator +
                type + separator +
                dateUtil.getDate("yyyy") + separator +
                dateUtil.getDate("MM") + separator +
                dateUtil.getDate("dd") + separator +
                separator + name;

        // 将值组装成固定的时间和数据
        String content = DateUtil.getDateStampInteter() + "000 " + value.trim();
        writeFile(dir, content, true);
    }

    /**
     * 替换掉特殊字符
     *
     * @param content
     *
     * @return
     */
    String replace(String content) {
        content = content.replace("\\n", " ");
        return content;
    }


    /**
     * @param ip
     * @param name
     * @return
     */
    public  static  String getMonitorFile(String ip, String name){

        // 拼接文件目录
        String file = dataDir + separator + "monitor" + separator +
                ip + separator +
                dateUtil.getDate("yyyy") + separator +
                dateUtil.getDate("MM") + separator +
                dateUtil.getDate("dd") + separator +
                separator + name;
        return file;
    }

    /**
     * 将监控数据写入到文件
     *
     * @param ip
     * @param name
     * @param entity
     */
    public static void writeMonitorHistory(String ip, String name, PushEntity entity) {

        // 拼接文件目录
        String file = getMonitorFile(ip, name);
        if (entity.getTime() == null) {
            entity.setTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        }

        String content = DateUtil.dateToStamp(entity.getTime()) + " ";
        if(entity.getMessages()!=null) {
            content += "[" + entity.getMessages().replaceAll("(\r\n|\r|\n|\n\r)", "") + "]";
        }
        content = content.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "");
        content = content.replaceAll("(\r\n|\r|\n|\n\r)", "");
        writeFile(file, content, true);
    }

    /**
     * 错误日志写入
     * @param entity
     */
    public static void writeErrorHistory(PushEntity entity) {
        // 拼接文件目录
        String file = dataDir + separator + "monitor" + separator +
                dateUtil.getDate("yyyy") + separator +
                dateUtil.getDate("MM") + separator +
                dateUtil.getDate("dd") + separator +"error";

        if (entity.getTime() == null) {
            entity.setTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        }

        String content = entity.getTime() + " ";
        content += entity.getCommand() + " ";
        content += entity.getStatus() + " ";
        content += entity.getValue() + " ";
        content += "[" + entity.getMessages().replace("\\n", " ") + "]";
        writeFile(file, content, true);

    }

//    public static void main(String[] args) {
//        FileWriter.writeFile("1.txt","a",false);
//    }

    /**
     * 获取系统信息的希尔目录
     * @return
     */
    public static String getSysInfoDir(){
        String dir = dataDir + separator + "graph" + separator
                +"sysinfo"
                +separator
                +DateUtil.getDate("yyyy")
                +separator
                +DateUtil.getDate("MM")
                +separator
                +DateUtil.getDate("dd") + separator;
        return dir;
    }

    /**
     * 获取指定指标数据
     * @return
     */
    public static String getIndexData(String ip, String type, String name,int dataLen){
        String data  ="";
        String file = dataDir + separator + "graph" + separator
                +ip + separator + type
                +separator
                +DateUtil.getDate("yyyy")
                +separator
                +DateUtil.getDate("MM")
                +separator
                +DateUtil.getDate("dd") + separator + name;
        try {
           String lastData =  FileRender.readLastLine(file);
            if (lastData!= null){
                String[] datas = lastData.split(" ");
                data = datas[dataLen];
            }
        }catch (Exception e){
             e.printStackTrace();
        }
        return data;
    }

    /**
     *
     * @param datas
     * @param ip
     * @param start
     * @return
     */
    public  static String getItemData(String datas, String ip, int start){
        try {
            String[] items = datas.split("\\|");
            String data = FileWriter.getIndexData(ip, items[0], items[1], start);
            return data.trim();
        }catch (Exception e){
            return "";
        }
    }
}