import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Server3 {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // creating a server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started at port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                // accepting client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                // creating a new thread to handle this client
                ClientHandler3 handler = new ClientHandler3(clientSocket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler3 implements Runnable {
    private Socket clientSocket;

    public ClientHandler3(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // setting up communication streams
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

            String clientMessage;

            // processing messages until '.' is entered
            while ((clientMessage = fromClient.readLine()) != null) {
                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + clientMessage);

                // converting the message to uppercase and sending it back
                String upperCaseMessage = clientMessage.toUpperCase() + "\n";
                toClient.writeBytes(upperCaseMessage);

                // if client sends '.', ending the client's session
                if (clientMessage.equals(".")) {
                    System.out.println("Client " + clientSocket.getInetAddress() + " sent termination signal.");
                    break;
                }
            }

            // closing resources
            fromClient.close();
            toClient.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}