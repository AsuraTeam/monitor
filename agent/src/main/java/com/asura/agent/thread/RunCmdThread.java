package com.asura.agent.thread;

import com.asura.agent.util.CommandUtil;

import java.util.List;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 脚本使用线程方式调用，设置超时时间关闭线程，防止把程序拖挂
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class RunCmdThread extends Thread{

    private String cmd;
    private List<String> result;

    public RunCmdThread(String cmd, List<String> result) {
        this.cmd = cmd;
        this.result = result;
    }

    public void run(){
        String cmdResult = CommandUtil.execScript(cmd);
        if (cmdResult != null && cmdResult.length()>0){
            result.add(cmdResult);
            return;
        }else{
            result.add("00");
        }
    }
}
