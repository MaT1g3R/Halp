import pytest

from halp_backend.controller import require_json_validation


@require_json_validation({'type': 'object'})
def mock_func(data, *args, **kwargs):
    return data, args, kwargs


@pytest.mark.parametrize('data', [
    {},
    {'foo': 'baz'},
    {'foo': 1},
    {'': [{}]},
])
def test_success(data):
    args = ('I', 'Am', 'the', 'Senate')
    kwargs = {'Not_yet': "It's treason, then."}
    res = mock_func(data, *args, **kwargs)
    assert res[0] == data
    assert res[1] == args
    assert res[2] == kwargs


@pytest.mark.parametrize('data', [
    [1, 3],
    [{'foo': 'baz'}],
])
def test_fail(data):
    res = mock_func(data)
    assert res.is_err
    assert 'is not of type' in res.unwrap_err().message
