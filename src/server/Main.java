package server;

import java.io.*;
import java.net.*;

public class Main {
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started!");
            while (true) {
                try {
                    Socket clientSocket = server.accept();
                    DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                    String pass = generatePassword();

                    while (true) {
                        String msgIn = input.readUTF();
                        if (msgIn.equals(pass)) output.writeUTF("Connection success!");
                        else output.writeUTF("Wrong password!");
                    }
//                    RequestManager manager = new RequestManager(input, output);
//                    manager.start();
                } catch (EOFException e2) {
                    System.out.println("The client disconnected.");
                } catch (SocketException e3) {
                    System.out.println("The connection was broken.");
                } catch (IOException e) {
                    System.out.println("Error! " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    private static String generatePassword(){
        return "abc";
    }
}
