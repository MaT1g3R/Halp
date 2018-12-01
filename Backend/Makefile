.PHONY: lint test cov_html migrate

lint:
	flake8

test:
	pytest --cov -vvv

cov_html:
	pytest --cov -vvv --cov-report=html

migrate:
	python manage.py makemigrations
	python manage.py migrate
