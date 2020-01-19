1. pull docker image from remote repository
docker pull joannagolebiowska/xmlanalyzer-img:task
2. To run docker image call
docker run -p 8080:8080 --name xmlanalyzer -d joannagolebiowska/xmlanalyzer-img:task
