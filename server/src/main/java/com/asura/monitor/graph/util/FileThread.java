package com.asura.monitor.graph.util;

import java.io.IOException;

import static com.asura.monitor.graph.util.FileWriter.makeDirs;

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

public class FileThread extends Thread {

    private String  file ;
    private String content;
    private boolean append;
    /**
     *
     * @param file
     * @param content
     */
    FileThread(String file, String content, boolean append){
        this.file = file;
        this.content = content;
        this.append = append;
    }

    @Override
    public void run() {
        String files = this.file.replace("..", "");
        makeDirs(file);
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(files, append);
            fileWriter.write(this.content + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
