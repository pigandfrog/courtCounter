#include <jni.h>
#include <string>
#include <iostream>


JNIEXPORT jintArray JNICALL
Java_com_example_richard_courtcounter_MainActivity_myIntArry(JNIEnv *env, jobject instance,
                                                             jintArray arr_) {
    jint *arr = env->GetIntArrayElements(arr_, NULL);

    // TODO

    env->ReleaseIntArrayElements(arr_, arr, 0);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_richard_courtcounter_MainActivity_teamAScore(JNIEnv *env, jobject instance,
                                                              jint a, jint prevScore) {

    jint sum = a + prevScore;

    return sum;

}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_richard_courtcounter_MainActivity_teamBScore(JNIEnv *env, jobject instance,
                                                              jint a, jint prevScore) {

   jint sum = a + prevScore;
    return  sum;

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_richard_courtcounter_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

