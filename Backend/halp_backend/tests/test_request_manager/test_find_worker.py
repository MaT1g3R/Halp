from unittest.mock import MagicMock

from halp_backend.models import Request, User


def test_found():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    request = MagicMock(Request)
    worker = MagicMock(User)
    Request.objects.paired_requests[request.id] = worker
    assert Request.objects.find_worker(request).unwrap() == worker
    assert request.id not in Request.objects.paired_requests
    assert request.assigned_to == worker


def test_not_found():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    request = MagicMock(Request)
    another_request = MagicMock(Request, assigned_to=None)
    worker = MagicMock(User)
    Request.objects.pending_requests.append(another_request)
    Request.objects.paired_requests[request.id] = worker
    assert Request.objects.find_worker(another_request).is_none
    assert another_request.assigned_to is None
    assert Request.objects.paired_requests[request.id] == worker
    assert another_request in Request.objects.pending_requests
