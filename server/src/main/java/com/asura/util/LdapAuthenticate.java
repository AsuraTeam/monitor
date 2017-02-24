package com.asura.util;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;
import com.asura.common.entity.LdapEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author zy
 * @Date 2016-06-21 ldap登陆验证
 */
@Component
public class LdapAuthenticate {

	@Resource(name = "ldapConfig")
	private LdapEntity ldapDao ;

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LdapAuthenticate.class);


	/**
	 * 连接LDAP
	 * 
	 * @throws LDAPException
	 */
	@SuppressWarnings("deprecation")
	public boolean connetLDAP(String username, String password)
			throws LDAPException {
		LDAPConnection ldapConnection = new LDAPConnection();
		// 连接Ldap需要的信息
		ldapConnection.connect(ldapDao.getLdapServer(), ldapDao.getLdapPort());
		try {
			ldapConnection.bind(ldapDao.getLdapPrefix() + username, password);
			boolean result =  ldapConnection.isBound();
			ldapConnection.clone();
			ldapConnection.disconnect();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}


	/**
	 *
	 * @param keys
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public Map ldapSearch(String[] keys, String username) {
		LDAPConnection lc = new LDAPConnection();
		Map map = new HashMap<>();
		try {
			lc.connect(ldapDao.getLdapServer(), ldapDao.getLdapPort());
			lc.bind(3, ldapDao.getLdapPrefix() + ldapDao.getLdapUsername(), ldapDao.getLdapPassword().getBytes("UTF8"));
			String searchBase =  ldapDao.getLdapSearchBase();
			String searchFilter = username;
			int searchScope = 2;
			LDAPSearchResults searchResults = lc.search(searchBase, searchScope, searchFilter, keys, false);

			while (searchResults.hasMore()) {        //LDAPSearchResults 实现了 collection 接口
				LDAPEntry le = searchResults.next();      //结果集中每个内容都是一个 LDAPEntry 对象
				System.out.println(le.getDN());
				LDAPAttributeSet attributeSet = le.getAttributeSet();  //通过 LDAPEntry 对象来获取 LDAPAttributeSet 对象
				Set sortedAttributes = new TreeSet(attributeSet);
				Iterator allAttributes = sortedAttributes.iterator();

				while (allAttributes.hasNext()) {
					LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
					String attributeName = attribute.getName();   //获取参数名
					System.out.println("\t\t" + attributeName);
					Enumeration allValues = attribute.getStringValues();//其参数值可以为多个，利用Enumeration 列出全部该属性的值
					if (allValues != null) {
						while (allValues.hasMoreElements()) {
							String Value = (String) allValues.nextElement();
							System.out.println("\t\t\t" + Value);
							for (String key : keys) {
								if (key.equals(attributeName)) {
									map.put(attributeName, Value);
								}
							}
						}
					}
				}

			}
			lc.disconnect();
		}catch (Exception e){
			logger.error("ldap错误", e);
		}
		return map;
	}

	/**
	 *
	 * @param keys
	 * @param username
     * @return
     */
	public List ldapSearchList(String[] keys, String username) {
		LDAPConnection lc = new LDAPConnection();
		List<Map> list = new ArrayList<>();

		try {
			lc.connect(ldapDao.getLdapServer(), ldapDao.getLdapPort());
			lc.bind(3, ldapDao.getLdapPrefix() + ldapDao.getLdapUsername(), ldapDao.getLdapPassword().getBytes("UTF8"));
			String searchBase =  ldapDao.getLdapSearchBase();
			String searchFilter = username;
			int searchScope = 2;
			LDAPSearchResults searchResults = lc.search(searchBase, searchScope, searchFilter, keys, false);

			while (searchResults.hasMore()) {        //LDAPSearchResults 实现了 collection 接口
				LDAPEntry le = searchResults.next();      //结果集中每个内容都是一个 LDAPEntry 对象
				System.out.println(le.getDN());
				LDAPAttributeSet attributeSet = le.getAttributeSet();  //通过 LDAPEntry 对象来获取 LDAPAttributeSet 对象
				Set sortedAttributes = new TreeSet(attributeSet);
				Iterator allAttributes = sortedAttributes.iterator();

				while (allAttributes.hasNext()) {
					Map map = new HashMap<>();
					LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
					String attributeName = attribute.getName();   //获取参数名
					System.out.println("\t\t" + attributeName);
					Enumeration allValues = attribute.getStringValues();//其参数值可以为多个，利用Enumeration 列出全部该属性的值
					if (allValues != null) {
						while (allValues.hasMoreElements()) {
							String Value = (String) allValues.nextElement();
							System.out.println("\t\t\t" + Value);
							map.put(attributeName, Value);
							list.add(map);
						}
					}
				}

			}
			lc.disconnect();
		}catch (Exception e){
			logger.error("ldap错误", e);
		}
		return list;
	}

	/**
	 * 获取ldap返回数据的值
	 * @param map
	 * @param key
     * @return
     */
	public String getMap(Map<String,String> map, String key){
		for (Map.Entry<String,String> entry:map.entrySet()){
			if (entry.getKey().equals(key)){
			   return entry.getValue();
			}
		}
		return null;
	}

	/**
	 *
	 * @param key
	 * @param user
     * @return
     */
	public String getSignUserInfo(String key, String user){
		Map map = ldapSearch(new String[]{key}, user);
		return getMap(map, key);
	}

}
