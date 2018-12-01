import binascii
from base64 import b64decode
from functools import wraps
from typing import Callable

from django.contrib.auth import authenticate
from django.http import HttpRequest, HttpResponse, JsonResponse
from option import NONE, Option, maybe

from halp_backend.models import User
from halp_backend.util import get_http_header


def authenticate_user(reqeust: HttpRequest) -> Option[User]:
    """
    Authenticate a user for a given HTTP request

    Args:
        reqeust: The HTTP reqeust

    Returns:
        Some(User) if authentication was successful
        NONE otherwise
    """
    auth_header = get_http_header(reqeust, 'AUTHORIZATION').unwrap_or('')
    if not auth_header:
        return NONE
    if not auth_header.startswith('Basic '):
        return NONE
    auth_string = auth_header[6:]
    try:
        email_pass = b64decode(auth_string, validate=True).decode()
    except (binascii.Error, UnicodeDecodeError):
        return NONE

    email, _, password = email_pass.partition(':')
    user = authenticate(username=email, password=password)
    return maybe(user)


def require_auth(view: Callable[[User, HttpRequest], HttpResponse]):
    """
    Decorator to authenticate a view

    Args:
        view: The original view function
    """

    @wraps(view)
    def wrapper(request: HttpRequest):
        user = authenticate_user(request)
        if user:
            return view(user.value, request)
        return JsonResponse({'error': 'User is not authenticated'}, status=401)

    return wrapper
