package bsu.rfe.java.group7.lab7.ravgeysh.varС1.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

public class ConnectionHandler extends Thread {

    @Override
    public synchronized void run() {
        while (Main.isRunning) {
            try {
                var socket = Main.server.accept();

                Connections connections = new Connections(
                        socket,
                        new ObjectOutputStream(socket.getOutputStream()),
                        new ObjectInputStream(socket.getInputStream())
                );
                Main.connectionsMapping.put(
                        socket.getPort(),
                        connections
                );
            } catch (SocketException e) {
                if (Main.isRunning) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}