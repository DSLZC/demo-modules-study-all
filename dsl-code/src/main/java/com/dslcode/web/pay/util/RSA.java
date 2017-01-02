package com.dslcode.web.pay.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by dongsilin on 2016/12/1.
 */
public class RSA {

    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * RSA加密
     * @param content 需要加密的内容
     * @param privateKey 私密
     * @return
     */
    public static String RSAEncode(String content, String privateKey){

        try {

            PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, privateKey);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * RSA验签名检查
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 支付宝公钥
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            byte[] encodedKey = publicKey.getBytes(DEFAULT_CHARSET);
            encodedKey = Base64.decodeBase64(encodedKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = privateKey.getBytes(DEFAULT_CHARSET);

        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

}
