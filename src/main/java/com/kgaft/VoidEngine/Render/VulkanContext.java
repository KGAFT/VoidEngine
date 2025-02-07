package com.kgaft.VoidEngine.Render;

import com.kgaft.VoidEngine.Logging.Logger;
import com.kgaft.VoidEngine.Logging.LoggerMessage;
import com.kgaft.VoidEngine.Logging.VulkanLoggerCallback;
import com.kgaft.VoidEngine.Window.Window;
import com.kgaft.VulkanLib.Device.DeviceBuilder;
import com.kgaft.VulkanLib.Device.LogicalDevice.LogicalDevice;
import com.kgaft.VulkanLib.Device.PhysicalDevice.DeviceSuitability;
import com.kgaft.VulkanLib.Device.PhysicalDevice.DeviceSuitabilityResults;
import com.kgaft.VulkanLib.Device.PhysicalDevice.PhysicalDevice;
import com.kgaft.VulkanLib.Instance.Instance;
import com.kgaft.VulkanLib.Instance.InstanceBuilder;
import com.kgaft.VulkanLib.Instance.InstanceLogger.DefaultVulkanFileLoggerCallback;
import com.kgaft.VulkanLib.Instance.InstanceLogger.DefaultVulkanLoggerCallback;
import com.kgaft.VulkanLib.Utils.VkErrorException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.KHRSurface;

import java.io.FileNotFoundException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;

public class VulkanContext {
    private static Instance instance = null;
    private static boolean debugEnabled = false;
    private static LogicalDevice device = null;
    private static Window attachedWindow = null;
    public static void initialize(boolean requireDebug){
        PointerBuffer windowExtensions = glfwGetRequiredInstanceExtensions();
        InstanceBuilder builder = null;
        try {
            builder = new InstanceBuilder();
        } catch (IllegalClassFormatException e) {
            throw new RuntimeException(e);
        }
        if(requireDebug){
            builder.presetForDebug();
            debugEnabled = requireDebug;
            builder.addStartingVulkanLoggerCallback(new VulkanLoggerCallback());
        }
        Logger.dispatchMessage(new LoggerMessage("GENERAL", "VOIDENGINE_VK_CONTEXT", "VERBOSE", "initializing instance", false));
        builder.presetForPresent();
        while (windowExtensions.hasRemaining()) {
            builder.addExtension(windowExtensions.getStringUTF8());
        }

        try {
            instance = new Instance(builder);
            Logger.dispatchMessage(new LoggerMessage("GENERAL", "VOIDENGINE_VK_CONTEXT", "VERBOSE", "instance successfully initialized", false));
        } catch (VkErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public static void pickDevice(Window window, IDevicePicker picker, boolean rtEnable){
        Logger.dispatchMessage(new LoggerMessage("GENERAL", "VOIDENGINE_VK_CONTEXT", "VERBOSE", "started picking device phase", false));
        List<PhysicalDevice> devices = null;
        try {
            devices = PhysicalDevice.getPhysicalDevices(instance);
        } catch (VkErrorException e) {
            throw new RuntimeException(e);
        }
        HashMap<PhysicalDevice, DeviceSuitabilityResults> supportedDevices = new HashMap<>();
        List<PhysicalDevice> supportedDevicesS = new ArrayList<>();
        DeviceBuilder deviceBuilder= new DeviceBuilder();
        deviceBuilder.requestGraphicSupport();
        deviceBuilder.addExtension("fjnosadofhbods");
        if(rtEnable){
            deviceBuilder.requestRayTracingSupport();
        }
        deviceBuilder.requestPresentSupport(window.getSurface(instance.getInstance()));
        devices.forEach(dev -> {
            try {
                DeviceSuitabilityResults results = DeviceSuitability.isDeviceSuitable(deviceBuilder, dev);
                if(results!=null){
                    supportedDevices.put(dev, results);
                    supportedDevicesS.add(dev);
                }
            } catch (VkErrorException e) {
                throw new RuntimeException(e);
            }
        });
        attachedWindow = window;

        Logger.dispatchMessage(new LoggerMessage("GENERAL", "VOIDENGINE_VK_CONTEXT", "VERBOSE", "found "+supportedDevicesS.size()+" devices", false));
        if(supportedDevicesS.isEmpty()){
            Logger.dispatchMessage(new LoggerMessage("GENERAL", "VOIDENGINE_VK_CONTEXT", "ERROR", "found Failed to find suitable devices", true));
            throw new RuntimeException("Failed to find suitable devices");
        }
        PhysicalDevice physDev = picker.pickDevice(supportedDevicesS);
        Logger.dispatchMessage(new LoggerMessage("GENERAL", "VOIDENGINE_VK_CONTEXT", "INFO", "Selected device: "+physDev.getProperties().get().deviceNameString(), false));
        try {
            device = new LogicalDevice(instance, physDev, deviceBuilder, supportedDevices.get(physDev));
        } catch (VkErrorException e) {
            throw new RuntimeException(e);
        }


    }

    public static void shutdown(){
        if(device!=null){
            device.destroy();
            KHRSurface.vkDestroySurfaceKHR(instance.getInstance(), attachedWindow.getSurface(instance.getInstance()), null);
        }
        if(instance!=null){
            instance.destroy();
        }

    }


}
