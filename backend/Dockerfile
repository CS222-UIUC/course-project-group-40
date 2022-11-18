# Version 0.1

# Basic Image
FROM ubuntu:latest

# Personal Info
LABEL maintainer="liam@cs222-40"

# Commands
# Set up tools
RUN apt-get update \
    && apt-get install -y git default-jre default-jdk \
    && apt-get install -y python3.6 pip

# Clone Git Repo
RUN git clone https://github.com/CS222-UIUC/course-project-group-40.git cs222

# Pip install Machine Learning libraries
RUN yes | pip install -r cs222/backend/vision_model/requirements.txt

# Start to build the server program
RUN cd cs222/backend/server \
    && ./mvnw clean package \
    && cd ../vision_model \
    && bash ./output/object/download_weights.sh
# Download weights of Deep Learning models


# Global Env Variables
ENV PART SERVER
ENV CUDA_VISIBLE_DEVICES ""

# Export Port for Android client to connect
EXPOSE 1080

# For Reference Only
# https://stackoverflow.com/questions/33907835/docker-error-cannot-delete-docker-container-conflict-unable-to-remove-reposito
# list all containers:
# docker ps -a 
# list all images:
# docker images
# remove container first and then image:
# docker rm <container_id>
# docker rmi <image_id>
