import pytest

from halp_backend.models import Request
from halp_backend.tests.conftest import fake_request_creation

pytestmark = pytest.mark.django_db


def test_delete():
    fake_request = fake_request_creation(False)
    req = Request.objects.create_request(**fake_request)
    req.save()
    req_id = req.id
    assert req in Request.objects.pending_requests
    Request.objects.delete_request(req)
    assert req not in Request.objects.pending_requests
    assert not Request.objects.filter(id=req_id).exists()


def test_delete_not_in_queue():
    orig_queue = Request.objects.pending_requests

    fake_request = fake_request_creation(True)
    req = Request.objects.create_request(**fake_request)
    req.save()
    req_id = req.id
    assert req not in Request.objects.pending_requests
    Request.objects.delete_request(req)
    assert req not in Request.objects.pending_requests
    assert not Request.objects.filter(id=req_id).exists()

    assert Request.objects.pending_requests is orig_queue
