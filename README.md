
# <h1 align="center"> Recipe Management System API with AWS </h1>
___ 
<p align="center">
    <a href="Java url">
        <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
    </a>
    <a href="Maven url" >
        <img alt "Maven" src="https://img.shields.io/badge/maven-4.0-brightgreen.svg" />
    </a>
    <a href="Spring Boot url" >
        <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.1.4-brightgreen.svg" />
    </a>
    <a href="License url" >
        <img alt="BSD Clause 3" src="https://img.shields.io/badge/License-BSD_3--Clause-blue.svg"/>
    </a>
</p> <!-- Closing the first alignment paragraph -->

<p align="center"> <!-- Opening a new alignment paragraph for the last three URLs -->
    <a href="mySQL url" >
        <img alt="mySQL" src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" />
    </a>
    <a href="Git Action url" >
        <img alt="Git Action" src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white" />
    </a>
    <a href="AWS url" >
        <img alt="AWS" src="https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white"/>
    </a>
</p> <!-- Closing the second alignment paragraph -->

---

<p align="left">

## Overview

The Recipe Management System API with AWS is a Spring Boot-based system that empowers users to efficiently manage recipes. It provides a set of RESTful API endpoints for creating, updating, and categorizing recipes, and allows users to leave comments on their favorite recipes. This README provides an overview of the system, the technologies used, and details on the User and Comment functionalities.

## Technologies Used

- **Framework:** Spring Boot
- **Language:** Java
- **Database:** MySQL
- **Build Tool:** Maven
- **Database:** MySQL (Hosted on AWS EC2)
- **Continuous Integration:** GitHub Actions
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)

## AWS EC2 for Database

Our application utilizes an AWS EC2 instance to host the MySQL database. This instance is responsible for securely storing and managing recipe data, user information, comments, and other related data. It allows for scalable and reliable database access while ensuring data security and performance.

## Continuous Integration with GitHub Actions

We have implemented a robust continuous integration (CI) workflow using GitHub Actions. With every code push to our repository, GitHub Actions automatically builds and tests our application to ensure code quality and reliability. This helps us maintain a stable and error-free application.

## Dependencies Used

The project leverages various dependencies to achieve its functionality:

- **Spring Boot Starter Data JPA**: Simplifies database access using Spring Data repositories.

- **Spring Boot Starter Web**: Provides support for building web applications, including RESTful APIs.

- **MySQL Connector/J (Runtime Dependency)**: The MySQL JDBC driver for connecting to MySQL databases.

- **Project Lombok (Optional)**: A library for reducing boilerplate code, such as getters and setters.

- **Spring Boot Starter Test (For Testing)**: Provides support for testing Spring Boot applications.

- **Springdoc OpenAPI (Swagger UI)**: Adds Swagger UI for documenting and testing API endpoints.

- **Thymeleaf (Optional)**: A templating engine used for generating dynamic HTML reports.

- **Other Dependencies:** Various other dependencies are included in the `pom.xml` file for specific functionalities, such as Jackson for JSON serialization, Spring Web for web-related features, and more.

For a comprehensive list of all dependencies and their versions, please refer to the project's `pom.xml` file.

Certainly, here's an overview of the data flow in the Recipe Management System API with AWS:

## Data Flow

The Recipe Management System API follows a structured data flow for creating, updating, and categorizing recipes, as well as allowing users to add comments. This data flow illustrates how users can interact with the application and manage data effectively:

1. **Recipe Management**:

    - A user creates a new recipe by sending a `POST` request to the `/api/recipes` endpoint, providing details such as the recipe's title, description, and category.

    - The system validates the request, creates the recipe entity, and associates it with the user who created it and the selected category.

    - The new recipe is saved in the database.

    - The system responds with a confirmation message, indicating the successful creation of the recipe.

    - Users can also update or delete their recipes by sending `PUT` or `DELETE` requests to the `/api/recipes/{recipeId}` endpoint.

    - The system checks if the provided recipe exists and is associated with the user.

    - If the recipe is valid, it is updated or deleted from the database, and the system sends a response confirming the update or deletion.

2. **Category Management**:

    - Users can create and manage recipe categories through API endpoints, such as creating a new category with a `POST` request to `/api/categories`.

    - The system manages recipe categories and their associations with recipes.

3. **Comment Management**:

    - Users can leave comments on their favorite recipes by sending a `POST` request to the `/api/comments` endpoint, specifying the comment text and the recipe to which it belongs.

    - The system validates the request, creates the comment entity, and associates it with the user who made the comment and the selected recipe.

    - The new comment is saved in the database, and the system responds with a confirmation message.

    - Users can also update or delete their comments by sending `PUT` or `DELETE` requests to the `/api/comments/{commentId}` endpoint.

    - The system checks if the provided comment exists and is associated with the user.

    - If the comment is valid, it is updated or deleted from the database, and the system sends a response confirming the update or deletion.

4. **User Authentication and Authorization**:

    - User authentication and authorization are handled securely to ensure that only authorized users can create, update, delete, and comment on recipes.

    - The system manages user accounts and ensures that user data is protected.

5. **Data Persistence**:

    - The application relies on a MySQL database for data storage. Entities such as recipes, users, comments, categories, and others are mapped to their corresponding database tables.

6. **RESTful API Endpoints**:

    - RESTful API endpoints provide a clear interface for users to interact with the application. These endpoints are documented using Swagger UI, providing a user-friendly way to explore and use the API.

7. **Database Design**:

    - The database design includes tables for recipes, users, comments, categories, and other entities, ensuring a structured and efficient data storage approach.

This data flow illustrates how users can interact with the Recipe Management System API to manage recipes, categories, and comments effectively. It ensures a seamless experience for users and maintains data integrity and security throughout the process.

## User and Comment Functionality

In addition to recipe management, this system allows users to interact with recipes by adding comments and provides user management functionality. Here are the details of User and Comment functionalities:

### User Management

#### User Table

The User table stores information about users of the Recipe Management System.

**User Table Fields:**

| Column Name  | Data Type    | Description               |
| ------------ | ------------ | ------------------------- |
| userId       | INT          | Unique identifier for users |
| username     | VARCHAR(255) | User's username           |
| password     | VARCHAR(255) | Encrypted user password   |

**User Data Structures:**

#### User Entity

The User entity represents a user in the system.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;

    // Constructors, getters, setters, and other methods
}
```

#### UserRepository

The UserRepository is a repository interface for managing User entities.

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

### Comment Management

#### Comment Table

The Comment table stores comments associated with recipes in the Recipe Management System.

**Comment Table Fields:**

| Column Name  | Data Type    | Description               |
| ------------ | ------------ | ------------------------- |
| commentId    | INT          | Unique identifier for comments |
| text         | TEXT         | The text of the comment    |
| user_userId  | INT          | Foreign key referencing the user who made the comment |
| recipe_recipeId | INT      | Foreign key referencing the recipe to which the comment is related |

**Comment Data Structures:**

#### Comment Entity

The Comment entity represents a comment in the system.

```java
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_recipeId")
    private Recipe recipe;

    // Constructors, getters, setters, and other methods
}
```

#### CommentRepository

The CommentRepository is a repository interface for managing Comment entities.

```java
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipe(Recipe recipe);
}
```

With these additions, you can now manage users and comments in your Recipe Management System. The User entity represents users, and the Comment entity represents comments, and you can use the UserRepository and CommentRepository to perform database operations on these entities.

## Project Structure

The project follows a structured and organized architecture:

- **Main Application Class:** The entry point for the application is defined in the main class.

- **Entities:** The application includes entities such as `Recipe`, `User`, `Comment`, and

 others to model the data.

- **Repository Interfaces:** Spring Data JPA repository interfaces manage data access for recipes, users, and comments.

- **Service Classes:** Business logic is implemented in service classes for managing recipes, users, and comments.

- **Controller Classes:** These classes define and document RESTful API endpoints for creating, updating, categorizing, and commenting on recipes.

- **Data Flow:** The Recipe Management System follows a structured data flow for managing recipes and allowing user interactions, including adding comments and user management.

> This data flow illustrates how users can interact with the application, manage recipes, add comments, and manage user accounts.

## RESTful API Endpoints

The application provides RESTful API endpoints for various functionalities:

### Recipe Management

- **Create Recipe:** `POST /api/recipes`
- **Update Recipe:** `PUT /api/recipes/{recipeId}`
- **Delete Recipe:** `DELETE /api/recipes/{recipeId}`

### Category Management

- **Create Category:** `POST /api/categories`
- **Update Category:** `PUT /api/categories/{categoryId}`
- **Delete Category:** `DELETE /api/categories/{categoryId}`

### Comment Management

- **Create Comment:** `POST /api/comments`
- **Update Comment:** `PUT /api/comments/{commentId}`
- **Delete Comment:** `DELETE /api/comments/{commentId}`
- **Get Comments for Recipe:** `GET /api/comments/recipe/{recipeId}`

The API endpoints are documented, adhering to REST principles, and provide the core features of the Recipe Management System.

## Database Design

The application uses a relational database to store data, including recipes, user information, comments, categories, and more. Key attributes and tables include:

### Recipe Table

| Column Name | Data Type    | Description                 |
| ----------- | ------------ | --------------------------- |
| recipeId    | INT          | Unique identifier for recipes |
| title       | VARCHAR(255) | Title of the recipe         |
| description | TEXT         | Description of the recipe   |
| user_userId | INT          | Foreign key referencing the user who created the recipe |
| category_categoryId | INT  | Foreign key referencing the category of the recipe |

### Category Table

| Column Name | Data Type    | Description                 |
| ----------- | ------------ | --------------------------- |
| categoryId  | INT          | Unique identifier for categories |
| name        | VARCHAR(255) | Category name               |
| recipes     | One-to-Many  | Recipes associated with the category |

> The Recipe and Category tables are part of the core functionality for recipe management and categorization.

For detailed information on the User and Comment tables, please refer to the "User Table" and "Comment Table" sections in the README.

## Data Structures Used

1. **Entities**:
    - **Recipe**: Represents a recipe with attributes like `recipeId`, `title`, `description`, a reference to the associated `user`, and a reference to the `category`.

    - **User**: Represents a user with attributes like `userId`, `username`, `password`, and a collection of `recipes`.

    - **Comment**: Represents a comment with attributes like `commentId`, `text`, a reference to the `user` who made the comment, and a reference to the `recipe` to which the comment is related.

    - **Category**: Represents a category with attributes like `categoryId`, `name`, and a collection of `recipes`.

2. **Repositories**:
    - JPA repositories for data access, including repositories for recipes, users, comments, categories, and other related entities.

3. **ArrayLists**:
    - ArrayLists are used for efficiently managing lists of entities. For example, a user's recipes are managed using a collection of `Recipe` entities within the `User` entity.

> ArrayLists provide flexibility for storing and managing multiple entities efficiently within the application. The actual storage and retrieval of data are typically handled by the JPA repositories and the underlying database systems.

## Database Configuration

The database connection properties, including the URL, username, and password, are specified in the `application.properties` file. Ensure that these properties are correctly configured to connect to your MySQL database.

Example configuration for MySQL:

```properties
spring.datasource.url=jdbc:mysql://<Public IP>:3306/RecipeManager
spring.datasource.username=root
spring.datasource.password=yourPassword
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

Please replace `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` with your database connection details.

## Project Summary

The Recipe Management System API with AWS is a comprehensive system for managing recipes, comments, user accounts, and categories. Users can create, update, categorize, and comment on recipes with ease. This system also allows for user registration and management, ensuring a personalized experience for every user.

The application follows best practices for clean code, separation of concerns, secure user data handling, and RESTful API design. Additionally, it leverages AWS services for robust and scalable data storage.

### License

This project is licensed under the [BSD 3-Clause License](LICENSE).

### Acknowledgments

We would like to thank the Spring Boot and Java communities for providing excellent tools, frameworks, and resources that have contributed to the development of this project.

### Contact

For questions or feedback, please contact [Amit Ashok Swain](mailto:business.amitswain@gmail.com).

</p>
