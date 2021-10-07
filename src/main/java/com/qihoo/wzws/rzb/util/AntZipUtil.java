
package com.qihoo.wzws.rzb.util;


import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;


public class AntZipUtil {

    public static boolean zip(String baseDirName, String[] fileNames, String targetFileName, String encoding) {

        boolean flag = false;


        try {

            File baseDir = new File(baseDirName);

            if (!baseDir.exists() || !baseDir.isDirectory()) {

                System.err.println("压缩失败! 根目录不存在: " + baseDirName);

                return false;

            }


            String baseDirPath = baseDir.getAbsolutePath();


            File targetFile = new File(targetFileName);

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));

            out.setEncoding(encoding);


            if (fileNames.equals("*")) {

                dirToZip(baseDirPath, baseDir, out);

            } else {

                File[] files = new File[fileNames.length];

                for (int i = 0; i < files.length; i++) {

                    files[i] = new File(baseDir, fileNames[i]);

                }

                if (files[0].isFile()) {

                    filesToZip(baseDirPath, files, out);

                }

            }

            out.close();


            flag = true;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return flag;

    }


    private static void fileToZip(String baseDirPath, File file, ZipOutputStream out) throws IOException {

        FileInputStream in = null;

        ZipEntry entry = null;


        byte[] buffer = new byte[4096];

        int bytes_read = 0;

        if (file.isFile()) {

            in = new FileInputStream(file);


            String zipFileName = getEntryName(baseDirPath, file);

            entry = new ZipEntry(zipFileName);


            out.putNextEntry(entry);


            while ((bytes_read = in.read(buffer)) != -1) {

                out.write(buffer, 0, bytes_read);

            }

            out.closeEntry();

            in.close();

        }

    }


    private static void filesToZip(String baseDirPath, File[] files, ZipOutputStream out) throws IOException {

        for (int i = 0; i < files.length; i++) {

            File file = files[i];

            if (file.isFile()) {


                fileToZip(baseDirPath, file, out);


            } else {


                dirToZip(baseDirPath, file, out);

            }

        }

    }


    private static void dirToZip(String baseDirPath, File dir, ZipOutputStream out) throws IOException {

        File[] files = dir.listFiles();


        if (files.length == 0) {


            String zipFileName = getEntryName(baseDirPath, dir);

            ZipEntry entry = new ZipEntry(zipFileName);

            out.putNextEntry(entry);

            out.closeEntry();

        } else {


            for (int i = 0; i < files.length; i++) {

                File file = files[i];

                if (file.isFile()) {


                    fileToZip(baseDirPath, file, out);


                } else {


                    dirToZip(baseDirPath, file, out);

                }

            }

        }

    }


    private static String getEntryName(String baseDirPath, File file) {

        if (!baseDirPath.endsWith(File.separator)) {

            baseDirPath = baseDirPath + File.separator;

        }

        String filePath = file.getAbsolutePath();


        if (file.isDirectory()) {

            filePath = filePath + "/";

        }

        int index = filePath.indexOf(baseDirPath);

        return filePath.substring(index + baseDirPath.length());

    }


    public static boolean unZip(String zipFileName, String outputDirectory) {

        boolean flag = false;

        try {

            ZipFile zipFile = new ZipFile(zipFileName);

            Enumeration<ZipEntry> e = zipFile.getEntries();

            ZipEntry zipEntry = null;

            createDirectory(outputDirectory, "");

            while (e.hasMoreElements()) {

                zipEntry = e.nextElement();


                if (zipEntry.isDirectory()) {

                    String name = zipEntry.getName();

                    name = name.substring(0, name.length() - 1);

                    File f = new File(outputDirectory + File.separator + name);

                    f.mkdir();

                    System.out.println("创建目录：" + outputDirectory + File.separator + name);

                } else {

                    String fileName = zipEntry.getName();

                    fileName = fileName.replace('\\', '/');


                    if (fileName.indexOf("/") != -1) {

                        createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));


                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());

                    }


                    File f = new File(outputDirectory + File.separator + zipEntry.getName());


                    f.createNewFile();

                    InputStream in = zipFile.getInputStream(zipEntry);

                    FileOutputStream out = new FileOutputStream(f);


                    byte[] by = new byte[1024];

                    int c;

                    while ((c = in.read(by)) != -1) {

                        out.write(by, 0, c);

                    }

                    out.close();

                    in.close();

                }

                flag = true;

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return flag;

    }


    private static void createDirectory(String directory, String subDirectory) {

        File fl = new File(directory);

        try {

            if (subDirectory == "" && fl.exists() != true) {

                fl.mkdir();

            } else if (subDirectory != "") {

                String[] dir = subDirectory.replace('\\', '/').split("/");

                for (int i = 0; i < dir.length; i++) {

                    File subFile = new File(directory + File.separator + dir[i]);

                    if (!subFile.exists())
                        subFile.mkdir();

                    directory = directory + File.separator + dir[i];

                }

            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }

    }


    public static void main(String[] temp) {

        String baseDirName = "D:\\360\\work@360\\devp\\newwork\\rzb-sa\\target\\result\\17";

        String[] fileNames = {"192.168.10-2014102911-常规日志分析报告.html", "192.168.10-2014102911-普通攻击.txt", "192.168.10-2014102911-异常访问.txt"};

        String zipFileName = "D:\\360\\work@360\\devp\\newwork\\rzb-sa\\target\\result\\17.zip";


        System.out.println(zip(baseDirName, fileNames, zipFileName, "GBK"));

    }

}