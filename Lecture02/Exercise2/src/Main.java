import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        URI uri = URI.create("https://uacs.edu.mk/home/home");

        try {
            URL url = uri.toURL();
            System.out.println("Authority: " + url.getAuthority());
            System.out.println("Default port: " + url.getDefaultPort());
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Path: " + url.getPath());
            System.out.println("File: " + url.getFile());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Port: " + url.getPort());
            System.out.println(url);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }
}