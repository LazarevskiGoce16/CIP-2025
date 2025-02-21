import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop operations are not supported!");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        File file = new File("download2_uacs.html");

        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}