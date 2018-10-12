.PHONY: docs clean
docs: build
	./scripts/docs.sh

clean:
	-rm -rf build/*

build: ; mkdir build
