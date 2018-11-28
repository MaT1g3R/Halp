from django.http import HttpRequest
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
