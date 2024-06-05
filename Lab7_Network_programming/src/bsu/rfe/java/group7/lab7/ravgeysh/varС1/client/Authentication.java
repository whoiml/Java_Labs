package bsu.rfe.java.group7.lab7.ravgeysh.varС1.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static bsu.rfe.java.group7.lab7.ravgeysh.varС1.client.MessageHandler.send;

public class Authentication {
    public static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest
                    .getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String register(String login, byte[] password) {
        String hashedPasswd =
                new String(messageDigest.digest(password));

        Arrays.fill(password, (byte) ' ');
        send("register:")
                .also(login)
                .also(hashedPasswd);

        return MessageHandler.receiveString();
    }

    public static void disconnect() {
        send("disconnect:");
    }

    public static String login(String login, byte[] passwd) {
        String hashedPasswd =
                new String(messageDigest.digest(passwd));

        Arrays.fill(passwd, (byte) ' ');
        send("login:")
                .also(login)
                .also(hashedPasswd);

        return MessageHandler.receiveString();
    }
}