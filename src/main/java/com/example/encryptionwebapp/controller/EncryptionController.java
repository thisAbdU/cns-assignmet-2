package com.example.encryptionwebapp.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Base64;

@Controller
public class EncryptionController {

    @GetMapping("/")
    public String home() {
        return "redirect:/encrypt";
    }

    @GetMapping("/encrypt")
    public String showEncryptionPage() {
        return "encryption";
    }

    @PostMapping("/encrypt/aes")
    @ResponseBody
    public String encryptAES(@RequestParam String text, @RequestParam String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/encrypt/3des")
    @ResponseBody
    public String encrypt3DES(@RequestParam String text, @RequestParam String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "DESede");
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/encrypt/otp")
    @ResponseBody
    public String encryptOTP(@RequestParam String text, @RequestParam String key) {
        try {
            byte[] textBytes = text.getBytes();
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] encrypted = new byte[textBytes.length];
            for (int i = 0; i < textBytes.length; i++) {
                encrypted[i] = (byte) (textBytes[i] ^ keyBytes[i]);
            }
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
} 