package network;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Server {
    //hashmap to save all info
    private HashMap<String, ArrayList<String>> userInfoMap;
    //array list to save all info
    private ArrayList<ArrayList<String>> userAndInfo;
    int numOfUsers = 0;
    int index = 0;

    public int getNumOfUsers() {
        return numOfUsers;
    }

    private File userInfoFile;

    {
        try {
            userInfoFile = createAFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server() {
        userInfoMap = new HashMap<>();
        userAndInfo = new ArrayList<>();
        restoreUserInfo(); //this method is called each time server is created
    }

    //method to restore all file lines  into user info hashmap in the beginning
    public void restoreUserInfo() {
//        int count = 0;
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


                ArrayList<String> userInfo = new ArrayList<>();
                userInfo.add(u);
                userInfo.add(m);
                userInfo.add(t);
                userInfo.add(w);
                userInfo.add(l);
                userInfo.add(s);

                //put name and info array in HashMap if they aren't empty
                if (!u.equals("") && !m.equals("") && !t.equals("") && !w.equals("")
                        && !l.equals("") && !s.equals("")) {
                    userInfoMap.put(u, restoredInfo);
                    userAndInfo.add(userInfo);
//                    userAndInfo.get(count).add(u);
//                    userAndInfo.get(count).add(m);
//                    userAndInfo.get(count).add(t);
//                    userAndInfo.get(count).add(w);
//                    userAndInfo.get(count).add(l);
//                    userAndInfo.get(count).add(s);
//                    count++;
                }

                numOfUsers = userInfoMap.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(String username, String mode, String type, Integer wins
            , Integer looses, Integer score) {
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

        userInfoMap.put(u, info);

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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bf != null;
                bf.close();
            } catch (Exception e) {
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


    public boolean isChangeAvailable(String oldUsername, String newUsername){
        int flag = 0;
        //check if new username is available
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMap.entrySet()) {
            //new username is already taken
            if (entry.getKey().equalsIgnoreCase(newUsername)) {
                flag = 1;
                return false;
            }
        }
        //replace with new one
        if(flag == 0){
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(oldUsername)) {
                    ArrayList<String> temp = entry.getValue();
                    userInfoMap.remove(oldUsername);
                    userInfoMap.put(newUsername,temp);
                    changeUserInFile(oldUsername,newUsername,temp);
                    return true;
                }
            }
        }
        return true;
    }

    public boolean isLoginAvailable(String username) {
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMap.entrySet()) {
            //username exists and is correct
            if (entry.getKey().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSignUpAvailable(String username) {
        if (!userInfoMap.isEmpty()) {
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMap.entrySet()) {
                //username is already taken
                if (entry.getKey().equalsIgnoreCase(username)) {
                    return false;
                }
            }
        }
        //username is new and will be added
        writeToFile(username, null, null, 0, 0, 0);
        return true;
    }




    public void changeUserInFile(String oldUser,String newUSer, ArrayList<String> newInfo){
        BufferedReader br = null;
        ArrayList<String> lines = new ArrayList<>();

 //       List<String> content = new ArrayList<String>(Paths.get(userInfoFile.getAbsolutePath()),StandardCharsets.UTF_8);

        //add all lines to a list
        try {
            br = new BufferedReader(new FileReader(userInfoFile));
            String line = null;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            for(int i = 0; i < lines.size(); i++){
                String[] info = lines.get(i).split("-");
                String u = info[0].trim(); //username
                if(u.equalsIgnoreCase(oldUser)){
                    lines.set(i,newUSer + "-" + newInfo.get(0) + "-" + newInfo.get(1) + "-"
                            + newInfo.get(2) + "-" + newInfo.get(3) + "-" +
                            newInfo.get(4));
                }
            }

            Files.write(Paths.get(userInfoFile.getAbsolutePath()),lines,StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public ArrayList<String> returnUserAndInfo(){
        ArrayList<String> info = userAndInfo.get(index);
        index++;
        return info;
    }


    public File createAFile() throws IOException {
        File usersInfoFile = null;
        try {
            usersInfoFile = new File(".\\src\\network\\usersInfoFile.txt");
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