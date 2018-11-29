from halp_backend import user_converter
from halp_backend.models import Response


def to_dict(response: Response):
    return {
        'response_id': response.id,
        'worker': user_converter.to_dict(response.worker),
        'comment': response.comment
    }
