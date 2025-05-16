#!/bin/bash

# Generate private key and certificate
openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days 365 -nodes -subj "/C=US/ST=State/L=City/O=Organization/CN=localhost"

# Convert to PKCS12 keystore
openssl pkcs12 -export -in cert.pem -inkey key.pem -out src/main/resources/keystore.p12 -name mysslcert -password pass:yourpassword

# Clean up temporary files
rm key.pem cert.pem

echo "Keystore generated successfully at src/main/resources/keystore.p12" 