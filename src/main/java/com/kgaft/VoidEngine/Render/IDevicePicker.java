package com.kgaft.VoidEngine.Render;

import com.kgaft.VulkanLib.Device.PhysicalDevice.PhysicalDevice;

import java.util.List;

public interface IDevicePicker {
    PhysicalDevice pickDevice(List<PhysicalDevice> supportedDevices);
}
