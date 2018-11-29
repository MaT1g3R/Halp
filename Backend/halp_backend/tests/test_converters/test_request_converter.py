import pytest

from halp_backend import request_converter, response_converter, user_converter
from halp_backend.tests.conftest import fake_request, fake_response

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('has_start,has_assign,response_count,finished', [
    (True, True, 0, True),
    (True, False, 10, False),
    (False, True, 5, False),
    (False, False, 0, True),
])
def test_to_dict(has_start, has_assign, create_full_user, response_count, finished):
    request = fake_request(has_start)
    request.finished = finished

    worker, _ = create_full_user
    if has_assign:
        request.assigned_to = worker
    responses = [
        fake_response(request) for _ in range(response_count)
    ]

    request.save()

    request_dict = request_converter.to_dict(request)
    assert request_dict['request_id'] == request.id
    assert request_dict['customer'] == user_converter.to_dict(request.customer)
    if has_start:
        assert request_dict['start_time'] == int(request.start_time.timestamp())
    else:
        assert request_dict['start_time'] is None

    assert request_dict['duration'] == int(request.duration.total_seconds())
    assert abs(request_dict['latitude'] - float(request.latitude)) < 0.0001
    assert abs(request_dict['longitude'] - float(request.longitude)) < 0.0001
    assert request_dict['finished'] == request.finished
    assert request_dict['description'] == request.description
    if has_assign:
        assert request_dict['assigned_to'] == user_converter.to_dict(worker)
    else:
        assert request_dict['assigned_to'] is None
    assert len(request_dict['responses']) == len(responses)
    for response_dict in (response_converter.to_dict(resp) for resp in responses):
        assert response_dict in request_dict['responses']
