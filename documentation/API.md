# API specification
<!-- TODO add errors responses-->

## /auth
### /auth/register
Registers a user.
#### Request
```json 
{
  "username": String,
  "password": String,
  "email": String,
  "acceptsEmails": boolean
}
```

#### Responses
##### 200
```json
{
  "status": "success",
  "data": null,
  "message": "User created"
}
```

### /auth/login
Logs a user in. Returns a token that you can then use to access the other endpoints.
#### Request 
```json 
{
  "email": String,
  "password": String,
  "device": String
}
```

#### Responses
##### 200
```json 
{
  "status": "success",
  "data": {
    "token": "YOUR_TOKEN"
  },
  "message": "Token generated"
}
```
---
> All the endpoints after this point needs authentication.  
> Authentication works by putting `X-Email` and `X-Token` header in your requests. The `X-Email` is the account's email address, and the `X-Token` the token you get by using the `/auth/login` endpoint.

## /user
### /user/revoke
Revokes a token.
#### Request
```json
{
  "device": String
}
```
#### Responses
##### 200
```json 
{
  "status": "success",
  "data": null,
  "message": "Token revoked"
}
```

### /user/edit
Edits the user's informations and preferences.
#### Request
> Every field is optional, but you must fill at least one to get a successful request.
```json
{
  "email": String,
  "password": String,
  "device": String,
  "acceptsEmails": boolean
}
```
#### Responses 
##### 200
```json
{
  "status": "success",
  "data": null,
  "message": "User modified"
}
```