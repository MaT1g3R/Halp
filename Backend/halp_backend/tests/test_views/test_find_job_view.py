import json

import pytest
from django.utils.http import urlencode

from halp_backend import request_converter
from halp_backend.models import Request
from halp_backend.tests.conftest import encode_auth, fake_request_creation

pytestmark = pytest.mark.django_db


def test_found(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user
    request = Request.objects.create_request(**fake_request_creation(False))
    resp = client.get(
        '/api/v1/find_job',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] == request_converter.to_dict(request)
    assert request.assigned_to == user


def test_not_found(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()

    user, password = create_full_user
    Request.objects.create_request(**fake_request_creation(False))
    client.get(
        '/api/v1/find_job',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    resp = client.get(
        '/api/v1/find_job',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] is None


def test_found_radius(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    user, password = create_full_user
    request = Request.objects.create_request(**fake_request_creation(False))
    request.longitude = 0
    request.latitude = 0
    request.save()

    resp = client.get(
        '/api/v1/find_job?lat=1&long=1&radius=158',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] == request_converter.to_dict(request)
    assert request.assigned_to == user


def test_found_duration(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    user, password = create_full_user
    request = Request.objects.create_request(**fake_request_creation(False))
    resp = client.get(
        f'/api/v1/find_job?duration={int(request.duration.total_seconds())}',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] == request_converter.to_dict(request)
    assert request.assigned_to == user


def test_not_found_radius(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    user, password = create_full_user
    request = Request.objects.create_request(**fake_request_creation(False))
    request.longitude = 0
    request.latitude = 0
    request.save()

    resp = client.get(
        '/api/v1/find_job?lat=1&long=1&radius=15',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] is None


def test_not_found_duration(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    user, password = create_full_user
    request = Request.objects.create_request(**fake_request_creation(False))
    initial = request.duration
    url = f'/api/v1/find_job?duration={int(initial.total_seconds()) - 2}'
    resp = client.get(
        url,
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] is None


def test_work_on_yourself(create_full_user, client):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    user, password = create_full_user
    fake_req = fake_request_creation(False)
    fake_req['customer'] = user
    request = Request.objects.create(**fake_request_creation(False))

    resp = client.get(
        '/api/v1/find_job',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    assert resp_json['request'] is None


@pytest.mark.parametrize('query', [
    {'duration': 1.1},
    {'radius': ''},
    {'lat': None},
    {'long': []},
])
def test_bad_data(create_full_user, client, query):
    Request.objects.paired_requests = {}
    Request.objects.pending_requests.clear()
    user, password = create_full_user
    request = Request.objects.create_request(**fake_request_creation(False))
    resp = client.get(
        f'/api/v1/find_job?{urlencode(query)}',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )

    assert resp.status_code == 400
