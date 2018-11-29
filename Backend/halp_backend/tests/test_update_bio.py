import json

import pytest

from halp_backend.models import User
from halp_backend.tests import fake
from halp_backend.tests.conftest import encode_auth

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('bio', [
    fake.text(),
    fake.text(),
    fake.text(),
    fake.text(),
    fake.text(),
])
def test_update_bio(bio, create_full_user, client):
    user, password = create_full_user
    resp = client.post('/api/v1/update_bio', data={'bio': bio}, content_type='application/json',
                       HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    assert json.loads(resp.content.decode()) == {'bio': bio}
    user = User.objects.get(id=user.id)
    assert user.bio == bio
