package passHacker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;

    public Connection(String SERVER_ADDRESS, int SERVER_PORT) {
        this.SERVER_ADDRESS = SERVER_ADDRESS;
        this.SERVER_PORT = SERVER_PORT;
    }

    public void startHack() {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())

        ) {

            PassHacker passHacker = new PassHacker(input, output);
            passHacker.dictionaryBasedBruteForce();

        } catch (
                IOException e) {
            System.out.println("Unable to connect server!");
        }
    }
}

