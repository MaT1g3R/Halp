from json import loads

import pytest
from hypothesis import assume, given, strategies

from halp_backend import user_converter
from halp_backend.controller import get_profile
from halp_backend.models import User

pytestmark = pytest.mark.django_db


def test_found():
    user = User.objects.create_user('email@email.org', '12', '23', '34')
    user.save()
    assert loads(get_profile(user.id).content.decode()) == user_converter.to_dict(user)


@pytest.mark.parametrize('user_id', [
    None,
    '',
    1.2
])
def test_bad_id(user_id):
    resp = get_profile(user_id)
    assert 'is not a valid' in resp.content.decode()
    assert resp.status_code == 400


@given(strategies.integers())
def test_not_found(user_id):
    assume(user_id not in User.objects.values_list('id'))
    resp = get_profile(user_id)
    assert 'does not exist' in resp.content.decode()
    assert resp.status_code == 400
