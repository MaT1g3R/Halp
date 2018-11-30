import threading
from collections import deque
from datetime import timedelta

from django.db import models
from django.utils.datetime_safe import datetime
from haversine import haversine
from option import NONE, Option, Some


class RequestManager(models.Manager):
    def __init__(self):
        super().__init__()
        self.pending_requests = deque()
        self.paired_requests = {}
        self.request_mutex = threading.Lock()
        self.pair_mutex = threading.Lock()

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

    def find_worker(self, request) -> Option['User']:
        if request.id in self.paired_requests:
            with self.pair_mutex:
                worker = self.paired_requests[request.id]
                del self.paired_requests[request.id]
            request.assigned_to = worker
            request.save()
            return Some(worker)
        return NONE

    def find_job(
            self,
            worker,
            duration: int = None,
            radius=None,
            latitude=None,
            longitude=None
    ) -> Option['Request']:
        has_radius = not (radius is None or latitude is None or longitude is None)
        with self.request_mutex:
            for candidate in self.pending_requests:
                if duration is not None and candidate.duration.total_seconds() > duration:
                    continue
                if has_radius and haversine(
                        (candidate.latitude, candidate.longgitude),
                        (latitude, longitude)) > radius:
                    continue
                found = candidate
                self.pending_requests = deque(
                    filter(lambda r: r.id != found.id, self.pending_requests)
                )
                break
            else:
                return NONE
        with self.pair_mutex:
            self.paired_requests[found.id] = worker
        assert found is not None
        return Some(found)
