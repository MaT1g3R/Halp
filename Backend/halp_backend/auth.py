from base64 import b64decode

from django.contrib.auth import authenticate
from django.http import HttpRequest
from option import NONE, Option, maybe

from halp_backend.models import User


def authenticate_user(reqeust: HttpRequest) -> Option[User]:
    """
    Authenticate a user for a given HTTP request

    Args:
        reqeust: The HTTP reqeust

    Returns:
        Some(User) if authentication was successful
        NONE otherwise
    """
    auth_header = reqeust.META.get('HTTP_AUTHORIZATION')
    if not auth_header or not auth_header.startswith('Basic '):
        return NONE
    auth_string = b64decode(auth_header[6:]).decode()
    email, _, password = auth_string.partition(':')
    user = authenticate(email=email, password=password)
    return maybe(user)
