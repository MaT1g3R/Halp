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
| 404  | Not found (end point doesn't exist, etc)  |
| 500  | Internal Server Error (server screwed up) |

For error code `400`, `401`, and `404`, the server will reply back
with a more detailed error message in the response body as JSON like so:

```JSON
{"error": "Some error message"}
```

## Authentication

User authentication is done via HTTP basic auth.

To make an authenticated request, join the user name and password with a
colon (:), then base64 encode the resulting string. Prepend the resulting
string with `Basic ` (note the space) and set it as the `Authorization` field
in the request headers.

Example with user name `Aladdin` and password `open sesame`:

Encoding `Alaadin: open sesame` with base64 yields: `QWxhZGRpbjpvcGVuIHNlc2FtZQ==`

Then to make the authenticated request:

```bash
curl -i -H "Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==" -H "Accept: application/json" -H "Content-Type: application/json" /api/v1/something
```

## Routes

TODO

