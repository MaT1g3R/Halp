Halp Back end
=============

## API Documentation
API documentation can be found at: https://github.com/csc301-fall-2018/project-team-22/blob/master/deliverables/Artifacts/API.md

## Developing
- Requirements:
    - Python 3.7 or above
- Setting up the dev environment:
    ```bash
    $ python -m venv my_venv # Create and activate python virtual environment
    $ source my_venv/bin/activate
    $ python -m pip install -U poetry # Install dependencies
    $ poetry install
    $ python manage.py makemigrations # Database migrations
    $ python manage.py migrate
    ```
- Running tests:
    ```bash
    $ make test # or pytest -vvv --cov if you don't have make
    ```
- Running the development server:
    ```bash
    $ python manage.py runserver
    ```
    The development server will be served at `localhost:8000` by default
