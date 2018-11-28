from base64 import b64encode

import pytest
from django.test.client import RequestFactory


@pytest.fixture()
def request_factory():
    return RequestFactory()


def encode_auth(email, password):
    return b64encode(f'Basic {email}:{password}'.encode()).decode()
