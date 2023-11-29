#!/bin/bash

# Set the URL of the server endpoint
URL="http://localhost:8081/api/logs/ingest"

# Define log data (adjust as needed)
LEVEL="info"
MESSAGE="Log message"
RESOURCE_ID="resource123"
TRACE_ID="trace123"
SPAN_ID="span123"
COMMIT="commit123"
PARENT_RESOURCE_ID="parentResource123"

# Number of requests to send in parallel
NUM_REQUESTS=2000

# Function to send a single POST request
send_request() {
  URL="http://localhost:8081/api/logs/ingest"

function generate_three_digit_number() {
    echo $((100 + RANDOM % 900))
}
  LEVELS=("info" "warning" "error" "debug" "fatal" "trace")
  MESSAGES=("Failed to connect to DB" "Connection established" "Query executed successfully" "Error in transaction" "Server restarted")
  #RESOURCE_IDS=("resource123" "resource456" "resource789")
  TRACES=("abc-xyz-123" "def-uvw-456" "ghi-pqr-789" "jkl-mno-012")
  #SPANS=("span123" "span456" "span789")
  COMMITS=("5e5342f" "a1b2c3d4" "e5f6g7h8" "i9j0k1l2" "a9f9e939c" "ds9wn324n32")
  PARENT_RESOURCE_IDS=("parentResource123" "parentResource456" "parentResource789")
  # Construct the JSON payload

  # Randomly select values from arrays
      LEVEL=${LEVELS[$RANDOM % ${#LEVELS[@]}]}
      MESSAGE=${MESSAGES[$RANDOM % ${#MESSAGES[@]}]}
      RESOURCE_ID="server-$(generate_three_digit_number)"
      TRACE_ID=${TRACES[$RANDOM % ${#TRACES[@]}]}
      SPAN_ID="span-$(generate_three_digit_number)"
      COMMIT=${COMMITS[$RANDOM % ${#COMMITS[@]}]}
      PARENT_RESOURCE_ID="server-$(generate_three_digit_number)"

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

  # Make the POST request and print the response
  curl -X POST -H "Content-Type: application/json" -d "${JSON_PAYLOAD}" "${URL}" -w "\n"
}

# Set the function as export so that it's available for xargs
export -f send_request

# Generate a sequence of numbers representing the number of requests
# shellcheck disable=SC2016
seq 1 $NUM_REQUESTS | xargs -I{} -P $NUM_REQUESTS bash -c 'send_request "$URL" "$LEVEL" "$MESSAGE" "$RESOURCE_ID" "$TRACE_ID" "$SPAN_ID" "$COMMIT" "$PARENT_RESOURCE_ID"'
