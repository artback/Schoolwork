cmake_minimum_required(VERSION 3.3)
project(Vehicle)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    Boat.cpp
    Boat.h
    Car.cpp
    Car.h
    main.cpp
    Truck.cpp
    Truck.h
    Vehicle.cpp
    Vehicle.h)

add_executable(Vehicle ${SOURCE_FILES})