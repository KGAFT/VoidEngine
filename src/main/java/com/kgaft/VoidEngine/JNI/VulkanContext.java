package com.kgaft.VoidEngine.JNI;

import java.util.List;

public class VulkanContext {
    public static native void initializeInstance(boolean sdlWindow, List<String> enableExtensions);
    public static native long getInstanceHandle();
}
