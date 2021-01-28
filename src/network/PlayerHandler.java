package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));

            String clientData = "";
            while (true) {
                String request = in.readUTF();

                if (!request.equals("over\n")) {
                    clientData += request;
                    System.out.print("Client" + clientNum + ": " + request);
                    out.writeUTF(clientData);
                    out.flush();
                } else {
                    System.out.println("All client" + clientNum + " messages are sent.");
                    break;
                }
            }
            in.close();
            out.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
