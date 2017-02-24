package com.asura.common.entity;

import org.springframework.stereotype.Component;

/**
 * 
 * @author zy
 * @Date 2016-06-21
 * 
 */
@Component
public class RedisEntity {


	private String url;
	private String app;
	private int port;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
