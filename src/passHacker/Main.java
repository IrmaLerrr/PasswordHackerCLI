package passHacker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 23456;
    private static final char[] allChars = "abcdefghijklmnopqrstuvwxyz01234567890".toCharArray();

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())

        ) {
            System.out.println(findPassword(input, output));
        } catch (IOException e) {
            System.out.println("Unable to connect server!");
        }
    }

    public static String findPassword(DataInputStream input, DataOutputStream output) throws IOException {
        Queue<String> queue = new LinkedList<>();
        queue.add("");

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (char c : allChars) {
                String pass = current + c;
                output.writeUTF(pass);
                String msgIn = input.readUTF();

                if (msgIn.equals("Connection success!")) {
                    return pass;
                }
                if (msgIn.equals("Too many attempts")) {
                    return "Error!";
                }

                queue.add(pass);
            }
        }

        return "Error!";
    }
}
