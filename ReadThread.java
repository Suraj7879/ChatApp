import java.io.*;
import java.net.*;
import java.util.*;  
import java.io.Serializable;
 
/**
 * This thread is responsible for reading server's input and printing it
 * to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
 
    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                // when the server don't send any respose after LEAVING the chat
                // the client will be disconnected from the chat
                // and executes the ChatClient main class waiting for a new connection
                String response = reader.readLine();
                System.out.println("\n" + response);
                
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("Chat Room has disconnected.");
                ChatClient.main(null);
                break;
            }
        }
    }
}