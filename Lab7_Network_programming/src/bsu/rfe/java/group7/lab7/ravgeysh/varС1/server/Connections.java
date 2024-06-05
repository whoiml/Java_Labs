package bsu.rfe.java.group7.lab7.ravgeysh.varС1.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connections {
    public Socket socket;
    public ObjectInputStream receiveStream;
    public ObjectOutputStream sendStream;

    public Connections(
            Socket socket,
            ObjectOutputStream sendStream,
            ObjectInputStream receiveStream
    ) {
        this.socket = socket;
        this.receiveStream = receiveStream;
        this.sendStream = sendStream;
    }
}