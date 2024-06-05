package bsu.rfe.java.group7.lab7.ravgeysh.varС1.client;

import bsu.rfe.java.group7.lab7.ravgeysh.varС1.ui.ApplicationRunner;
import bsu.rfe.java.group7.lab7.ravgeysh.varС1.ui.MessagingFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageHandler extends Thread {

    public static ObjectOutputStream sendStream;
    public static ObjectInputStream receiveStream;

    @Override
    public void run() {
        while (ApplicationRunner.isRunning) {
            try {
                Thread.sleep(1000);
                if (receiveStream.available() != 0) {
                    receiveStream.skipBytes(1);
                    String from = (String) receiveStream.readObject();
                    String message = (String) receiveStream.readObject();
                    System.out.println("New message from: " + from);
                    System.out.println(message);
                    MessagingFrame.receiveMessage(from, message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("some IOException has occurred MessageHandler.class line 26");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("something wrong have been sent");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static Message send(Object object) {
        //we want to send some byte so server knows that we want to send something
        try {
            sendStream.writeByte(0);
            sendStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Message();
    }

    public static String find(String login) {
        try {
            sendStream.writeByte(0);
            sendStream.writeObject("find:");
            sendStream.writeObject(login);

            return (String) receiveStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessageTo(String login, String message) {
        try {
            sendStream.writeByte(0);
            sendStream.writeObject("send:");
            sendStream.writeObject(login);
            sendStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String receiveString() {
        try {
            return (String) receiveStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Message {
        public Message also(Object o) {
            try {
                sendStream.writeObject(o);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
    }
}