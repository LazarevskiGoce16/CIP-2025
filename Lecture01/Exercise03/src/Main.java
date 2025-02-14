import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path directoryPath = Paths.get("Lecture01/Exercise01/src");
        String ext = ".java";
        try {
            Files.newDirectoryStream(directoryPath, "*" + ext).forEach(p -> System.out.println(p));
//            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath, "*" + ext);
//            for (Path p : directoryStream) {
//                System.out.println(p);
//            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}