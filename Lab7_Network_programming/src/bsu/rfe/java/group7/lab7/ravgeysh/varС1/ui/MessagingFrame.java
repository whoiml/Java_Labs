package bsu.rfe.java.group7.lab7.ravgeysh.varС1.ui;

import bsu.rfe.java.group7.lab7.ravgeysh.varС1.client.MessageHandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessagingFrame extends JFrame {

    private final static Map<String, ChatTab> tabs = new ConcurrentHashMap<>();
    private final JTextField nicknameField;
    private final JTabbedPane tabbedPane;

    public MessagingFrame() {
        super("Chat Application");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ApplicationRunner.isRunning = false;
                dispose();
            }
        });

        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField();
        JButton startChatButton = new JButton("Start Chat");

        tabbedPane = new JTabbedPane();

        startChatButton.addActionListener(e -> {
            String nickname = nicknameField.getText();

            String answer = MessageHandler.find(nickname);

            if (answer.equals("found")) {
                ChatTab chatTab = new ChatTab(nickname);
                tabbedPane.addTab(nickname, chatTab);
                tabs.put(nickname, chatTab);
            }

        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(nicknameLabel, BorderLayout.WEST);
        inputPanel.add(nicknameField, BorderLayout.CENTER);
        inputPanel.add(startChatButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }

    public static void receiveMessage(String loginFrom, String message) {
        tabs.get(loginFrom).chatTextArea.append(
                loginFrom + ": " + message + System.lineSeparator()
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MessagingFrame::new);
    }
}

class ChatTab extends JPanel {
    public JTextArea chatTextArea;
    private final JTextField messageField;

    public ChatTab(String chatPartnerNickname) {
        setLayout(new BorderLayout());

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);
        add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.addActionListener(e -> {
            String message = messageField.getText();

            MessageHandler.sendMessageTo(chatPartnerNickname, message);

            chatTextArea.append("You: " + message + System.lineSeparator());
            messageField.setText("");
        });
        add(messageField, BorderLayout.SOUTH);
    }
}