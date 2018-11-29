import threading
from collections import deque

from django.db import models


class RequestManager(models.Manager):
    def __init__(self):
        super().__init__()
        self.pending_requests = deque()
        self.request_mutex = threading.Lock()

    def create_request(
            self,
            customer,
            duration: int,
            latitude: float,
            longitude: float,
            description: str,
            start_time: int = None,
    ):
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

    def delete_request(self, request):
        with self.request_mutex:
            self.pending_requests = deque(
                filter(lambda r: r.id != request.id, self.pending_requests)
            )
