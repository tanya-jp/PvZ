package network;

import utils.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class handles server.
 * each time clients are connected to server, all their information is updated
 * and checked via server.
 * server checks all the info with a file that stores players information.
 * @version 1.0 2021
 * @author Elaheh Akbari and Tanya Djavaherpour
 */
public class Server {
    //hashmap to save all info
    private final HashMap<String, ArrayList<String>> userInfoMapNormal;
    private final HashMap<String, ArrayList<String>> userInfoMapHard;
    int numOfUsers = 0;

    private ArrayList<String> returnArray = new ArrayList<>();


    //a file containing all players info
    private File userInfoFile;

    //create the file and handle possible exception
    {
        try {
            userInfoFile = createAFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor to create all needed fields.
     */
    public Server() {
        userInfoMapNormal = new HashMap<>();
        userInfoMapHard = new HashMap<>();
        restoreUserInfo(); //this method is called each time server is created
    }

    /**
     * method to restore all file lines  into user info hashmap in the beginning
     * each time the server is called, all the information will be
     * restored from file to hashmaps.
     */
    public void restoreUserInfo() {
        //new buffered reader
        BufferedReader br = null;

        try {
            //create new buffered reader to read from the file
            br = new BufferedReader(new FileReader(userInfoFile));
            String line = null;

            //read till reaching the end of file
            while ((line = br.readLine()) != null) {

                //split the current line by ,
                String[] info = line.split(",");

                //split a line using ,
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


                //add all to arraylist
                ArrayList<String> userInfo = new ArrayList<>();
                userInfo.add(u);
                userInfo.add(m);
                userInfo.add(t);
                userInfo.add(w);
                userInfo.add(l);
                userInfo.add(s);

                //add to normal info hashmap
                if(m.equalsIgnoreCase("normal")) {
                    //put name and info array in HashMap if they aren't empty
                    if (!u.equals("") && !t.equals("") && !w.equals("")
                            && !l.equals("") && !s.equals("")) {
                        userInfoMapNormal.put(u, restoredInfo);
                    }
                }else{
                    //put name and info array in HashMap if they aren't empty
                    if (!u.equals("") && !t.equals("") && !w.equals("")
                            && !l.equals("") && !s.equals("")) {
                        userInfoMapHard.put(u, restoredInfo);
                    }
                }


                numOfUsers = userInfoMapNormal.size() + userInfoMapHard.size();
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

    /**
     * This method gets the strings and writes them to hashmap and files
     * @param username player username
     * @param mode game mode
     * @param type game type
     * @param wins number of wins
     * @param looses number of loses
     * @param score score
     */
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

        //add to arraylist
        info.add(m);
        info.add(t);
        info.add(w);
        info.add(l);
        info.add(s);

        //add to hashmap based on mode
        if(mode.equalsIgnoreCase("normal"))
            userInfoMapNormal.put(u, info);
        else
            userInfoMapHard.put(u, info);

        BufferedWriter bf = null;

        try {
            //new buffered writer
            bf = new BufferedWriter(new FileWriter(userInfoFile));

            //iterate hashmap keys
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
                //write values separated by ,
                bf.write(entry.getKey() + "," + entry.getValue().get(0)
                        + "," + entry.getValue().get(1) + "," + entry.getValue().get(2)
                        + "," + entry.getValue().get(3) + "," + entry.getValue().get(4));
                //go to new line
                bf.newLine();
            }

            //iterate hashmap keys
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMapHard.entrySet()) {
                //write values separated by ,
                bf.write(entry.getKey() + "," + entry.getValue().get(0)
                        + "," + entry.getValue().get(1) + "," + entry.getValue().get(2)
                        + "," + entry.getValue().get(3) + "," + entry.getValue().get(4));
                //go to new line
                bf.newLine();
            }
//            }
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

    /**
     * This method creates a connection socket and waits for clients
     */
    public void waitForClient() {
        try (
                //create new server socket
                ServerSocket welcomingSocket = new ServerSocket(3040);) {
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

    /**
     * This method checks if changing username is available.
     * usernames in file are checked.
     * @param oldUsername the username that player already has
     * @param newUsername new username
     * @return false if the username is already taken, true if change is possibel
     */
    public boolean isChangeAvailable(String oldUsername, String newUsername){
        int flag = 0;
        //check if new username is available
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
            //new username is already taken
            if (entry.getKey().equalsIgnoreCase(newUsername)) {
                return false;
            }
        }
        ArrayList<String> temp = new ArrayList<>();
        //replace with new one
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(oldUsername)) {
                temp = entry.getValue();
                changeUserInFile(oldUsername,newUsername,temp);
            }
        }

        userInfoMapNormal.remove(oldUsername);
        userInfoMapNormal.put(newUsername,temp);
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMapHard.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(oldUsername)) {
                temp = entry.getValue();
                changeUserInFile(oldUsername,newUsername,temp);
            }
        }
        userInfoMapHard.remove(oldUsername);
        userInfoMapHard.put(newUsername,temp);
        changeUserFiles(oldUsername, newUsername);
        return true;
    }

    /**
     * This method changes all user files if the username is changed
     * @param oldUsername old username
     * @param newUsername new username
     */
    public void changeUserFiles(String oldUsername, String newUsername)
    {
        FileUtils.makeFolder(".\\users\\"+newUsername+"\\");
        File[] allFiles = FileUtils.getFilesInDirectory(".\\users\\"+oldUsername+"\\");
        //iterate files
        for (File allFile : allFiles) {
            String content="";
            if (allFile.getName().equals("score")) {
                content = "score" + FileUtils.scanByLineNumber(allFile, 1);
                FileUtils.fileWriter(content, ".\\users\\" + newUsername + "\\");
            }
            else {
                String fileName = allFile.getName();
                String[] fName = fileName.split("\\.");
                Scanner scanner = null;
                try {
                    scanner = new Scanner(allFile);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    content = content + line + "\n";
                }
                scanner.close();
                FileUtils.fileWriterByFileName(content, fName[0], ".\\users\\"+newUsername+"\\");
            }
            allFile.delete();
        }
    }

    /**
     * This method checks if login is available
     * @param username player username
     * @return true if possible, false otherwise
     */
    public boolean isLoginAvailable(String username) {
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
            //username exists and is correct
            if (entry.getKey().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if sign up is available
     * @param username player username
     * @return true if available, false otherwise
     */
    public boolean isSignUpAvailable(String username) {
        if (!userInfoMapNormal.isEmpty()) {
            for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
                //username is already taken
                if (entry.getKey().equalsIgnoreCase(username)) {
                    return false;
                }
            }
        }
        //username is new and will be added
        writeToFile(username, "normal", "d/n", 0, 0, 0);
        writeToFile(username, "hard", "d/n", 0, 0, 0);
        return true;
    }


    /**
     * this method changes the file if username has been changed.
     * @param oldUser old username that needs to be changed
     * @param newUSer new username
     * @param newInfo info that needs to be overwritten
     */
    public void changeUserInFile(String oldUser,String newUSer, ArrayList<String> newInfo){
        BufferedReader br = null;
        ArrayList<String> lines = new ArrayList<>();


        //add all lines to a list
        try {
            br = new BufferedReader(new FileReader(userInfoFile));
            String line = null;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            for(int i = 0; i < lines.size(); i++){
                String[] info = lines.get(i).split(",");
                String u = info[0].trim(); //username
                String m = info[1].trim(); //mode
                if(u.equalsIgnoreCase(oldUser) && m.equalsIgnoreCase("normal")){
                    lines.set(i,newUSer + "," + "normal" + "," + newInfo.get(1) + ","
                            + newInfo.get(2) + "," + newInfo.get(3) + "," +
                            newInfo.get(4));
                }
                else if(u.equalsIgnoreCase(oldUser) && m.equalsIgnoreCase("hard")){
                    lines.set(i,newUSer + "," + "hard" + "," + newInfo.get(1) + ","
                            + newInfo.get(2) + "," + newInfo.get(3) + "," +
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

    /**
     * This method creates a file which stores all information
     * The file is created only once.
     * Each line in this file has this format:
     * username,mode,type,wins,loses,score
     * @return the created file
     * @throws IOException possible exception
     */
    public File createAFile() throws IOException {
        File usersInfoFile = null;
        try {
            usersInfoFile = new File(".\\src\\network\\usersInfoFile.txt");
            if (usersInfoFile.createNewFile()) {
                System.out.println("File created: " + usersInfoFile.getName());
            } else {
                //if file has already been created
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return usersInfoFile;
    }

    /**
     * This method reads all info from file and stores
     * them in an array list of strings.
     * @return array list of strings containing file info.
     */
    public ArrayList<String> returnAllInfo(){
        //create new arraylist
        returnArray = new ArrayList<>();
        BufferedReader br = null;

        try {
            //create new buffered reader
            br = new BufferedReader(new FileReader(userInfoFile));
            String line = null;

            ///read till end of file
            while ((line = br.readLine()) != null) {
                //add to the arraylist
                returnArray.add(line);
            }
        } catch (Exception e) {
            //handle exception
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnArray;
    }

}
