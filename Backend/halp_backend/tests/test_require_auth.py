import pytest

from halp_backend.auth import require_auth
from halp_backend.models import User
from halp_backend.tests.conftest import encode_auth

pytestmark = pytest.mark.django_db


def mock_view(user, request):
    return user, request


@pytest.mark.parametrize('email,password', [
    ('foo@bar.baz', '123456789'),
])
def test_success(email, password, request_factory):
    user = User.objects.create_user(email, password, '', '')
    user.save()
    wrapped = require_auth(mock_view)
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = encode_auth(email, password)
    assert wrapped(request) == (user, request)


@pytest.mark.parametrize('email,password', [
    ('foo@bar.baz', '123456789'),
])
def test_fail(email, password, request_factory):
    user = User.objects.create_user(email, password, '', '')
    user.save()
    wrapped = require_auth(mock_view)
    request = request_factory.get('/')
    request.META['TTP_AUTHORIZATION'] = encode_auth(email, password)
    assert wrapped(request).status_code == 401
