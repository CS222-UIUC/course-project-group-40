# Version 0.1

# Basic Image
FROM ubuntu:latest

# Personal Info
LABEL maintainer="liam@cs222-40"

# Commands
# Set up tools
RUN apt-get update \
    && apt-get install -y git default-jre default-jdk

# Clone Git Repo
RUN git clone https://github.com/CS222-UIUC/course-project-group-40.git cs222

# Start to build the server program
RUN cd cs222/backend/server \
    && ./mvnw clean package

# Global Env Variables
ENV PART SERVER

# Export Port for Android client to connect
EXPOSE 80 1080