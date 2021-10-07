/*     */ package com.qihoo.wzws.rzb.util.ip;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class IPDataHandler
/*     */ {
/*  20 */   private static String default_IP_DATA_PATH = "D:\\360\\work@360\\devp\\newwork\\data_index\\lib\\17monipdb.dat";
/*  21 */   private static DataInputStream inputStream = null;
/*  22 */   private static long fileLength = -1L;
/*  23 */   private static int dataLength = -1;
/*  24 */   private static Map<String, String> cacheMap = null;
/*  25 */   private static byte[] allData = null;
/*     */ 
/*     */   
/*     */   public static void initIPData(String IP_DATA_PATH) {
/*  29 */     if (IP_DATA_PATH == null) {
/*  30 */       IP_DATA_PATH = default_IP_DATA_PATH;
/*     */     }
/*     */     
/*  33 */     File file = new File(IP_DATA_PATH);
/*     */     try {
/*  35 */       inputStream = new DataInputStream(new FileInputStream(file));
/*  36 */       fileLength = file.length();
/*  37 */       cacheMap = new HashMap<String, String>();
/*  38 */       if (fileLength > 2147483647L) {
/*  39 */         throw new Exception("the filelength over 2GB");
/*     */       }
/*     */       
/*  42 */       dataLength = (int)fileLength;
/*  43 */       allData = new byte[dataLength];
/*  44 */       inputStream.read(allData, 0, dataLength);
/*  45 */       dataLength = (int)getbytesTolong(allData, 0, 4, ByteOrder.BIG_ENDIAN);
/*  46 */     } catch (FileNotFoundException e) {
/*  47 */       e.printStackTrace();
/*  48 */     } catch (IOException e) {
/*  49 */       e.printStackTrace();
/*  50 */     } catch (Exception e) {
/*  51 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static long getbytesTolong(byte[] bytes, int offerSet, int size, ByteOrder byteOrder) {
/*  57 */     if (offerSet + size > bytes.length || size <= 0) {
/*  58 */       return -1L;
/*     */     }
/*  60 */     byte[] b = new byte[size];
/*  61 */     for (int i = 0; i < b.length; i++) {
/*  62 */       b[i] = bytes[offerSet + i];
/*     */     }
/*     */     
/*  65 */     ByteBuffer byteBuffer = ByteBuffer.wrap(b);
/*  66 */     byteBuffer.order(byteOrder);
/*     */     
/*  68 */     long temp = -1L;
/*  69 */     if (byteBuffer.hasRemaining()) {
/*  70 */       temp = byteBuffer.getInt();
/*     */     }
/*  72 */     return temp;
/*     */   }
/*     */   
/*     */   private static long ip2long(String ip) throws UnknownHostException {
/*  76 */     InetAddress address = InetAddress.getByName(ip);
/*  77 */     byte[] bytes = address.getAddress();
/*  78 */     long reslut = getbytesTolong(bytes, 0, 4, ByteOrder.BIG_ENDIAN);
/*  79 */     return reslut;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getIntByBytes(byte[] b, int offSet) {
/*  85 */     if (b == null || b.length < offSet + 3) {
/*  86 */       return -1;
/*     */     }
/*     */     
/*  89 */     byte[] bytes = Arrays.copyOfRange(allData, offSet, offSet + 3);
/*  90 */     byte[] bs = new byte[4];
/*  91 */     bs[3] = 0;
/*  92 */     for (int i = 0; i < 3; i++) {
/*  93 */       bs[i] = bytes[i];
/*     */     }
/*     */     
/*  96 */     return (int)getbytesTolong(bs, 0, 4, ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String findGeography(String address) {
/* 101 */     if (address == null || address.length() == 0) {
/* 102 */       return "illegal address";
/*     */     }
/*     */     
/* 105 */     if (dataLength < 4 || allData == null) {
/* 106 */       return "illegal ip data";
/*     */     }
/*     */     
/* 109 */     String ip = "127.0.0.1";
/*     */     try {
/* 111 */       ip = Inet4Address.getByName(address).getHostAddress();
/* 112 */     } catch (UnknownHostException e) {
/* 113 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 116 */     String[] ipArray = ip.split("\\.");
/* 117 */     int ipHeadValue = Integer.parseInt(ipArray[0]);
/* 118 */     if (ipArray.length != 4 || ipHeadValue < 0 || ipHeadValue > 255) {
/* 119 */       return "illegal ip";
/*     */     }
/*     */     
/* 122 */     if (cacheMap.containsKey(ip)) {
/* 123 */       return cacheMap.get(ip);
/*     */     }
/*     */ 
/*     */     
/* 127 */     long numIp = 1L;
/*     */     try {
/* 129 */       numIp = ip2long(address);
/* 130 */     } catch (UnknownHostException e1) {
/* 131 */       e1.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 135 */     int tempOffSet = ipHeadValue * 4 + 4;
/* 136 */     long start = getbytesTolong(allData, tempOffSet, 4, ByteOrder.LITTLE_ENDIAN);
/* 137 */     int max_len = dataLength - 1028;
/* 138 */     long resultOffSet = 0L;
/* 139 */     int resultSize = 0;
/*     */     
/* 141 */     for (start = start * 8L + 1024L; start < max_len; start += 8L) {
/* 142 */       if (getbytesTolong(allData, (int)start + 4, 4, ByteOrder.BIG_ENDIAN) >= numIp) {
/* 143 */         resultOffSet = getIntByBytes(allData, (int)(start + 4L + 4L));
/* 144 */         resultSize = (char)allData[(int)start + 7 + 4];
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 149 */     if (resultOffSet <= 0L) {
/* 150 */       return "resultOffSet too small";
/*     */     }
/*     */     
/* 153 */     byte[] add = Arrays.copyOfRange(allData, (int)(dataLength + resultOffSet - 1024L), (int)(dataLength + resultOffSet - 1024L + resultSize));
/*     */     try {
/* 155 */       if (add == null) {
/* 156 */         cacheMap.put(ip, new String("no data found!!"));
/*     */       } else {
/* 158 */         cacheMap.put(ip, new String(add, "UTF-8"));
/*     */       }
/*     */     
/* 161 */     } catch (UnsupportedEncodingException e) {
/* 162 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 165 */     return cacheMap.get(ip);
/*     */   }
/*     */   
/*     */   public static String findGeographyNoCache(String address) {
/* 169 */     if (address == null || address.length() == 0) {
/* 170 */       return "illegal address";
/*     */     }
/*     */     
/* 173 */     if (dataLength < 4 || allData == null) {
/* 174 */       return "illegal ip data";
/*     */     }
/*     */     
/* 177 */     String ip = "127.0.0.1";
/*     */     try {
/* 179 */       ip = Inet4Address.getByName(address).getHostAddress();
/* 180 */     } catch (UnknownHostException e) {
/* 181 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 184 */     String[] ipArray = ip.split("\\.");
/* 185 */     int ipHeadValue = Integer.parseInt(ipArray[0]);
/* 186 */     if (ipArray.length != 4 || ipHeadValue < 0 || ipHeadValue > 255) {
/* 187 */       return "illegal ip";
/*     */     }
/*     */     
/* 190 */     long numIp = 1L;
/*     */     try {
/* 192 */       numIp = ip2long(address);
/* 193 */     } catch (UnknownHostException e1) {
/* 194 */       e1.printStackTrace();
/*     */     } 
/*     */     
/* 197 */     int tempOffSet = ipHeadValue * 4 + 4;
/* 198 */     long start = getbytesTolong(allData, tempOffSet, 4, ByteOrder.LITTLE_ENDIAN);
/* 199 */     int max_len = dataLength - 1028;
/* 200 */     long resultOffSet = 0L;
/* 201 */     int resultSize = 0;
/*     */     
/* 203 */     for (start = start * 8L + 1024L; start < max_len; start += 8L) {
/* 204 */       if (getbytesTolong(allData, (int)start + 4, 4, ByteOrder.BIG_ENDIAN) >= numIp) {
/* 205 */         resultOffSet = getIntByBytes(allData, (int)(start + 4L + 4L));
/* 206 */         resultSize = (char)allData[(int)start + 7 + 4];
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 211 */     if (resultOffSet <= 0L) {
/* 212 */       return "resultOffSet too small";
/*     */     }
/*     */     
/* 215 */     byte[] add = Arrays.copyOfRange(allData, (int)(dataLength + resultOffSet - 1024L), (int)(dataLength + resultOffSet - 1024L + resultSize));
/*     */     try {
/* 217 */       if (add == null) {
/* 218 */         return null;
/*     */       }
/* 220 */       return new String(add, "UTF-8");
/*     */     
/*     */     }
/* 223 */     catch (UnsupportedEncodingException e) {
/* 224 */       e.printStackTrace();
/*     */ 
/*     */       
/* 227 */       return null;
/*     */     } 
/*     */   }
/*     */   public static void main(String[] args) {
/* 231 */     long s = System.currentTimeMillis();
/* 232 */     initIPData(default_IP_DATA_PATH);
/* 233 */     System.out.println(findGeography("202.196.64.10"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\ip\IPDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */