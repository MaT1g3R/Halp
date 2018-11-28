from functools import partial, wraps

from django.http import HttpRequest, JsonResponse
from option import Option


def get_http_header(reqeust: HttpRequest, header: str) -> Option[str]:
    """
    Get an http header from a http request.

    Args:
        reqeust: The http request
        header:  The header name

    Returns:
        Some(header) if the header exists
        NONE         otherwise

    """
    return Option.maybe(reqeust.META.get(f'HTTP_{header.upper()}'))


def allow_methods(methods, view=None):
    """
    Decorator to allow a list of methods for a view

    Args:
        methods: The list of methods to allow
        view:    The view
    """
    if not view:
        return partial(allow_methods, methods)

    @wraps(view)
    def wrapper(request):
        if request.method not in methods:
            return JsonResponse({'error': f'{request.method} method is not supported'}, status=403)
        return view(request)

    return wrapper
