#!/bin/bash
for file in deliverables/*.md; do
    pandoc $file -o build/${file##*/}.pdf;
done
