# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 2.8

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list

# Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /boilermake/opencv-src/opencv

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /boilermake/opencv-cmake

# Utility rule file for pch_Generate_opencv_softcascade.

# Include the progress variables for this target.
include modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/progress.make

modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade: modules/softcascade/precomp.hpp.gch/opencv_softcascade_Release.gch

modules/softcascade/precomp.hpp.gch/opencv_softcascade_Release.gch: /boilermake/opencv-src/opencv/modules/softcascade/src/precomp.hpp
modules/softcascade/precomp.hpp.gch/opencv_softcascade_Release.gch: modules/softcascade/precomp.hpp
modules/softcascade/precomp.hpp.gch/opencv_softcascade_Release.gch: lib/libopencv_softcascade_pch_dephelp.a
	$(CMAKE_COMMAND) -E cmake_progress_report /boilermake/opencv-cmake/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Generating precomp.hpp.gch/opencv_softcascade_Release.gch"
	cd /boilermake/opencv-cmake/modules/softcascade && /usr/bin/cmake -E make_directory /boilermake/opencv-cmake/modules/softcascade/precomp.hpp.gch
	cd /boilermake/opencv-cmake/modules/softcascade && /usr/bin/c++ -O3 -DNDEBUG -DNDEBUG -fPIC -DOPENCV_NOSTL -I"/boilermake/opencv-src/opencv/modules/ml/include" -I"/boilermake/opencv-src/opencv/modules/imgproc/include" -I"/boilermake/opencv-src/opencv/modules/core/include" -isystem"/boilermake/opencv-cmake/modules/softcascade" -I"/boilermake/opencv-src/opencv/modules/softcascade/src" -I"/boilermake/opencv-src/opencv/modules/softcascade/include" -isystem"/boilermake/opencv-cmake" -D__OPENCV_BUILD=1 -fsigned-char -W -Wall -Werror=return-type -Werror=non-virtual-dtor -Werror=address -Werror=sequence-point -Wformat -Werror=format-security -Winit-self -Wpointer-arith -Wshadow -Wsign-promo -Wno-narrowing -Wno-delete-non-virtual-dtor -fdiagnostics-show-option -Wno-long-long -pthread -fomit-frame-pointer -msse -msse2 -msse3 -ffunction-sections -fvisibility=hidden -fvisibility-inlines-hidden -Wno-undef -Wno-missing-declarations -DCVAPI_EXPORTS -x c++-header -o /boilermake/opencv-cmake/modules/softcascade/precomp.hpp.gch/opencv_softcascade_Release.gch /boilermake/opencv-cmake/modules/softcascade/precomp.hpp

modules/softcascade/precomp.hpp: /boilermake/opencv-src/opencv/modules/softcascade/src/precomp.hpp
	$(CMAKE_COMMAND) -E cmake_progress_report /boilermake/opencv-cmake/CMakeFiles $(CMAKE_PROGRESS_2)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold "Generating precomp.hpp"
	cd /boilermake/opencv-cmake/modules/softcascade && /usr/bin/cmake -E copy /boilermake/opencv-src/opencv/modules/softcascade/src/precomp.hpp /boilermake/opencv-cmake/modules/softcascade/precomp.hpp

pch_Generate_opencv_softcascade: modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade
pch_Generate_opencv_softcascade: modules/softcascade/precomp.hpp.gch/opencv_softcascade_Release.gch
pch_Generate_opencv_softcascade: modules/softcascade/precomp.hpp
pch_Generate_opencv_softcascade: modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/build.make
.PHONY : pch_Generate_opencv_softcascade

# Rule to build all files generated by this target.
modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/build: pch_Generate_opencv_softcascade
.PHONY : modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/build

modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/clean:
	cd /boilermake/opencv-cmake/modules/softcascade && $(CMAKE_COMMAND) -P CMakeFiles/pch_Generate_opencv_softcascade.dir/cmake_clean.cmake
.PHONY : modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/clean

modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/depend:
	cd /boilermake/opencv-cmake && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /boilermake/opencv-src/opencv /boilermake/opencv-src/opencv/modules/softcascade /boilermake/opencv-cmake /boilermake/opencv-cmake/modules/softcascade /boilermake/opencv-cmake/modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : modules/softcascade/CMakeFiles/pch_Generate_opencv_softcascade.dir/depend

