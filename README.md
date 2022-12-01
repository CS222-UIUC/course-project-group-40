# CS222 Course Project - Group 40
We only tested this project on Macbook laptops with Intel chips.

# Table of Contents
**[Introduction](#Introduction)**<br>
**[Features](#Features)**<br>
**[Technical Architecture](#Technical-Architecture)**<br>
**[Development](#Development)**<br>
**[Installation and Usage](#Installation-and-Usage)**<br>
**[Group Information](#Group-Information)**<br>

## Introduction
- Project Name: Universal Recognition
- This project integrates and presents the computer vision technologies such as Optical Character Recognition (OCR) and Object Detection on Android mobile devices with the help of the server program and pre-trained Machine Learning models.
- There exist some applications or software that provide similar functionalities related to Optical Character Recognition (OCR) or Object Detection, but all of them only support one of these two techniques rather than both of them to the best of our knowledge. However, our project provides the functionalities with both of Optical Character Recognition (OCR) and Object Detection in one single application.

## Features
- Implemented the function of  Optical Character Recognition (OCR) which is able to convert text images to machine-readable text format.
- Implemented the function of Object Detection which uses YOLOv3 to detects objects from pictures from your phone.
- Utilized Docker to easily share the server program and interact with other components conveniently.
- Utilized TCP network protocol to ensure reliability and security.
- Allow users to utilize cell phone camera to take photos or select target photo from photo gallery.
- Users can crop image from select photo and rotate them.


## Technical Architecture
- Diagram of Application Architecture:  
![Diagram of Application Architecture](https://github.com/CS222-UIUC/course-project-group-40/blob/main/extra_files/architecture.jpeg)

- According to our project proposal, we devided our application architecture into three components: Machine Learning program, Server program, and Android client.
 
### Backend: Machine Learning
Our application contains two core machine learning modules, Optical Character Recognition (OCR) and Object Detection (ObjDetect). They are used to provide the feedback to users based on their input images after computations.
- Optical Character Recognition (OCR) in this project is implemented based on the backbone of MoblieNet, and uses the pre-trained network architecture of CRNN.
- Object Detection (ObjDetect) in this project is implemented based on the YOLOv3 framework, which is pre-trained on COCO dataset using a Darknet-53 backend on ImageNet.
- Both of the Machine Learning module is implemented in Python and PyTorch and utilizes a plenty of libraries such as Numpy and OpenCV. 
- Although we support freely training with customized training parameters and modifying the ML model structure, the finetuned models have been directly encapsulated in this project and supports direct calls from the server script to make OCR or ObjDetect predictions. 
- The model makes recognition and prediction based on the images received from the server program, and returns the results back to the server for final presentation in the front-end of the app.
- Yuanxi Li mainly worked on Machine Learning Modules, which is the core algorithm part in this application.
 
### Backend: Server
The server program includes several modules such as the network libraries, Docker service, Java main server program, Image libraries, System IO and File System.
- The server program plays an important role in the application. It encapsulates and handles most of tasks in this application without disturbing users.
- The main functionality of the server program is to communicate with Machine Learning prediction program and Android client via TCP connections. It receives images from Android client and stores these images to call Machine Learning prediction program. After Machine Learning prediction program finishes its calculation, the server program records the evaluation result and sends it to Android client.
- The server program is written in Java. It utilizes a plenty of libraries such as maven, jcommander and so on. Moreover, Docker is used and integrated to help us easily share and run the server program.
- Yang Duan mainly worked on the server program.

### Frontend: Android
- Android is the interfact and plays an important role on displaying the straightforward result.
- Android allows users to interact with the backend server. It provides a platform for users to take potos, display their select picture and show the corresponding result.
- The Android APP is written in Java. It uses Gradle to manage library dependencies. The libraries used here are expresso, commons-io, android-image-cropper and so on.
- The frontend part are implemented by Ziyu Wang and Mike Cheng.

## Development
- There is one problem about the interaction between Machine Learning models and the server program. We previous planned to load pre-trained models and do the prediction purely on Java inside the server program. After testing several popular Machine Learning Java libraries, we found compatibility issues with PyTorch models that caused the server program to malfunction. We finally decided to let the server program create one subprocess to call the python programs with Numpy and PyTorch to implement this part of functionalities in our project. In this way, we can avoid unnecessary redundant work on Java and utilize the native environment of Python and PyTorch to do the prediction, which makes the computation incredibly efficient.

- During the early stage of the development, a group member faced difficulties setting up Github in Android Studio. When he tried to pull from or push to the remote repository, errors that he didn't understand occured. His initial approach was to search on the internet and try various solutions online, but none of those solutions work, and the problem went worse due to his improper operation without enough knowledge. Later, he turned to his teammates who have richer experience and knowledge about Git. After several conversations and Zoom sessions, he was able to spot the issue with his teammates, and he learned a lot from this experience. This not only includes the Git knowledge but also includes the importance of working as a team. When working as a team, teammates can share their knowledge and experience with each other, making progress and learning new things more effectively.


## Installation and Usage
### Installation
Note: You need to install Android Studo and Docker.    
 
Assumption: docker is installed and running
```bash
# Use curl (curl is installed in Mac OS by default)
sh -c "$(curl -fsSL https://raw.githubusercontent.com/CS222-UIUC/course-project-group-40/main/backend/install.sh)"
```
Or
```bash
# Use wget (wget needs to be installed beforehand)
sh -c "$(wget https://raw.githubusercontent.com/CS222-UIUC/course-project-group-40/main/backend/install.sh -O -)"
```

### Usage
1. Install and run docker first
2. Run the one-line command in the above Installation section. After running that line of code, this github repo is automatically cloned, the pre-trained Machine Learning models are downloaded, and the server is automatically started [our default network port is 1040, please make sure this port is not used by other applications].       
Note: If you want to exit the output window of the server program, you can close the terminal window and the server program will continue working as a background process. If you stop the docker container and want to restart it, you need to run docker first and run the following command to restart the container and run the server program inside it:
```shell
docker run -p 1040:1040 -i universalrecognition:v1 sh -c "cd cs222/backend/server/ && \
java -jar target/UniversalRecognition-1.0-SNAPSHOT-jar-with-dependencies.jar \
     -pp /usr/bin/python3 \
     -mp output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth \
     -i ../server/src/test/resources/uiuc.png \
     -p 1040"
```
3. Change directory into `cs222` under the directory where you run the command in the last step
4. Install Android Studio
5. Start the application from Android Studio by open the folder `frontend/android-frontend` and then clicking the triangle button to run the app. To run our Android APP successfully, please make sure to use the most updated version Android Studio - Dolphin. To make sure the app run smoothly on the emulator, please set up one emulator with the version Pixel 3 API 28.
6. You will see an interface that looks like this:

<img src="https://github.com/CS222-UIUC/course-project-group-40/blob/main/extra_files/UI.jpeg" height="500" />

5. By clicking on the "Launch Image Recognize" button, you will see a window allowing you to select the image source, including taking photo through camera and selecting image from system files. 
6. After getting an image of your choice, the application will display the image on the screen and enable "OCR Results" and "Detect Object" buttons. 

<img src="https://github.com/CS222-UIUC/course-project-group-40/blob/main/extra_files/UI_with_Image.jpeg" height="500" />

7. You may enter the address and port of a server into the text feilds near the bottom to "Connect" to the server. Default settings are provided. 
8. After clicking on the "Connect" button, you can select either "OCR Result" or "Detect Object" based on your purpose. OCR results will be displayed in a new page, and the object detection results will be drawn on the image. 


## Group Information
- Yuanxi Li (yuanxi3), mainly worked on the implementation and deployment of machine learning modules, He is also responsible for the design of prediction APIs for server program.
- Yang Duan (yangd4), mainly worked on the server program. He also worked with other team members on the communication part of Android client. 
- Mike Cheng (minyuc2), mainly worked on the frontend logic and usr interface. He also deals with the deserialization and display of data received from backend. 
- Ziyu Wang (ziyu4), mainly worked on the Android Mobile App.


