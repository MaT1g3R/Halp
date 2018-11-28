from django.http import HttpRequest, JsonResponse

from halp_backend import user_converter
from halp_backend.auth import require_auth
from halp_backend.models import User
from halp_backend.util import allow_methods


@allow_methods(methods=['GET'])
@require_auth
def profile(user: User, request: HttpRequest):
    user_id = request.GET.get('user_id')
    if user_id is None:
        return JsonResponse(user_converter.to_dict(user))

    try:
        user_id = int(user_id)
    except (TypeError, ValueError):
        return JsonResponse({'error': f'{user_id} is not a valid user ID'}, status=400)

    try:
        found_user = User.objects.get(id=user_id)
    except User.DoesNotExist:
        return JsonResponse({'error': f'User with ID {user_id} does not exist'}, status=400)

    return JsonResponse(user_converter.to_dict(found_user))
