import json

import pytest

from halp_backend import request_converter
from halp_backend.models import Request
from halp_backend.tests.conftest import encode_auth, fake_request, generate_users

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('has_start_time', [True, False])
def test_assign_success_self(create_full_user, client, has_start_time):
    user, password = create_full_user
    request = fake_request(has_start_time)

    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    request = Request.objects.get(id=request.id)
    assert request.assigned_to == user
    assert json.loads(resp.content) == request_converter.to_dict(request)


@pytest.mark.parametrize('has_start_time', [True, False])
def test_assign_success_other(client, has_start_time):
    users = list(generate_users(2))
    worker, worker_pass = users[0]
    user, password = users[1]
    worker.save()
    user.save()
    request = fake_request(has_start_time, user)

    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id, 'worker_id': worker.id},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    request = Request.objects.get(id=request.id)
    assert request.assigned_to == worker
    assert json.loads(resp.content) == request_converter.to_dict(request)


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_already_assigned(client, has_start_time):
    users = list(generate_users(3))
    worker, worker_pass = users[0]
    user, password = users[1]
    other_worker, other_pass = users[2]
    worker.save()
    user.save()
    other_worker.save()
    request = fake_request(has_start_time, user)
    request.assigned_to = other_worker
    request.save()

    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id, 'worker_id': worker.id},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 400
    assert request.assigned_to == other_worker


@pytest.mark.parametrize('has_id', [True, False])
@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_cannot_assign_self(create_full_user, client, has_start_time, has_id):
    user, password = create_full_user
    request = fake_request(has_start_time, user)

    data = {'request_id': request.id}
    if has_id:
        data['worker_id'] = user.id

    resp = client.post(
        '/api/v1/assign_worker',
        data=data,
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 400
    assert request.assigned_to is None


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_cannot_assign_others_job(client, has_start_time):
    users = list(generate_users(3))
    user, password = users[0]
    other_user, other_pass = users[1]
    worker, worker_pass = users[2]
    user.save()
    other_user.save()
    worker.save()

    request = fake_request(has_start_time, other_user)
    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id, 'worker_id': worker.id},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 403
    assert request.assigned_to is None


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_job_not_found(create_full_user, has_start_time, client):
    user, password = create_full_user
    request = fake_request(has_start_time)
    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id + 1},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 404
    assert request.assigned_to is None


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_worker_not_found(create_full_user, has_start_time, client):
    user, password = create_full_user
    request = fake_request(has_start_time)
    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id, 'worker_id': user.id + request.customer.id + 1},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 404
    assert request.assigned_to is None


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_finished(create_full_user, client, has_start_time):
    user, password = create_full_user
    request = fake_request(has_start_time)
    request.finished = True
    request.save()
    resp = client.post(
        '/api/v1/assign_worker',
        data={'request_id': request.id},
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 400
    assert request.assigned_to is None
