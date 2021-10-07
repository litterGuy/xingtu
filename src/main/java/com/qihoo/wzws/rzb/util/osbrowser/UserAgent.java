/*     */ package com.qihoo.wzws.rzb.util.osbrowser;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserAgent
/*     */ {
/*     */   private boolean isMobile;
/*     */   private String browserVersion;
/*     */   private String platform;
/*     */   private String brand;
/*     */   private String device;
/*     */   private String browser;
/*     */   private String osVersion;
/*     */   
/*     */   public String getPlatform() {
/*  38 */     return this.platform;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlatform(String platform) {
/*  44 */     this.platform = platform;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBrand() {
/*  50 */     return this.brand;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBrand(String brand) {
/*  56 */     this.brand = brand;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDevice() {
/*  62 */     return this.device;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDevice(String device) {
/*  68 */     this.device = device;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBrowser() {
/*  74 */     return this.browser;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBrowser(String browser) {
/*  80 */     this.browser = browser;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOsVersion() {
/*  86 */     return this.osVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOsVersion(String osVersion) {
/*  92 */     this.osVersion = osVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  99 */     return "UserAgent [platform=" + this.platform + ", brand=" + this.brand + ", device=" + this.device + ", browser=" + this.browser + ", osVersion=" + this.osVersion + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String output() {
/* 109 */     return "|" + this.platform + "|" + ((this.osVersion == null) ? "" : this.osVersion) + "|" + ((this.brand == null) ? "" : this.brand) + "|" + ((this.device == null) ? "" : this.device.trim()) + "|" + ((this.browser == null) ? "" : this.browser);
/*     */   }
/*     */   
/*     */   public String outputDevice() {
/* 113 */     return "|" + this.platform + "|" + ((this.brand == null) ? "" : this.brand) + "|" + ((this.device == null) ? "" : this.device.trim());
/*     */   }
/*     */   
/*     */   public String outputBrowser() {
/* 117 */     return this.platform + "|" + ((this.browser == null) ? "" : this.browser.trim());
/*     */   }
/*     */   
/*     */   public String outputBrowserForMobile() {
/* 121 */     return "|MO|" + this.platform + "|" + ((this.browser == null) ? "" : this.browser.trim());
/*     */   }
/*     */   
/*     */   public String outputBrowserForPC() {
/* 125 */     return "|PC|" + ((this.platform == null) ? "" : this.platform) + "|" + ((this.osVersion == null) ? "" : this.osVersion) + "|" + ((this.browser == null) ? "" : this.browser) + "|" + ((this.browserVersion == null) ? "" : this.browserVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMobile() {
/* 132 */     return this.isMobile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMobile(boolean isMobile) {
/* 138 */     this.isMobile = isMobile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBrowserVersion() {
/* 144 */     return this.browserVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBrowserVersion(String browserVersion) {
/* 150 */     this.browserVersion = browserVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\osbrowser\UserAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */