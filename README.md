# Employee Management System (XML + Servlet + JSP)

A college-level CRUD web application using Java Servlets, JSP, `web.xml`, JDBC, MySQL, Maven, and Apache Tomcat 10.1.

## Requirements
- JDK 11 or newer
- Apache Tomcat 10.1
- MySQL 8.x
- Maven 3.8+
- Eclipse IDE for Enterprise Java and Web Developers (or IntelliJ IDEA)

## Setup
1. Run `database.sql` in MySQL Workbench or the MySQL command line.
2. Open `src/main/java/com/college/ems/util/DBConnection.java` and set your own MySQL username/password. Do not use your real database password in shared/public code.
3. From the project folder run: `mvn clean package`
4. Copy `target/EmployeeManagementSystem.war` into Tomcat's `webapps` directory.
5. Start Tomcat and open `http://localhost:8080/EmployeeManagementSystem/`.

## Features
- List employees
- Add employee
- Edit employee
- Delete employee
- Server-side validation and prepared SQL statements

## Notes
This is a learning/demo project, not production-ready. Add authentication/authorization, CSRF protection, connection pooling, stronger validation, and secure secret management before real deployment. It uses Jakarta Servlet 6.0, so deploy on Tomcat 10.1 rather than Tomcat 9.
