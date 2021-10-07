
package com.qihoo.wzws.rzb.util.security;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


public class RSASignatureManager
        implements SignatureManager {
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String KEY_ALGORITHM = "RSA";
    public static final String C_PRI = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;


    @Override
    public String sign(String content, String privateKey) {

        try {

            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));


            KeyFactory keyf = KeyFactory.getInstance("RSA");

            PrivateKey priKey = keyf.generatePrivate(priPKCS8);


            Signature signature = Signature.getInstance("SHA1WithRSA");


            signature.initSign(priKey);

            signature.update(content.getBytes("utf-8"));


            byte[] signed = signature.sign();


            return Base64.encodeBase64URLSafeString(signed);

        } catch (Exception e) {

            e.printStackTrace();


            return null;

        }

    }


    @Override
    public String encrypt(byte[] data, String key) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(key);


        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");


        Key publicKey = keyFactory.generatePublic(x509KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());


        cipher.init(1, publicKey);


        int inputLen = data.length;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offSet = 0;


        int i = 0;


        while (inputLen - offSet > 0) {

            byte[] cache;
            if (inputLen - offSet > 117) {

                cache = cipher.doFinal(data, offSet, 117);

            } else {

                cache = cipher.doFinal(data, offSet, inputLen - offSet);

            }

            out.write(cache, 0, cache.length);

            i++;

            offSet = i * 117;

        }

        byte[] encryptedData = out.toByteArray();

        out.close();


        return Base64.encodeBase64URLSafeString(encryptedData);

    }


    @Override
    public byte[] decrypt(byte[] encryptedData) throws Exception {

        byte[] keyBytes = Base64.decodeBase64("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");


        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");


        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());


        cipher.init(2, privateKey);


        int inputLen = encryptedData.length;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offSet = 0;


        int i = 0;


        while (inputLen - offSet > 0) {

            byte[] cache;
            if (inputLen - offSet > 128) {

                cache = cipher.doFinal(encryptedData, offSet, 128);

            } else {


                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);

            }


            out.write(cache, 0, cache.length);

            i++;

            offSet = i * 128;

        }

        byte[] decryptedData = out.toByteArray();

        out.close();

        return decryptedData;

    }


    @Override
    public boolean verify(String content, String publicKey, String sign) throws Exception {

        byte[] keyBytes = Base64.decodeBase64(publicKey);


        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");


        PublicKey pubKey = keyFactory.generatePublic(keySpec);


        Signature signature = Signature.getInstance("SHA1WithRSA");


        signature.initVerify(pubKey);


        signature.update(content.getBytes("utf-8"));


        return signature.verify(Base64.decodeBase64(sign));

    }


    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
    }

}