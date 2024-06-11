# API specification
<!-- TODO add errors responses-->

## /auth
### /auth/register
Registers a user.  
HTTP verb: `POST`
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
##### 400 Bad Request
There are two responses that uses Bad Request as an HTTP Status Code:
You should refer to HTTP Status Code and status in order to handle the error.
###### PNV (Invalid password)
A password must meet the following requirements: min. height of 8 characters, (at least) one upper case letter, one lower case letter, one number and one special character. 
```json
{
  "status": "PNV",
  "data": null,
  "message": "Password does not meet requirements"
}
```
###### EAU (Email already used)
This email is already associated with an account.
```json
{
  "status": "EAU",
  "data": null,
  "message": "Email already used"
}
```


### /auth/login
Logs a user in. Returns a token that you can then use to access the other endpoints.  
HTTP verb: `POST`
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
##### 401 Unauthorized
###### Invalid credentials
This means either the user does not exists, either the credentials are invalid.
```json
{
  "status": "CNV",
  "data": null,
  "message": "Invalid credentials"
}
```

### /auth/password_reset/request
Requests a password reset. It will send an email to the provided address if an account exists.   
HTTP verb: `POST`
#### Request
```json
{
  "email": String
}
```
#### Responses
##### 200
```json
{
  "status": "success",
  "data": null,
  "message": "If an account with this email exists, a mail has been sent."
}
```
### /auth/password_reset
Resets the password associated to the provided email. You need to request a password reset before.
HTTP verb: `POST`
#### Request
```json
{
  "email": String,
  "token": String,
  "newPassword": String
}
```
#### Responses
##### 200
```json
{
  "status": "success",
  "data": null,
  "message": "Password changed successfully."
}
```
##### 400 Bad request
###### PNV (Invalid password)
A password must meet the following requirements: min. height of 8 characters, (at least) one upper case letter, one lower case letter, one number and one special character.
```json
{
  "status": "PNV",
  "data": null,
  "message": "Password does not meet requirements"
}
```
###### EAU (Email already used)
This email is already associated with an account.
```json
{
  "status": "EAU",
  "data": null,
  "message": "Email already used"
}
```


---
> All the endpoints after this point needs authentication.  
> Authentication works by putting `X-Email` and `X-Token` header in your requests. The `X-Email` is the account's email address, and the `X-Token` the token you get by using the `/auth/login` endpoint.

## /user
### /user/revoke
Revokes a token.   
HTTP verb: `DELETE`
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
Edits the user's information and preferences.  
HTTP verb: `PATCH`
#### Request
> Every field is optional, but you must fill at least one to get a successful request.
```json
{
  "username": String,
  "email": String,
  "password": String,
  "device": String,
  "acceptsEmails": boolean,
  "avatar": int
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
##### 400 Bad request
###### PNV (Invalid password)
A password must meet the following requirements: min. height of 8 characters, (at least) one upper case letter, one lower case letter, one number and one special character.
```json
{
  "status": "PNV",
  "data": null,
  "message": "Password does not meet requirements"
}
```
###### EAU (Email already used)
This email is already associated with an account.
```json
{
  "status": "EAU",
  "data": null,
  "message": "Email already used"
}
```
### /user/infos
Gets the user information and preferences.  
HTTP verb: `GET`
#### Responses
##### 200
```json
{
  "status": "success",
  "data": {
    "username": "username",
    "email": "email",
    "acceptsEmails": false,
    "avatarId": 1
  },
  "message": null
}
```

### /user/delete
Deletes an user account.  
HTTP verb: `DELETE`
#### Responses
##### 200
```json 
{
  "status": "success",
  "data": null,
  "message": "User deleted"
}
```

## /medecine
### /medecine/`id`
Gets this medecine information if it does exists.  
HTTP verb: `GET`
#### Responses
##### 200
```json
{
  "status": "success",
  "data": {
    "name": "Medecine name",
    "cip": "CIP"
  },
  "message": null
}
```
##### 404 Not found
###### INF (Id not found)
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the medecine"
}
```
## /city
### /city 
Gets all cities in a paginated way.  
HTTP verb: `GET`
#### Request
##### Query parameters
| Parameter | Value                            |
|-----------|----------------------------------|
| page      | int (the page to display)        |
#### Responses
##### 200 
```json
{
    "status": "success",
    "data": {
        "items": [
            {
                "id": 1,
                "name": "Ville",
                "zipCode": "00000"
            }
        ],
        "totalPageNumber": 1,
        "currentPage": 0,
        "lastPage": true
    },
    "message": null
}
```
##### 400 Bad request
###### PIN (Page index negative)
```json
{
  "status": "PIN",
  "data": null,
  "message": "Page index negative"
}
```
### /city/*`id`*/pharmacies
Gets all pharmacies from a city in a paginated way.  
HTTP verb: `GET`
#### Request
##### URL
- `id`: a city id. Refer to `/city` section in order to get a city id.
##### Query parameters
| Parameter | Value                            |
|-----------|----------------------------------|
| page      | int (the page to display)        |
### Responses
#### 200
```json
{
  "status": "success",
  "data": {
    "items": [
      {
        "id": 1,
        "number": "2bis",
        "name": "Pharmacie de Ville 1",
        "street": "rue"
      }
    ],
    "totalPageNumber": 1,
    "currentPage": 0,
    "lastPage": true
  },
  "message": null
}
```
##### 400 Bad request
###### PIN (Page index negative)
```json
{
  "status": "PIN",
  "data": null,
  "message": "Page index negative"
}
```
##### 404 Not found
###### INF (Id not found)
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the city"
}
```

## /reports
### /reports
Gets all reports associated to the user, in a paginated way.  
HTTP verb: `GET`
#### Request
##### Query parameters
| Parameter | Value                            |
|-----------|----------------------------------|
| page      | int (the page to display)        |
#### Responses
##### 200
```json
{
  "status": "success",
  "data": {
    "items": [
      {
        "submissionDate": "2024-05-25T09:21:39.787+00:00",
        "id": 11,
        "pharmacyId": 1,
        "currentState": "submitted",
        "cip": "3400935955838"
      }
    ],
    "totalPageNumber": 1,
    "currentPage": 0,
    "lastPage": true
  },
  "message": null
}
```
##### 400 Bad request
###### PIN (Page index negative)
```json
{
  "status": "PIN",
  "data": null,
  "message": "Page index negative"
}
```
### /reports/`id`/history
Gets the history of state changes for a given report.  
HTTP verb: `GET`
#### Request
##### URL
- `id`: a report id. Refer to `/reports` section in order to get a report id.
##### Query parameters
| Parameter | Value                            |
|-----------|----------------------------------|
| page      | int (the page to display)        |
### Responses
#### 200
```json
{
	"status": "success",
	"data": {
		"items": [
			{
				"actionDate": "2024-05-25T09:21:39.787+00:00",
				"oldState": null,
				"newState": "submitted"
			}
		],
		"totalPageNumber": 1,
		"currentPage": 0,
		"lastPage": true
	},
	"message": null
}
```
##### 400 Bad request
###### PIN (Page index negative)
```json
{
  "status": "PIN",
  "data": null,
  "message": "Page index negative"
}
```
##### 404 Not found
###### INF (Id not found)
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the report"
}
```
### /reports/new 
Creates a new report.  
HTTP verb: `POST`
#### Request
```json
{
	"pharmacyId": int,
	"medecineCIP": String 
}
```
#### Responses
##### 200
```json
{
	"status": "success",
	"data": null,
	"message": "Report successfully created."
}
```
##### 404 Not found
###### INF (Id not found)
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the pharmacy"
}
```
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the medecine"
}
```

## /admin
### /admin/pharmacy/`id`/reports
Lists all reports from a pharmacy, in a paginated way.  
HTTP verb: `GET`
#### Request
##### URL
- `id`: a pharmacy id.
##### Query parameters
| Parameter | Value                            |
|-----------|----------------------------------|
| page      | int (the page to display)        |
#### Response
##### 200
```json
{
	"status": "success",
	"data": {
		"items": [
			{
				"submissionDate": "2024-06-01T10:28:19.005+00:00",
				"id": 25,
				"pharmacyId": 1,
				"currentState": "submitted",
				"cip": "3400935955838"
			},
			{
				"submissionDate": "2024-06-01T10:28:18.850+00:00",
				"id": 24,
				"pharmacyId": 1,
				"currentState": "submitted",
				"cip": "3400935955838"
			},
			{
				"submissionDate": "2024-06-01T10:28:18.685+00:00",
				"id": 23,
				"pharmacyId": 1,
				"currentState": "submitted",
				"cip": "3400935955838"
			},
			{
				"submissionDate": "2024-06-01T10:28:18.519+00:00",
				"id": 22,
				"pharmacyId": 1,
				"currentState": "submitted",
				"cip": "3400935955838"
			},
			{
				"submissionDate": "2024-06-01T10:28:18.364+00:00",
				"id": 21,
				"pharmacyId": 1,
				"currentState": "submitted",
				"cip": "3400935955838"
			}
		],
		"totalPageNumber": 3,
		"currentPage": 0,
		"lastPage": false
	},
	"message": null
}
```
##### 421 Unauthorized
###### NER (Not Enough Rights)
```json
{
  "status": "NER",
  "data": null,
  "message": "You do not have enough rights for this"
}
```
##### 400 Bad request
###### PIN (Page index negative)
```json
{
  "status": "PIN",
  "data": null,
  "message": "Page index negative"
}
```
##### 404 Not found
###### INF (Id not found)
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the pharmacy"
}
```
### /admin/report/`id`/change_state
Changes a report state.  
HTTP verb: `POST`
#### Request
##### URL
- `id`: a pharmacy id.
##### Json
```json
{
  "newState": ReportState
}
```
ReportState values: "submitted", "rejected", "resupplying", "resupplied"
#### Response
##### 200
```json
{
  "status": "success",
  "data": null,
  "message": "Report state added"
}
```
##### 421 Unauthorized
###### NER (Not Enough Rights)
```json
{
  "status": "NER",
  "data": null,
  "message": "You do not have enough rights for this"
}
```
##### 404 Not found
###### INF (Id not found)
```json
{
  "status": "INF",
  "data": null,
  "message": "Could not find the report"
}
