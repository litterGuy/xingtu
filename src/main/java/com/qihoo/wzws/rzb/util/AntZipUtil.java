/*     */
package com.qihoo.wzws.rzb.util;
/*     */
/*     */

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

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
/*     */ public class AntZipUtil
        /*     */ {
    /*     */
    public static boolean zip(String baseDirName, String[] fileNames, String targetFileName, String encoding) {
        /*  32 */
        boolean flag = false;
        /*     */
        /*     */
        try {
            /*  35 */
            File baseDir = new File(baseDirName);
            /*  36 */
            if (!baseDir.exists() || !baseDir.isDirectory()) {
                /*  37 */
                System.err.println("压缩失败! 根目录不存在: " + baseDirName);
                /*  38 */
                return false;
                /*     */
            }
            /*     */
            /*     */
            /*  42 */
            String baseDirPath = baseDir.getAbsolutePath();
            /*     */
            /*     */
            /*  45 */
            File targetFile = new File(targetFileName);
            /*  46 */
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));
            /*  47 */
            out.setEncoding(encoding);
            /*     */
            /*     */
            /*  50 */
            if (fileNames.equals("*")) {
                /*  51 */
                dirToZip(baseDirPath, baseDir, out);
                /*     */
            } else {
                /*  53 */
                File[] files = new File[fileNames.length];
                /*  54 */
                for (int i = 0; i < files.length; i++)
                    /*     */ {
                    /*  56 */
                    files[i] = new File(baseDir, fileNames[i]);
                    /*     */
                }
                /*  58 */
                if (files[0].isFile())
                    /*     */ {
                    /*  60 */
                    filesToZip(baseDirPath, files, out);
                    /*     */
                }
                /*     */
            }
            /*  63 */
            out.close();
            /*     */
            /*  65 */
            flag = true;
            /*  66 */
        } catch (FileNotFoundException e) {
            /*  67 */
            e.printStackTrace();
            /*  68 */
        } catch (IOException e) {
            /*  69 */
            e.printStackTrace();
            /*     */
        }
        /*  71 */
        return flag;
        /*     */
    }

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
    private static void fileToZip(String baseDirPath, File file, ZipOutputStream out) throws IOException {
        /*  87 */
        FileInputStream in = null;
        /*  88 */
        ZipEntry entry = null;
        /*     */
        /*  90 */
        byte[] buffer = new byte[4096];
        /*  91 */
        int bytes_read = 0;
        /*  92 */
        if (file.isFile()) {
            /*  93 */
            in = new FileInputStream(file);
            /*     */
            /*  95 */
            String zipFileName = getEntryName(baseDirPath, file);
            /*  96 */
            entry = new ZipEntry(zipFileName);
            /*     */
            /*  98 */
            out.putNextEntry(entry);
            /*     */
            /* 100 */
            while ((bytes_read = in.read(buffer)) != -1) {
                /* 101 */
                out.write(buffer, 0, bytes_read);
                /*     */
            }
            /* 103 */
            out.closeEntry();
            /* 104 */
            in.close();
            /*     */
        }
        /*     */
    }

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
    private static void filesToZip(String baseDirPath, File[] files, ZipOutputStream out) throws IOException {
        /* 121 */
        for (int i = 0; i < files.length; i++) {
            /* 122 */
            File file = files[i];
            /* 123 */
            if (file.isFile()) {
                /*     */
                /* 125 */
                fileToZip(baseDirPath, file, out);
                /*     */
                /*     */
            }
            /*     */
            else {
                /*     */
                /* 130 */
                dirToZip(baseDirPath, file, out);
                /*     */
            }
            /*     */
        }
        /*     */
    }

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
    private static void dirToZip(String baseDirPath, File dir, ZipOutputStream out) throws IOException {
        /* 145 */
        File[] files = dir.listFiles();
        /*     */
        /* 147 */
        if (files.length == 0) {
            /*     */
            /* 149 */
            String zipFileName = getEntryName(baseDirPath, dir);
            /* 150 */
            ZipEntry entry = new ZipEntry(zipFileName);
            /* 151 */
            out.putNextEntry(entry);
            /* 152 */
            out.closeEntry();
            /*     */
        } else {
            /*     */
            /* 155 */
            for (int i = 0; i < files.length; i++) {
                /* 156 */
                File file = files[i];
                /* 157 */
                if (file.isFile()) {
                    /*     */
                    /* 159 */
                    fileToZip(baseDirPath, file, out);
                    /*     */
                    /*     */
                }
                /*     */
                else {
                    /*     */
                    /*     */
                    /* 165 */
                    dirToZip(baseDirPath, file, out);
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
    }

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
    private static String getEntryName(String baseDirPath, File file) {
        /* 183 */
        if (!baseDirPath.endsWith(File.separator)) {
            /* 184 */
            baseDirPath = baseDirPath + File.separator;
            /*     */
        }
        /* 186 */
        String filePath = file.getAbsolutePath();
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 192 */
        if (file.isDirectory()) {
            /* 193 */
            filePath = filePath + "/";
            /*     */
        }
        /* 195 */
        int index = filePath.indexOf(baseDirPath);
        /* 196 */
        return filePath.substring(index + baseDirPath.length());
        /*     */
    }

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
    public static boolean unZip(String zipFileName, String outputDirectory) {
        /* 211 */
        boolean flag = false;
        /*     */
        try {
            /* 213 */
            ZipFile zipFile = new ZipFile(zipFileName);
            /* 214 */
            Enumeration<ZipEntry> e = zipFile.getEntries();
            /* 215 */
            ZipEntry zipEntry = null;
            /* 216 */
            createDirectory(outputDirectory, "");
            /* 217 */
            while (e.hasMoreElements()) {
                /* 218 */
                zipEntry = e.nextElement();
                /*     */
                /* 220 */
                if (zipEntry.isDirectory()) {
                    /* 221 */
                    String name = zipEntry.getName();
                    /* 222 */
                    name = name.substring(0, name.length() - 1);
                    /* 223 */
                    File f = new File(outputDirectory + File.separator + name);
                    /* 224 */
                    f.mkdir();
                    /* 225 */
                    System.out.println("创建目录：" + outputDirectory + File.separator + name);
                    /*     */
                } else {
                    /* 227 */
                    String fileName = zipEntry.getName();
                    /* 228 */
                    fileName = fileName.replace('\\', '/');
                    /*     */
                    /* 230 */
                    if (fileName.indexOf("/") != -1) {
                        /* 231 */
                        createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));
                        /*     */
                        /* 233 */
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                        /*     */
                    }
                    /*     */
                    /*     */
                    /*     */
                    /* 238 */
                    File f = new File(outputDirectory + File.separator + zipEntry.getName());
                    /*     */
                    /*     */
                    /* 241 */
                    f.createNewFile();
                    /* 242 */
                    InputStream in = zipFile.getInputStream(zipEntry);
                    /* 243 */
                    FileOutputStream out = new FileOutputStream(f);
                    /*     */
                    /* 245 */
                    byte[] by = new byte[1024];
                    /*     */
                    int c;
                    /* 247 */
                    while ((c = in.read(by)) != -1) {
                        /* 248 */
                        out.write(by, 0, c);
                        /*     */
                    }
                    /* 250 */
                    out.close();
                    /* 251 */
                    in.close();
                    /*     */
                }
                /* 253 */
                flag = true;
                /*     */
            }
            /* 255 */
        } catch (Exception ex) {
            /* 256 */
            ex.printStackTrace();
            /*     */
        }
        /* 258 */
        return flag;
        /*     */
    }

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
    private static void createDirectory(String directory, String subDirectory) {
        /* 271 */
        File fl = new File(directory);
        /*     */
        try {
            /* 273 */
            if (subDirectory == "" && fl.exists() != true) {
                /* 274 */
                fl.mkdir();
                /* 275 */
            } else if (subDirectory != "") {
                /* 276 */
                String[] dir = subDirectory.replace('\\', '/').split("/");
                /* 277 */
                for (int i = 0; i < dir.length; i++) {
                    /* 278 */
                    File subFile = new File(directory + File.separator + dir[i]);
                    /* 279 */
                    if (!subFile.exists())
                        /* 280 */ subFile.mkdir();
                    /* 281 */
                    directory = directory + File.separator + dir[i];
                    /*     */
                }
                /*     */
            }
            /* 284 */
        } catch (Exception ex) {
            /* 285 */
            System.out.println(ex.getMessage());
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static void main(String[] temp) {
        /* 291 */
        String baseDirName = "D:\\360\\work@360\\devp\\newwork\\rzb-sa\\target\\result\\17";
        /* 292 */
        String[] fileNames = {"192.168.10-2014102911-常规日志分析报告.html", "192.168.10-2014102911-普通攻击.txt", "192.168.10-2014102911-异常访问.txt"};
        /* 293 */
        String zipFileName = "D:\\360\\work@360\\devp\\newwork\\rzb-sa\\target\\result\\17.zip";
        /*     */
        /* 295 */
        System.out.println(zip(baseDirName, fileNames, zipFileName, "GBK"));
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\AntZipUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */