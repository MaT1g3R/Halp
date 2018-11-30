from django.http import HttpRequest, JsonResponse

from halp_backend import controller, user_converter
from halp_backend.auth import require_auth
from halp_backend.models import User
from halp_backend.util import (
    allow_methods, convert_dict_types, validate_bool, validate_float, validate_int,
)


@allow_methods(methods={'GET'})
@require_auth
def profile(user: User, request: HttpRequest):
    user_id = request.GET.get('user_id')
    if user_id is None:
        return JsonResponse(user_converter.to_dict(user))
    return controller.get_profile(user_id)


@allow_methods(methods=['POST'])
def create_user(reqeust: HttpRequest):
    return controller.create_user(reqeust.body)


@allow_methods(methods=['POST'])
@require_auth
def update_bio(user: User, request: HttpRequest):
    return controller.update_bio(request.body, user)


@allow_methods(methods=['GET', 'POST', 'DELETE'])
@require_auth
def request_view(user: User, request: HttpRequest):
    if request.method == 'GET':
        get_data = convert_dict_types(request.GET, {
            'finished': validate_bool,
            'assigned': validate_bool,
            'starts_after': validate_int,
            'radius': validate_int,
            'lat': validate_float,
            'long': validate_float
        })
        if get_data:
            return controller.get_reqeusts(get_data.unwrap(), user)
        return JsonResponse(status=400, data={'error': get_data.unwrap_err()})
    elif request.method == 'POST':
        return controller.create_request(request.body, user)
    else:
        request_id = request.GET.get('request_id')
        if request_id is None:
            return JsonResponse(status=400, data={'error': 'you must provide a request_id'})
        return controller.delete_request(request_id, user)
