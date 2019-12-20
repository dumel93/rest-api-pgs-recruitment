TODO list API


Create a web application issuing the REST API using Java version 8 or this version and the Spring framework. 
"My Matters" - list of tasks you have to do - model users, tasks, archive of tasks completed.

Code contains: 
  A. Data recording:
    a. Adding
    b. Edit
    c. Removal
  B. Reading data:
    a. Listing of all data
    b. Displaying details of a single entity
    c. Filtering
    d. Sorting
    e. Pagination
  C. Validation of input data (for point A)
  D. New user registration and login
    a. ADMIN - sees all USER data
    b. USER - can operate only on its data
  E. API and code documentation
  F. Unit tests
  
  Documention of REST API is on this web site : http://localhost:8080/swagger-ui.html#/
  
 API Reference and Resource Types
 
 ![Alt text](/taskAPI.png)
![alt text](https://github.com/dumel93/rest-api-pgs-recruitment/blob/master/userAPi.png)
  


 
 Get Started
 
     To build TODO list API, I used list of standard libraries to get the server up and running. So, the first one is Spring Boot.
     All librarires are in pom.xml.
     I used Tomcat sever.
     So, Spring web application framework, and is used to build all the API endpoints. 
     Also, I used mySQl database to store TODO list data. Finally, 
     I used Swagger to document API.

    To be able to run this project, you need the following

    Fork/Clone: https://github.com/dumel93/rest-api-pgs-recruitment.git
    create database -pgs f.e. in MySQLWorkbench.
    Change this credentials for your own according with your DBMS.
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.datasource.url=jdbc:mysql://localhost:3306/pgs?jdbcCompliantTruncation=false
    spring.datasource.username=root
    spring.datasource.password=coderslab
  
    IMPORTNANT!
    
    ADMIN : 
      login: admin@wp.pl 
      password: 'admin123'
      
    USER : 
      login: xxxx@gmail.com ( you will find in your db)
      password: 'user123'
      
     all password are bcrypted in db 
  
    To be able to test TODO list API endpoints, you can use front-end tools such as Api Tester, Postman, etc.
    
    Authors
    Damian Krawczyk
