# CS222 Course Project - Group 40
We only tested this project on Macbook laptops with Intel chips.

# Table of Contents
**[Introduction](#Introduction)**<br>
**[Features](#Features)**<br>
**[Technical Architecture](#Technical-Architecture)**<br>
**[Installation and Usage](#Installation-and-Usage)**<br>
**[Group Information](#Group-Information)**<br>

## Introduction
- Project Name: Universal Recognition
- This project integrates and presents the computer vision technologies such as Optical Character Recognition (OCR) and Object Detection on Android mobile devices with the help of the server program and pre-trained Machine Learning models.
- There exist some applications or software that provide similar functionalities related to Optical Character Recognition (OCR) or Object Detection, but all of them only support one of these two techniques rather than both of them to the best of our knowledge. However, our project provides the functionalities with both of Optical Character Recognition (OCR) and Object Detection in one single application.

## Features
- 
-
- 


## Technical Architecture
- Diagram of Application Architecture:  
![Diagram of Application Architecture](https://github.com/CS222-UIUC/course-project-group-40/blob/main/extra_files/architecture.jpeg)
- For each component, explains:
-   (b) Role in the application
-   (c) Interactions with other components 
-   (d) Languages/libraries used to build it 
-   (e) Who worked on it

- According to our project proposal, we devided our application architecture into three components: Machine Learning program, Server program, and Android client.
 
### Backend: Machine Learning
-

### Backend: Server
The server program includes several modules such as the network libraries, Docker service, Java main server program, Image libraries, System IO and File System.
- The server program plays an important role in the application. It encapsulates and handles most of tasks in this application without disturbing users.
- The main functionality of the server program is to communicate with Machine Learning prediction program and Android client via TCP connections. It receives images from Android client and stores these images to call Machine Learning prediction program. After Machine Learning prediction program finishes its calculation, the server program records the evaluation result and sends it to Android client.
- The server program is written in Java. It utilizes a plenty of libraries such as maven, jcommander and so on. Moreover, Docker is used and integrated to help us easily share and run the server program.
- Yang Duan mainly worked on the server program.

### Frontend: Android
-

## Installation and Usage
### Installation
Assumption: docker is installed and running
```bash
# Use curl (curl is installed in Mac OS by default)
sh -c "$(curl -fsSL https://raw.githubusercontent.com/CS222-UIUC/course-project-group-40/main/backend/install.sh)"
```
```bash
# Or use wget (wget needs to be installed beforehand)
sh -c "$(wget https://raw.githubusercontent.com/CS222-UIUC/course-project-group-40/main/backend/install.sh -O -)"
```

### Machine Learning Models for Vision Recognition
>python 3.8
>torch 1.12.1
>flake8 5.0.4

### Usage
1. Install and run docker first
2. Run the one-line command in the above Installation section. After running that line of code, this github repo is automatically cloned, the pre-trained Machine Learning models are downloaded, and the server is automatically started.
3. Change directory into `cs222` under the directory where you run the command in the last step
4. Android: 


## Group Information
- Yuanxi Li, 
- Yang Duan (yangd4), mainly worked on the server program. He also worked with other team members on the communication part of Android client. 
- Mike Cheng, 
- Ziyu Wang,


