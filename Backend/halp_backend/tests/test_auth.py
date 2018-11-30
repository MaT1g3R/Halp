from base64 import b64encode

import pytest

from halp_backend.auth import authenticate_user
from halp_backend.tests.conftest import encode_auth

pytestmark = pytest.mark.django_db


def test_success(request_factory, create_full_user):
    user, password = create_full_user
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = encode_auth(user.email,
                                                     password)
    assert user == authenticate_user(request).unwrap()


def test_bad_header(request_factory, create_full_user):
    user, password = create_full_user
    request = request_factory.get('/')
    request.META['AUTHORIZATION'] = encode_auth(user.email, password)
    assert authenticate_user(request).is_none


def test_bad_password(request_factory, create_full_user):
    user, password = create_full_user
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = encode_auth(user.email, password + 'a')
    assert authenticate_user(request).is_none


def test_bad_email(request_factory, create_full_user):
    user, password = create_full_user
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = encode_auth(user.email + 'c', password)
    assert authenticate_user(request).is_none


def test_bad_format(request_factory, create_full_user):
    user, password = create_full_user
    email = user.email
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basi {email}:{password}'.encode()).decode()
    assert authenticate_user(request).is_none
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic {email}{password}'.encode()).decode()
    assert authenticate_user(request).is_none
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic{email}:{password}'.encode()).decode()
    assert authenticate_user(request).is_none


def test_bad_encode(request_factory, create_full_user):
    user, password = create_full_user
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = encode_auth(user.email, password)[1:]
    assert authenticate_user(request).is_none
