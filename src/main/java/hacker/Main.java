package hacker;

public class Main {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        Connection connection = new Connection(SERVER_ADDRESS, SERVER_PORT);
        connection.startHack();
    }
}
