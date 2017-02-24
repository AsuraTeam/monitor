package com.asura.framework.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleUrlConnection {

	public static final String Mammoth_Signal = "mammothrequest";

	public static final String Mammoth_Ua = "NokiaN73";

	private String m_url = null;

	private Map<String, String> m_getpar = null;

	private Map<String, String> m_postpar = null;

	private byte[] m_postbody = null;

	private Map<String, String> m_requestheader = null;

	private String m_authorization = null;

	private int m_result = 500;

	private Map<String, List<String>> m_responseheader = null;

	private byte[] m_pagebody = null;

	private String m_responsemessage = null;

	private int m_conntimeout = 1000;

	private int m_readtimeout = 5000;

	private int m_responsebodysize;

	/**
	 * 创建对象
	 */
	public SimpleUrlConnection() {
		super();
	}

	/**
	 * 创建针对某个url访问的对象
	 * 
	 * @param url
	 */
	public SimpleUrlConnection(final String url) {
		super();
		setUrl(url);
	}

	/**
	 * 增加GET协议的参数
	 * 
	 * @param key
	 *            参数名称
	 * @param value
	 *            参数值
	 */
	public void addGetPar(final String key, final String value) {
		if (strIsRight(key) && strIsRight(value)) {
			if (m_getpar == null)
				m_getpar = new HashMap<String, String>();
			m_getpar.put(key, value);
		}
	}

	/**
	 * 增加POST的参数
	 * 
	 * @param key
	 *            参数名称
	 * @param value
	 *            参数值
	 */
	public void addPostPar(final String key, final String value) {
		if (strIsRight(key) && strIsRight(value)) {
			if (m_postpar == null)
				m_postpar = new HashMap<String, String>();
			m_postpar.put(key, value);
		}
	}

	/**
	 * 增加请求目标URL的HEAD参数
	 * 
	 * @param key
	 *            参数名称
	 * @param value
	 *            参数值
	 */
	public void addRequestHeader(final String key, final String value) {
		if (strIsRight(key) && strIsRight(value)) {
			if (m_requestheader == null)
				m_requestheader = new HashMap<String, String>();
			m_requestheader.put(key, value);
		}
	}

	private void collectResponseHeader(final HttpURLConnection urlconn) {
		m_responseheader = urlconn.getHeaderFields();
	}

	/**
	 * 提交链接请求
	 * 
	 * @return 目标URL返回的回应值（一般应为200）
	 * @throws Exception
	 */
	public int connect() throws Exception {
		return connect(true);
	}

	public int connect(final boolean readbody) throws Exception {
		// addRequestHeader(Mammoth_Signal, Mammoth_Signal);
		m_result = 510;
		if (m_url != null) {
			HttpURLConnection urlconn = null;
			OutputStream out = null;
			InputStream in = null;
			ByteArrayOutputStream brbuf = null;
			final String urlname = fixUrl(m_url, m_getpar);
			try {
				final URL url = new URL(urlname);
				if (url != null) {
					final String poststr = getPar(m_postpar);
					// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
					// InetSocketAddress("10.0.0.200",80));
					// urlconn = (HttpURLConnection)url.openConnection(proxy);
					urlconn = (HttpURLConnection) url.openConnection();
					if (urlconn != null) {
						urlconn.setInstanceFollowRedirects(true);
						urlconn.setConnectTimeout(m_conntimeout);
						urlconn.setReadTimeout(m_readtimeout);
						urlconn.setUseCaches(false);
						if (m_authorization != null)
							urlconn.addRequestProperty("Authorization", m_authorization);
						doWithHeader(urlconn, m_requestheader);
						urlconn.setDoInput(true);
						urlconn.setDefaultUseCaches(false);
						if (poststr.equals("") && (m_postbody == null))
							urlconn.setRequestMethod("GET");
						else {
							if (m_postbody == null)
								m_postbody = poststr.getBytes();
							urlconn.addRequestProperty("Content-Length", m_postbody.length + "");
							urlconn.setRequestMethod("POST");
							urlconn.setDoOutput(true);
							final byte[] bytebuf = m_postbody;
							if ((bytebuf != null) && (bytebuf.length > 0)) {
								out = urlconn.getOutputStream();
								if (out != null) {
									out.write(bytebuf);
									out.flush();
									out.close();
									out = null;
								}
							}
						}
						urlconn.connect();
						m_result = urlconn.getResponseCode();
						collectResponseHeader(urlconn);
						m_responsemessage = urlconn.getResponseMessage();
						in = urlconn.getInputStream();
						if (in != null) {
							if (readbody == false) {

								int len = Primitive.parseInt(urlconn.getHeaderField("Content-Length"), 0);
								if (len < 0)
									len = 0xFFFFFFF;
								final byte[] buffer = new byte[102400];
								m_responsebodysize = 0;
								for (int num = 0; (num = in.read(buffer)) > 0;) {
									m_responsebodysize += num;
									// System.out.println(m_responsebodysize);
								}
								in.close();
								in = null;
							} else {
								int len = Primitive.parseInt(urlconn.getHeaderField("Content-Length"), 0);
								if (len < 0)
									len = 0xFFFFFFF;
								brbuf = new ByteArrayOutputStream();
								final byte[] buffer = new byte[2048];
								for (int num = 0; (num = in.read(buffer)) > 0;) {
									brbuf.write(buffer, 0, num);
									if (brbuf.size() >= len)
										break;
								}
								in.close();
								in = null;
							}
						}
						urlconn.disconnect();
						urlconn = null;
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (brbuf != null) {
					if (brbuf.size() > 0)
						m_pagebody = brbuf.toByteArray();
					try {
						brbuf.close();
					} catch (final IOException e) {
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (final IOException e) {
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (final IOException e) {
					}
				}
				if (urlconn != null)
					urlconn.disconnect();
			}
		}
		return m_result;
	}

	private void doWithHeader(final HttpURLConnection urlconn, final Map<String, String> header) {
		boolean haveua = false;
		if ((urlconn != null) && (header != null) && (header.size() > 0)) {
			final Iterator<String> it = header.keySet().iterator();
			if (it != null) {
				while (it.hasNext()) {
					final String name = it.next();
					if ((name != null) && !name.equals("")) {
						final String value = header.get(name);
						if ((value != null) && !value.equals("")) {
							if (name.toLowerCase().equals("user-agent"))
								haveua = true;
							urlconn.addRequestProperty(name, value);
						}
					}
				}
			}
		}
		if (!haveua)
			urlconn.addRequestProperty("User-Agent", Mammoth_Ua);
	}

	private String fixUrl(String url, final Map<String, String> par) {
		final String parstr = getPar(par);
		if (!parstr.equals("")) {
			final int npos = url.indexOf('?');
			if (npos > 0) {
				if (!url.endsWith("?"))
					url += "&";
			} else
				url += "?";
			url += parstr;
		}
		return url;
	}

	/**
	 * 获得目标URL反馈的二进制文件体
	 * 
	 * @return 文件体
	 */
	public byte[] getBody() {
		return m_pagebody;
	}

	public String getBodyString() {
		return this.getBodyString("UTF-8");
	}

	public String getBodyString(final String encode) {
		if (m_pagebody != null) {
			if (encode != null) {
				try {
					return new String(m_pagebody, encode);
				} catch (final UnsupportedEncodingException e) {
				}
			} else
				return new String(m_pagebody);
		}
		return null;
	}

	/**
	 * 获得设置过的GET协议的参数值
	 * 
	 * @param key
	 *            参数名称
	 * @return 参数值
	 */
	public String getGetPar(final String key) {
		return m_getpar == null ? null : (String) m_getpar.get(key);
	}

	/**
	 * 获得目标URL反馈的HEAD的MAP对象
	 * 
	 * @return MAP对象
	 */
	public Map<String, List<String>> getHeader() {
		return m_responseheader;
	}

	/**
	 * 获得目标URL反馈的HEAD的参数值
	 * 
	 * @param key
	 *            参数名称
	 * @return 能找到的第一个此参数的参数值
	 */
	public String getHeader(final String key) {
		return getHeader(key, 0);
	}

	/**
	 * 获得目标URL反馈的HEAD的参数值
	 * 
	 * @param key
	 *            参数名称
	 * @param index
	 *            第几个（可能会反馈回多个同名的参数）
	 * @return 参数值
	 */
	public String getHeader(final String key, final int index) {
		if (m_responseheader != null) {
			final List<String> s = m_responseheader.get(key);
			if (s != null) {
				if (index < s.size())
					return s.get(index);
			}
		}
		return null;
	}

	/**
	 * 获得目标URL反馈的HEAD的参数名称数组
	 * 
	 * @return 参数名称数组
	 */
	public String[] getHeaderNames() {
		if ((m_responseheader != null) && (m_responseheader.size() > 0)) {
			final String[] list = new String[m_responseheader.size()];
			m_responseheader.keySet().toArray(list);
			return list;
		}
		return null;
	}

	/**
	 * 获得访问时产生的一些消息
	 * 
	 * @return 消息内容
	 */
	public String getMessage() {
		return m_responsemessage;
	}

	private String getPar(final Map<String, String> m) {
		if ((m != null) && (m.size() > 0)) {
			final StringBuffer par = new StringBuffer();
			final Iterator<String> it = m.keySet().iterator();
			if (it != null) {
				while (it.hasNext()) {
					final String name = it.next();
					if ((name != null) && !name.equals("")) {
						final String value = m.get(name);
						if ((value != null) && !value.equals(""))
							par.append("&").append(name).append("=").append(Check.encodeUrl(value));
					}
				}
			}
			if (par.length() > 0)
				return par.substring(1).toString();
		}
		return "";
	}

	/**
	 * 获得曾经设置过的POST的参数
	 * 
	 * @param key
	 *            参数名称
	 * @return 参数值
	 */
	public String getPostPar(final String key) {
		return m_postpar == null ? null : (String) m_postpar.get(key);
	}

	public int getResponseBodySize() {
		return m_responsebodysize;
	}

	/**
	 * 获得目标URL反馈的回应值
	 * 
	 * @return 回应值（一般应为200）
	 */
	public int getResult() {
		return m_result;
	}

	public void setAuthorization(final String authorization) {
		if (authorization != null) {
			m_authorization = authorization;
			if (!m_authorization.toLowerCase().startsWith("basic "))
				m_authorization = "Basic " + m_authorization;
		}
	}

	public void setAuthorization(final String username, final String password) {
		//		if ( (username != null) && (password != null) )
		//		{
		//			final BASE64Encoder encoder = new BASE64Encoder();
		//			m_authorization = "Basic "
		//					+ JspTools.removeAll(encoder
		//							.encodeBuffer((username + ":" + password)
		//									.getBytes()), "\n");
		//		}
	}

	/**
	 * 设置要POST的二进制流，替代POST的参数
	 * 
	 * @param body
	 *            二进制包体
	 */
	public void setPostBody(final byte[] body) {
		m_postbody = body;
	}

	/**
	 * 设置超时
	 * 
	 * @param conn
	 *            连接超时（毫秒）
	 * @param read
	 *            读超时（毫秒）
	 */
	public void setTimeout(final int conn, final int read) {
		m_conntimeout = conn;
		m_readtimeout = read;
	}

	/**
	 * 设置访问的目标url
	 * 
	 * @param url
	 */
	public void setUrl(final String url) {
		m_url = url;
	}

	private boolean strIsRight(final String str) {
		return (str != null) && !str.equals("");
	}
}
