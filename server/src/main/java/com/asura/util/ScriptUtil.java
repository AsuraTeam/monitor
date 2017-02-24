package com.asura.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

public class ScriptUtil {

    private static Logger logger = Logger.getLogger(ScriptUtil.class);
    private static Runtime runtime = Runtime.getRuntime();

    /**
     * 脚本执行
     *
     * @param command
     *
     * @return
     */
    public  static  void  run(String command) {
        String result = "";
        String line = "";
        try {
            logger.info("run 获取到脚本 " + command);
            Process process = runtime.exec(command);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                logger.info(line);
                result += line;
            }
            logger.info("脚本执行结果 " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(command + " run faild");
        }
    }
}
