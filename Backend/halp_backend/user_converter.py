from typing import Dict

from halp_backend.models import User


def to_dict(user: User) -> Dict:
    """
    Convert a user to a python dict

    Args:
        user: The user

    Returns:
        The user as a python dict

    """
    return {
        'user_id': user.id,
        'first_name': user.first_name,
        'last_name': user.last_name,
        'bio': user.bio
    }
