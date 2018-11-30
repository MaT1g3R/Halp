.PHONY: lint test cov_html

lint:
	flake8

test:
	pytest --cov -vvv

cov_html:
	pytest --cov -vvv --cov-report=html
