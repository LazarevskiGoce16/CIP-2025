import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuoteOfTheDayServer {
    private static final int PORT = 9000;
    private static final List<String> quotesList = Arrays.asList(
            "'What doesn't kill you, makes you stronger.'",
            "'We can all fight against loneliness by engaging in random acts of kindness.'",
            "'Goals transform a random walk into a chase.'",
            "'Sometimes in life, random things can blind-side you.'",
            "'The world is governed by chance.'");

    public static void main(String[] args) {
        System.out.println("Quote of the day server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    String quote = quotesList.get(new Random().nextInt(quotesList.size()));
                    out.println(quote);
                    System.out.println("Quote sent: " + quote);
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}