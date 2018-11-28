from functools import partial, wraps

from django.http import JsonResponse
from option import Err, Ok, Result

from halp_backend import user_converter
from halp_backend.models import User
from halp_backend.typedefs import HttpError


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


@json_resposne(user_converter.to_dict)
def get_profile(user_id) -> Result[User, HttpError]:
    try:
        user_id = int(user_id)
    except (TypeError, ValueError):
        return Err(HttpError(400, f'{user_id} is not a valid user ID'))

    try:
        found_user = User.objects.get(id=user_id)
    except User.DoesNotExist:
        return Err(HttpError(400, f'User with ID {user_id} does not exist'))

    return Ok(found_user)
