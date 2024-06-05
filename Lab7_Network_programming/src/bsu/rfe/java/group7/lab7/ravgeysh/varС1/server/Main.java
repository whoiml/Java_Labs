package bsu.rfe.java.group7.lab7.ravgeysh.varС1.server;

import java.net.ServerSocket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static final Map<Integer, Connections> connectionsMapping = new ConcurrentHashMap<>();
    public static final Map<String, String> authPassword = new ConcurrentHashMap<>();
    public static final Map<String, Integer> loginPort = new ConcurrentHashMap<>();
    public static final Map<Integer, String> portLogin = new ConcurrentHashMap<>();
    public static boolean isRunning = true;
    public static ServerSocket server;


    public static void main(String[] args) throws Exception {
        server = new ServerSocket(9090);
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.start();
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.start();

        System.out.println("Server has been initialised");

        System.out.println("Enter anything to exit");
        new Scanner(System.in).next();
        Main.isRunning = false;


        System.out.println("Exiting!!!");

        server.close();

        connectionHandler.join();
        messageHandler.join();


        System.out.println("Ending application:");
        System.out.println(authPassword);

        for (var connection : connectionsMapping.entrySet()) {
            Connections connectionValue = connection.getValue();
            connectionValue.receiveStream.close();
            connectionValue.sendStream.close();
            connectionValue.socket.close();
        }
    }
}