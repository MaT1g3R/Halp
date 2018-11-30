import json
from functools import partial, wraps
from typing import AnyStr, Callable, Dict, Optional

from django.http import HttpRequest, JsonResponse
from jsonschema import ValidationError, validate
from option import Err, NONE, Ok, Option, Result, Some

from halp_backend.typedefs import Json


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


def load_json(string: AnyStr) -> Option[Json]:
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


def validate_json(json_data: Json, schema: Dict, *args, **kwargs) -> Result[Json, str]:
    """Validate a JSON value"""
    try:
        validate(json_data, schema, *args, **kwargs)
    except ValidationError as e:
        return Err(e.message)
    return Ok(json_data)


def validate_json_string(string: AnyStr, schema: Dict, *args, **kwargs) -> Result[Json, str]:
    """Parse a JSON string and validate it"""
    parsed_json = load_json(string)
    if not parsed_json:
        return Err(f'{string} is not a valid json string')
    return validate_json(parsed_json.value, schema, *args, **kwargs)


def dict_filter(d: dict, filter_: Optional[Callable] = None) -> dict:
    """
    Filter a dictionary by a given filter function
    Args:
        d: The dict to filter
        filter_:
            The filter function, takes the dict key and values as arguments,
            defaults to checking for both the key and value to be not None
    Returns:
        The filtered dict
    """
    filter_func = filter_ if filter_ else lambda key, val: key is not None and val is not None
    return {key: val for key, val in d.items() if filter_func(key, val)}


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


def validate_float(x) -> Option[float]:
    try:
        f = float(x)
    except (TypeError, ValueError):
        return NONE
    return Some(f)


def validate_bool(x) -> Option[bool]:
    if str(x).lower() == 'true':
        return Some(True)
    elif str(x).lower() == 'false':
        return Some(False)
    return NONE


def convert_dict_types(dict_, types) -> Result[Dict, str]:
    res = {}
    for key, val in dict_.items():
        if key in types:
            converted = types[key](val)
            if not converted:
                return Err(f'`{val}` is not a valid value for `{key}`')
            else:
                res[key] = converted.unwrap()
        else:
            res[key] = val
    return Ok(res)
