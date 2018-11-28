import pytest

from halp_backend.util import allow_methods


def mock_view(request):
    return request


@pytest.mark.parametrize('method', [
    'GET', 'POST', 'DELETE', 'PUT'
])
def test_one(request_factory, method):
    wrapped = allow_methods([method], mock_view)
    request = request_factory.generic(method, '/')
    assert wrapped(request) == request


def test_many(request_factory):
    methods = ['GET', 'POST', 'DELETE', 'PUT']
    method = 'POST'
    wrapped = allow_methods(methods, mock_view)
    request = request_factory.generic(method, '/')
    assert wrapped(request) == request


def test_not_in(request_factory):
    methods = ['POST', 'DELETE', 'PUT']
    method = 'GET'
    wrapped = allow_methods(methods, mock_view)
    request = request_factory.generic(method, '/')
    assert wrapped(request).status_code == 403


def test_empty(request_factory):
    methods = []
    method = 'GET'
    wrapped = allow_methods(methods, mock_view)
    request = request_factory.generic(method, '/')
    assert wrapped(request).status_code == 403
