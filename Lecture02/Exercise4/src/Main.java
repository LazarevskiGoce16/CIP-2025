import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        URI uri = URI.create("https://uacs.edu.mk/home/home");

        try {
            URL url = uri.toURL();

            InputStream inputStream = new BufferedInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("download2_uacs.html");
            byte[] bufferData = new byte[1024];
            int readBytes;

            while ((readBytes = inputStream.read(bufferData, 0, 1024)) != -1) {
                fileOutputStream.write(bufferData, 0, readBytes);
            }

            System.out.println("The content from the URL was successfully saved to the file!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}