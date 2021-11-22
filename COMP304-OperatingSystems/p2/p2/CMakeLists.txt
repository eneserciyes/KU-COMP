cmake_minimum_required(VERSION 3.10)
project(304_p2)

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)


if(NOT CMAKE_CXX_STANDARD)
  set(CMAKE_CXX_STANDARD 14)
endif()

add_executable(main p2.cpp)
target_include_directories(main PUBLIC include)
target_link_libraries(main PRIVATE Threads::Threads)