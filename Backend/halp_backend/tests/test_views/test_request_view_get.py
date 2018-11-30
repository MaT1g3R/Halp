import json
import random
from itertools import chain

import pytest
from django.utils.datetime_safe import datetime
from django.utils.http import urlencode

from halp_backend import request_converter
from halp_backend.models import Request
from halp_backend.tests import fake
from halp_backend.tests.conftest import (
    encode_auth, fake_request, fake_request_creation, fake_response, generate_users,
)

pytestmark = pytest.mark.django_db


def test_no_query(create_full_user, client):
    user, password = create_full_user
    reqeusts = [fake_request(random.choice([True, False]), user) for _ in range(10)]
    other_requests = [fake_request(random.choice([True, False])) for _ in range(10)]
    for req in reqeusts:
        for _ in range(random.randint(0, 5)):
            fake_response(req)
    resp = client.get('/api/v1/request', HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    resp_dict = json.loads(resp.content)
    assert sorted(resp_dict['requests'], key=lambda d: d['request_id']) == sorted(
        (request_converter.to_dict(req) for req in reqeusts), key=lambda d: d['request_id']
    )


@pytest.mark.parametrize('query,expected', [
    ('assigned=true', [0]),
    ('assigned=false', [1]),
    ('finished=true', [0]),
    ('finished=false', [1]),
    ('starts_after=20000', [1]),
    ('starts_after=60000', []),
])
def test_query(query, expected, create_full_user, client):
    user, password = create_full_user

    customers = [u for u, _ in generate_users(5)]
    workers = [u for u, _ in generate_users(5)]
    for u in chain(customers, workers):
        u.save()

    default_args = lambda: {'duration': fake.time_delta(), 'latitude': fake.latitude(),
                            'longitude': fake.longitude(), 'description': fake.text(),
                            'customer': random.choice(customers)}
    reqeusts = [
        Request(
            start_time=datetime.fromtimestamp(1000),
            assigned_to=random.choice(workers),
            finished=True, **default_args()
        ),
        Request(
            start_time=datetime.fromtimestamp(30000),
            assigned_to=None,
            finished=False,
            **default_args()
        )
    ]
    for r in reqeusts:
        r.clean()
        r.save()
    resp = client.get(f'/api/v1/request?{query}',
                      HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    resp_dict = json.loads(resp.content)
    resp_requests = resp_dict['requests']
    assert len(resp_requests) == len(expected)
    assert sorted([request_converter.to_dict(reqeusts[i]) for i in expected],
                  key=lambda d: d['request_id']) == sorted(
        resp_requests, key=lambda d: d['request_id']
    )


@pytest.mark.parametrize('lat,long,radius,expected', [
    (9.999, -9.999, 8832, [0, 1]),
    (0.544, -0.744, 103, [0]),
    (-44.5454, 50.1545, 607, [1]),
    (10, 10, 10, []),
])
def test_query_radius(lat, long, radius, expected, client, create_full_user):
    request_dict_lst = [
        fake_request_creation(True),
        fake_request_creation(False),
    ]
    reqeusts = [
        Request.objects.create_request(**d) for d in request_dict_lst
    ]
    reqeusts[0].latitude = 0
    reqeusts[0].longitude = 0
    reqeusts[0].save()

    reqeusts[1].latitude = -50
    reqeusts[1].longitude = 50
    reqeusts[1].save()

    user, password = create_full_user
    resp = client.get(f'/api/v1/request?lat={lat}&long={long}&radius={radius}',
                      HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 200
    resp_dict = json.loads(resp.content)
    resp_requests = resp_dict['requests']
    assert len(resp_requests) == len(expected)
    assert sorted([request_converter.to_dict(reqeusts[i]) for i in expected],
                  key=lambda d: d['request_id']) == sorted(
        resp_requests, key=lambda d: d['request_id']
    )


@pytest.mark.parametrize('missing', [
    ['lat'],
    ['long'],
    ['radius'],
    ['radius', 'lat'],
    ['radius', 'long'],
    ['lat', 'long'],
])
def test_missing_query_radius(missing, client, create_full_user):
    user, password = create_full_user
    d = {'lat': 0, 'long': 0, 'radius': 10}
    for miss in missing:
        del d[miss]
    query_string = urlencode(d)
    resp = client.get(f'/api/v1/request?{query_string}',
                      HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 400
    assert 'dependency' in resp.content.decode()


@pytest.mark.parametrize('query', [
    'lat=foo',
    'assigned=fals',
    'starts_after=0.1',
    'hi=hi',
    'lat=300'
])
def test_bad_query(query, client, create_full_user):
    user, password = create_full_user
    resp = client.get(f'/api/v1/request?{query}',
                      HTTP_AUTHORIZATION=encode_auth(user.email, password))
    assert resp.status_code == 400
    assert '"error":' in resp.content.decode()
