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
        List<String> result = new ArrayList<>();
        result.add("");

        for (char c : pass.toCharArray()) {
            List<String> newList = new ArrayList<>();
            for (String s : result) {
                newList.add(s + Character.toLowerCase(c));
                if (Character.isLetter(c)) {
                    newList.add(s + Character.toUpperCase(c));
                }
            }
            result = newList;
        }

        return result;
    }
}

