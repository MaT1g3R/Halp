from base64 import b64encode
from random import choice

import pytest
from django.test.client import RequestFactory

from halp_backend.models import Request, Response, User
from halp_backend.tests import fake


@pytest.fixture()
def fake_email():
    return fake.email()


@pytest.fixture()
def fake_password():
    return fake.password()


@pytest.fixture()
def fake_first_name():
    return fake.first_name()


@pytest.fixture()
def fake_last_name():
    return fake.last_name()


@pytest.fixture()
def request_factory():
    return RequestFactory()


def _generate_user():
    kwargs = {}
    if choice([True, False]):
        kwargs['bio'] = fake.text()
    password = fake.password()
    return User.objects.create_user(
        fake.email(),
        password,
        fake.first_name(),
        fake.last_name(),
        **kwargs
    ), password


@pytest.fixture
def generate_user():
    return _generate_user()


@pytest.fixture
def create_full_user(generate_user):
    user, pw = generate_user
    user.save()
    return user, pw


def generate_users(amount):
    for _ in range(amount):
        yield _generate_user()


def fake_request_creation(has_start_time):
    user, password = _generate_user()
    user.save()
    d = {
        'customer': user,
        'duration': fake.time_delta(),
        'latitude': fake.latitude(),
        'longitude': fake.longitude(),
        'description': fake.text(),
    }
    if has_start_time:
        d['start_time'] = fake.date_time()
    return d


def fake_request(has_start_time):
    req_dict = fake_request_creation(has_start_time)
    req = Request.objects.create_request(**req_dict)
    return req


def fake_response(request):
    user, _ = _generate_user()
    response = Response(worker=user, comment=fake.text(), request=request)
    response.save()
    return response


def encode_auth(email, password):
    return b64encode(f'Basic {email}:{password}'.encode()).decode()
