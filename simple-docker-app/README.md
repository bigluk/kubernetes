# simple-docker-app
The application was created to be deployed on docker hub in order to use it on kubernetes project. This is a simple Rest app that expose the 4 basic CRUD operations.

To use it locally you have to install docker and launch the following comand to use postgress database.

    docker run -p 5432:5432 -d -e POSTGRES_PASSWORD=psw -e POSTGRES_USER=user -e POSTGRES_DB=customerdb postgres:bullseye
