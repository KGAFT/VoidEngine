package com.kgaft.VoidEngine.Logging;

import com.kgaft.VulkanLib.Instance.InstanceLogger.IVulkanLoggerCallback;
import com.kgaft.VulkanLib.Instance.InstanceLogger.VulkanLoggerCallbackType;

public class VulkanLoggerCallback implements IVulkanLoggerCallback {
    @Override
    public void messageRaw(int i, int i1, long l, long l1) {

    }

    @Override
    public void translatedMessage(String severity, String type, String messageS, boolean isError) {
        LoggerMessage message = new LoggerMessage(type, "VULKAN", severity, messageS, isError);
        Logger.dispatchMessage(message);
    }

    @Override
    public VulkanLoggerCallbackType getCallbackType() {
        return VulkanLoggerCallbackType.TRANSLATED_VULKAN_DEFS;
    }
}
