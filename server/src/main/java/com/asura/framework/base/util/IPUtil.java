/**
 * @FileName: IPUtil.java
 * @Package com.asura.framework.base.util
 * 
 * @author zhangshaobin
 * @created 2015年1月21日 上午1:14:45
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class IPUtil {
	
	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	public static final String LOCALHOST = "127.0.0.1";

	public static final String ANYHOST = "0.0.0.0";
	
	
	
	/**
	 * 
	 * 验证是不是有效的ip地址
	 *
	 * @author zhangshaobin
	 * @created 2015年1月21日 上午1:16:21
	 *
	 * @param address
	 * @return
	 */
	private static boolean isValidAddress(InetAddress address) {
		if (address == null || address.isLoopbackAddress())
			return false;
		String name = address.getHostAddress();
		return (name != null && !ANYHOST.equals(name) && !LOCALHOST.equals(name) && IP_PATTERN.matcher(name).matches());
	}
	
	
	/**
	 * 
	 * 获取本地ip地址
	 *
	 * @author zhangshaobin
	 * @created 2015年1月21日 上午1:16:44
	 *
	 * @return
	 */
	public static InetAddress getLocalAddress() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				return localAddress;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					try {
						NetworkInterface network = interfaces.nextElement();
						Enumeration<InetAddress> addresses = network.getInetAddresses();
						if (addresses != null) {
							while (addresses.hasMoreElements()) {
								try {
									InetAddress address = addresses.nextElement();
									if (isValidAddress(address)) {
										return address;
									}
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return localAddress;
	}
	
	public static void main(String []args) {
		System.out.println(getLocalAddress().getHostAddress());
	}

}
