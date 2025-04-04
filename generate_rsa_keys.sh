#!/bin/bash

# Generate 2048-bit RSA private key
echo "Generating 2048-bit RSA private key..."
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048

# Extract the public key
echo "Extracting public key..."
openssl rsa -in private_key.pem -pubout -out public_key.pem

# Verify the generated keys
echo "Verifying private key..."
openssl rsa -in private_key.pem -text -noout

echo "Verifying public key..."
openssl rsa -in public_key.pem -pubin -text -noout

echo "RSA key generation complete!"
echo "Private key saved to: private_key.pem"
echo "Public key saved to: public_key.pem" 