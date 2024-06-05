package bsu.rfe.java.group7.lab7.ravgeysh.varС1.server;

import java.io.IOException;

public class MessageHandler extends Thread {

    @Override
    public void run() {
        while (Main.isRunning) {
            for (var user : Main.connectionsMapping.entrySet()) {
                try {
                    if (user.getValue().receiveStream.available() != 0) {
                        //skip one byte 'cause in java there is no non-blocking
                        //object stream, so client sends some byte
                        //to indicate that he want to send something
                        user.getValue().receiveStream.skipBytes(1);
                        String action = (String) user.getValue()
                                .receiveStream
                                .readObject();
                        handleAction(user.getValue(), action);
                    }
                } catch (IOException e) {
                    System.out.println("some IOException has occurred MessageHandler.class line 23");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("something wrong have been sent");
                }
            }
        }
    }

    private void handleAction(Connections connections, String action) throws IOException, ClassNotFoundException {
        switch (action) {
            case "register:" -> {
                String login = (String) connections.receiveStream.readObject();
                String pass = (String) connections.receiveStream.readObject();
                AuthenticationHandler.register(login, pass, connections);
            }
            case "disconnect:" -> disconnect(connections.socket.getPort());
            case "login:" -> {
                String login = (String) connections.receiveStream.readObject();
                String pass = (String) connections.receiveStream.readObject();
                AuthenticationHandler.login(login, pass, connections);
            }
            case "find:" -> {
                String loginToFind = (String) connections.receiveStream.readObject();
                if (Main.loginPort.containsKey(loginToFind)) {
                    connections.sendStream.writeObject("found");
                } else {
                    connections.sendStream.writeObject("notfound");
                }
            }
            case "send:" -> {
                String login = (String) connections.receiveStream.readObject();
                String message = (String) connections.receiveStream.readObject();
                handleMessageSending(login, message, connections);
                System.out.println("message: " + message + " to login: " + login);
            }
            default -> {
                String login = Main.portLogin.get(connections.socket.getPort());
                System.out.println(action + " from: " + login);
            }

        }
    }

    private static void handleMessageSending(
            String login,
            String message,
            Connections connections
    ) {
        String loginFrom = Main.portLogin.get(connections.socket.getPort());
        int userToPort = Main.loginPort.get(login);
        Connections recipientConnection = Main.connectionsMapping.get(userToPort);
        System.out.println("resolved all endpoints, trying to send a message");
        sendMessageFrom(loginFrom, message, recipientConnection);
    }

    private void disconnect(Integer port) {
        String login = Main.portLogin.get(port);
        System.out.printf("User %s disconnected!", login);
        System.out.println();
        Main.connectionsMapping.remove(port);
        if (login == null) {
            return;
        }
        Main.loginPort.remove(login);
        Main.portLogin.remove(port);
    }

    public static void sendMessageFrom(
            String login,
            String message,
            Connections connections
    ) {
        //we want to send some byte so server knows that we want to send something
        try {
            connections.sendStream.writeByte(0);
            connections.sendStream.writeObject(login);
            connections.sendStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Message {

        private final Connections connections;

        public Message(Connections connections) {
            this.connections = connections;
        }
        public Message also(Object o) {
            try {
                connections.sendStream.writeObject(o);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
    }
}