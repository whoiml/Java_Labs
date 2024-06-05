package bsu.rfe.java.group7.lab7.ravgeysh.varС1.ui;

import bsu.rfe.java.group7.lab7.ravgeysh.varС1.client.Authentication;

import javax.swing.*;
import java.awt.*;

public class LoginRegisterUI extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginRegisterUI() {
        super("Login and Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            byte[] pass = new byte[passwordChars.length];
            for (int i = 0; i < passwordChars.length; i++) {
                pass[i] = (byte) passwordChars[i];
            }

            String answer = Authentication.login(username, pass);
            if (answer.equals("invalid")) {
                JOptionPane.showMessageDialog(null, "login or password is incorrect");
            } else {
                SwingUtilities.invokeLater(MessagingFrame::new);
                dispose();
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            byte[] pass = new byte[passwordChars.length];
            for (int i = 0; i < passwordChars.length; i++) {
                pass[i] = (byte) passwordChars[i];
            }


            String answer = Authentication.register(username, pass);

            if (answer.equals("failure")) {
                JOptionPane.showMessageDialog(null, "Username exists");
            } else {
                JOptionPane.showMessageDialog(null, "Done");
            }

        });

        // Add buttons to the panel
        panel.add(loginButton);
        panel.add(registerButton);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }
}