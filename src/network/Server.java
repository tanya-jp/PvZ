package network;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private HashMap<String, ArrayList<String>> userInfoMap;
    private File userInfoFile;

    {
        try {
            userInfoFile = createAFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(){
        userInfoMap = new HashMap<>();
        restoreUserInfo(); //this method is called each time server is created
    }

    //method to restore all file lines  into user info hashmap in the beginning
    public void restoreUserInfo(){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(userInfoFile));
            String line = null;

            while ((line = br.readLine()) != null) {

                String[] info = line.split("-");

                //split a line using -
                String u = info[0].trim(); //username
                String m = info[1].trim(); //mode
                String t = info[2].trim(); //type
                String w = info[3].trim(); //wins
                String l = info[4].trim(); //looses
                String s = info[5].trim(); //score

                //add to an array list
                ArrayList<String> restoredInfo = new ArrayList<>();
                restoredInfo.add(m);
                restoredInfo.add(t);
                restoredInfo.add(w);
                restoredInfo.add(l);
                restoredInfo.add(s);

                //put name and info array in HashMap if they aren't empty
                if (!u.equals("") && !m.equals("") && !t.equals("") && !w.equals("")
                        && !l.equals("") && !s.equals(""))
                    userInfoMap.put(u,restoredInfo);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert br != null;
                br.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(String username, String mode, String type, Integer wins
    , Integer looses, Integer score){
        //array list for user info
        ArrayList<String> info = new ArrayList<>();
        //set info
        String u = username;
        String m = mode;
        String t = type;
        String w = wins.toString();
        String l = looses.toString();
        String s = score.toString();

        info.add(m);
        info.add(t);
        info.add(w);
        info.add(l);
        info.add(s);

        userInfoMap.put(u,info);

        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(userInfoFile));

            //iterate hashmap keys
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMap.entrySet()) {
                //write values separated by -
                bf.write(entry.getKey() + "-" + entry.getValue().get(0)
                + "-" + entry.getValue().get(1) + "-" + entry.getValue().get(2)
                + "-" + entry.getValue().get(3) + "-" + entry.getValue().get(4));
                //go to new line
                bf.newLine();
            }
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert bf != null;
                bf.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //   public static void main(String[] args){
    public void waitForClient() {
        try (
                ServerSocket welcomingSocket = new ServerSocket(5000);) {
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
//    public boolean isLoginAvailable(String username){
//    }
//
    public boolean isSignUpAvailable(String username){
        if(!userInfoMap.isEmpty()) {
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMap.entrySet()) {
                //username is already taken
                if (entry.getKey().equalsIgnoreCase(username)) {
                    return false;
                }
            }
        }
        //username is new and will be added
        writeToFile(username,null,null,0,0,0);
        return true;
    }

public File createAFile() throws IOException {
    File usersInfoFile = null;
    try {
        usersInfoFile = new File("C:\\Users\\kanoon\\IdeaProjects\\final-project\\src\\network\\usersInfoFile.txt");
        if (usersInfoFile.createNewFile()) {
            System.out.println("File created: " + usersInfoFile.getName());
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    return usersInfoFile;
}
}