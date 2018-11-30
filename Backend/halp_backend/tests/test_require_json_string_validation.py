import json

import pytest

from halp_backend.controller import require_json_string_validation


@require_json_string_validation({'type': 'object'})
def mock_func(data, *args, **kwargs):
    return data, args, kwargs


@pytest.mark.parametrize('string', [
    json.dumps({}),
    json.dumps({'foo': 'baz'}).encode(),
    json.dumps({'foo': 1}),
    json.dumps({'': [{}]}),
])
def test_success(string):
    args = ('I', 'Am', 'the', 'Senate')
    kwargs = {'Not_yet': "It's treason, then."}
    res = mock_func(string, *args, **kwargs)
    assert res[0] == json.loads(string)
    assert res[1] == args
    assert res[2] == kwargs


@pytest.mark.parametrize('string', [
    json.dumps([1, 3]).encode(),
    json.dumps([{'foo': 'baz'}])
])
def test_fail(string):
    res = mock_func(string)
    assert res.is_err
    assert 'is not of type' in res.unwrap_err().message


@pytest.mark.parametrize('string', [
    '{ a }',
    b'm,em'
])
def test_fail_load_json(string):
    res = mock_func(string)
    assert res.is_err
    assert 'is not a valid json string' in res.unwrap_err().message
