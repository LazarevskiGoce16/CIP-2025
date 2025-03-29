import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client4 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            // connecting to the server
            System.out.println("Connecting to the server at " + SERVER_IP + ":" + SERVER_PORT + "...");
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected successfully.");

            // setting up communication streams
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;

            System.out.println("Enter messages:");
            System.out.println("- Type '.' to end your session");
            System.out.println("- Type 'END' to terminate the server");

            // sending messages until "." or "END" is entered
            while (true) {
                System.out.print("> ");
                message = userInput.readLine();

                // sending the message to the server
                toServer.writeBytes(message + "\n");

                // getting response from the server
                String response = fromServer.readLine();
                System.out.println("Server: " + response);

                if (message.equals(".")) {
                    System.out.println("Ending session...");
                    break;
                } else if (message.equalsIgnoreCase("END")) {
                    System.out.println("Terminating server...");
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server at " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        } finally {
            // closing resources
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Connection closed.");
                }
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
