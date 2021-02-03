package network;

import java.net.Socket;

/**
 * This class implements runnable and creates a thread.
 * Multiple players can join the server when the thread starts.
 * @version 1.0 2021
 * @author Elaheh Akbari and Tanya Djavaherpour
 */
public class PlayerHandler implements Runnable{
    private final Socket client;
    private final int clientNum;

    /**
     * Client handler constructor
     * @param client socket server
     * @param clientNum number of clients
     */
    public PlayerHandler(Socket client, int clientNum) {
        this.client = client;
        this.clientNum = clientNum;
    }

    /**
     * This method is called when thread starts.
     */
    @Override
    public void run(){
        try {
            System.out.println("PlayerHandler Running");
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
