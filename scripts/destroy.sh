#!/bin/bash

# SSH setupini
mkdir -p ~/.ssh
echo "$SSH_PRIVATE_KEY" | tr -d '\r' > ~/.ssh/id_rsa
chmod 600 ~/.ssh/id_rsa
ssh-keyscan -H $VM_IP >> ~/.ssh/known_hosts

# removini containerini
echo "Destroying deployment on $VM_IP..."
ssh -p $SSH_PORT $VM_USER@$VM_IP "docker-compose down -v"
