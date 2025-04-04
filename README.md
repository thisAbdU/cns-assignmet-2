# Cryptographic Applications Suite

A comprehensive suite of cryptographic applications implementing AES, 3DES, OTP, and RSA encryption/decryption algorithms with user-friendly graphical interfaces.

## Features

- **AES Encryption/Decryption**
  - 128-bit key size
  - ECB mode with PKCS5Padding
  - Random key generation
  - File operations support

- **3DES Encryption/Decryption**
  - 168-bit key size
  - CBC mode with PKCS5Padding
  - Password-based key generation
  - File operations support

- **OTP (One-Time Pad) Encryption/Decryption**
  - True random key generation
  - Perfect secrecy implementation
  - Dynamic key length matching
  - Hex encoding support

- **RSA Encryption/Decryption**
  - 2048-bit key size
  - PKCS1Padding
  - OpenSSL key generation
  - File operations support

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- OpenSSL (for RSA key generation)
- Basic understanding of cryptographic concepts

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd cns-assignmet-2
```

2. Generate RSA keys (required for RSA operations):
```bash
chmod +x generate_rsa_keys.sh
./generate_rsa_keys.sh
```

3. Compile all Java files:
```bash
javac AESEncryptionGUI.java
javac TripleDESEncryptionGUI.java
javac OTPEncryptionGUI.java
javac RSAEncryptionGUI.java
```

## Usage

### AES Encryption/Decryption
```bash
java AESEncryptionGUI
```
1. Enter text in the input area
2. Click "Generate New Key" to create an AES key
3. Click "Encrypt" to encrypt the text
4. Click "Decrypt" to decrypt the text
5. Use "Save to File" and "Load from File" for file operations

### 3DES Encryption/Decryption
```bash
java TripleDESEncryptionGUI
```
1. Enter a password in the password field
2. Click "Generate Key from Password" to create a 3DES key
3. Enter text in the input area
4. Click "Encrypt" to encrypt the text
5. Click "Decrypt" to decrypt the text
6. Use "Save to File" and "Load from File" for file operations

### OTP Encryption/Decryption
```bash
java OTPEncryptionGUI
```
1. Enter text in the input area
2. Click "Generate New Key" to create an OTP key
3. Click "Encrypt" to encrypt the text
4. Click "Decrypt" to decrypt the text

### RSA Encryption/Decryption
```bash
java RSAEncryptionGUI
```
1. Click "Load RSA Keys" to load the generated keys
2. Enter text in the input area
3. Click "Encrypt" to encrypt the text
4. Click "Decrypt" to decrypt the text
5. Use "Save to File" and "Load from File" for file operations

## Security Considerations

### AES
- Uses ECB mode (Note: CBC or GCM would be more secure for production use)
- Implements proper padding (PKCS5Padding)
- Secure key generation using KeyGenerator
- Base64 encoding for key and ciphertext storage

### 3DES
- Uses CBC mode for better security
- Password-based key derivation using MD5 (Note: PBKDF2 would be more secure)
- Fixed IV (Note: Random IV would be more secure)
- Proper padding implementation

### OTP
- True random key generation using SecureRandom
- Key length matches input length
- Perfect secrecy when key is truly random and used only once
- Hex encoding for key and ciphertext storage

### RSA
- 2048-bit key size
- PKCS1Padding
- Secure key generation using OpenSSL
- Proper key loading and parsing

## File Structure

```
cns-assignmet-2/
├── AESEncryptionGUI.java
├── TripleDESEncryptionGUI.java
├── OTPEncryptionGUI.java
├── RSAEncryptionGUI.java
├── generate_rsa_keys.sh
├── .gitignore
├── README.md
├── private_key.pem    (generated)
└── public_key.pem     (generated)
```

## Future Improvements

1. Implement CBC or GCM mode for AES
2. Use PBKDF2 for key derivation in 3DES
3. Add random IV generation for 3DES
4. Implement file encryption for large files
5. Add key exchange mechanisms
6. Implement proper key storage and management
7. Add input validation and sanitization
8. Implement proper error logging

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Java Cryptography Extension (JCE)
- OpenSSL
- Java Swing for GUI implementation

## Disclaimer

This project is for educational purposes only. The implementations are not intended for production use without proper security audits and enhancements.

## Contact

For any questions or suggestions, please open an issue in the repository. 