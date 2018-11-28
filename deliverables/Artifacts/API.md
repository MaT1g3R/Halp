# Halp API specification

## API end point

`/api/v1/`

## Error Codes

The API will return error codes listed below:

| Code | Reason                                    |
| ---- | ----------------------------------------- |
| 200  | Success                                   |
| 400  | Bad request (client screwed up)           |
| 401  | Client is unauthorized                    |
| 403  | Forbidden (Client does not have the right permissions to access this resource) |
| 404  | Not found (end point doesn't exist, etc)  |
| 500  | Internal Server Error (server screwed up) |

For error code `400`, `401`, and `403`, the server will reply back
with a more detailed error message in the response body as JSON like so:

```json
{"error": "Some error message"}
```

## Authentication

User authentication is done via HTTP basic auth.

To make an authenticated request, join the email and password with a
colon (:), then base64 encode the resulting string. Prepend the resulting
string with `Basic ` (note the space) and set it as the `Authorization` field
in the request headers.

Example with email `halp@halp.org` and password `halp me`:

Encoding `hap@halp.org:halp me` with base64 yields: `aGFscEBoYWxwLm9yZzpoYWxwIG1l`

Then to make the authenticated request:

```bash
curl -i -H "Authorization: Basic aGFscEBoYWxwLm9yZzpoYWxwIG1l" -H "Accept: application/json" -H "Content-Type: application/json" /api/v1/something
```
## Objects
### User
| Name | Type | Description |
| ---  | ---  | ----------  |
| user_id | Int | The id of the user |
| first_name | String | The first name of the user |
| last_name | String | The last name of the user |
| bio  | String | The bio of the user |

### Response
| Name | Type | Description |
| ---  | ---  | ----------- |
| response_id | Int | The id of the response |
| worker_id   | Int | The user id of the worker that created this response |
| comment | String | The response comment |

### Request
| Name | Type | Description |
| ---  | ---  | ----------- |
| request_id | Int | The id of the request |
| customer_id | Int | The customer that made this request |
| start_time | Optional\[Int\] | The start date and time for this request, in Unix epoch. If the start time is null, the request can start at any time. |
| duration | Int | The duration of this request, in seconds |
| latitude | Double | The latitude of this request |
| longitude | Double | The longitude of this request |
| finished | Boolean | Is the request finished |
| assigned_to | Optional\[Int\] | The user id for the worker that will work/worked on this job (can be null if no one is available) |
| description | String | The job description |
| responses | List\[[Response](#response)\] | The list of responses for this request |

## Routes

### GET /api/v1/profile
Get the profile of a user by ID or by authentication.

If no query string is provided, the request must be authenticated.

#### Authentication
Yes

#### Query String
| Name | Type | Required | Description |
| ---  | ---  | -------  | ----------  |
| user_id | Int | No | The ID of the user to lookup |

#### Response
A [user object](#user) representing the authenticated user.

#### Examples
Request:
```
# Assuming the user is authenticated as Dennis Ritchie
GET /api/v1/profile
```

Response:
```json
{
    "user_id": 1,
    "firt_name": "Dennis",
    "last_name": "Ritchie",
    "bio": "I created the C progamming language"
}
```

Request:
```
GET /api/v1/profile?user_id=2
```

Response:
```json
{
    "user_id": 2,
    "first_name": "Ken",
    "last_name": "Thompson",
    "bio": ""
}
```
---------------------------
### POST /api/v1/create_user
Create a new user.

#### Authentication
No

#### Request Body
| Name | Type | Required | Description |
| ---  | ---  | --- | --- |
| first_name | String | Yes | The first name of the user |
| last_name  | String | Yes | The last name of the user |
| email      | String | Yes | The email address of the user |
| password   | String | Yes | The password of the user |

#### Response
A [user object](#user) representing the new user.

#### Example
Request:
```
POST /api/v1/create_user
```

Request body:
```json
{
    "first_name": "Ken",
    "last_name": "Thompson",
    "email": "ken.thompson@belllabs.com",
    "password": "hunter2"
}
```

Response:
```json
{
    "user_id": 2,
    "first_name": "Ken",
    "last_name": "Thompson",
    "bio": ""
}
```

-----------------

### POST /api/v1/update_bio
Update the bio for the authenticated user.

#### Authentication
Yes

#### Request body
| Name | Type | Required | Description |
| ---- | ---- | ------- | ----------- |
| bio | String | Yes | The new bio of the user |

#### Response
| Name | Type | Description |
| --- | --- | ------------ |
| bio | String | The new bio of the user |

#### Example
Request:
```
POST /api/v1/update_bio
```

Request Body:
```json
{"bio": "I created Unix"}
```

Response:
```json
{"bio": "I created Unix"}
```

----------------

### GET /api/v1/request
Query a list of requests.

If no query string is provided, it will return the list
of requests created by the authenticated user by default.

#### Authentication
Yes

#### Query String
Query parameters are evaulated on an AND bases

When querying by radius, both `lat` and `long` parameters must be supplied

| Name | Type | Required | Description |
| ---  | ---- | -------- | ----------- |
| finished | Boolean | No | Whether the request is finished or not |
| assigned | Boolean | No | Wether the request has an assigned worker |
| starts_after | Int | No | Only return the requests with start time after this parameter |
| radius | Int | No | Only return requests within a certain radius (in km) |
| lat | Double | No | The center latitude for the radius |
| long | Double | No | The center longitude for the radius |

#### Response
| Name | Type | Description |
| --- | --- | ---- |
| requests | List\[[Request](#request)\] | List of requests created by the user |

#### Examples
Request:
```
GET /api/v1/request
```

Response:
```json
{
    "requests": [
        {
            "request_id": 1,
            "customer_id": 1,
            "start_time": 1543274213,
            "duration": 10800,
            "latitude": 43.6748,
            "longitude": -79.4092,
            "finished": true,
            "assigned_to": 2,
            "description": "Please do my CSC301 homework for me",
            "responses": [
                {"response_id": 1, "worker_id": 2, "comment": "Ok"},
                {"response_id": 2, "worker_id": 3, "comment": "I'm good with Java"}
            ]
        },
        {
            "request_id": 2,
            "customer_id": 1,
            "start_time": 1543274789,
            "duration": 7000,
            "latitude": 41.837,
            "longitude": -71.719,
            "finished": false,
            "assigned_to": null,
            "description": "Help me mow my lawn",
            "responses": [
                {"response_id": 3, "worker_id": 7, "comment": "I like grass"},
                {"response_id": 4, "worker_id": 11, "comment": "I have an advanced lawn mower"}
            ]
        }
    ]
}
```

Request:
```
GET /api/v1/request?finished=false&assigned=false&radius=10&lat=41.8353&long=-71.7240
```

Response:
```json
{
    "requests": [
        {
            "request_id": 2,
            "customer_id": 1,
            "start_time": 1543274789,
            "duration": 7000,
            "latitude": 41.837,
            "longitude": -71.719,
            "finished": false,
            "assigned_to": null,
            "description": "Help me mow my lawn",
            "responses": [
                {"response_id": 3, "worker_id": 7, "comment": "I like grass"},
                {"response_id": 4, "worker_id": 11, "comment": "I have an advanced lawn mower"}
            ]
        }
    ]
}
```

-----------------

### POST /api/v1/request

Create a new request as the authenticated user.

#### Authentication
Yes

#### Reqeust Body

| Name | Type | Required | Description |
| ---- | ---- | --------- | ----------- |
| start_time | Int | No | The start date and time for the request in Unix epoch. If the start time is null, the system will look for a worker in real time |
| duration | Int | Yes | The duration of the reqeust, in seconds |
| latitude | Double | The latitude of this request |
| longitude | Double | The longitude of this request |
| description | String | Yes | The job description |

#### Response

A created [request](#request) object

#### Example

Request:

```
POST /api/v1/request
```

Request Body:
```json
{
    "start_time": 1543278946,
    "duration": 3600,
    "latitude": 52.48, 
    "longitude": -66.71,
    "description": "Help me move a fridge"
}
```

Response:
```json
{
    "request_id": 5,
    "customer_id": 22,
    "start_time": 1543278946,
    "duration": 3600,
    "latitude": 52.48,
    "longitude": -66.71,
    "finished": false,
    "assigned_to": null,
    "description": "Help me move a fridge",
    "responses": []
}
```

-----------------------

### DELETE /api/v1/request

Delete a request.

#### Authentication
Yes

#### Query String
| Name | Type | Required | Description |
| --- | ---- | --------- | ----------- |
| reqeust_id | Int | Yes | The ID of the request to delete |

#### Response

There's no response body for this method, the client is responsible to check
the error code. (200 for successful deletion)

#### Example

```
DELETE /api/v1/reqeust?request_id=13
```

-------- 

### GET /api/v1/find_worker

Find a worker for a reqeust in real time.

#### Authentication
Yes

#### Query String
| Name | Type | Required | Description |
| --- | ---- | --------- | ----------- |
| reqeust_id | Int | Yes | The ID of the request that's trying to find a worker |

#### Response

| Name | Type | Description |
| ---- | ---- | ----------- |
| worker | Optional\[[User](#user)\] | The matched worker, if any |

#### Example

Request:
```
GET /api/v1/find_worker?request_id=7
```

Response:
```json
{"worker": null}
```

Request:
```
GET /api/v1/find_worker?request_id=7
```

Response:
```json
{
    "worker": {
        "user_id": 55,
        "first_name": "Alan",
        "last_name": "Turing",
        "bio": "I defined the turing machine"
    }
}
```

-------------------------

### GET /api/v1/find_job

Find a job to work on in real time

#### Authentication
Yes

#### Query String

Query parameters are evaulated on an AND bases

When querying by radius, both `lat` and `long` parameters must be supplied

| Name | Type | Required | Description |
| ---  | ---- | -------- | ----------- |
| duration | Int | No | The max duration for the job, in seconds |
| radius | Int | No | Only return requests within a certain radius (in km) |
| lat | Double | No | The center latitude for the radius |
| long | Double | No | The center longitude for the radius |

#### Response

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | Optional\[[Request](#request)\] | The matched request, if any |

#### Example

Request:
```
GET /api/v1/find_job?radius=10&lat=45.44997&long=-75.68366&duration=5000
```

Response:
```json
{"request": null}
```

Request:
```
GET /api/v1/find_job?radius=10&lat=45.44997&long=-75.68366
```

Response:
```json
{
    "request": {
        "request_id": 9,
        "customer_id": 66,
        "start_time": null,
        "duration": 3600,
        "latitude": 45.44967,
        "longitude": -75.68651,
        "finished": false,
        "assigned_to": 88,
        "description": "Walk my dog",
        "responses": []
    }
}
```
