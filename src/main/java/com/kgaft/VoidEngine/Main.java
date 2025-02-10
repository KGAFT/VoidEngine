package com.kgaft.VoidEngine;

import com.kgaft.VoidEngine.Logging.Logger;
import com.kgaft.VoidEngine.Render.VulkanContext;
import java.util.Scanner;

import com.kgaft.VoidEngine.Window.Window;


public class Main {
    public static void main(String[] args) {
        Logger.startLogger("VoidEngineLog.txt");
        Window.prepareWindow(800, 600, "VoidEngine", true);
        Window window = Window.getWindow();
        VulkanContext.initialize(true);
        VulkanContext.pickDevice(window, supportedDevices -> {
            if (supportedDevices.size() > 1) {
                supportedDevices.forEach(element -> {
                    System.out.println(element.getProperties().get().deviceNameString());
                });
                Scanner scanner = new Scanner(System.in);
                return supportedDevices.get(scanner.nextInt());
            }
            return supportedDevices.getFirst();
        }, true);
        while (window.isWindowActive()) {
            window.postEvents();
        }
        VulkanContext.shutdown();
        Logger.shutdown();
    }
}
