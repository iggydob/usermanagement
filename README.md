
# User Management REST API

This project is a User Management Tool build in Java and Quarkus. It provides functionalitiy to manage users, including creating and deleting users, and updating user information. The project has a layered architecture with distinct layers for presentation, business logic, and data access.

## [API Reference](http://localhost:8080/q/swagger-ui)


#### Get all users if no query parameters added

```http
  GET /users
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstName`| `string` | **Optional**  |
| `lastName`| `string` | **Optional**  |
| `email`   | `string` | **Optional**   |

#### Get a user by email

```http
  GET /users/${email}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`   | `string` | **Required**        |

#### add(num1, num2)

Takes two numbers and returns the sum.

#### Create a New User

```http
  POST /api/items/${id}
```
**Required body**

    {
        "firstName": "string",
        "lastName": "string",
        "email": "string"
    }

#### Update existing user

```http
  PATCH users/update
```
**Required body**

    {
        "userId": 0,
        "firstName": "string",
        "lastName": "string",
        "email": "string",
        "address": "string",
        "phone": "string",
        "bio": "string"
    }

#### Delete a user

```http
  DELETE users/delete
```
**Required body**

    {
        "userId": 0,
        "firstName": "string",
        "lastName": "string",
        "email": "string",
        "address": "string",
        "phone": "string",
        "bio": "string"
    }

## Database

The project uses an PostgreSQL database for storing user information. The database schema is defined in the `schema.sql` file, and sample data can be found in the `sample.sql` file.

![img_1.png](img_1.png)

## Used Technologies

- [Java](https://www.java.com/en/)
- [Quarkus](https://quarkus.io/)
- [Gradle](https://gradle.org/)
- [Panache ORM](https://thorben-janssen.com/introduction-panache/)
- [Hibernate](https://hibernate.org/orm/)
- [PostgreSQL](https://www.postgresql.org/)
- [Lombok](https://projectlombok.org/)
- [Mockito](https://site.mockito.org/)
- [Swagger](https://swagger.io/)
- [MapStruct](https://mapstruct.org/)

