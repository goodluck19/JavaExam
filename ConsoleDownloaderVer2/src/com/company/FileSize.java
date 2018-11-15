package com.company;


/**
 * Класс, который хранит информацию об объёме скачанных файлов
 */
public class FileSize {

    private static double sizeAll = 0;

    /**
     * Получение объёма скачанных файлов
     * @return объём в байтах
     */
    public double getSizeAll() {

        return sizeAll;
    }

    /**
     * При каждом скачивании значение суммарного объёма увеличивается
     * @param size - объём скачанного файла
     */
    public static void setSizeAll(double size) {

        sizeAll += size;
    }
}
