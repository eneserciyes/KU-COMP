cmake_minimum_required(VERSION 3.10)
project(p1 C)

set(CMAKE_C_STANDARD 11)

set(SOURCES seashell.c hashmap.c include/hashmap.h)
add_executable(p1
        ${SOURCES})

target_include_directories(p1 PRIVATE include)