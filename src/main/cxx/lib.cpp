#include "com_kgaft_VoidEngine_JNI_Test.h"
#include "com_kgaft_VoidEngine_JNI_Second.h"
#include <iostream>

JNIEXPORT void JNICALL Java_com_kgaft_VoidEngine_JNI_Test_sayHello
  (JNIEnv *, jclass){
    std::cout<<"Hello world!"<<std::endl;
  }
  JNIEXPORT void JNICALL Java_com_kgaft_VoidEngine_JNI_Second_second
    (JNIEnv *, jclass){
           std::cout<<"second hello!"<<std::endl;
    }