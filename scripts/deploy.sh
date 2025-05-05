#!/bin/bash
# Author: MJ
# Date: 05/05/2025

set -euo pipefail
LOG_FILE="deploy.log"

log() {
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" | tee -a "$LOG_FILE"
}

main() {
  log "deploynini composini"

  if command -v docker-compose &>/dev/null; then
    COMPOSE="docker-compose"
  else
    COMPOSE="docker compose"
  fi

  log "stop container"
  $COMPOSE down || true

  log "detahced mode"
  $COMPOSE up -d

  log "Deploynini completini successfullini."
}

main "$@"