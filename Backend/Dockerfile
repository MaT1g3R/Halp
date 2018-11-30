FROM python:3.7.1-stretch
MAINTAINER MaT1g3R

ENV PYTHONUNBUFFERED 1
ENV DJANGO_SETTINGS_MODULE halp.prod_settings

RUN mkdir /code
WORKDIR /code
ADD . /code/

RUN pip install -U pip poetry
RUN pip --version
RUN poetry --version
RUN poetry install --no-dev --no-interaction

CMD ["/code/prod.sh"]
