import pytest

from halp_backend.auth import require_auth
from halp_backend.tests.conftest import encode_auth

pytestmark = pytest.mark.django_db


def mock_view(user, request):
    return user, request


def test_success(request_factory, create_full_user):
    user, password = create_full_user
    wrapped = require_auth(mock_view)
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = encode_auth(user.email, password)
    assert wrapped(request) == (user, request)


def test_fail(request_factory, create_full_user):
    user, password = create_full_user
    email = user.email
    wrapped = require_auth(mock_view)
    request = request_factory.get('/')
    request.META['TTP_AUTHORIZATION'] = encode_auth(email, password)
    assert wrapped(request).status_code == 401
