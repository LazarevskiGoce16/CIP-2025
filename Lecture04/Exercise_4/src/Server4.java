import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server4 {
    // Port the server listens on
    private static final int PORT = 8080;
    // Flag to control server execution
    private static volatile boolean running = true;
    // Thread pool for client handlers
    private static ExecutorService threadPool;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        threadPool = Executors.newCachedThreadPool();

        try {
            // creating server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");
            System.out.println("Server will terminate if a client sends 'END'");

            while (running) {
                try {
                    // setting a timeout so we can check the running flag periodically
                    serverSocket.setSoTimeout(1000);

                    // accepting client connection
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from: " + clientSocket.getInetAddress());

                    // creating a new thread to handle this client
                    ClientHandler4 handler = new ClientHandler4(clientSocket);
                    threadPool.submit(handler);
                } catch (SocketTimeoutException e) {
                    // continuing
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            // shutting down thread pool
            threadPool.shutdown();
            try {
                // waiting for existing tasks to terminate
                if (!threadPool.awaitTermination(15, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // closing the server socket
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("Server socket closed.");
                }
            } catch (IOException e) {
                System.err.println("Error closing the server socket: " + e.getMessage());
            }

            System.out.println("Server terminated.");
        }
    }

    // method to stop the serve
    public static void stopServer() {
        running = false;
    }
}

class ClientHandler4 implements Runnable {
    private Socket clientSocket;

    public ClientHandler4(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // setting up communication streams
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

            String clientMessage;

            // processing messages until client enters '.' or 'END'
            while ((clientMessage = fromClient.readLine()) != null) {
                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + clientMessage);

                // checking for termination command
                if (clientMessage.equals("END")) {
                    System.out.println("Received termination command from client: " + clientSocket.getInetAddress());
                    toClient.writeBytes("SERVER SHUTTING DOWN\n");
                    Server4.stopServer();
                    break;
                }

                // converting messages to uppercase and sending them back to the client
                String uppercaseMessage = clientMessage.toUpperCase() + "\n";
                toClient.writeBytes(uppercaseMessage);

                // if client sends '.', ending the session
                if (clientMessage.equals(".")) {
                    System.out.println("Client " + clientSocket.getInetAddress() + " sent session termination signal.");
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