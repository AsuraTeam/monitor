package com.asura.monitor.configure.conf;

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

public class SevertityConfig {

    /**
     * 获取处理进度
     * @param id
     * @return
     */
    public static String  getStatus(String id){
        String status  = "";
        switch (id){
            case "1":
                status = "开始处理";
                break;
            case "2":
                status = "处理中";
                break;
            case "3":
                status = "处理完成";
                break;
        }
        return status;
    }

    public static String getSevertity(int id, boolean color){
        String status = "";
        String colors = "";
        switch (id){
            case 1:
                status = "正常";
                if (color){
                   colors = "#1ab394";
                }
                break;
            case 2:
                status = "危险";
                if (color){
                    colors = "#ed5565";
                }
                break;
            case 3:
                status = "警告";
                if (color){
                    colors = "#f8ac59";
                }
                break;
            case 4:
                status = "未知";
                if (color){
                    colors = "#bababa";
                }
                break;
        }
        if(color) {
            return "<font color='" + colors + "'>" + status + "</font>";
        }
        return status;
    }
}
