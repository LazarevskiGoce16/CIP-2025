import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        long timeStamp = System.currentTimeMillis();
        String filePathString = "Lecture01/Exercise01/uniqueName_" + timeStamp + ".txt";
        File f = new File(filePathString);

        if (f.exists()) {
            System.out.println("File already exists on the given location!");
        } else {
            Path filePath = Paths.get(filePathString);
            try {
                Files.createFile(filePath);
                System.out.println("File was successfully created.");
                File f1 = new File(filePathString);
                System.out.println("File name: " + f1.getName());
                System.out.println("File relative path (String): " + f1.getPath());
                System.out.println("File absolute path (String): " + f1.getAbsolutePath());
                System.out.println("File read privileges: " + f1.canRead());
                System.out.println("File write privileges: " + f1.canWrite());
                System.out.println("File execute privileges: " + f1.canExecute());
            } catch (IOException e) {
                System.err.println("Error occurred while creating the file!");
                e.printStackTrace();
            }
        }
    }
}