package com.asura.agent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 * @Date 2016-08-13 07:33:00
 * 文件路径，文件内容工具
 */
public class FileIoUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileIoUtil.class);

    public static String separator = System.getProperty("file.separator");


    /**
     * 已arrayList 返回文件内容
     *
     * @param filePath
     *
     * @return
     */
    public static ArrayList readTxtFile(String filePath) {

        ArrayList arr = new ArrayList();
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
                    arr.add(lineTxt);
                }
                read.close();
            } else {
                logger.warn("找不到指定的文件" + filePath);
            }
        } catch (Exception e) {
            logger.warn("读取文件内容出错" + filePath);
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 获取文件
     *
     * @param dir
     *
     * @return
     */
    public static ArrayList getDirFiles(String dir) {
        ArrayList fileArr = new ArrayList();
        File file = new File(dir);
        logger.info(dir);
        File[] files = file.listFiles();
        if (files == null) {
            return fileArr;
        }
        for (File f : files) {
            if (f.isFile()) {
                fileArr.add(dir + separator + f.getName());
            }
        }
        return fileArr;
    }

    /**
     * @param file
     *         文件名
     * @param content
     *         文件内容
     */
    public static void writeFile(String file, String content, boolean append) {
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file, append);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            logger.error("文件写入出错:" + file);
            logger.error(e.toString());

        }
    }

    /**
     * 文件复制
     *
     * @param oldFile
     * @param newFile
     *
     * @throws Exception
     */
    public static void copyFile(String oldFile, String newFile) throws Exception {
        File file1 = new File(oldFile);
        int byteRead;
        FileOutputStream fs = new FileOutputStream(newFile);
        InputStream inputStream = new FileInputStream(file1);
        byte[] buffer = new byte[1024];
        while ((byteRead = inputStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteRead);
        }
        fs.close();
        inputStream.close();
    }

   public static void makeDir(String dir){
        File folder = new File(dir);
        if(!folder.exists() || !folder.isDirectory()){
            folder.mkdirs();
        }
    }

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
                    result += lineTxt;
                }
                read.close();
            } else {
                logger.warn("找不到指定的文件" + filePath);
            }
        } catch (Exception e) {
            logger.warn("读取文件内容出错" + filePath);
        }
        return result;
    }
}