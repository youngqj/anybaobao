package com.interesting.common.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Security;

/**
 * ClassName SM4
 * Create by jc
 * Date 2022/6/16 15:45
 * Description
 */
@Component
public class SM4Util {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static String key;

    @Value("${sm4_key}")
    private void setKey(String sm4_key) {
        SM4Util.key = sm4_key;
    }

    public String encryptSm4(String plaintext) {
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());
        return sm4.encryptHex(plaintext);
    }

    public String decryptSm4(String ciphertext) {
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());
        return sm4.decryptStr(ciphertext);
    }

}
