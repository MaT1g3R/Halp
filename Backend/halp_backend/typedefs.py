from typing import NamedTuple


class HttpError(NamedTuple):
    status: int
    message: str = None
