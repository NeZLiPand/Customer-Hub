<h1>
  <p align="center">
    REST API Server
  </p>
</h1>

The server is with basic CRUD operations for the Customer entity though endpoints.

> [!NOTE]
>  The operation of the server is demonstrated in the release notes. 

##
<h3>
  <p align="center">
    Description
  </p>
</h3>

##
###
**`Stack of project:`**

<details>
<summary>:electron:	The set of</summary>
  
###
- Backend:
  - Java 21;
  - Spring Boot (3.3.0):
    - Hibernate;
    - Tomcat;
    - JUnit Jupiter and Mockito.
  - Maven (3.9.9);
  - Jacoco (0.8.12).
- Frontend:
  - None.
- Database:
  - PostgreSQL (It is recommended 16.3 or later)
- Authentication:
  - None.

</details>



### 
**`Description of endpoint processing:`**

<details>
<summary>:point_left:(:eyes:] The set of</summary>

###
Create customer
<details>
<summary>:triangular_flag_on_post: Configuration</summary>
  
###
Configuration->

</details>

###
Read all customers
<details>
<summary>:triangular_flag_on_post: Configuration</summary>

###
Configuration->

</details>

###
Read customer
<details>
<summary>:triangular_flag_on_post: Configuration</summary>

###
Configuration->

</details>

###
Update customer
<details>
<summary>:triangular_flag_on_post: Configuration</summary>

###
Configuration->

</details>

###
Delete customer
<details>
<summary>:triangular_flag_on_post: Configuration</summary>

###
Configuration->

</details>

</details>

##
<h3>
  <p align="center">
    How to run the Server
  </p>
</h3>

##

**Project launch instructions.**

<details>
<summary>:shipit: Tap it to open\close all spoilers :grey_exclamation:</summary>

##

**I. Ensure that the following components are installed** on your device:

<details>
<summary>:fire_extinguisher: Ensured that</summary>
  
###
- `Java` 21 (previous versions can't be supported, check it yourself);

- `Maven` (version 3.9.9 or higher);

- `PostgreSQL` and `psql JDBC driver` (or another database and his JDBC driver, but another DBs can't be supported, check it yourself);

- `Git` (if you plan to clone the project from a repository).

</details>

##

**II. Clone the Project** or download its in ZIP file

<details>
<summary>:rainbow: As across Bifrost ㄟ( ▔-▔ )ㄏ</summary>
  
###
- `Use Git` to clone the project repository onto your device:

> ```
> git clone https://github.com/NeZLiPand/CustomerHub.git
> ```

---

- Or `use the button` **[<> Code]** to download on main page of the project, as on this screenshot:

> ![image](https://github.com/user-attachments/assets/48067ebe-a8a5-46ff-822e-472d5fd5d6af)
> ![image](https://github.com/user-attachments/assets/72ea9414-83fb-45b9-8b53-1577a28a69f3)

---

> - `Or use another way`, which you prefer and can 😁👌.

</details>

##

**III. Set it up**
 
<details>
<summary>:milky_way: As God our galaxy</summary>

###
You have to `change configuration` of `the server` in the file application.properties located at the path:
```
yourPathToTheProjectFolder/src/main/resources/application.properties
```
- The server is configured to hear port: `8888`;
- `You have to change the "Login" and "password"` to match one of the "users" in your database, for example, "postgres" and "postgres".

Or create and configure application.yml, then you have to delete application.properties file.

**Here is explain of others field** of application.properties file:

<details>
<summary>:herb: Other field</summary>

##

###
**About spring.jpa.`open-in-view` field:**

<details>
<summary>:leaves: Tap it</summary>
  
###
This parameter determines whether the JPA session will be open for viewing (Open Session in View) during an HTTP request. It controls access to lazy-loaded data after the transaction has ended. Possible values:

- true — the JPA session remains open after the transaction ends, allowing lazy loading of data within the HTTP request.

- false — the JPA session closes after the transaction ends, and lazy loading outside the transaction will throw a LazyInitializationException.

</details>

##

###
**About spring.jpa.`hibernate.ddl-auto` field:**

<details>
<summary>:leaves: Tap it</summary>
  
###
This parameter defines the strategy for automatically managing the Hibernate database schema (DDL — Data Definition Language). Possible values:

- none — no changes are applied to the database schema. Hibernate will not automatically create, update, or delete tables.

- update — Hibernate updates the schema while preserving existing data. It creates new tables and columns but does not delete or modify existing ones.

- create — Hibernate creates a new database schema at startup, deleting all existing tables.

- create-drop — Hibernate creates a new database schema at startup and deletes it after the application stops.

- validate — checks whether the existing database schema matches the entities in your code, but does not make any changes.

- create-only — creates the schema based on entities, but doesn’t drop it after the application stops (rarely used).
</details>

##

###
**About spring.jpa.`show-sql` field:**

<details>
<summary>:leaves: Tap it</summary>
  
###
This parameter controls whether SQL queries are displayed in the console. Possible values:

- true — displays the SQL queries generated by Hibernate in the logs.

- false — SQL queries will not be displayed in the logs.

</details>

##

###
**About spring.jpa.`properties.hibernate.format_sql` field:**

<details>
<summary>:leaves: Tap it</summary>
  
###
This parameter controls the formatting of SQL queries in the output. Possible values:

- true — SQL queries will be formatted, meaning they will be easy to read (split across multiple lines with indentation).

- false — SQL queries will be displayed as a single continuous line (unformatted).

</details>

</details>

</details>

##

**IV. Build the Project**

<details>
<summary>:tornado: Build it</summary>
  
###
- Build the project using Maven by your IDE. 

- Or navigate to the root directory of the project and run the following command in your cmd:
> ```
> mvn clean install
> ```

[Tap here if it doesn't work, but Maven has already installed](https://mkyong.com/maven/how-to-install-maven-in-windows/)

</details>

##

**V. Run the Project**

<details>
<summary>:comet: Run it</summary>
  
###
After the successful build, run the project with the following command:

```
mvn spring-boot:run
```

[Tap here if it doesn't work, but Maven has already installed and you skip same link in previous step](https://mkyong.com/maven/how-to-install-maven-in-windows/)

</details>

##

**VI. Check Availability**

<details>
<summary>:sun_behind_large_cloud: Brother whaat's that</summary>
  
###
If the project starts successfully, it will be available in your browser at the following link:
```
http://localhost:8888
```

But if you want to check how it works, I recommend installing [Postman](https://www.postman.com/downloads/)

</details>

##

**VII. Notes**

<details>
<summary>:deciduous_tree: It's helpful</summary>

###
- Ensure that all dependencies in the pom.xml file are correctly configured.
- When deciding whether to build your project as a JAR file, or as a WAR file it's important to consider the architecture and environment in which your application will run.

<details>
<summary>:herb: The benefits of JAR and WAR files (short) :grey_exclamation:</summary>

##
###
<details>
<summary>:leaves: When to use a JAR file :grey_exclamation:</summary>

###
- Spring Boot applications: If your project is built with Spring Boot, creating a JAR file is the standard approach. Spring Boot produces self-contained JAR files that bundle all necessary dependencies and can run independently on any machine with a Java Runtime Environment (JRE) or Java Development Kit (JDK).
- Easy distribution and deployment: A JAR file is convenient for distribution and deployment on different machines or servers. All that is required to run it is a working installation of Java.

</details>

##
###
<details>
<summary>:leaves: When to use a WAR file :grey_exclamation:</summary>

###
- For web applications using WAR files: If your project is more traditional and intended to be deployed on web servers (e.g., Apache Tomcat or JBoss), creating a WAR file might be more appropriate. WAR files are better suited for web applications that need to be deployed in servlet containers.
- Containers (Docker): For complex infrastructures or microservice-based architectures, containerizing your application with Docker might be a better solution. This ensures a consistent environment across all devices, regardless of the operating system or configurations.
- Executable (native) packages: If you need the application to run directly as a binary file, there are tools available to convert Java applications into native executables for different OS platforms (e.g., GraalVM).

</details>

</details>

</details>
