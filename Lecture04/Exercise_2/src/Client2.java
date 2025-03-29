import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        String message;
        String modifiedMessage;

        // setting up input from keyboard
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            // connecting to the server
            System.out.println("Connecting to server at " + SERVER_IP + ":" + SERVER_PORT + "...");
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected successfully.");

            // setting up communication streams
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // getting user input
            System.out.println("Enter a message: ");
            message = userInput.readLine();

            // sending the message to the server
            toServer.writeBytes(message + "\n");
            System.out.println("Message sent to server.");

            // receiving and displaying the server's response
            modifiedMessage = fromServer.readLine();
            System.out.println("Server response: " + modifiedMessage);

            // closing the connection
            clientSocket.close();
            System.out.println("Connection closed.");
        } catch (UnknownHostException e) {
            System.err.println("Can't find the server at " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }
}
