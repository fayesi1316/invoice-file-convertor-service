# Invoice File Convertor Service

# Requirement
* All IHEA lines need to be removed from the source report
* Lines with no payment date and lines with zero payment amount need to be removed
* Multiple lines for the same invoice need to be collapsed into single one
* lines need to be sorted by payment date, invoice number and glAccountId in ascending order

# How to start the Invoice File Convertor Service application locally
---
* Run `mvn clean install` to build your application 
* Start application with `java -jar target/invoice-file-convertor-service-1.0-SNAPSHOT.jar server config.yml`
* To check that your application is running enter url `http://localhost:8081/healthcheck?pretty=true`

# Deployment to Google Cloud
* Cloud Build to compile and create docker image 
* Cloud Build then pushes the image to Container Registry
* Image is deployed to Cloud Run as a running container
* URL is: https://invoice-file-convertor-service-vrvjpivpsq-uc.a.run.app

# Testing notes:
* Create a post request from postman
  * POST - http://localhost:8080/invoice-file/upload
  * Headers: 
    * Content-Type: multipart/form-data
    * Accept: application/octet-stream
  * For request body, select 'form-data'. Set key to be 'source', select 'file' from dropdown, and select the source report from 'select file' button.
* Sample source report has been attached to test/resource/source.csv


