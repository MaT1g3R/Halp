from django.contrib.auth.models import AbstractUser
from django.core.exceptions import ValidationError
from django.db import models
from django.utils.translation import gettext_lazy as _

from halp_backend.request_manager import RequestManager
from halp_backend.user_manager import EmailUserManager


class User(AbstractUser):
    username = None
    email = models.EmailField(unique=True, null=True)
    bio = models.TextField(default="")

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['first_name', 'last_name']
    objects = EmailUserManager()

    def __str__(self):
        return self.email

    @property
    def full_name(self):
        return f'{self.first_name} {self.last_name}'


class Request(models.Model):
    start_time = models.DateTimeField(null=True, default=None)
    customer = models.ForeignKey(User, models.CASCADE, related_name='reqeust_customer')
    assigned_to = models.ForeignKey(User, models.SET_NULL, null=True, default=None,
                                    related_name='reqeust_worker')
    duration = models.DurationField()
    latitude = models.DecimalField(max_digits=10, decimal_places=8)
    longitude = models.DecimalField(max_digits=11, decimal_places=8)
    finished = models.BooleanField(default=False)
    description = models.TextField()
    title = models.TextField()

    objects = RequestManager()

    def clean(self):
        if self.customer == self.assigned_to:
            raise ValidationError(_('Customer cannot be assigned to their own request.'))


class Response(models.Model):
    worker = models.ForeignKey(User, models.CASCADE)
    request = models.ForeignKey(Request, models.CASCADE)
    comment = models.TextField()

    class Meta:
        unique_together = ('worker', 'request')
