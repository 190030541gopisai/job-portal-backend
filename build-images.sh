#!/bin/bash

# Build config-server
echo "Building config-server..."
docker build -t gopi/job-portal-config-server:latest \
  -f cloud/job-portal-config-server/Dockerfile .

# Build service-registry
echo "Building service-registry..."
docker build -t gopi/job-portal-service-registry:latest \
  -f cloud/job-portal-service-registry/Dockerfile .

echo "Done! Images built."
