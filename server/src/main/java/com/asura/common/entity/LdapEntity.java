package com.asura.common.entity;

/**
 * @author zy
 * @Date 2016-06-21
 * ldap登陆使用验证
 */
public class LdapEntity {

    private String ldapServer;
    private int ldapPort;
    private String ldapPrefix;
    private String ldapUsername;
    private String ldapPassword;
    private String ldapSearchBase;

    public String getLdapSearchBase() {
        return ldapSearchBase;
    }

    public void setLdapSearchBase(String ldapSearchBase) {
        this.ldapSearchBase = ldapSearchBase;
    }

    public String getLdapPassword() {
        return ldapPassword;
    }

    public void setLdapPassword(String ldapPassword) {
        this.ldapPassword = ldapPassword;
    }

    public String getLdapUsername() {
        return ldapUsername;
    }

    public void setLdapUsername(String ldapUsername) {
        this.ldapUsername = ldapUsername;
    }

    public String getLdapPrefix() {
        return ldapPrefix;
    }

    public void setLdapPrefix(String ldapPrefix) {
        this.ldapPrefix = ldapPrefix;
    }

    public String getLdapServer() {
        return ldapServer;
    }

    public void setLdapServer(String ldapServer) {
        this.ldapServer = ldapServer;
    }

    public int getLdapPort() {
        return ldapPort;
    }

    public void setLdapPort(int ldapPort) {
        this.ldapPort = ldapPort;
    }


}
