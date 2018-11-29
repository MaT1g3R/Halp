from typing import Dict

from halp_backend import response_converter, user_converter
from halp_backend.models import Request


def to_dict(request: Request) -> Dict:
    start_time = request.start_time
    assigned_to = request.assigned_to
    return {
        'request_id': request.id,
        'customer': user_converter.to_dict(request.customer),
        'start_time': None if start_time is None else int(start_time.timestamp()),
        'duration': int(request.duration.total_seconds()),
        'latitude': float(request.latitude),
        'longitude': float(request.longitude),
        'finished': request.finished,
        'description': request.description,
        'assigned_to': None if assigned_to is None else user_converter.to_dict(assigned_to),
        'responses': [
            response_converter.to_dict(response) for response in request.response_set.all()
        ]
    }
