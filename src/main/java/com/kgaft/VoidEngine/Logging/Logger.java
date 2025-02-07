package com.kgaft.VoidEngine.Logging;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Logger implements Runnable{
    private static Thread loggerThread;
    private static volatile List<LoggerMessage> messages = new ArrayList<>();
    private static volatile Semaphore messageSemaphore = new Semaphore(1);
    private static volatile boolean isRunning = true;
    public static void startLogger(String fileName){
        loggerThread = new Thread(new Logger(fileName));
        loggerThread.start();
    }

    public static void shutdown(){
        isRunning = false;
    }

    private FileOutputStream fos;

    private Logger(String fileName){
        try {
            fos = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dispatchMessage(LoggerMessage message){
        try {
            messageSemaphore.acquire();
            messages.add(message);
            messageSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(isRunning){
            try {
                messageSemaphore.acquire();
                messages.forEach(element->{
                    PrintStream output = element.isError()?System.err:System.out;
                    String outputMessage = new Date()+" "+element.getSource()+" "+element.getType()+" ["+element.getSeverity()+"] "+element.getMessage()+"\n";
                    output.print(outputMessage);

                    try {
                        fos.write(outputMessage.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                messages.clear();
                messageSemaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
