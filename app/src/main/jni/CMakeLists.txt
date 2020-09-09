cmake_minimum_required(VERSION 3.10)

project(liveEffect LANGUAGES C CXX)

### INCLUDE OBOE LIBRARY ###
set (OBOE_DIR ../../../../oboe)
add_subdirectory(${OBOE_DIR} ./oboe-bin)

add_library(liveEffect
    SHARED
        LiveEffectEngine.cpp
        FullDuplexStream.cpp
        jni_bridge.cpp
        debug-utils/trace.cpp)
target_include_directories(liveEffect
    PRIVATE
        debug-utils
        ${OBOE_DIR}/include)
target_link_libraries(liveEffect
    PRIVATE
        oboe
        android
        atomic
        log)

# Enable optimization flags: if having problems with source level debugging,
# disable -Ofast ( and debug ), re-enable it after done debugging.
target_compile_options(liveEffect PRIVATE -Wall -Werror "$<$<CONFIG:RELEASE>:-Ofast>")

