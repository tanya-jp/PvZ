package network;

import java.net.Socket;

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
     * Method to read each client massages until "over" is sent. it appends them all together
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
