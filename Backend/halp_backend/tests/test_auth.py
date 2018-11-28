from base64 import b64encode

import pytest

from halp_backend.auth import authenticate_user
from halp_backend.models import User

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('email,password', [
    ('email@halp.com', 'hunter2')
])
def test_success(email, password, request_factory):
    user = User.objects.create_user(email, password)
    user.save()
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic {email}:{password}'.encode()).decode()
    assert user == authenticate_user(request).unwrap()


@pytest.mark.parametrize('email,password', [
    ('email@halp.com', 'hunter2')
])
def test_bad_header(email, password, request_factory):
    user = User.objects.create_user(email, password)
    user.save()
    request = request_factory.get('/')
    request.META['AUTHORIZATION'] = b64encode(f'Basic {email}:{password}'.encode()).decode()
    assert authenticate_user(request).is_none


@pytest.mark.parametrize('email,password', [
    ('email@halp.com', 'hunter2')
])
def test_bad_password(email, password, request_factory):
    user = User.objects.create_user(email, password)
    user.save()
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic {email}:{password}a'.encode()).decode()
    assert authenticate_user(request).is_none


@pytest.mark.parametrize('email,password', [
    ('email@halp.com', 'hunter2')
])
def test_bad_email(email, password, request_factory):
    user = User.objects.create_user(email, password)
    user.save()
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic c{email}:{password}'.encode()).decode()
    assert authenticate_user(request).is_none


@pytest.mark.parametrize('email,password', [
    ('email@halp.com', 'hunter2')
])
def test_bad_format(email, password, request_factory):
    user = User.objects.create_user(email, password)
    user.save()
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basi {email}:{password}'.encode()).decode()
    assert authenticate_user(request).is_none
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic {email}{password}'.encode()).decode()
    assert authenticate_user(request).is_none
    request.META['HTTP_AUTHORIZATION'] = b64encode(f'Basic{email}:{password}'.encode()).decode()
    assert authenticate_user(request).is_none


@pytest.mark.parametrize('email,password', [
    ('email@halp.com', 'hunter2')
])
def test_bad_encode(email, password, request_factory):
    user = User.objects.create_user(email, password)
    user.save()
    request = request_factory.get('/')
    request.META['HTTP_AUTHORIZATION'] = b64encode(
        f'Basic {email}:{password}'.encode()).decode()[1:]
    assert authenticate_user(request).is_none