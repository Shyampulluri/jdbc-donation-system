#!/bin/bash

# 1. Start a Virtual Framebuffer (Xvfb) on display :99
Xvfb :99 -screen 0 1024x768x16 &
export DISPLAY=:99

# 2. Start VNC server so you can view the Swing GUI remotely
x11vnc -display :99 -nopw -forever -bg -xkb

# 3. Run the original Java Swing application
java -cp ".:h2.jar" StudentForm