package com.zmh.coupon.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
    public static final String encrypt(String inputStr) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
        byte[] srcBytes = inputStr.getBytes();

        sha.update(srcBytes);

        byte[] resultBytes = sha.digest();

        return BytesToStrUtils.enHex(resultBytes);

    }

    // public static void main(String args [] ){
    // System.out.println(encrypt("12345678"));
    // }
}
