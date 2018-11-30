import json

import pytest

from halp_backend import user_converter
from halp_backend.tests.conftest import encode_auth, generate_users

pytestmark = pytest.mark.django_db


def test_success(create_full_user, client):
    user, password = create_full_user
    resp = client.get('/api/v1/profile', HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    assert resp.content.decode() == json.dumps(user_converter.to_dict(user))


def test_success_query(create_full_user, client):
    my_user, password = create_full_user
    other_users = list(generate_users(10))
    for user, _ in other_users:
        user.save()
    for other, _ in other_users:
        resp = client.get(f'/api/v1/profile?user_id={other.id}',
                          HTTP_AUTHORIZATION=encode_auth(my_user.email, password))
        assert resp.status_code == 200
        assert resp.content.decode() == json.dumps(user_converter.to_dict(other))


def test_fail_not_found(create_full_user, client):
    user, password = create_full_user
    resp = client.get(f'/api/v1/profile?user_id={user.id + 1}',
                      HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 404
    assert 'does not exist' in resp.content.decode()
