package passHacker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PassHacker {
    private final DataInputStream input;
    private final DataOutputStream output;
    private static final char[] allChars = "abcdefghijklmnopqrstuvwxyz01234567890".toCharArray();
    private final List<String> passList;

    public PassHacker(DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
        this.passList = FileManager.getPassList();
    }

    @Deprecated
    public void simpleBruteForce() throws IOException {
        Queue<String> queue = new LinkedList<>();
        queue.add("");

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (char c : allChars) {
                String pass = current + c;
                if (checkPass(pass)) return;
                queue.add(pass);
            }
        }
    }

    public void dictionaryBasedBruteForce() throws IOException {
        for (String pass : passList) {
            for (String combination : generateAllCombinations(pass)) {
                if (checkPass(combination)) return;
            }
        }
    }

    private boolean checkPass(String pass) throws IOException {
        output.writeUTF(pass);
        String msgIn = input.readUTF();
        System.out.print("\033[2K\rChecking pass :" + pass + ". Result: " + msgIn);

        if (msgIn.equals("Too many attempts")) throw new IOException("Server is closed!");
        return msgIn.equals("Connection success!");
    }

    public List<String> generateAllCombinations(String pass) {
        List<List<String>> listOfQueue = new ArrayList<>();

        listOfQueue.add(new LinkedList<>());
        listOfQueue.getFirst().add("");
        char[] chars = pass.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            List<String> queueFrom = listOfQueue.get(i);
            listOfQueue.add(new LinkedList<>());
            List<String> queueTo = listOfQueue.get(i + 1);
            for (String s : queueFrom) {
                String c = String.valueOf(chars[i]);
                if (c.matches("\\d")) {
                    queueTo.add(s + c);
                } else {
                    queueTo.add(s + c.toLowerCase());
                    queueTo.add(s + c.toUpperCase());
                }
            }
        }
        return listOfQueue.getLast();
    }
}

