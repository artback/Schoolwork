cmake_minimum_required(VERSION 3.3)
project(friendbook)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    CMakeFiles/3.3.2/CompilerIdC/CMakeCCompilerId.c
    CMakeFiles/3.3.2/CompilerIdCXX/CMakeCXXCompilerId.cpp
    CMakeFiles/3.4.0-rc3/CompilerIdC/CMakeCCompilerId.c
    CMakeFiles/3.4.0-rc3/CompilerIdCXX/CMakeCXXCompilerId.cpp
    CMakeFiles/feature_tests.c
    CMakeFiles/feature_tests.cxx
    Friend.cpp
    Friend.h
    Friendbook.cpp
    Friendbook.h
    main.cpp
    utilities.cpp
    utilities.h)

add_executable(friendbook ${SOURCE_FILES})