# file-upload

## Sample file-upload application which allows verified users to upload s sensitive data in the form of large files: 
1. User should be able to login to the platform using email and password.
2. Each user can view all the all the files that they own. 
3. User should be allowed to upload/delete a file with metaData.
4. Uploaded file should be compressed for storage.
5. A user can share his/her files publicly using a URL Shortner.

### High Level Architecture:  https://github.com/kmalhotra/file-upload/wiki/High-Level-Architecture

## Setup Instructions

1. Install Docker
2. Run "docker-compose up -d". Note: This might take sometime for the first time,
since all the dependencies are downloaded, subsiquent builds will be faster.
3. Follow Logs "docker-compose logs -f"

##API Details:

### Authenticate

#####Request:
```
curl -X POST \
  http://localhost:8080/authenticate \
  -H 'content-type: application/json' \
  -d '{
	"userName": "fileupload@demo.com",
	"password": "secretPassword"
}'
```


### Create a Document:

#####Request:
```
curl -X POST \
  http://localhost:8080/users/1/documents \
  -H 'authorization: Bearer <token>' \
  -H 'content-type: application/json' \
  -d '{
	"title": "first Document",
	"fileType": "PNG",
	"description": "this is the first file"
}'
```


### Fetch Document By Id

#####Request:

```
curl -X GET \
  http://localhost:8080/users/1/document/{documentId} \
  -H 'authorization: Bearer <token>

```

### Fetch All Documents

```
curl -X GET \
  http://localhost:8080/users/1/documents \
  -H 'authorization: Bearer <token>

```

### Delete a document

```
curl -X GET \
  http://localhost:8080/users/1/document/{documentId} \
  -H 'authorization: Bearer <token>
```
