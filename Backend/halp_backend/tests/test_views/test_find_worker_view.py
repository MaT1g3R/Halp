import json
import random

import pytest

from halp_backend import user_converter
from halp_backend.models import Request
from halp_backend.tests.conftest import encode_auth, fake_request_creation, generate_users

pytestmark = pytest.mark.django_db


def test_found(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user
    worker, _ = random.choice(list(generate_users(3)))

    request_dict = fake_request_creation(False)
    request_dict['customer'] = user
    request = Request.objects.create_request(**request_dict)
    assert Request.objects.find_job(worker)
    resp = client.get(
        f'/api/v1/find_worker?request_id={request.id}',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['worker'] == user_converter.to_dict(worker)


def test_not_found(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user

    request_dict = fake_request_creation(False)
    request_dict['customer'] = user
    request = Request.objects.create_request(**request_dict)

    resp = client.get(
        f'/api/v1/find_worker?request_id={request.id}',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['worker'] is None


def test_fail_other_customer(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user

    request_dict = fake_request_creation(False)
    request = Request.objects.create_request(**request_dict)

    resp = client.get(
        f'/api/v1/find_worker?request_id={request.id}',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 403


def test_fail_not_exist(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user

    request_dict = fake_request_creation(False)
    request = Request.objects.create_request(**request_dict)

    resp = client.get(
        f'/api/v1/find_worker?request_id={request.id + 1}',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 404


def test_bad_id(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user
    worker, _ = random.choice(list(generate_users(3)))

    request_dict = fake_request_creation(False)
    request_dict['customer'] = user
    request = Request.objects.create_request(**request_dict)
    assert Request.objects.find_job(worker)
    resp = client.get(
        f'/api/v1/find_worker?request_id={request.id}.1',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 400
