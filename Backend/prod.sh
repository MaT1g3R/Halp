#!/usr/bin/env bash
set -e
python manage.py check --fail-level WARNING --deploy
python manage.py makemigrations
python manage.py migrate
gunicorn halp.wsgi
