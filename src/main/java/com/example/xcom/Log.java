package com.example.xcom;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String logFilePath;
    private boolean isFileCleared;

    public Log(String logFilePath) {
        this.logFilePath = logFilePath;
        this.isFileCleared = false;
    }

    public void log(String message) {
        boolean isNewLineNeeded = !isFileEmpty();

        if (!isFileCleared) {
            isFileCleared = true;
        }

        String timestamp = getCurrentTimestamp();
        String logMessage = (isNewLineNeeded ? "\n" : "") + "[" + timestamp + "] " + message + "\n";

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.print(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFileEmpty() {
        try {
            return Files.size(Paths.get(logFilePath)) == 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

