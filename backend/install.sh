#!/bin/bash

# For Grader Use Only

# Clone our project repo
git clone https://github.com/CS222-UIUC/course-project-group-40.git cs222
cd cs222/backend/

# Assumption: docker is running
# build the image based on the Dockerfile in this folder
docker build -t universalrecognition:v1 .

# run the image and login as root
docker run -p 1040:1040 -i universalrecognition:v1 /bin/bash << 'EOF'

# start the server program
cd cs222/backend/server/
java -jar target/UniversalRecognition-1.0-SNAPSHOT-jar-with-dependencies.jar \
     -pp /usr/bin/python3 \
     -mp output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth \
     -i ../server/src/test/resources/uiuc.png \
     -p 1040

exit
EOF
