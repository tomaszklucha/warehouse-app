#!/bin/bash
sudo apt update -y
sudo apt install openjdk-11-jre awscli -y

sudo  aws s3 cp s3://horizons-warehouse-app/app-${version}.jar /opt/app.jar

sudo echo "spring.datasource.url=jdbc:postgresql://${dbHost}:${dbPort}/${dbName}" >> /opt/application.properties
sudo echo "spring.datasource.username=${dbUsername}" >> /opt/application.properties
sudo echo "spring.datasource.password=${dbPassword}" >> /opt/application.properties

cd /opt
sudo nohup java -jar app.jar &
