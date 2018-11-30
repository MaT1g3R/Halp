from functools import partial, wraps
from typing import AnyStr, Dict, List

from django.core.exceptions import ValidationError
from django.http import JsonResponse
from django.utils.datetime_safe import datetime
from haversine import haversine
from option import Err, Ok, Result

from halp_backend import request_converter, user_converter
from halp_backend.models import Request, User
from halp_backend.typedefs import HttpError
from halp_backend.util import dict_filter, validate_int, validate_json, validate_json_string


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


def _help_validate(func, json_data, *args, **kwargs):
    if json_data.is_err:
        return Err(HttpError(400, json_data.unwrap_err()))
    dict_ = json_data.unwrap()
    return func(dict_, *args, **kwargs)


def require_json_validation(schema, func=None, *vargs, **vkwargs):
    """Decorator to validate JSON data"""
    if not func:
        return partial(require_json_validation, schema)

    @wraps(func)
    def wrapper(raw_json_data: Dict, *args, **kwargs):
        json_data = validate_json(raw_json_data, schema, *vargs, **vkwargs)
        return _help_validate(func, json_data, *args, **kwargs)

    return wrapper


def require_json_string_validation(schema, func=None, *vargs, **vkwargs):
    """Decorator to load and validate a string to JSON data"""
    if not func:
        return partial(require_json_string_validation, schema)

    @wraps(func)
    def wrapper(string: AnyStr, *args, **kwargs):
        json_data = validate_json_string(string, schema, *vargs, **vkwargs)
        return _help_validate(func, json_data, *args, **kwargs)

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
@require_json_string_validation({
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


@json_resposne(dict)
@require_json_string_validation({
    'type': 'object',
    'properties': {
        'bio': {'type': 'string'},
    },
    'additionalProperties': False,
    'required': ['bio']
})
def update_bio(valid_data: Dict, user: User) -> Result[Dict, HttpError]:
    user.bio = valid_data['bio']
    user.save()
    return Ok({'bio': user.bio})


@json_resposne(lambda lst: {'requests': lst})
@require_json_validation({
    'type': 'object',
    'properties': {
        'finished': {'type': 'boolean'},
        'assigned': {'type': 'boolean'},
        'starts_after': {'type': 'integer'},
        'radius': {'type': 'integer'},
        'lat': {'type': 'number', 'minimum': -90, 'maximum': 90},
        'long': {'type': 'number', 'minimum': -180, 'maximum': 180}
    },
    'additionalProperties': False,
    'dependencies': {
        'radius': ['lat', 'long'],
        'lat': ['long', 'radius'],
        'long': ['lat', 'radius']
    }
})
def get_reqeusts(valid_data: Dict, user: User) -> Result[List[Dict], HttpError]:
    if not valid_data:
        return Ok([request_converter.to_dict(r) for r in Request.objects.filter(customer=user)])

    radius = valid_data.get('radius')
    lat = valid_data.get('lat')
    long = valid_data.get('long')
    finished = valid_data.get('finished')
    assigned = valid_data.get('assigned')
    starts_after = valid_data.get('starts_after')
    if starts_after is not None:
        starts_after = datetime.fromtimestamp(starts_after)

    filters = dict_filter({
        'finished': finished,
        'start_time__gte': starts_after,
    })
    if assigned is not None:
        filters['assigned_to__isnull'] = not assigned

    request_query = Request.objects.filter(**filters)
    if radius is None or lat is None or long is None:
        return Ok([request_converter.to_dict(r) for r in request_query])
    return Ok([
        request_converter.to_dict(r) for r in request_query if
        haversine((lat, long), (r.latitude, r.longitude)) <= radius
    ])


@json_resposne(request_converter.to_dict)
@require_json_string_validation({
    'type': 'object',
    'properties': {
        'start_time': {'type': 'integer'},
        'duration': {'type': 'integer', 'minimum': 0},
        'latitude': {'type': 'number', 'minimum': -90, 'maximum': 90},
        'longitude': {'type': 'number', 'minimum': -180, 'maximum': 180},
        'description': {'type': 'string'}
    },
    'additionalProperties': False,
    'required': ['duration', 'latitude', 'longitude', 'description']
})
def create_request(valid_data: Dict, user: User):
    try:
        request = Request.objects.create_request(user, **valid_data)
    except ValidationError as e:
        return Err(HttpError(400, '\n'.join(e.messages)))
    else:
        return Ok(request)


def delete_request():
    pass
