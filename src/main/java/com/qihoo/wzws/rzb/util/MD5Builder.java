
package com.qihoo.wzws.rzb.util;


import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Builder {

    private static String encryptFile(File file, String enc) throws Exception {

        FileInputStream fis = null;

        MessageDigest md = null;

        String strDes = null;

        try {

            fis = new FileInputStream(file);

            byte[] buffer = new byte[2048];

            int length = -1;

            while ((length = fis.read(buffer)) != -1) {

                md.update(buffer, 0, length);

            }

            byte[] bt = md.digest();


            md = MessageDigest.getInstance(enc);

            md.update(bt);

            strDes = bytes2Hex(md.digest());

        } catch (Exception e) {

            throw e;

        }

        return strDes;

    }


    private static String encryptString(String strSrc, String enc) throws NoSuchAlgorithmException {

        MessageDigest md = null;

        String strDes = null;


        byte[] bt = strSrc.getBytes();

        try {

            md = MessageDigest.getInstance(enc);

            md.update(bt);

            strDes = bytes2Hex(md.digest());

        } catch (NoSuchAlgorithmException e) {

            throw e;

        }

        return strDes;

    }


    private static String bytes2Hex(byte[] bts) {

        String des = "";

        String tmp = null;

        for (int i = 0; i < bts.length; i++) {

            tmp = Integer.toHexString(bts[i] & 0xFF);

            if (tmp.length() == 1) {

                des = des + "0";

            }

            des = des + tmp;

        }

        return des;

    }


    public static String getMD5File(File file) {

        try {

            return encryptFile(file, "MD5");

        } catch (Exception e) {

            return null;

        }

    }


    public static String getMD5String(String str) {

        try {

            return encryptString(str, "MD5");

        } catch (NoSuchAlgorithmException e) {

            return null;

        }

    }


    public static String getSHA1File(File file) {

        try {

            return encryptFile(file, "SHA-1");

        } catch (Exception e) {

            return null;

        }

    }


    public static String getSHA1String(String str) {

        try {

            return encryptString(str, "SHA-1");

        } catch (NoSuchAlgorithmException e) {

            return null;

        }

    }


    public static String encrypt(byte[] obj) {

        String s = null;

        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");

            md5.update(obj);

            s = bytes2Hex(md5.digest());

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        }


        return s;

    }

}