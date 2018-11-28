import pytest
from django.test.client import RequestFactory


@pytest.fixture()
def request_factory():
    return RequestFactory()
