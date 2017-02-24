/**
 * @FileName: EncryptionUtil.java
 * @Package com.asura.framework.util
 * @author zhangshaobin
 * @created 2012-12-18 下午5:04:40
 * <p>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>加密工具类</p>
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
public class EncryptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

    private static final String AES_TYPE = "AES/ECB/PKCS5Padding";

    /**
     *
     * 采用MD5方式加密字符串
     *
     * @author zhangshaobin
     * @created 2012-12-18 下午5:07:45
     *
     * @param source    要加密的字符串
     * @return 加密后的结果
     */
    public static String MD5(String source) {
        // 32位加密md
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(source.getBytes()); // 更新
        byte[] bt = md.digest(); // 摘要

        // 保留结果的字符串
        StringBuilder sb = new StringBuilder();
        int p = 0;
        for (int i = 0; i < bt.length; i++) {
            p = bt[i];
            if (p < 0)
                p += 256; // 负值时的处理
            if (p < 16)
                sb.append("0");
            sb.append(Integer.toHexString(p));// 转换成16进制
        }
        return sb.toString();
    }

    /**
     * @Description: AES 加密
     * @param  keyStr 密钥
     * @param  plainText 加密数据
     * @return String 加密完数据
     * @throws
     * @author liuxm
     * @date 2014年11月21日
     */
    public static String AESEncrypt(String keyStr, String plainText) throws Exception {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
            return new String(Base64.encodeBase64(encrypt));
        } catch (Exception e) {
            LOGGER.error("aes encrypt failure : {}", e);
            throw e;
        }
    }

    /**
     * @Description: 解密
     * @param  keyStr 密钥
     * @param  encryptData 解密数据
     * @return String
     * @throws
     * @author liuxm
     * @date 2014年11月21日
     */
    public static String AESDecrypt(String keyStr, String encryptData) throws Exception {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes()));
            return new String(decrypt).trim();
        } catch (Exception e) {
            LOGGER.error("aes decrypt failure : {}", e);
            throw e;
        }
    }

    /**
     * @Description: 封装KEY值
     * @param key
     * @author liuxm
     * @date 2014年11月21日
     */
    private static Key generateKey(String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            LOGGER.error("get aes key failure : {}", e);
            throw e;
        }
    }

    /**
     * DES加密
     *
     * @param content
     * @return
     */
    public static String DESEncrypt(String ivString, String keyString, String content) {
        try {
            if (Check.NuNStr(content)) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            DESKeySpec dks = new DESKeySpec(keyString.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));
            return byteArr2HexStr(result);
        } catch (Exception e) {
            LOGGER.error("ENCRYPT ERROR:"+e);
        }
        return null;
    }

    public static String DESDecrypt(String ivString, String keyString, String content) {
        try {
            if (Check.NuNStr(content)) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            DESKeySpec dks = new DESKeySpec(keyString.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(hexStr2ByteArr(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            LOGGER.error("ENCRYPT ERROR:"+e);
        }
        return null;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private static String byteArr2HexStr(byte[] arrB){
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    private static byte[] hexStr2ByteArr(String strIn){
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

}
