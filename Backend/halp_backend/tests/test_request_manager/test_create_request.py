import pytest

from halp_backend.models import Request
from halp_backend.tests.conftest import fake_request_creation

pytestmark = pytest.mark.django_db


def test_success():
    Request.objects.pending_requests.clear()
    fake_request = fake_request_creation(True)
    req = Request.objects.create_request(**fake_request)
    assert req
    assert req not in Request.objects.pending_requests


def test_success_time_null():
    Request.objects.pending_requests.clear()
    fake_request = fake_request_creation(False)
    req = Request.objects.create_request(**fake_request)
    assert req
    assert req in Request.objects.pending_requests
