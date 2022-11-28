# Universal Recognition
We only tested this project on Macbook laptops with Intel chips.

# Table of Contents
**[Introduction](#Introduction)**<br>
**[Technical Architecture](#Technical-Architecture)**<br>
**[Installation and Usage](#Installation-and-Usage)**<br>
**[Group Information](#Group-Information)**<br>

## Introduction



## Technical Architecture
### Machine Learning Model

### Server
The server program needs to communicate with Yuanxi's Machine Learning prediction programs and Mike and Ziyu's Android programs.

### Android


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
- Yang Duan (yangd4), mainly works on the server program. 
- Mike Cheng, 
- Ziyu Wang,

