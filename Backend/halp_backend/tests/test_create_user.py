import json

import pytest
from django.contrib.auth import authenticate

from halp_backend import controller, user_converter

pytestmark = pytest.mark.django_db


@pytest.fixture
def fake_user_dict(fake_password, fake_last_name, fake_first_name, fake_email):
    return {
        'email': fake_email,
        'password': fake_password,
        'first_name': fake_first_name,
        'last_name': fake_last_name
    }


def test_success(fake_user_dict):
    string = json.dumps(fake_user_dict)
    resp = controller.create_user(string)
    assert resp.status_code == 200
    user = json.loads(resp.content)
    assert user == user_converter.to_dict(
        authenticate(username=fake_user_dict['email'], password=fake_user_dict['password']))


def test_fail_user_exists(create_full_user, fake_user_dict):
    user, _ = create_full_user
    fake_user_dict['email'] = user.email
    resp = controller.create_user(json.dumps(fake_user_dict))
    assert resp.status_code == 400
    assert 'already exists' in resp.content.decode()


@pytest.mark.parametrize('extras', [
    {'memes': 'dank'},
    {'hello there': 'no'},
    {'wat man': ['nan', 'nan']}
])
def test_fail_extra_field(fake_user_dict, extras):
    fake_user_dict.update(extras)
    resp = controller.create_user(json.dumps(fake_user_dict))
    assert resp.status_code == 400
    assert 'Additional properties' in resp.content.decode()


@pytest.mark.parametrize('to_del', [
    'email',
    'password',
    'first_name',
    'last_name'
])
def test_fail_missing_field(fake_user_dict, to_del):
    del fake_user_dict[to_del]
    resp = controller.create_user(json.dumps(fake_user_dict))
    assert resp.status_code == 400
    assert 'is a required property' in resp.content.decode()


@pytest.mark.parametrize('to_empty', [
    'email',
    'password',
    'first_name',
    'last_name'
])
def test_fail_empty_string(fake_user_dict, to_empty):
    fake_user_dict[to_empty] = ''
    resp = controller.create_user(json.dumps(fake_user_dict))
    assert resp.status_code == 400
    assert 'is too short' in resp.content.decode()
