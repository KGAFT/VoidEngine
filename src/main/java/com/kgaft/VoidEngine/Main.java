package com.kgaft.VoidEngine;

import com.kgaft.VoidEngine.JNI.Second;
import com.kgaft.VoidEngine.JNI.Test;

import java.io.File;

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

    public static void main(String[] args) {
        Second.second();
        Test.sayHello();
        System.out.println("Hello, World!");
    }
}