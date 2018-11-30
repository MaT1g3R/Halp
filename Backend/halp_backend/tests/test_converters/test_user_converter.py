import pytest

from halp_backend import user_converter
from halp_backend.tests.conftest import generate_users

pytestmark = pytest.mark.django_db


def test_to_dict():
    for user, _ in generate_users(10):
        user_dict = user_converter.to_dict(user)
        assert user_dict['user_id'] == user.id
        assert user_dict['first_name'] == user.first_name
        assert user_dict['last_name'] == user.last_name
        assert user_dict['bio'] == user.bio
        assert isinstance(user_dict['bio'], str)
        for key in user_dict:
            assert key in ['user_id', 'first_name', 'last_name', 'bio']
