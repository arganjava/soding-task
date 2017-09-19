# Task

Basic CRUD & List Paging

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment demo https://argansodingtask.herokuapp.com/

### Installing

A step by step series of examples that tell you have to get a development env running on your local machine

```
1. Install Java 7 or 8 (Recomended)
2. Download and Install Maven, refer to google by keyword (How To Install Maven)
3. Install Database Postgresql 
```

## Running On Local
```
1. Create Database Name ## tasksoding
2. Open application-dev.prperties change spring.datasource.username: {your username} & spring.datasource.password: {your password}
3. Open application.properties should spring.profiles.active=dev
2. open cmd and select project directory and run, example : D:\Argan\task-soding>mvn spring-boot:run
3. Running on Debug Mode, example : D:\Argan\task-soding>mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000
```

## Deployment On Heroku

```
1. Create Free Account to heroku https://www.heroku.com
2. Follow Tutorial https://devcenter.heroku.com/articles/getting-started-with-java#introduction
3. Open application-heroku.prperties change spring.datasource.username: {your username} & spring.datasource.password: {your password}
4. Open application.properties should spring.profiles.active=heroku
```