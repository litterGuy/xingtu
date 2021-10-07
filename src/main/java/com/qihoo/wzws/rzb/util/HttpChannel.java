/*     */ package com.qihoo.wzws.rzb.util;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.qihoo.wzws.rzb.secure.ReportOutput;
/*     */ import com.qihoo.wzws.rzb.secure.RespBean;
/*     */ import com.qihoo.wzws.rzb.util.security.SignatureFactory;
/*     */ import com.qihoo.wzws.rzb.util.security.SignatureManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.params.BasicHttpParams;
/*     */ import org.apache.http.params.HttpConnectionParams;
/*     */ import org.apache.http.params.HttpParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpChannel
/*     */ {
/*  37 */   private static int connectionTimeout = 9000;
/*  38 */   private static int socketTimeout = 9000;
/*  39 */   private static int socketBufferSize = 8192;
/*     */   
/*     */   private HttpParams httpParams;
/*     */   private static HttpChannel instance;
/*     */   
/*     */   public static synchronized HttpChannel getInstance() {
/*  45 */     if (instance == null) {
/*  46 */       instance = new HttpChannel();
/*     */     }
/*  48 */     return instance;
/*     */   }
/*     */   
/*     */   private HttpChannel() {
/*  52 */     this.httpParams = (HttpParams)new BasicHttpParams();
/*  53 */     HttpConnectionParams.setConnectionTimeout(this.httpParams, connectionTimeout);
/*  54 */     HttpConnectionParams.setSocketBufferSize(this.httpParams, socketBufferSize);
/*  55 */     HttpConnectionParams.setSoTimeout(this.httpParams, socketTimeout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(String url, String s, String d) {
/*  65 */     DefaultHttpClient defaultHttpClient = new DefaultHttpClient(this.httpParams);
/*  66 */     HttpPost httpPost = new HttpPost(url);
/*     */ 
/*     */ 
/*     */     
/*  70 */     InputStream is = null;
/*     */     
/*  72 */     List<NameValuePair> nvps = new ArrayList<NameValuePair>();
/*  73 */     nvps.add(new BasicNameValuePair("s", s));
/*  74 */     nvps.add(new BasicNameValuePair("d", d));
/*     */     
/*  76 */     StringBuilder sb = new StringBuilder();
/*     */     try {
/*  78 */       httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity(nvps));
/*  79 */       HttpResponse response = defaultHttpClient.execute((HttpUriRequest)httpPost);
/*  80 */       StatusLine statusLine = response.getStatusLine();
/*  81 */       if (statusLine.getStatusCode() == 200) {
/*  82 */         HttpEntity entity = response.getEntity();
/*  83 */         is = entity.getContent();
/*  84 */         String theLine = null;
/*  85 */         BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*  86 */         while ((theLine = br.readLine()) != null) {
/*  87 */           sb.append(theLine);
/*     */         }
/*     */       } 
/*  90 */     } catch (ClientProtocolException e) {
/*  91 */       e.printStackTrace();
/*  92 */     } catch (IOException e) {
/*  93 */       e.printStackTrace();
/*     */     } finally {
/*  95 */       if (is != null) {
/*     */         try {
/*  97 */           is.close();
/*  98 */         } catch (IOException e) {}
/*     */       }
/*     */       
/* 101 */       defaultHttpClient.getConnectionManager().shutdown();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (sb.toString().length() > 0) {
/*     */       
/* 108 */       Gson gson = new Gson();
/* 109 */       RespBean map = (RespBean)gson.fromJson(sb.toString(), RespBean.class);
/*     */ 
/*     */       
/* 112 */       SignatureManager signatureManager = SignatureFactory.getSignature("0001");
/*     */       try {
/* 114 */         if (map.getD() != null && map.getS() != null && map.getV() != null) {
/* 115 */           byte[] decryptData = signatureManager.decrypt(Base64.decodeBase64(map.getV()));
/* 116 */           String newVersion = new String(decryptData, "utf-8");
/* 117 */           boolean isSign = false;
/*     */           
/* 119 */           isSign = signatureManager.verify(map.getD(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwyjo1eVFBxLHwhFY4tWJsNpR0EZZl/fmGfR+SK4kn2UPFyXyiW2T+WKyO3ELVW+2Eaww0+hCVlu+4Ga8dvzYt+p9yvrqFHhU+asVQ2eQCR3tnmsH/9/DzUvMUagdSi2nAEjCy82GNjnMDbtRiPIOy7EH9/pWD7pjDKV777ktAZQIDAQAB", map.getS());
/* 120 */           if (!isSign) {
/* 121 */             System.out.println("verfify failed.");
/*     */             
/*     */             return;
/*     */           } 
/* 125 */           if (ReportOutput.updateRuleFile(map.getD()))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 130 */             if (ReportOutput.updateRuleVersion(newVersion))
/*     */             {
/*     */               
/* 133 */               ConfigUtil.formatConfig.put("rule_ver", newVersion);
/*     */             }
/*     */           }
/*     */         }
/*     */       
/* 138 */       } catch (Exception e) {
/* 139 */         e.printStackTrace();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUseInfo(String url, String s, String d) {
/* 155 */     DefaultHttpClient defaultHttpClient = new DefaultHttpClient(this.httpParams);
/* 156 */     HttpPost httpPost = new HttpPost(url);
/*     */ 
/*     */ 
/*     */     
/* 160 */     InputStream is = null;
/*     */     
/* 162 */     List<NameValuePair> nvps = new ArrayList<NameValuePair>();
/* 163 */     nvps.add(new BasicNameValuePair("s", s));
/* 164 */     nvps.add(new BasicNameValuePair("d", d));
/*     */     
/*     */     try {
/* 167 */       httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity(nvps));
/* 168 */       HttpResponse response = defaultHttpClient.execute((HttpUriRequest)httpPost);
/* 169 */       StatusLine statusLine = response.getStatusLine();
/* 170 */       if (statusLine.getStatusCode() == 200) {
/*     */         return;
/*     */       }
/* 173 */     } catch (ClientProtocolException e) {
/* 174 */       e.printStackTrace();
/* 175 */     } catch (IOException e) {
/* 176 */       e.printStackTrace();
/*     */     } finally {
/* 178 */       if (is != null) {
/*     */         try {
/* 180 */           is.close();
/* 181 */         } catch (IOException e) {}
/*     */       }
/*     */       
/* 184 */       defaultHttpClient.getConnectionManager().shutdown();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\HttpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */