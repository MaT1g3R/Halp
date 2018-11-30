import threading
from collections import deque
from datetime import timedelta

from django.db import models
from django.utils.datetime_safe import datetime


class RequestManager(models.Manager):
    def __init__(self):
        super().__init__()
        self.pending_requests = deque()
        self.request_mutex = threading.Lock()

    def create_request(
            self,
            customer,
            duration: timedelta,
            latitude,
            longitude,
            description: str,
            start_time: datetime = None,
    ):
        if isinstance(start_time, int):
            start_time = datetime.fromtimestamp(start_time)
        if isinstance(duration, int):
            duration = timedelta(seconds=duration)
        request = self.model(
            customer=customer,
            duration=duration,
            latitude=latitude,
            longitude=longitude,
            description=description,
            start_time=start_time
        )
        request.clean()
        request.save()
        if start_time is None:
            with self.request_mutex:
                self.pending_requests.append(request)
        return request

    def delete_request(self, request, using=None, keep_parents=False):
        request.delete(using, keep_parents)
        if request.start_time is None:
            with self.request_mutex:
                self.pending_requests = deque(
                    filter(lambda r: r.id != request.id, self.pending_requests)
                )
