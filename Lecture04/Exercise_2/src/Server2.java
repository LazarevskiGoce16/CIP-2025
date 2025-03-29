import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        String clientMessage;
        String uppercaseMessage;
        ServerSocket serverSocket = null;

        try {
            // creating a server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                // waiting and accepting client connection
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Client connected from: " + connectionSocket.getInetAddress());

                // setting up communication streams
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream toClient = new DataOutputStream(connectionSocket.getOutputStream());

                // reading message from client
                clientMessage = fromClient.readLine();
                System.out.println("Received: " + clientMessage);

                // converting the message to uppercase
                uppercaseMessage = clientMessage.toUpperCase() + '\n';

                // sending converted message back to client
                toClient.writeBytes(uppercaseMessage);
                System.out.println("Sent: " + uppercaseMessage.trim());

                // closing the connection and keeping the server running
                connectionSocket.close();
                System.out.println("Client disconnected. Waiting for new clients...\n");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // closing the server socket when done
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("Server socket closed.");
                }
            } catch (IOException e) {
                System.err.println("Error while closing the server socket: " + e.getMessage());
            }
        }
    }
}