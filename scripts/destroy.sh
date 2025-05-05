#!/bin/bash
#Author: MJ
#Date: 05/05/2025

set -euo pipefail
LOG_FILE="destroy.log"

log() {
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" | tee -a "$LOG_FILE"
}

main() {
  log "stopping containers!"
  sudo docker compose down -v
  log "dockerini ripperoni (i am going crazy)"
}

main "$@"