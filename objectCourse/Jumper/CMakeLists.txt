cmake_minimum_required(VERSION 3.3)
project(Jumper)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp Jumper.cpp Jumper.h)
add_executable(Jumper ${SOURCE_FILES})