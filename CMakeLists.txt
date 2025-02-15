cmake_minimum_required(VERSION 3.10)

project(VoidEngine)

add_subdirectory(modules/SDL)
add_subdirectory(modules/VulkanLib)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/bin)

find_package(JNI REQUIRED)
find_package(Python3 REQUIRED COMPONENTS Interpreter)
execute_process(COMMAND ${Python3_EXECUTABLE} ${CMAKE_CURRENT_SOURCE_DIR}/make_headers.py ${CMAKE_CURRENT_SOURCE_DIR}/src/main/java/com/kgaft/VoidEngine/JNI ${CMAKE_CURRENT_SOURCE_DIR}/jni_include/)

file(GLOB_RECURSE SRC_FILES src/main/cxx/*.*)

add_library(VoidEngine SHARED ${SRC_FILES})

target_include_directories(VoidEngine PRIVATE ${JNI_INCLUDE_DIRS} jni_include/)
target_link_libraries(VoidEngine PRIVATE ${JNI_LIBRARIES} SDL3::SDL3-shared VulkanLib)
