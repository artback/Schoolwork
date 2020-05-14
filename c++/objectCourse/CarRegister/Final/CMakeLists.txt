cmake_minimum_required(VERSION 3.3)
project(CarRegister)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    Car.cpp
    Car.h
    Carregister.cpp
    Carregister.h
    main.cpp)

add_executable(CarRegister ${SOURCE_FILES})