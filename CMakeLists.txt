cmake_minimum_required(VERSION 3.10)

project(VoidEngine)

add_subdirectory(modules/SDL)
add_subdirectory(modules/VulkanLib)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/bin)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

file(COPY ${CMAKE_CURRENT_BINARY_DIR}/compile_commands.json DESTINATION ${CMAKE_CURRENT_SOURCE_DIR})

find_package(JNI REQUIRED)
find_package(Python3 REQUIRED COMPONENTS Interpreter)
execute_process(COMMAND ${Python3_EXECUTABLE} ${CMAKE_CURRENT_SOURCE_DIR}/make_headers.py ${CMAKE_CURRENT_SOURCE_DIR}/src/main/java/com/kgaft/VoidEngine/JNI ${CMAKE_CURRENT_SOURCE_DIR}/jni_include/)

file(GLOB_RECURSE SRC_FILES src/main/cxx/*.*)

add_library(VoidEngine SHARED ${SRC_FILES})

target_include_directories(VoidEngine PRIVATE ${JNI_INCLUDE_DIRS} ${CMAKE_CURRENT_SOURCE_DIR}/jni_include/)
target_link_libraries(VoidEngine PRIVATE ${JNI_LIBRARIES} SDL3::SDL3-shared VulkanLib)
