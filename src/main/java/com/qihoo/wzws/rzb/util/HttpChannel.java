
package com.qihoo.wzws.rzb.util;


import com.google.gson.Gson;
import com.qihoo.wzws.rzb.secure.ReportOutput;
import com.qihoo.wzws.rzb.secure.RespBean;
import com.qihoo.wzws.rzb.util.security.SignatureFactory;
import com.qihoo.wzws.rzb.util.security.SignatureManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


public class HttpChannel {
    private static int connectionTimeout = 9000;
    private static int socketTimeout = 9000;
    private static int socketBufferSize = 8192;

    private HttpParams httpParams;
    private static HttpChannel instance;


    public static synchronized HttpChannel getInstance() {

        if (instance == null) {

            instance = new HttpChannel();

        }

        return instance;

    }


    private HttpChannel() {

        this.httpParams = (HttpParams) new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(this.httpParams, connectionTimeout);

        HttpConnectionParams.setSocketBufferSize(this.httpParams, socketBufferSize);

        HttpConnectionParams.setSoTimeout(this.httpParams, socketTimeout);

    }


    public void update(String url, String s, String d) {

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(this.httpParams);

        HttpPost httpPost = new HttpPost(url);


        InputStream is = null;


        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("s", s));

        nvps.add(new BasicNameValuePair("d", d));


        StringBuilder sb = new StringBuilder();

        try {

            httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(nvps));

            HttpResponse response = defaultHttpClient.execute((HttpUriRequest) httpPost);

            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == 200) {

                HttpEntity entity = response.getEntity();

                is = entity.getContent();

                String theLine = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                while ((theLine = br.readLine()) != null) {

                    sb.append(theLine);

                }

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (is != null) {

                try {

                    is.close();

                } catch (IOException e) {
                }

            }


            defaultHttpClient.getConnectionManager().shutdown();

        }


        if (sb.toString().length() > 0) {


            Gson gson = new Gson();

            RespBean map = (RespBean) gson.fromJson(sb.toString(), RespBean.class);


            SignatureManager signatureManager = SignatureFactory.getSignature("0001");

            try {

                if (map.getD() != null && map.getS() != null && map.getV() != null) {

                    byte[] decryptData = signatureManager.decrypt(Base64.decodeBase64(map.getV()));

                    String newVersion = new String(decryptData, "utf-8");

                    boolean isSign = false;


                    isSign = signatureManager.verify(map.getD(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB", map.getS());

                    if (!isSign) {

                        System.out.println("verfify failed.");


                        return;

                    }

                    if (ReportOutput.updateRuleFile(map.getD())) {


                        if (ReportOutput.updateRuleVersion(newVersion)) {


                            ConfigUtil.formatConfig.put("rule_ver", newVersion);

                        }

                    }

                }


            } catch (Exception e) {

                e.printStackTrace();

                return;

            }

        }

    }


    public void updateUseInfo(String url, String s, String d) {

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(this.httpParams);

        HttpPost httpPost = new HttpPost(url);


        InputStream is = null;


        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("s", s));

        nvps.add(new BasicNameValuePair("d", d));


        try {

            httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(nvps));

            HttpResponse response = defaultHttpClient.execute((HttpUriRequest) httpPost);

            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == 200) {

                return;

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (is != null) {

                try {

                    is.close();

                } catch (IOException e) {
                }

            }


            defaultHttpClient.getConnectionManager().shutdown();

        }

    }

}