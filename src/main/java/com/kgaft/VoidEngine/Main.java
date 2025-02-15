package com.kgaft.VoidEngine;

import com.kgaft.VoidEngine.JNI.Second;
import com.kgaft.VoidEngine.JNI.Test;
import com.kgaft.VulkanLib.Instance.Instance;
import com.kgaft.VulkanLib.Instance.InstanceBuilder;
import com.kgaft.VulkanLib.Utils.VkErrorException;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class Main {

    //Debug only
    static {
        File file = new File("bin/");
        for (File listFile : file.listFiles()) {
            try{
                System.load(listFile.getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws VkErrorException, IllegalClassFormatException {
        Second.second();
        Test.sayHello();
        glfwInit();
        InstanceBuilder builder = new InstanceBuilder();
        builder.presetForPresent();
        builder.presetForDebug();
        Instance instance = new Instance(builder);
    }
}