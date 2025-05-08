package com.blueprinthell.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
    private static Logger instance;
    private PrintWriter writer;
    private boolean logToConsole = true;

    private Logger() {
        try {
            writer = new PrintWriter(new FileWriter("log.txt", true));
        } catch (IOException e) {
            System.err.println("Failed to create log file.");
        }
    }

    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().toString();
        String full = "[" + timestamp + "][" + level + "] " + message;

        if (logToConsole) System.out.println(full);
        if (writer != null) writer.println(full);
    }

    public void info(String message) { log("INFO", message); }
    public void warn(String message) { log("WARN", message); }
    public void error(String message) { log("ERROR", message); }
    public void debug(String message) { log("DEBUG", message); }
    public void run() {log("RUN", ""); }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
