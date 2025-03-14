import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileLogger {
    public static void main(String[] args) throws Exception {
        String fileName = "log.txt";
        Scanner scanner = new Scanner(System.in);
        PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true), true);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        while (true) {
            System.out.println("Enter command: ");
            String commandInput = scanner.nextLine();

            if (commandInput.equalsIgnoreCase("EXIT")) break;
            String[] parts = commandInput.split(" ", 2);

            String command = parts[0];
            switch (command) {
                case "log":
                    if (parts.length < 2) {
                        System.out.println("Invalid log command!");
                    }

                    if (!(new File(fileName).exists())) {
                        Path path = Paths.get(fileName);
                        Files.createFile(path);
                        System.out.println("File " + fileName + " was successfully created");
                    }

                    String message = parts[1];
                    printWriter.println(message);
                    System.out.println("Message was successfully saved");
                    break;
                case "read":
                    if (!(new File(fileName).exists())) {
                        System.out.println("File does not exist!");
                        break;
                    }

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.println("Successfully finished reading");
                case "clear":
                    if (!(new File(fileName).exists())) {
                        System.out.println("File does not exist!");
                    } else {
                        printWriter.close();
                        bufferedReader.close();
                        (new File(fileName)).delete();
                    }
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}