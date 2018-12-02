#!/usr/bin/env bash
set -e
poetry run python manage.py check --fail-level WARNING --deploy
poetry run python manage.py makemigrations
poetry run python manage.py migrate
poetry run gunicorn halp.wsgi -b 0.0.0.0:8000
