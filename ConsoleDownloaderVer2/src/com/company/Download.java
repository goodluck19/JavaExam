package com.company;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Класс, который скачивает файл по ссылке
 */
public class Download extends Thread {

    String url;
    List<String> outFiles;
    private String pack;

    public Download(String url, List<String> outFiles, String args){
        this.url = url;
        this.outFiles = outFiles;
        this.pack = args + "/";  // "src/" + args + "/"
    }

    public void run(){
        System.out.println("Starting: " + Thread.currentThread().getId());
        System.out.println("Загружается файл: " + outFiles.get(0));
        System.out.println();

        File folder = new File(pack);
        if (!folder.exists()){
            folder.mkdirs();
        }

        long startFile = System.currentTimeMillis();

        try {

            URL urlConnect = new URL(url);

            byte[] buffer = new byte[1024];
            int count = 0;

            // Каждая ссылка запускается 1 раз, а если файлов несколько, то скачанный файл записывается во все

            BufferedInputStream bis = new BufferedInputStream(urlConnect.openStream());
            FileOutputStream fos = new FileOutputStream(pack + outFiles.get(0));
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            bis.close();

            if (outFiles.size() != 0 || outFiles.size() != 1) {
                for (int i = 1; i < outFiles.size(); i++){
                    File source = new File(pack + outFiles.get(0));
                    File dest = new File(pack + outFiles.get(i));
                    Files.copy(source.toPath(), dest.toPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Completed: " + Thread.currentThread().getId());
        long timeFile = System.currentTimeMillis() - startFile;
        SimpleDateFormat sdfFile = new SimpleDateFormat("mm");

        double size = 0;
        File file = new File(pack + outFiles.get(0));
        if (file.exists()){
            size = (double) ((file.length() / 1024) * outFiles.size());
            if (size < 1024) {
                System.out.println("Размер файла в kB: " + size);
                System.out.println("Файл " + outFiles.get(0) + " загружен: " + size + " kB" + " за " + sdfFile.format(timeFile) + " минут(ы)");
                System.out.println();
            } else {
                System.out.printf("Размер файла в MB: %.1f\n", (size / 1024));
                System.out.printf("Файл " + outFiles.get(0) + " загружен: %.1f  MB" + " за " + sdfFile.format(timeFile) + " минуту\n", (size / 1024));
                System.out.println();
            }
            FileSize.setSizeAll(size);
        }
        System.out.println();
    }

}