mvn clean jacoco:prepare-agent install jacoco:report

open target/site/jacoco/index.html

#mvn clean package

# java -jar /home/liam/Documents/cs222/backend/server/target/UniversalRecognition-1.0-SNAPSHOT-jar-with-dependencies.jar  \
#      -pp /home/liam/.conda/envs/cs222/bin/python3 -mp output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth -i /home/liam/Documents/cs222/backend/server/src/test/resources/uiuc.png

#java -jar target/UniversalRecognition-1.0-SNAPSHOT-jar-with-dependencies.jar \
#     -pp  /home/liam/.conda/envs/cs222/bin/python3 \
#     -mp output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth \
#     -i ../server/src/test/resources/uiuc.png \
#     -p 1080
