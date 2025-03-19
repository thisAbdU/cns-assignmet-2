import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.util.Arrays;

public class OTPEncryptionGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextArea keyTextArea;
    private byte[] currentKey;
    
    public OTPEncryptionGUI() {
        setTitle("OTP Encryption/Decryption Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Input Text:"), BorderLayout.NORTH);
        inputTextArea = new JTextArea(5, 40);
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
        
        // Key panel
        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.add(new JLabel("Key (in hex):"), BorderLayout.NORTH);
        keyTextArea = new JTextArea(3, 40);
        keyTextArea.setEditable(false);
        keyPanel.add(new JScrollPane(keyTextArea), BorderLayout.CENTER);
        
        // Output panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(new JLabel("Output Text:"), BorderLayout.NORTH);
        outputTextArea = new JTextArea(5, 40);
        outputTextArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
        
        // Add panels to main panel
        mainPanel.add(inputPanel);
        mainPanel.add(keyPanel);
        mainPanel.add(outputPanel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateKeyButton = new JButton("Generate New Key");
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        
        buttonPanel.add(generateKeyButton);
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        
        // Add components to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add button listeners
        generateKeyButton.addActionListener(e -> generateNewKey());
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
        
        // Generate initial key
        generateNewKey();
    }
    
    private void generateNewKey() {
        String inputText = inputTextArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some text first!");
            return;
        }
        
        currentKey = new byte[inputText.getBytes().length];
        new SecureRandom().nextBytes(currentKey);
        keyTextArea.setText(bytesToHex(currentKey));
    }
    
    private void encrypt() {
        if (currentKey == null) {
            JOptionPane.showMessageDialog(this, "Please generate a key first!");
            return;
        }
        
        String inputText = inputTextArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some text to encrypt!");
            return;
        }
        
        byte[] inputBytes = inputText.getBytes();
        if (inputBytes.length != currentKey.length) {
            JOptionPane.showMessageDialog(this, "Input text length must match key length!");
            return;
        }
        
        byte[] encrypted = new byte[inputBytes.length];
        for (int i = 0; i < inputBytes.length; i++) {
            encrypted[i] = (byte) (inputBytes[i] ^ currentKey[i]);
        }
        
        outputTextArea.setText(bytesToHex(encrypted));
    }
    
    private void decrypt() {
        if (currentKey == null) {
            JOptionPane.showMessageDialog(this, "Please generate a key first!");
            return;
        }
        
        String inputText = inputTextArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter encrypted text to decrypt!");
            return;
        }
        
        try {
            byte[] inputBytes = hexToBytes(inputText);
            if (inputBytes.length != currentKey.length) {
                JOptionPane.showMessageDialog(this, "Input text length must match key length!");
                return;
            }
            
            byte[] decrypted = new byte[inputBytes.length];
            for (int i = 0; i < inputBytes.length; i++) {
                decrypted[i] = (byte) (inputBytes[i] ^ currentKey[i]);
            }
            
            outputTextArea.setText(new String(decrypted));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid hex input!");
        }
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    
    private byte[] hexToBytes(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string");
        }
        
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OTPEncryptionGUI().setVisible(true);
        });
    }
}