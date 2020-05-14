cmake_minimum_required(VERSION 3.3)
project(extenta)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp Media.cpp Media.h NewsPaper.cpp NewsPaper.h Books.cpp Books.h)
add_executable(extenta ${SOURCE_FILES})