package com.think123.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 加密工具类
 */
public class BCryptPasswordEncoderUtils {
    private static BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    public static String encodePassword(String password){
        String encode = bCryptPasswordEncoder.encode(password);
        return encode;
    }

    /*
    测试
    public static void main(String[] args) {
        String password="think123";
        String s = encodePassword(password);
        System.out.println(s);
    }*/
}