from django.http import HttpRequest, JsonResponse

from halp_backend import controller, user_converter
from halp_backend.auth import require_auth
from halp_backend.models import User
from halp_backend.util import allow_methods


@allow_methods(methods={'GET'})
@require_auth
def profile(user: User, request: HttpRequest):
    user_id = request.GET.get('user_id')
    if user_id is None:
        return JsonResponse(user_converter.to_dict(user))
    return controller.get_profile(user_id)
