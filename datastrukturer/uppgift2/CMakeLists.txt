cmake_minimum_required(VERSION 3.3)
project(uppgift2)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES Course.h Hash.h HashTableLinearProbing.h HashingCourses.cpp)

add_executable(uppgift2 ${SOURCE_FILES})