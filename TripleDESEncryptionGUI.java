import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.*;
import java.security.*;
import java.util.Base64;
import java.util.Arrays;

public class TripleDESEncryptionGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextArea keyTextArea;
    private JPasswordField passwordField;
    private SecretKey secretKey;
    private Cipher cipher;
    private IvParameterSpec iv;
    private final String ALGORITHM = "DESede";
    private final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";
    
    public TripleDESEncryptionGUI() {
        setTitle("3DES Encryption/Decryption Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Input Text:"), BorderLayout.NORTH);
        inputTextArea = new JTextArea(5, 40);
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
        
        // Password panel
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(new JLabel("Password:"), BorderLayout.NORTH);
        passwordField = new JPasswordField();
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        
        // Key panel
        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.add(new JLabel("Key (Base64):"), BorderLayout.NORTH);
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
        mainPanel.add(passwordPanel);
        mainPanel.add(keyPanel);
        mainPanel.add(outputPanel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateKeyButton = new JButton("Generate Key from Password");
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JButton saveButton = new JButton("Save to File");
        JButton loadButton = new JButton("Load from File");
        
        buttonPanel.add(generateKeyButton);
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        
        // Add components to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add button listeners
        generateKeyButton.addActionListener(e -> generateKeyFromPassword());
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());
        
        // Initialize cipher and IV
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            iv = new IvParameterSpec(new byte[8]); // 8 bytes for DESede
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(this, "Error initializing cipher: " + e.getMessage());
        }
    }
    
    private void generateKeyFromPassword() {
        String password = new String(passwordField.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a password!");
            return;
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(password.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            
            // Triple the key bytes for 3DES
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }
            
            secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            keyTextArea.setText(Base64.getEncoder().encodeToString(keyBytes));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating key: " + e.getMessage());
        }
    }
    
    private void encrypt() {
        if (secretKey == null) {
            JOptionPane.showMessageDialog(this, "Please generate a key first!");
            return;
        }
        
        String inputText = inputTextArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some text to encrypt!");
            return;
        }
        
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptedBytes = cipher.doFinal(inputText.getBytes("utf-8"));
            outputTextArea.setText(Base64.getEncoder().encodeToString(encryptedBytes));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during encryption: " + e.getMessage());
        }
    }
    
    private void decrypt() {
        if (secretKey == null) {
            JOptionPane.showMessageDialog(this, "Please generate a key first!");
            return;
        }
        
        String inputText = inputTextArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter encrypted text to decrypt!");
            return;
        }
        
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(inputText);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            outputTextArea.setText(new String(decryptedBytes, "UTF-8"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during decryption: " + e.getMessage());
        }
    }
    
    private void saveToFile() {
        try {
            String outputText = outputTextArea.getText();
            if (outputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No text to save!");
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                Path filePath = fileChooser.getSelectedFile().toPath();
                Files.write(filePath, outputText.getBytes());
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }
    
    private void loadFromFile() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Path filePath = fileChooser.getSelectedFile().toPath();
                String content = new String(Files.readAllBytes(filePath));
                inputTextArea.setText(content);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TripleDESEncryptionGUI().setVisible(true);
        });
    }
}