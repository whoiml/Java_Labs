package bsu.rfe.java.group7.lab7.ravgeysh.varС1.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {

    public static boolean isRunning = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 9090);
        ObjectOutputStream sendStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream receiveStream = new ObjectInputStream(socket.getInputStream());
        MessageHandler.sendStream = sendStream;
        MessageHandler.receiveStream = receiveStream;

        MessageHandler messageHandler = new MessageHandler();
        messageHandler.start();

        System.out.println("trying to register");


        Authentication.register("test", "test".getBytes());
        System.out.println("registered");
        Thread.sleep(100);
        System.out.println("trying to login with incorrect password");
        Authentication.login("test", "test1".getBytes());
        System.out.println("trying to login with correct password");
        Authentication.login("test", "test".getBytes());
        System.out.println("trying to send a message");
        MessageHandler.find("test");
        MessageHandler.sendMessageTo("test", "Hello world");
        Thread.sleep(10000);
        System.out.println("disconnecting");
        Authentication.disconnect();

        isRunning = false;

        messageHandler.join();

        sendStream.close();
        receiveStream.close();
        socket.close();
    }
}