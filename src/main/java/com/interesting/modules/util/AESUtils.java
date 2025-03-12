package com.interesting.modules.util;


import com.alibaba.druid.util.StringUtils;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES工具类
 * <pre>
 *    因为某些国家的进口管制限制，Java发布的运行环境包中的加解密有一定的限制。比如默认不允许256位密钥的AES加解密，解决方法就是修改策略文件。
 *   替换的文件：%JDK_HOME%\jre\lib\security\local_policy.jar
 *   参考： http://czj4451.iteye.com/blog/1986483
 *
 * @version 1.0
 */
public class AESUtils {
    // 密钥
    public static String key = "AD42F6697B035B75";
//    public static String key = "C5C1E2CE01694C72ACF7DE8BB126AC67";
    private static String charset = "utf-8";
    /**
     * 设置偏移量
     * 本例采用ECB加密模式，不需要设置偏移量
     */
    private static int offset = 0;
    private static String transformation = "AES/ECB/PKCS5Padding";
    private static String algorithm = "AES";
    private static String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncryptWithBase64(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encodeBuffer(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * @param accessToken
     * @param targetUserId
     * @param userNick
     * @param userNo
     * @return md5sum
     */
    public static String md5Sign(String accessToken, String targetUserId, String userNick, String userNo, String expiredTime) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accessToken=").append(accessToken);

        if (expiredTime != null) {
            stringBuilder.append("&").append("expiredTime=").append(expiredTime);
            System.out.println("expiredTime: " + expiredTime);
        }

        if (targetUserId != null) {
            stringBuilder.append("&").append("targetUserId=").append(targetUserId);
        }

        stringBuilder.append("&").append("userNick=").append(userNick)
                .append("&").append("userNo=").append(userNo);

        return base64Encode(DigestUtils.md5Digest(stringBuilder.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) {
        String passWord = "wangdun";
        try {
            String base64 = aesEncryptWithBase64(passWord, AESUtils.key);
            System.out.println("加密后："+base64);
            String aesDecrypt = AESUtils.aesDecrypt(base64, AESUtils.key);
            System.out.println("解密后: "+aesDecrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}