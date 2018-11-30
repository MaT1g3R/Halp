import json

import pytest

from halp_backend import request_converter
from halp_backend.models import Request
from halp_backend.tests import fake
from halp_backend.tests.conftest import encode_auth

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('has_start_time', [True, False])
def test_success(has_start_time, create_full_user, client):
    user, password = create_full_user
    d = {
        'duration': int(fake.time_delta().total_seconds()),
        'latitude': float(fake.latitude()),
        'longitude': float(fake.longitude()),
        'description': fake.text(),
    }
    if has_start_time:
        d['start_time'] = int(fake.date_time().timestamp())
    resp = client.post('/api/v1/request', data=d, content_type='application/json',
                       HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    resp_json = json.loads(resp.content)
    request_created = Request.objects.get(id=resp_json['request_id'])
    assert resp_json == request_converter.to_dict(request_created)
