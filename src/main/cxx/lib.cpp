#include "com_kgaft_VoidEngine_JNI_VulkanContext.h"
#include <iostream>
#include <VulkanLib/Instance.hpp>
#include <map>
std::shared_ptr<Instance> instance;
JNIEXPORT void JNICALL Java_com_kgaft_VoidEngine_JNI_VulkanContext_initializeInstance
    (JNIEnv *env, jclass, jboolean sdlWindow, jobject list){
    jclass listClass = env->GetObjectClass(list);

    // Get size() method ID
    jmethodID sizeMethod = env->GetMethodID(listClass, "size", "()I");
    jint size = env->CallIntMethod(list, sizeMethod);

    // Get get(int index) method ID
    jmethodID getMethod = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");

    std::map<jstring, const char*> extensions;
    InstanceBuilder builder;
    for (jint i = 0; i < size; i++) {
        // Get the String object from the list
        jstring jStr = (jstring) env->CallObjectMethod(list, getMethod, i);

        // Convert Java String to C++ string
        const char *cStr = env->GetStringUTFChars(jStr, nullptr);

        extensions[jStr] = cStr;
        builder.addExtension(cStr);

    }
    if(Instance::debugSupported()){
        builder.presetForDebug();
    }
    instance = std::make_shared<Instance>(builder);
    for(auto& item: extensions){
        env->ReleaseStringUTFChars(item.first, item.second);
        env->DeleteLocalRef(item.first);
    }
}

/*
 * Class:     com_kgaft_VoidEngine_JNI_VulkanContext
 * Method:    getInstanceHandle
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_kgaft_VoidEngine_JNI_VulkanContext_getInstanceHandle
    (JNIEnv *, jclass){
    auto localInstance = (VkInstance)instance->getInstance();
    return (jlong) localInstance;
}
