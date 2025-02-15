cmake_minimum_required(VERSION 3.10)

project(VoidEngine)

add_subdirectory(modules/SDL)
add_subdirectory(modules/VulkanLib)

find_package(JNI REQUIRED)

file(GLOB_RECURSE SRC_FILES src/main/cxx/*.*)

message(WARNING ${JAVA_FILES})
add_library(VoidEngine SHARED ${SRC_FILES})

target_include_directories(VoidEngine PRIVATE ${JNI_INCLUDE_DIRS} jni_include/)
target_link_libraries(VoidEngine PRIVATE ${JNI_LIBRARIES} SDL3::SDL3-shared VulkanLib)
