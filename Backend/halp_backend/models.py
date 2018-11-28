from django.contrib.auth.models import AbstractUser
from django.db import models

from halp_backend.user_manager import EmailUserManager


class User(AbstractUser):
    username = None
    email = models.EmailField(unique=True, null=True)
    bio = models.TextField(default="")

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []
    objects = EmailUserManager()

    def __str__(self):
        return self.email

    @property
    def full_name(self):
        return f'{self.first_name} {self.last_name}'


class Request(models.Model):
    start_time = models.DateTimeField(null=True)
    duration = models.DurationField()
    latitude = models.DecimalField(max_digits=10, decimal_places=8)
    longitude = models.DecimalField(max_digits=11, decimal_places=8)
    finished = models.BooleanField(default=False)
    description = models.TextField()


class RequestCustomer(models.Model):
    customer = models.ForeignKey(User, models.CASCADE)
    request = models.ForeignKey(Request, models.CASCADE)

    class Meta:
        unique_together = ('customer', 'request')


class RequestWorker(models.Model):
    worker = models.ForeignKey(User, models.CASCADE)
    request = models.ForeignKey(Request, models.CASCADE)

    class Meta:
        unique_together = ('worker', 'request')


class Response(models.Model):
    worker = models.ForeignKey(User, models.CASCADE)
    request = models.ForeignKey(Request, models.CASCADE)
    comment = models.TextField()

    class Meta:
        unique_together = ('worker', 'request')
