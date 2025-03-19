# Cryptographic Applications Report
## Implementation of AES, 3DES, and OTP Encryption/Decryption Tools

### 1. Project Overview
This project implements three different cryptographic algorithms (AES, 3DES, and OTP) with graphical user interfaces for encryption and decryption operations. The applications are built using Java Swing for the frontend and Java Cryptography Extension (JCE) for cryptographic operations.

#### 1.1 Technologies Used
- **Programming Language**: Java
- **Frontend Framework**: Java Swing (GUI)
- **Cryptographic Libraries**: 
  - Java Cryptography Extension (JCE)
  - Java Security API
- **Build Tools**: Java Compiler (javac)

### 2. Implementation Details

#### 2.1 AES Encryption/Decryption
- **Algorithm**: AES (Advanced Encryption Standard)
- **Key Size**: 128 bits
- **Mode**: ECB (Electronic Code Book)
- **Padding**: PKCS5Padding
- **Key Generation**: Random key generation using KeyGenerator

```java
// Key Generation
KeyGenerator keyGen = KeyGenerator.getInstance("AES");
keyGen.init(128);
SecretKey secretKey = keyGen.generateKey();

// Encryption
cipher.init(Cipher.ENCRYPT_MODE, secretKey);
byte[] encryptedBytes = cipher.doFinal(inputText.getBytes());
```

#### 2.2 3DES Encryption/Decryption
- **Algorithm**: Triple DES (DESede)
- **Key Size**: 168 bits (24 bytes)
- **Mode**: CBC (Cipher Block Chaining)
- **Padding**: PKCS5Padding
- **Key Generation**: Password-based using MD5 hash

```java
// Key Generation from Password
MessageDigest md = MessageDigest.getInstance("MD5");
byte[] digestOfPassword = md.digest(password.getBytes("utf-8"));
byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

// Triple the key bytes for 3DES
for (int j = 0, k = 16; j < 8;) {
    keyBytes[k++] = keyBytes[j++];
}
```

#### 2.3 OTP Encryption/Decryption
- **Algorithm**: One-Time Pad
- **Key Size**: Matches input text length
- **Operation**: XOR-based encryption
- **Key Generation**: SecureRandom for key generation

```java
// Key Generation
currentKey = new byte[inputText.getBytes().length];
new SecureRandom().nextBytes(currentKey);

// Encryption/Decryption
for (int i = 0; i < inputBytes.length; i++) {
    encrypted[i] = (byte) (inputBytes[i] ^ currentKey[i]);
}
```

### 3. Security Considerations

#### 3.1 AES Implementation
- Uses ECB mode (Note: CBC or GCM would be more secure for production use)
- Implements proper padding (PKCS5Padding)
- Secure key generation using KeyGenerator
- Base64 encoding for key and ciphertext storage

#### 3.2 3DES Implementation
- Uses CBC mode for better security
- Password-based key derivation using MD5 (Note: PBKDF2 would be more secure)
- Fixed IV (Note: Random IV would be more secure)
- Proper padding implementation

#### 3.3 OTP Implementation
- True random key generation using SecureRandom
- Key length matches input length
- Perfect secrecy when key is truly random and used only once
- Hex encoding for key and ciphertext storage

### 4. User Interface Features

#### 4.1 Common Features Across All Applications
- Input text area for plaintext/ciphertext
- Key display area
- Output text area
- File save/load capabilities
- Error handling and user feedback

#### 4.2 Specific Features
- **AES**: Random key generation
- **3DES**: Password-based key generation
- **OTP**: Dynamic key generation based on input length

### 5. Usage Instructions

#### 5.1 AES Encryption/Decryption
1. Enter text in the input area
2. Click "Generate New Key" to create an AES key
3. Click "Encrypt" to encrypt the text
4. Click "Decrypt" to decrypt the text
5. Use "Save to File" and "Load from File" for file operations

#### 5.2 3DES Encryption/Decryption
1. Enter a password in the password field
2. Click "Generate Key from Password" to create a 3DES key
3. Enter text in the input area
4. Click "Encrypt" to encrypt the text
5. Click "Decrypt" to decrypt the text
6. Use "Save to File" and "Load from File" for file operations

#### 5.3 OTP Encryption/Decryption
1. Enter text in the input area
2. Click "Generate New Key" to create an OTP key
3. Click "Encrypt" to encrypt the text
4. Click "Decrypt" to decrypt the text

### 6. Compilation and Execution

```bash
# Compile the applications
javac AESEncryptionGUI.java
javac TripleDESEncryptionGUI.java
javac OTPEncryptionGUI.java

# Run the applications
java AESEncryptionGUI
java TripleDESEncryptionGUI
java OTPEncryptionGUI
```

### 7. Future Improvements
1. Implement CBC or GCM mode for AES
2. Use PBKDF2 for key derivation in 3DES
3. Add random IV generation for 3DES
4. Implement file encryption for large files
5. Add key exchange mechanisms
6. Implement proper key storage and management
7. Add input validation and sanitization
8. Implement proper error logging

### 8. Conclusion
The project successfully implements three different cryptographic algorithms with user-friendly interfaces. While the implementations are functional and demonstrate the concepts, they should be enhanced with additional security features for production use. The modular design allows for easy extension and modification of the cryptographic implementations.

---

*Note: This report is generated for educational purposes. The implementations are not intended for production use without proper security audits and enhancements.* 