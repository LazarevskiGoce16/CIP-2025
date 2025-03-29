import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class QuoteOfTheDayClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9000;
    public static void main(String[] args) {
        System.out.println("Connecting to the server...");
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String quote = in.readLine();
            System.out.println("Quote for the day: " + quote);
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server at " + SERVER_IP + ":" + SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }

        System.out.println("Disconnected from the server.");
    }
}
