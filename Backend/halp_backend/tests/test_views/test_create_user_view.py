import json

import pytest
from django.contrib.auth import authenticate

from halp_backend import user_converter
# noinspection PyUnresolvedReferences
from halp_backend.tests.test_create_user import fake_user_dict

pytestmark = pytest.mark.django_db


def test_success(client, fake_user_dict):
    resp = client.post('/api/v1/create_user', data=fake_user_dict, content_type='application/json')
    assert resp.status_code == 200
    assert json.loads(resp.content.decode()) == user_converter.to_dict(
        authenticate(username=fake_user_dict['email'], password=fake_user_dict['password']))


def test_fail(client, fake_user_dict):
    del fake_user_dict['email']
    resp = client.post('/api/v1/create_user', data=fake_user_dict, content_type='application/json')
    assert resp.status_code == 400
    assert 'is a required property' in resp.content.decode()
