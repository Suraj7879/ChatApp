import java.net.*;
import java.io.*;
import java.util.*;  
  
/**
 * This is the chat client program.
 * Type 'SHUTDOWN' to terminte the program.
 */
public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
      
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
 
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
 
            System.out.println("Connected to the chat server");
 
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
 
    void setUserName(String userName) {
        this.userName = userName;
    }
 
    String getUserName() {
        return this.userName;
    }
 
 
    public static void main(String[] args) {

        // cmd expects 2 commands to either JOIN a  chat or terminate the program aka SHUTDOWN 

        Scanner sc= new Scanner(System.in);
        String cmd= sc.nextLine();
        
        // if cmd is SHUTDOWN program stops executing
        if(cmd.equals("SHUTDOWN")){
            return ;
        }

        // else program expects 'JOIN <hostname> <port>' as input to join a running server 
        // cmd string is converted to array of stirngs to store hostname and port number
        String[] join = cmd.split("\\s+");

        // checks whether hostname and port are valid 
        // if invalid, program keeps asking for input
        while(join.length!=3 || !join[0].equals("JOIN")){ 
        
            System.out.println("Client needs to join a chat, before a message can be sent");
            System.out.println("To join use syntax: JOIN <hostname> <port>");
            
            cmd= sc.nextLine(); 
    
            // if SHUTDOWN comes as input program terminates    
            if(cmd.equals("SHUTDOWN")){
                return ;
            }
            // removes all the spaces between words and store words in array of strings
            join = cmd.split("\\s+");
            
        }

        String hostname = join[1];
        int port = Integer.parseInt(join[2]);
        
        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }
}