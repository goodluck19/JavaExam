package com.company;

import com.company.model.Task;
import com.company.model.TaskFile;
import com.company.model.Downloader;

import java.io.*;
import java.util.Arrays;

/**
 *     @author Nikita Chuvilskiy
 */

public class Main {

    public static String string = "Прошу пардон, но при запуске программы произошла непростительная ошибка. Причина: Вы не указали входные параметры:\n" +
            "  n количество одновременно качающих потоков (1,2,3,4....)\n" +
            "  l общее ограничение на скорость скачивания, для всех потоков, размерность - байт/секунда, можно использовать параметры k, m (k=1024, m=1024*1024)\n" +
            "  f путь к файлу со списком ссылок\n" +
            "  o имя папки, куда складывать скачанные файлы\n" +
            "Пример вызова: java -jar utility.jar n l link output_folder\n";

    public static void main(String[] args) {
        // проверка входнх параметров
        int numberOfFlows;
        int speedLimit;
        String pathToFile;
        String nameOfDir;

        if (args.length != 4) {
            System.out.printf(string);
            return;
        } else {
            try {
                numberOfFlows = Integer.valueOf(args[0].replaceAll("[^0-9]", ""));

                // обработка параметров k - килобайты, m - мегабайты
                String suffix = args[1].replaceAll("[^a-zA-Z]", "").replaceAll("-", "").toLowerCase();
                speedLimit = Integer.valueOf(args[1].replaceAll("[^0-9]", ""));
                switch (suffix) {
                    case "k":
                        speedLimit = speedLimit * 1024;
                        break;
                    case "m":
                        speedLimit = speedLimit * 1024 * 1024;
                        break;
                }
                pathToFile = String.valueOf(args[2].replaceAll("-", ""));
                nameOfDir = String.valueOf(args[3].replaceAll("-", ""));
            } catch(Exception exc) {
                System.out.printf("%s", exc.toString());
                return;
            }
        }

        System.out.printf("Параметры: \n");
        System.out.printf("Количество потоков: %d\n", numberOfFlows);
        System.out.printf("Ограничение скорости: %d байт/сек\n", speedLimit);
        System.out.printf("Путь к файлу: %s\n", pathToFile);
        System.out.printf("Имя папки: %s\n\n", nameOfDir);


        // проверка на наличие файла
        TaskFile taskFile = new TaskFile(pathToFile);
        System.out.printf("Получены задания: \n");
        for (Task task : taskFile.getTasks()) {
            System.out.printf("Путь: %s в файл: %s\n", task.getPath(), task.getFileName());
        }
        System.out.printf("\n");
        File file = new File(nameOfDir);
        if (!file.exists() || !file.isDirectory()) {
            System.out.printf("Неверное имя папки для сохранения: %s", nameOfDir);
            return;
        }

        for (Task task : taskFile.getTasks()) {
            Downloader dl = new Downloader();
            try {
                dl.getFile(task.getPath());
            } catch (IOException exc) {
                System.out.printf("%s", exc.toString());
                return;
            }
            String pathToWriteFile = nameOfDir +"/" + task.getFileName();
            File fileToWrite = new File(pathToWriteFile);
            try {
                FileWriter writer = new FileWriter(fileToWrite);
                writer.write(dl.getBufferOfChars());
                writer.close();
            } catch (IOException e) {
                System.out.printf("%s", e.toString());
                return;
            }
            System.out.printf("Содержимое из: %s скачано и записано в файл: %s\n", task.getPath(), pathToWriteFile);
        }
    }
}
