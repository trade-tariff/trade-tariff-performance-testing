#/bin/bash
set -eo pipefail

mkdir -p ./target
docker build -t tariff-performance-tests .
docker run --rm -it \
	-v ./target:/app/target:z \
	-e PERFTESTURL="${PERFTESTURL:-https://staging.trade-tariff.service.gov.uk}" \
	tariff-performance-tests "$@"
