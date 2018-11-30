import pytest

from halp_backend import response_converter, user_converter
from halp_backend.tests.conftest import fake_request, fake_response

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('has_start_time', [True, False])
def test_to_dict(has_start_time):
    req = fake_request(has_start_time)
    resp = fake_response(req)
    resp_dict = response_converter.to_dict(resp)
    assert resp.id == resp_dict['response_id']
    assert user_converter.to_dict(resp.worker) == resp_dict['worker']
    assert resp.comment == resp_dict['comment']
