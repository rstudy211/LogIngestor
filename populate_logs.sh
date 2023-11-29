#!/bin/bash

# Set the URL of the server endpoint
URL="http://localhost:8081/api/logs/ingest"

# Define arrays for log data attributes
LEVELS=("info" "warning" "error" "debug" "fatal" "trace")
MESSAGES=("Failed to connect to DB" "Connection established" "Query executed successfully" "Error in transaction" "Server restarted")
#RESOURCE_IDS=("resource123" "resource456" "resource789")
TRACES=("abc-xyz-123" "def-uvw-456" "ghi-pqr-789" "jkl-mno-012")
#SPANS=("span123" "span456" "span789")
COMMITS=("5e5342f" "a1b2c3d4" "e5f6g7h8" "i9j0k1l2""a9f9e939c""ds9wn324n32")
PARENT_RESOURCE_IDS=("parentResource123" "parentResource456" "parentResource789")

# counter
# shellcheck disable=SC1068
count = 1
#parallel_count=5  # Adjust as needed


function generate_three_digit_number() {
    echo $((100 + RANDOM % 900))
}

# Number of times to run the script

function cleanup() {
    echo "Script interrupted. Cleaning up..."
    exit 1
}

# Trap Ctrl+C and call the cleanup function
trap cleanup INT
# Loop to run the script multiple times
while true; do
    echo "   Running script for iteration $count"
    ((count++))
    # Randomly select values from arrays
    LEVEL=${LEVELS[$RANDOM % ${#LEVELS[@]}]}
    MESSAGE=${MESSAGES[$RANDOM % ${#MESSAGES[@]}]}
    RESOURCE_ID="server-$(generate_three_digit_number)"
    TRACE_ID=${TRACES[$RANDOM % ${#TRACES[@]}]}
    SPAN_ID="span-$(generate_three_digit_number)"
    COMMIT=${COMMITS[$RANDOM % ${#COMMITS[@]}]}
    PARENT_RESOURCE_ID="server-$(generate_three_digit_number)"
    # Construct the JSON payload
    JSON_PAYLOAD='{
      "level": "'"${LEVEL}"'",
      "message": "'"${MESSAGE}"'",
      "resourceId": "'"${RESOURCE_ID}"'",
      "timestamp": "'"$(date -u +"%Y-%m-%dT%H:%M:%SZ")"'",
      "traceId": "'"${TRACE_ID}"'",
      "spanId": "'"${SPAN_ID}"'",
      "commit": "'"${COMMIT}"'",
      "metaData": {
        "parentResourceId": "'"${PARENT_RESOURCE_ID}"'"
      }
    }'

    # Make the POST request
    curl -X POST -H "Content-Type: application/json" -d "${JSON_PAYLOAD}" "${URL}"

    # Sleep for a short duration between requests (adjust as needed)
#    sleep 1
done
