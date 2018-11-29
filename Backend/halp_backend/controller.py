from functools import partial, wraps
from typing import AnyStr, Dict

from django.http import JsonResponse
from option import Err, Ok, Result

from halp_backend import user_converter
from halp_backend.models import User
from halp_backend.typedefs import HttpError
from halp_backend.util import validate_int, validate_json_string


def json_resposne(success_callback, func=None):
    """
    Decorator to convert a function that returns a result to
    returning a JsonResponse

    Args:
        success_callback: Callback to convert a Ok to a python dict
        func:             The wrapped function
    """
    if not func:
        return partial(json_resposne, success_callback)

    @wraps(func)
    def wrapper(*args, **kwargs) -> JsonResponse:
        return func(*args, **kwargs) \
            .map(lambda ok: JsonResponse(success_callback(ok))) \
            .unwrap_or_else(lambda err: JsonResponse({'error': err.message}, status=err.status))

    return wrapper


def require_json_validation(schema, func=None, *vargs, **vkwargs):
    """Decorator to load and validate a string to JSON data"""
    if not func:
        return partial(require_json_validation, schema)

    @wraps(func)
    def wrapper(string: AnyStr, *args, **kwargs):
        json_data = validate_json_string(string, schema, *vargs, **vkwargs)
        if json_data.is_err:
            return Err(HttpError(400, json_data.unwrap_err()))
        dict_ = json_data.unwrap()
        return func(dict_, *args, **kwargs)

    return wrapper


@json_resposne(user_converter.to_dict)
def get_profile(user_id) -> Result[User, HttpError]:
    parsed_id = validate_int(user_id)
    if not parsed_id:
        return Err(HttpError(400, f'{user_id} is not a valid user ID'))
    user_id = parsed_id.value

    try:
        found_user = User.objects.get(id=user_id)
    except (User.DoesNotExist, OverflowError):
        return Err(HttpError(400, f'User with ID {user_id} does not exist'))

    return Ok(found_user)


@json_resposne(user_converter.to_dict)
@require_json_validation({
    'type': 'object',
    'properties': {
        'email': {'type': 'string', 'minLength': 1},
        'password': {'type': 'string', 'minLength': 1},
        'first_name': {'type': 'string', 'minLength': 1},
        'last_name': {'type': 'string', 'minLength': 1},
    },
    'additionalProperties': False,
    'required': ['email', 'password', 'first_name', 'last_name']
})
def create_user(valid_data: Dict) -> Result[User, HttpError]:
    if User.objects.filter(email=valid_data['email']).exists():
        return Err(HttpError(400, f'User with email {valid_data["email"]} already exists'))
    new_user = User.objects.create_user(**valid_data)
    new_user.save()
    return Ok(new_user)
