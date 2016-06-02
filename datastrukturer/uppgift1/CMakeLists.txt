cmake_minimum_required(VERSION 3.3)
project(uppgift1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    Snake.cpp
    Snake.h
    SnakeEntity.cpp
    SnakeEntity.h
    SnakeHead.cpp
    SnakeHead.h main.cpp main.cpp)

add_executable(uppgift1 ${SOURCE_FILES})