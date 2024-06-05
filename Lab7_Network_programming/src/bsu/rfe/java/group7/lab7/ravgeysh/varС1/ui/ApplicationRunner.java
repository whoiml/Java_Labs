package bsu.rfe.java.group7.lab7.ravgeysh.varС1.ui;

import bsu.rfe.java.group7.lab7.ravgeysh.varС1.client.Authentication;
import bsu.rfe.java.group7.lab7.ravgeysh.varС1.client.MessageHandler;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ApplicationRunner {

    //to prevent caching
     public volatile static boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException, IOException {
        Socket socket = new Socket("localhost", 9090);
        ObjectOutputStream sendStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream receiveStream = new ObjectInputStream(socket.getInputStream());
        MessageHandler.sendStream = sendStream;
        MessageHandler.receiveStream = receiveStream;

        MessageHandler messageHandler = new MessageHandler();
        messageHandler.start();

        SwingUtilities.invokeLater(LoginRegisterUI::new);


        while (isRunning) {
            Thread.onSpinWait();
        }

        Authentication.disconnect();

        System.out.println("Disconnected");

        messageHandler.join();

        sendStream.close();
        receiveStream.close();
        socket.close();
    }
}