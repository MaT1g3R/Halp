import json

import pytest

from halp_backend import request_converter, user_converter
from halp_backend.models import Request
from halp_backend.tests import fake
from halp_backend.tests.conftest import encode_auth, fake_request_creation, fake_response

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('has_start_time', [True, False])
def test_success(create_full_user, client, has_start_time):
    user, password = create_full_user
    req_dict = fake_request_creation(has_start_time)
    req = Request.objects.create_request(**req_dict)
    actual_json = request_converter.to_dict(req)
    data = {
        'request_id': req.id,
        'comment': fake.text()
    }
    resp = client.post(
        '/api/v1/create_response',
        data=data,
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    actual_json['responses'].append(
        {'worker': user_converter.to_dict(user), 'comment': data['comment']})
    for response in resp_json['responses']:
        del response['response_id']
    assert resp_json == actual_json


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_alraedy_exist(create_full_user, client, has_start_time):
    user, password = create_full_user
    req_dict = fake_request_creation(has_start_time)
    req = Request.objects.create_request(**req_dict)
    response = fake_response(req, user)
    data = {
        'request_id': req.id,
        'comment': fake.text()
    }
    resp = client.post(
        '/api/v1/create_response',
        data=data,
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 400
    assert response in Request.objects.get(id=req.id).response_set.all()


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_no_request(create_full_user, client, has_start_time):
    user, password = create_full_user
    req_dict = fake_request_creation(has_start_time)
    req = Request.objects.create_request(**req_dict)
    data = {
        'request_id': req.id + 1,
        'comment': fake.text()
    }
    resp = client.post(
        '/api/v1/create_response',
        data=data,
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 404


@pytest.mark.parametrize('has_start_time', [True, False])
def test_fail_cannot_respond_to_self(create_full_user, client, has_start_time):
    user, password = create_full_user
    req_dict = fake_request_creation(has_start_time)
    req_dict['customer'] = user
    req = Request.objects.create_request(**req_dict)
    data = {
        'request_id': req.id,
        'comment': fake.text()
    }
    resp = client.post(
        '/api/v1/create_response',
        data=data,
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 400


def test_fail_bad_body(create_full_user, client):
    user, password = create_full_user
    data = {
        'request_i': 1,
        'comment': fake.text()
    }
    resp = client.post(
        '/api/v1/create_response',
        data=data,
        content_type='application/json',
        HTTP_AUTHORIZATION=encode_auth(user.email, password)
    )
    assert resp.status_code == 400
