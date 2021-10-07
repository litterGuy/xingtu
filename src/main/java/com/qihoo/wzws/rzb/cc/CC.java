/*     */ package com.qihoo.wzws.rzb.cc;
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
/*     */ 
/*     */ public class CC
/*     */ {
/*     */   private String requestKey;
/*     */   private int count;
/*     */   private String host;
/*     */   private String uri;
/*     */   private String ip;
/*     */   private String time;
/*     */   
/*     */   public CC() {}
/*     */   
/*     */   public CC(String requestKey, int count) {
/*  25 */     this.requestKey = requestKey;
/*  26 */     this.count = count;
/*     */   }
/*     */   
/*     */   public CC(String host, String uri, String ip, String time) {
/*  30 */     this.host = host;
/*  31 */     this.uri = uri;
/*  32 */     this.ip = ip;
/*  33 */     this.time = time;
/*     */   }
/*     */   
/*     */   public String getRequestKey() {
/*  37 */     return this.requestKey;
/*     */   }
/*     */   public void setRequestKey(String requestKey) {
/*  40 */     this.requestKey = requestKey;
/*     */   }
/*     */   public int getCount() {
/*  43 */     return this.count;
/*     */   }
/*     */   public void setCount(int count) {
/*  46 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String geturi() {
/*  53 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIp() {
/*  60 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void seturi(String uri) {
/*  67 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(String ip) {
/*  74 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTime() {
/*  81 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(String time) {
/*  88 */     this.time = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/*  96 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUri() {
/* 104 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHost(String host) {
/* 112 */     this.host = host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUri(String uri) {
/* 120 */     this.uri = uri;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\cc\CC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */