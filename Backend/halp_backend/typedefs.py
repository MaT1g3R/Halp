from typing import Dict, List, NamedTuple, Union


class HttpError(NamedTuple):
    status: int
    message: str = None


Json = Union[Dict, List]
