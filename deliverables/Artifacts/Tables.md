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
| reqeust_id | Int | The id of the request |
| customer_id | Int | The customer that made this request |
| start_time | Optional\[Int\] | The start date and time for this request, in Unix epoch. If the start time is null, the request can start at any time. |
| duration | Int | The duration of this request, in seconds |
| location | List\[Double\] | The Latitude and Longitude pair of the request location (the list must have 2 elements) |
| finished | Boolean | Is the request finished |
| assigned_to | Optional\[Int\] | The user id for the worker that will work/worked on this job (can be null if no one is available) |
| description | String | The job description |
| responses | List\[[Response](#response)\] | The list of responses for this request |

### WorkerRating
| Name | Type | Description |
| ---  | ---  | ----------  |
| user_id | Int | The id of a worker |
| Rating | Int | The rating of the worker |

### CustomerRating
| Name | Type | Description |
| ---  | ---  | ----------  |
| user_id | Int | The id of a customer |
| Rating | Int | The rating of the customer

### Password
| Name | Type | Description |
| ---  | ---  | ----------  |
| customer_id | Int | The id of a customer |
| password | String | The password of the customer |
