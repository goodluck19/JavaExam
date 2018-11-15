package com.company.model;

import java.io.*;
import java.util.ArrayList;

public class TaskFile {
    private String fileName;
    private ArrayList<Task> tasks;

    public TaskFile(String fileName) {
        this.fileName = fileName;
        this.tasks = new ArrayList<>();

        File taskFile = new File(fileName);
        if (taskFile.exists() && taskFile.isFile()) {
            try {
                FileReader reader = new FileReader(taskFile);
                char[] buffer = new char[(int)taskFile.length()];
                reader.read(buffer);
                parse(buffer);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parse(char[] buffer) {
        String fileAsString = new String(buffer);
        String[] words = fileAsString.trim().replaceAll("\r", " ").split(" ");
        for (int i = 0; i < words.length-1; i += 2) {
            Task task = new Task(words[i], words[i + 1]);
            tasks.add(task);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
