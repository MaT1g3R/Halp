SHELL := /bin/bash

.PHONY: list no_targets__ docs clean test emulator

list:
	@sh -c "$(MAKE) -p no_targets__ | \
		awk -F':' '/^[a-zA-Z0-9][^\$$#\/\\t=]*:([^=]|$$)/ {\
			split(\$$1,A,/ /);for(i in A)print A[i]\
	}' | grep -v '__\$$' | grep -v 'make\[1\]' | grep -v 'Makefile' | sort"

no_targets__:

test: emulator
	./scripts/test.sh

emulator: ; ./scripts/emulator.sh

build: ; mkdir build

docs: build
	./scripts/docs.sh

clean:
	-rm -rf build/*

