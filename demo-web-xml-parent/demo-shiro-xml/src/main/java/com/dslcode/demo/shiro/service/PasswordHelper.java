package com.dslcode.demo.shiro.service;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class PasswordHelper {

    private static final String algorithmName = "md5";
    private static final int hashIterations = 1;

    public static final String encryptPassword(String s, String salt) {
        return new SimpleHash(algorithmName, s, ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

}
