import json
from functools import partial, wraps
from typing import AnyStr, Dict, List, Union

from django.http import HttpRequest, JsonResponse
from jsonschema import ValidationError, validate
from option import Err, NONE, Ok, Option, Result, Some


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


def validate_int(x) -> Option[int]:
    """
    Try to convert some value to a int

    Args:
        x: The value

    Returns:
        Some(int) if the conversion is successful
        NONE      otherwise
    """
    if isinstance(x, (str, int)):
        try:
            parsed_int = int(x)
        except (TypeError, ValueError):
            return NONE
        else:
            return Some(parsed_int)
    return NONE


def load_json(string: AnyStr) -> Union[Option[Dict], Option[List]]:
    """
    Try to load a JSON string

    Args:
        string: The string to be loaded

    Returns:
        Some(json) if the load is successful
        NONE       otherwise
    """
    try:
        js = json.loads(string)
    except (TypeError, json.JSONDecodeError):
        return NONE
    return Some(js)


def validate_json(json_data, schema: Dict, *args, **kwargs) -> Result[None, str]:
    """Validate a JSON value"""
    try:
        validate(json_data, schema, *args, **kwargs)
    except ValidationError as e:
        return Err(e.message)
    return Ok(None)


def validate_json_string(string: AnyStr, schema: Dict, *args, **kwargs) -> Result[None, str]:
    """Parse a JSON string and validate it"""
    parsed_json = load_json(string)
    if not parsed_json:
        return Err(f'{string} is not a valid json string')
    return validate_json(parsed_json.value, schema, *args, **kwargs)
