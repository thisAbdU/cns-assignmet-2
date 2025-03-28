import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UnifiedEncryptionTool extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JComboBox<String> algorithmSelector;

    public UnifiedEncryptionTool() {
        setTitle("Unified Encryption/Decryption Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new BorderLayout());

        // Create algorithm selector
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectorPanel.add(new JLabel("Select Algorithm:"));
        algorithmSelector = new JComboBox<>(new String[]{"AES", "3DES", "OTP"});
        selectorPanel.add(algorithmSelector);
        add(selectorPanel, BorderLayout.NORTH);

        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add individual encryption GUIs as cards
        mainPanel.add(new AESEncryptionGUI().getContentPane(), "AES");
        mainPanel.add(new TripleDESEncryptionGUI().getContentPane(), "3DES");
        mainPanel.add(new OTPEncryptionGUI().getContentPane(), "OTP");

        add(mainPanel, BorderLayout.CENTER);

        // Add action listener to switch between cards
        algorithmSelector.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            cardLayout.show(mainPanel, selectedAlgorithm);
        });

        // Set default view
        cardLayout.show(mainPanel, "AES");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UnifiedEncryptionTool().setVisible(true);
        });
    }
}