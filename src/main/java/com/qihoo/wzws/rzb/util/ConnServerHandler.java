
package com.qihoo.wzws.rzb.util;


import com.qihoo.wzws.rzb.secure.AnalyzeSingle;
import com.qihoo.wzws.rzb.secure.ReportOutput;
import com.qihoo.wzws.rzb.util.security.SignatureFactory;
import com.qihoo.wzws.rzb.util.security.SignatureManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ConnServerHandler {
    public static final String S_PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB";
    private static final String S_URL = "http://101.226.4.56/rzb-server/upd";


    public static void requestUpdate(String mac, String clientVer, String ruleVer) {

        String data = new String("type=1,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer);


        SignatureManager signatureManager = SignatureFactory.getSignature("0001");

        String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");


        String base64Data = "";

        try {

            base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }


        HttpChannel.getInstance().update("http://101.226.4.56/rzb-server/upd", sign, base64Data);

    }


    public static void updateUseInfo(String mac, String clientVer, String ruleVer, long logLines, long attackCount) {

        String data = new String("type=2,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer + ",logLines=" + logLines + ",attackCount=" + attackCount);


        SignatureManager signatureManager = SignatureFactory.getSignature("0001");

        String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");


        String base64Data = "";

        try {

            base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");

        } catch (UnsupportedEncodingException e) {


            e.printStackTrace();

        } catch (Exception e) {


            e.printStackTrace();

        }


        HttpChannel.getInstance().updateUseInfo("http://101.226.4.56/rzb-server/upd", sign, base64Data);

    }


    public static void updateAttackInfo(String mac, String clientVer, String ruleVer, String attacks) {

        String data;

        try {

            data = new String("type=3,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer + ",data=" + URLEncoder.encode(attacks, "utf-8"));

        } catch (UnsupportedEncodingException e1) {

            return;

        }


        SignatureManager signatureManager = SignatureFactory.getSignature("0001");

        String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");


        String base64Data = "";

        try {

            base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }


        HttpChannel.getInstance().updateUseInfo("http://101.226.4.56/rzb-server/upd", sign, base64Data);

    }


    public static void sendMail(String mac, String clientVer, String ruleVer, String mail, String log_file, long records, long attacksCounts, long costTime, String content) {

        String data = new String("type=4,uid=" + mac + ",clientver=" + clientVer + ",rulever=" + ruleVer + ",mail=" + mail + ",log_file=" + log_file + ",records=" + records + ",attacksCounts=" + attacksCounts + ",costTime=" + costTime + ",content=" + content);


        SignatureManager signatureManager = SignatureFactory.getSignature("0001");

        String sign = signatureManager.sign(data, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMh3VLEl3/FTY0rPe+laOppvYQGpvAHKY7e65l/EgqJvVOy0XlyMSJGwFT5/aVMIfORmVRNcT0zmyahXDlfiUMTv7poKfZOs9tjl/3LMbB7oLqVKuqqGPaSGTzYLbDzbYAZYQ84CCJPZXBnquqkx0RIFOR0ZjKxc2VYTBkng0beXAgMBAAECgYAFW+tPsQuDkA8feNx/KIJYjpGxUbBM+/QefUibVs2HOiKzET9UiguGmYZ33UAbkCaGlJXBpr4X+DN75JflkY9HKGVCWj/RwOj5JCxxed58AUZHPWlnsY1/2NaNnuV+fSftbaDTRzz53uqEUAmr2VZwDa4Sakb3MY/GgMeLpY4RwQJBAOgCXOIQpSHiiFlEc47Ch0xYJHjLF+0pMZjYSrfZ6hclm6+bgyi0LbvFCCvtTVhEI+SKgneyheGivacyyq6wQcUCQQDdMfluPsIEQG9b/M04/hE2+X8cp4VOrWB3omVFFsvzN7N9qVQw9riK1I1H3Sf58mzxB24pP6JQHN56I4H1GDWrAkEAr9oqoMbRA0/63P5/QDO97WXZrxzw87eHejxm1dd8ETNWP9J1pYJ8L5h2SyVAvKhZND6wCR3tUErTyUk8SxAZqQJABbeq+NyKa023Eyufql921nLwhC8YVQZKPg1mjLigIPNJom/kCzf1YTFJTZg71kwb4McOY+aPLX7xiTqtu0Ya7QJBANOkUfvqPZz5dB583grOV8SsfPl+s4gNhKv/D2n4p/Yjouz0TK66I74Apq/lKmYJ8z97Dz/VSj0y1rt76HgEclI=");


        String base64Data = "";

        try {

            base64Data = signatureManager.encrypt(data.getBytes("utf-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }


        HttpChannel.getInstance().updateUseInfo("http://101.226.4.56/rzb-server/upd", sign, base64Data);

    }


    public static void main(String[] args) {

        AnalyzeSingle.basePath = "C:\\Users\\wangpeng3-s\\Desktop\\rzb\\full-v2.0\\";

        String mac = "F0-92-1C-E2-5D-E8";

        String clientVer = "0.6.2";

        String ruleVer = "20140830";

        long logLines = 1000L;

        long attackCount = 2L;


        ReportOutput.generateUid();


        requestUpdate(mac, clientVer, ruleVer);

    }

}