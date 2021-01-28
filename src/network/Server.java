package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //   public static void main(String[] args){
    public void waitForClient() {
        try (
                ServerSocket welcomingSocket = new ServerSocket(6023);) {
            System.out.println("Waiting for a client...");

            for (int i = 1; true; i++) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("Client" + i + " accepted.");

                Thread clientThread = new Thread(new PlayerHandler(connectionSocket, i), "client" + i);
                clientThread.start();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

//    public boolean isUserAdded(String username){
//        //check all lines, if it doesn't exit add it and let in
//
//    }

//    public boolean isChangeAvailable(){
//
//    }
//
//    public boolean isLoginAvailable(){
//
//    }
//
//    public boolean isSignUpAvailable(){
//
//    }
}

