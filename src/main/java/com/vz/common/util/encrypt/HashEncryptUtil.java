package com.vz.common.util.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * HASH加密解密工具(主流MD5\SHA1)
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 14:46:22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashEncryptUtil {

    /**
     * bytes转成十六进制
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytes2hex(byte[] bytes) {
        StringBuffer hex = new StringBuffer();
        for (int i = 0, length = bytes.length; i < length; i++) {
            byte b = bytes[i];
            //是否为负数
            boolean negative = false;
            if (b < 0) {
                negative = true;
            }

            int inte = Math.abs(b);
            if (negative) {
                inte = inte | 0x80;
            }
            //负数转化为正数（最高位的符号变成数值计算）
            String temp = Integer.toHexString(inte & 0xFF);
            if (temp.length() == 1) {
                hex.append("0");
            }
            hex.append(temp.toLowerCase());
        }
        return hex.toString();
    }

    /**
     * 16进制转为字节数组
     * @param hex 16进制数
     * @return 十六机制字节数组
     */
    private static byte[] hex2bytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i++) {
            String subStr = hex.substring(i, i + 2);
            //是否为负数
            boolean negative = false;
            int inte = Integer.parseInt(subStr, 16);
            if (inte > 127) {
                negative = true;
            }
            if (inte == 128) {
                inte = -128;
            } else if (negative) {
                inte = 0 - (inte & 0x7F);
            }

            byte b = (byte) inte;
            bytes[i / 2] = b;
        }
        return bytes;
    }

    /**
     * HASH加密
     * @param content   待加密内容
     * @param algorithm 加密算法（MD5,SHA-1）
     * @return 加密字符串
     * @throws NoSuchAlgorithmException 未找到该加密算法
     */
    public static String encrypt(String algorithm, String content) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md5.digest();
        return bytes2hex(bytes);
    }

}
