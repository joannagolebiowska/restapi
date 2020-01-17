#!/bin/bash


APP_IMAGE_NAME=xmlanalyzer-img
APP_CONTAINER_NAME=xmlanalyzer

#============================INSTALL MAVEN=====================================#
echo "$(tput setaf 3)Checking maven...$(tput sgr0)"
if [[ "$(dpkg -l | grep maven)" == "" ]]; then
    echo "$(tput setaf 3)Installing maven$(tput sgr0)"
    apt-get install -y maven
    echo "$(tput setaf 2)OK - maven installation completed.$(tput sgr0)"
else
    echo "$(tput setaf 2)OK - maven has already been installed.$(tput sgr0)"
fi
#===============================================================================#

#============================INSTALL DOCKER=====================================#
echo "$(tput setaf 3)Checking docker...$(tput sgr0)"
if [[ "$(dpkg -l | grep docker)" = "" ]]; then
    echo "$(tput setaf 3)Installing docker$(tput sgr0)"
    apt-get install -y libltdl7
    export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
    wget https://download.docker.com/linux/debian/dists/buster/pool/stable/amd64/docker-ce_18.06.3~ce~3-0~debian_amd64.deb
    dpkg -i  docker-ce_18.06.3~ce~3-0~debian_amd64.deb
    echo "$(tput setaf 2)OK - docker installation completed.$(tput sgr0)"
else
    echo "$(tput setaf 2)OK - docker has already been installed.$(tput sgr0)"
fi
#======================================================================================#

#============================CREATE APP WAR============================================#
echo "$(tput setaf 3)Preparing for making xmlanalyzer-0.1.war...$(tput sgr0)"
mvn clean
echo "$(tput setaf 3)Begin to make xmlanalyzer-0.1.war...$(tput sgr0)"
mvn install -Dmaven.test.skip=true
echo "$(tput setaf 2)OK -xmlanalyzer-0.1.war has been made.$(tput sgr0)"
#======================================================================================#

#============================BUILD APP IMAGE===========================================#
echo "$(tput setaf 3)Building xmlanalyzer image from Dockerfile...$(tput sgr0)"
docker build -t ${APP_IMAGE_NAME} .
echo "$(tput setaf 2)OK - image has been built.$(tput sgr0)"
#======================================================================================#

#============================RUN APP CONTAINER=========================================#
echo "$(tput setaf 3)Building xmlanalyzer container...$(tput sgr0)"
docker run -p 8080:8080 --name ${APP_CONTAINER_NAME} -d ${APP_IMAGE_NAME}
echo "$(tput setaf 2)OK - xmlanalyzer is up.$(tput sgr0)"
#======================================================================================#

