import json

import pytest
from django.core.serializers.json import DjangoJSONEncoder
from option import Err, Ok

from halp_backend.controller import json_resposne
from halp_backend.typedefs import HttpError


@pytest.mark.parametrize('data', [
    {'foo': [1, 3]},
])
def test_ok(data):
    wrapped = json_resposne(dict, Ok)
    resp = wrapped(data)
    assert resp.status_code == 200
    assert resp.content.decode() == json.dumps(data, cls=DjangoJSONEncoder)


@pytest.mark.parametrize('err', [
    HttpError(400, 'ohno'),
    HttpError(500)
])
def test_err(err):
    wrapped = json_resposne(dict, Err)
    resp = wrapped(err)
    assert resp.status_code == err.status
    assert resp.content.decode() == json.dumps({'error': err.message}, cls=DjangoJSONEncoder)


def test_partial():
    curry = json_resposne(dict)(Ok)({})
    normal = json_resposne(dict, Ok)({})
    assert curry.__dict__ == normal.__dict__
