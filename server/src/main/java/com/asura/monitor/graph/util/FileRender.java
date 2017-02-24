package com.asura.monitor.graph.util;

import com.google.gson.Gson;
import com.asura.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 文件读取
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/07 16:19:11
 * @since 1.0
 */
public class FileRender {


    private static Logger LOGGER = LoggerFactory.getLogger(FileRender.class);

    private static Gson gson = new Gson();

    // 默认用这个，如果需要更新，改掉就行
    private static  String dataDir = FileWriter.dataDir;

    // 获取文件分割符号，window \ linux /
    public static String separator = FileWriter.separator;

    // 获取临时文件目录
    private static String tempDir = System.getProperty("java.io.tmpdir") + separator + "monitor" + separator;

    /**
     * 计算百分比
     * @param totle
     * @param number
     * @return
     */
    public static  double getPercent(String totle, double number){
        double result = number/Integer.valueOf(totle)*100;
        String r = String.format("%.2f",result);
        return Double.parseDouble(r);
    }


    /**
     * 去掉文件读取的特殊字符，防止文件路径读取
     * @param str
     * @return
     */
    public static String replace(String str){
        str = str.replace("\\","");
        str = str.replace("..","");
        str = str.replace("\\.\\.","");
        str = str.replace("`","");
        str = str.replace("(","");
        str = str.replace(")","");
        str = str.replace("&","");
        str = str.replace("[","");
        str = str.replace("]","");
        return str;
    }

    /**
     * 文件读取
     * @param filePath
     * @param arr
     * @param totle
     * @return
     */
    public static ArrayList readTxtFile(String filePath,ArrayList arr,String totle){

        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(lineTxt.length()<10){
                        continue;
                    }
                    String[] d = lineTxt.split(" ");
                    ArrayList data = new ArrayList();
                    data.add(Long.valueOf(d[0]));
                    if(totle==null) {
                        data.add(Double.valueOf(d[1]));
                    }else{
                        data.add(getPercent(totle,Double.valueOf(d[1])));
                    }
                    arr.add(data);
                }
                read.close();
            }else{
                LOGGER.warn("找不到指定的文件"+filePath);
            }
        } catch (Exception e) {
            LOGGER.warn("读取文件内容出错"+filePath);
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList readHistory(String ip,String type,String name,String startT, String endT,String totle){
        ip = replace(ip);
        type = replace(type);
        name = replace(name);
        ArrayList arr = new ArrayList();
        String[] tt = new String[3];
        name = name.replace("---",separator);
        ArrayList<String> lDate = findDates(startT, endT);
        String[] checkTS = startT.split("-");
        String[] checkTE = startT.split("-");
        for(String s :checkTS){
            try{
               Integer.parseInt(s);
            }catch (Exception e){
                return arr;
            }
        }
        for(String s :checkTE){
            try{
                Integer.parseInt(s);
            }catch (Exception e){
                return arr;
            }
        }
        for(String d:lDate) {
            tt = d.split("-");
            // 拼接文件目录
            String dir = dataDir + separator + "graph" + separator +
                    ip + separator +
                    type + separator +
                    tt[0] + separator +
                    tt[1] + separator +
                    tt[2] + separator +
                    separator + name;
            readTxtFile(dir,arr,totle);
        }
        return arr;

    }

    public static ArrayList<String> findDates(String start, String end)  {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dBegin = sdf.parse(start);
            Date dEnd = sdf.parse(end);

            List<Date> lDate = new ArrayList();
            lDate.add(dBegin);
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(calBegin.getTime());
            }

            ArrayList result = new ArrayList();
            for (Date date : lDate) {
                result.add(sdf.format(date));
            }
            return result;
        }catch (Exception e){

        }
        return null;
    }


    /**
     * 获取某个IP地址的数据类型
     * @return
     */
    public static ArrayList getSubDir(String ip){

        // 拼接文件目录
        String dir = dataDir + separator + "graph" + separator +
                ip + separator ;
        ArrayList arrayList = new ArrayList();

        File[] fileList = getDirFiles(dir);
        if(fileList==null){
            return arrayList;
        }
        for(File f:fileList){
            if(f.isDirectory()){
                arrayList.add(f.getName());
            }
        }
        return arrayList;
    }

    /**
     * 获取目录下的文件
     * @param dir
     * @return
     */
    public static File[] getDirFiles(String dir){
        File file = new File(dir);
        File[] fileList = file.listFiles();
        return fileList;
    }


    /**
     *
     * @param ip
     * @return
     */
    public static String getStatusDir( String ip, String d){
        String startT = DateUtil.getDay();
        String[] tt = startT.split("-");
        // 拼接文件目录
        String fileDir = dataDir + separator + "graph" + separator +
                ip + separator ;
        String fdir = fileDir+d+separator+tt[0] + separator +
                tt[1] + separator +
                tt[2] + separator;
        return fdir;
    }

    /**
     *
     * @param dir
     * @param ip
     * @return
     */
    public static Map getGraphName(ArrayList<String> dir, String ip){
        Map map = new HashMap<>();
        for(String d :dir){
            String fdir = getStatusDir(ip, d);
            File[] fileList = getDirFiles(fdir);
            if(fileList==null){
                continue;
            }
            ArrayList<String> tArr  = new ArrayList();
            for (File f:fileList){
                if(f.isDirectory()) {
                    File[] subFile = getDirFiles(fdir+f.getName());
                    for (File subF : subFile) {
                        tArr.add(f.getName()+"---"+subF.getName());
                    }
                }else {
                    tArr.add(f.getName());
                }
            }
            map.put(d,tArr);
        }

        return  map;
    }


    public static String readLastLine(String files) throws IOException {
        File file = new File(files);
        String charset = "utf-8";
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                if (charset == null) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset);
                }
            }
        } catch (FileNotFoundException e) {
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e2) {
                }
            }
        }
        return null;
    }

    /**
     * 检查缓存文件，并获取内容
     * @param name
     * @param interval
     * @return
     */
    public  static  boolean checkCacheFileTime(String name, long interval){
        String fileName = tempDir + separator + name;
        File file = new File(fileName);
        if (file.exists()){
            if (System.currentTimeMillis()/1000 - file.lastModified()/100 > interval){
                return true;
            }else {
                return false;
            }
        }else {
            return true;
        }
    }

    /**
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        String  result = "";
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.length() < 10) {
                        continue;
                    }
                    result += lineTxt+"\n";
                }
                read.close();
            } else {
                LOGGER.warn("找不到指定的文件" + filePath);
            }
        } catch (Exception e) {
            LOGGER.warn("读取文件内容出错" + filePath);
        }
        return result;
    }

}
