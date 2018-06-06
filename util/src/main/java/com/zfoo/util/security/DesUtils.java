package com.zfoo.util.security;

/**
 * 用于加密和解密
 * <p>
 * DES是Data Encryption Standard（数据加密标准）的缩写。<br/>
 * 它是由IBM公司研制的一种对称密码算法，美国国家标准局于1977年公布把它作为非机要部门使用的数据加密标准。<br/>
 * 三十年来，它一直活跃在国际保密通信的舞台上，扮演了十分重要的角色
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.30 14:58
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public abstract class DesUtils {
    private static Key key;
    private static String KEY_STR = "myKey";

    private static String ENCRYPTION = "DES";
    private static String CHARSET_NAME = "UTF8";

    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ENCRYPTION);
            generator.init(new SecureRandom(KEY_STR.getBytes()));
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对str进行DES加密
     *
     * @param str
     * @return
     */
    public static String getEncryptString(String str) {
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byte[] strBytes = str.getBytes(CHARSET_NAME);
            Cipher cipher = Cipher.getInstance(ENCRYPTION);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return base64en.encode(encryptStrBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对str进行DES解密
     *
     * @param str
     * @return
     */
    public static String getDecryptString(String str) {
        BASE64Decoder base64De = new BASE64Decoder();
        try {
            byte[] strBytes = base64De.decodeBuffer(str);
            Cipher cipher = Cipher.getInstance(ENCRYPTION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptStrBytes = cipher.doFinal(strBytes);
            return new String(decryptStrBytes, CHARSET_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws Exception {

        String passWord = "aaaa";
        String encodePassWorld = getEncryptString(passWord);
        System.out.println(passWord + ":" + encodePassWorld);

        System.out.println(getDecryptString(encodePassWorld));
    }
}
