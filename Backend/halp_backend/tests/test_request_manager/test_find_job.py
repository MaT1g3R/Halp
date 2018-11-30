from datetime import timedelta
from unittest.mock import MagicMock

from halp_backend.models import Request, User


def test_found():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    request = MagicMock(Request)
    Request.objects.pending_requests.append(request)

    worker = MagicMock(User)
    assert Request.objects.find_job(worker).unwrap() == request
    assert request not in Request.objects.pending_requests
    assert Request.objects.paired_requests[request.id] == worker


def test_found_within_radius():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    requests = [
        MagicMock(Request, latitude=0, longitude=0, id=1),
        MagicMock(Request, latitude=-50, longitude=50, id=2)
    ]
    for request in requests:
        Request.objects.pending_requests.append(request)

    worker = MagicMock(User)
    assert Request.objects.find_job(
        worker, radius=607, latitude=-44.5454, longitude=50.1545
    ).unwrap() == requests[1]
    assert requests[1] not in Request.objects.pending_requests
    assert requests[0] in Request.objects.pending_requests

    assert Request.objects.paired_requests[requests[1].id] == worker
    assert Request.objects.paired_requests.get(requests[0].id) is None


def test_found_within_duration():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    requests = [
        MagicMock(Request, duration=timedelta(seconds=1000), id=1),
        MagicMock(Request, duration=timedelta(seconds=500), longitude=50, id=2)
    ]
    for request in requests:
        Request.objects.pending_requests.append(request)

    worker = MagicMock(User)
    assert Request.objects.find_job(
        worker, duration=700
    ).unwrap() == requests[1]
    assert requests[1] not in Request.objects.pending_requests
    assert requests[0] in Request.objects.pending_requests

    assert Request.objects.paired_requests[requests[1].id] == worker
    assert Request.objects.paired_requests.get(requests[0].id) is None


def test_not_found():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    request = MagicMock(Request)
    Request.objects.paired_requests[request.id] = other_user = MagicMock(User)
    worker = MagicMock(User)
    assert Request.objects.find_job(worker).is_none
    assert Request.objects.paired_requests[request.id] == other_user
    assert worker not in Request.objects.paired_requests.values()


def test_not_found_too_far():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    requests = [
        MagicMock(Request, latitude=0, longitude=0, id=1),
        MagicMock(Request, latitude=-50, longitude=50, id=2)
    ]
    for request in requests:
        Request.objects.pending_requests.append(request)

    worker = MagicMock(User)
    assert Request.objects.find_job(
        worker, radius=50, latitude=-44.5454, longitude=50.1545
    ).is_none
    assert requests[1] in Request.objects.pending_requests
    assert requests[0] in Request.objects.pending_requests

    assert Request.objects.paired_requests.get(requests[1].id) is None
    assert Request.objects.paired_requests.get(requests[0].id) is None


def test_not_found_too_long():
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    requests = [
        MagicMock(Request, duration=timedelta(seconds=5000), id=1),
        MagicMock(Request, duration=timedelta(seconds=4000), id=2)
    ]
    for request in requests:
        Request.objects.pending_requests.append(request)

    worker = MagicMock(User)
    assert Request.objects.find_job(worker, duration=300).is_none
    assert requests[1] in Request.objects.pending_requests
    assert requests[0] in Request.objects.pending_requests

    assert Request.objects.paired_requests.get(requests[1].id) is None
    assert Request.objects.paired_requests.get(requests[0].id) is None
