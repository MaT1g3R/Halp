import pytest

from halp_backend.models import Request
from halp_backend.tests.conftest import encode_auth, fake_request_creation

pytestmark = pytest.mark.django_db


@pytest.mark.parametrize('has_start_time', [True, False])
def test_delete_success(create_full_user, client, has_start_time):
    user, password = create_full_user
    fake_request = fake_request_creation(has_start_time)
    fake_request['customer'] = user
    request = Request.objects.create_request(**fake_request)
    request_id = request.id
    resp = client.delete(f'/api/v1/request?request_id={request_id}',
                         HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    assert not Request.objects.filter(id=request_id).exists()


@pytest.mark.parametrize('has_start_time', [True, False])
def test_delete_not_found(create_full_user, client, has_start_time):
    user, password = create_full_user
    fake_request = fake_request_creation(has_start_time)
    fake_request['customer'] = user
    request = Request.objects.create_request(**fake_request)
    request_id = request.id
    resp = client.delete(f'/api/v1/request?request_id={request_id + 1}',
                         HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 404
    assert Request.objects.filter(id=request_id).exists()


@pytest.mark.parametrize('has_start_time', [True, False])
def test_delete_fail_no_param(create_full_user, client, has_start_time):
    user, password = create_full_user
    fake_request = fake_request_creation(has_start_time)
    fake_request['customer'] = user
    request = Request.objects.create_request(**fake_request)
    request_id = request.id
    resp = client.delete(f'/api/v1/request?request_d={request_id}',
                         HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 400
    assert Request.objects.filter(id=request_id).exists()


@pytest.mark.parametrize('has_start_time', [True, False])
def test_delete_fail(create_full_user, client, has_start_time):
    user, password = create_full_user
    fake_request = fake_request_creation(has_start_time)
    fake_request['customer'] = user
    request = Request.objects.create_request(**fake_request)
    request_id = request.id
    resp = client.delete(f'/api/v1/request?request_id={request_id}.1',
                         HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 400
    assert Request.objects.filter(id=request_id).exists()


@pytest.mark.parametrize('has_start_time', [True, False])
def test_delete_other_people(create_full_user, client, has_start_time):
    user, password = create_full_user
    fake_request = fake_request_creation(has_start_time)
    request = Request.objects.create_request(**fake_request)
    request_id = request.id
    resp = client.delete(f'/api/v1/request?request_id={request_id}',
                         HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 403
    assert Request.objects.filter(id=request_id).exists()
