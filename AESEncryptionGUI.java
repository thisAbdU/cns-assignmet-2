import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.crypto.*;
import java.nio.file.*;
import java.security.*;
import java.util.Base64;

public class AESEncryptionGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextArea keyTextArea;
    private SecretKey secretKey;
    private Cipher cipher;
    private final String ALGORITHM = "AES";
    private final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    public AESEncryptionGUI() {
        setTitle("AES Encryption/Decryption Tool");
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
        mainPanel.add(keyPanel);
        mainPanel.add(outputPanel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateKeyButton = new JButton("Generate New Key");
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
        generateKeyButton.addActionListener(e -> generateNewKey());
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());
        
        // Initialize cipher
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(this, "Error initializing cipher: " + e.getMessage());
        }
        
        // Generate initial key
        generateNewKey();
    }
    
    private void generateNewKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128);
            secretKey = keyGen.generateKey();
            keyTextArea.setText(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
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
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(inputText.getBytes());
            outputTextArea.setText(Base64.getEncoder().encodeToString(encryptedBytes));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
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
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            outputTextArea.setText(new String(decryptedBytes));
        } catch (IllegalArgumentException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
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
            new AESEncryptionGUI().setVisible(true);
        });
    }
}
