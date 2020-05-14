cmake_minimum_required(VERSION 3.3)
project(shape)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp Shape.cpp Shape.h Circle.cpp Circle.h Rectangle.cpp Rectangle.h List.cpp List.h)
add_executable(shape ${SOURCE_FILES})