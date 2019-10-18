# REST API for an user control application

#### Features:

- JWT Authentication;
- List Users;
- Get an User by ID;
- Delete an User;
- Register a new User;
- Update an existent User.

#### Resources and dependencies:

- JWT
- Spring Boot
- Spring Security
- Swagger
- DTO pattern
- MongoDB

## API
####   authentication-controller : 
- Generate a JWT Token:  `  POST /auth`

- JWT Token refresh: `  POST /auth/refresh`

####   user-controller : 
- List Users: `   GET /api/user`

- Delete an User: `   DELETE /api/user/delete/{id}`

- Register a new User: `   POST /api/user/save`

- Update an User data: `   PUT /api/users/update`

- Get User by ID: `   GET /api/user/{id}`

- Get User by Email: `   GET /api/user/email/{email}`
