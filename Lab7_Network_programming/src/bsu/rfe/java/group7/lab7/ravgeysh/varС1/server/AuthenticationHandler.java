package bsu.rfe.java.group7.lab7.ravgeysh.varС1.server;

import java.io.IOException;

public class AuthenticationHandler {

    public static void register(String login, String password, Connections connections) {
        try {
            if (Main.authPassword.containsKey(login)) {
                connections.sendStream.writeObject("failure");
                return;
            }
            Main.authPassword.put(login, password);
            connections.sendStream.writeObject("success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void login(String login, String pass, Connections connections) {
        try {
            String realPass = Main.authPassword.get(login);
            if (!realPass.equals(pass)) {
                connections.sendStream.writeObject("invalid");
            } else {
                connections.sendStream.writeObject("valid");
                int port = connections.socket.getPort();
                Main.portLogin.put(port, login);
                Main.loginPort.put(login, port);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}