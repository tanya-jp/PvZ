package network;

import utils.FileUtils;

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
    private HashMap<String, ArrayList<String>> userInfoMapNormal;
    private HashMap<String, ArrayList<String>> userInfoMapHard;
    //array list to save all info
    private ArrayList<ArrayList<String>> userAndInfoNormal;
    private ArrayList<ArrayList<String>> userAndInfoHard;
    int numOfUsers = 0;
    int normalIndex = 0;
    int hardIndex = 0;
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
        userInfoMapNormal = new HashMap<>();
        userInfoMapHard = new HashMap<>();
        userAndInfoNormal = new ArrayList<>();
        userAndInfoHard = new ArrayList<>();
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
                if(m.equalsIgnoreCase("normal")) {
                    //put name and info array in HashMap if they aren't empty
                    if (!u.equals("") && !t.equals("") && !w.equals("")
                            && !l.equals("") && !s.equals("")) {
                        userInfoMapNormal.put(u, restoredInfo);
                        userAndInfoNormal.add(userInfo);
                    }
                }else{
                    //put name and info array in HashMap if they aren't empty
                    if (!u.equals("") && !t.equals("") && !w.equals("")
                            && !l.equals("") && !s.equals("")) {
                        userInfoMapHard.put(u, restoredInfo);
                        userAndInfoHard.add(userInfo);
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
//        System.out.println(info);

        if(mode.equalsIgnoreCase("normal"))
            userInfoMapNormal.put(u, info);
        else
            userInfoMapHard.put(u, info);

        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(userInfoFile));
//            if(mode.equalsIgnoreCase("normal")){

                //iterate hashmap keys
                for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
                    //write values separated by -
                    bf.write(entry.getKey() + "-" + entry.getValue().get(0)
                            + "-" + entry.getValue().get(1) + "-" + entry.getValue().get(2)
                            + "-" + entry.getValue().get(3) + "-" + entry.getValue().get(4));
                    //go to new line
                    bf.newLine();
                }
//            }
//            else{

                //iterate hashmap keys
                for (Map.Entry<String, ArrayList<String>> entry : userInfoMapHard.entrySet()) {
                    //write values separated by -
                    bf.write(entry.getKey() + "-" + entry.getValue().get(0)
                            + "-" + entry.getValue().get(1) + "-" + entry.getValue().get(2)
                            + "-" + entry.getValue().get(3) + "-" + entry.getValue().get(4));
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

    //   public static void main(String[] args){
    public void waitForClient() {
        try (
                ServerSocket welcomingSocket = new ServerSocket(2011);) {
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

    public void changeUserFiles(String oldUsername, String newUsername)
    {
        FileUtils.makeFolder(".\\users\\"+newUsername+"\\");
        File[] allFiles = FileUtils.getFilesInDirectory(".\\users\\"+oldUsername+"\\");
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

    public boolean isLoginAvailable(String username) {
        for (Map.Entry<String, ArrayList<String>> entry : userInfoMapNormal.entrySet()) {
            //username exists and is correct
            if (entry.getKey().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

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
                String[] info = lines.get(i).split("-");
                String u = info[0].trim(); //username
                String m = info[1].trim(); //mode
                if(u.equalsIgnoreCase(oldUser) && m.equalsIgnoreCase("normal")){
                    lines.set(i,newUSer + "-" + "normal" + "-" + newInfo.get(1) + "-"
                            + newInfo.get(2) + "-" + newInfo.get(3) + "-" +
                            newInfo.get(4));
                }
                else if(u.equalsIgnoreCase(oldUser) && m.equalsIgnoreCase("hard")){
                    lines.set(i,newUSer + "-" + "hard" + "-" + newInfo.get(1) + "-"
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


    public ArrayList<String> returnUserAndInfoNormal(){
        ArrayList<String> info = userAndInfoNormal.get(normalIndex);
        normalIndex++;
        return info;
    }

    public ArrayList<String> returnUserAndInfoHard(){
        ArrayList<String> info = userAndInfoHard.get(hardIndex);
        hardIndex++;
        return info;
    }

    public ArrayList<String> returnInfo() {
        BufferedReader br = null;
        ArrayList<String> userInfo = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(userInfoFile));
            String line = null;
            for (int i = 0; i < index; i++) {
                br.readLine();
            }

            String lastLine = br.readLine();
            String[] info = lastLine.split("-");

            //split a line using -
            String u = info[0].trim(); //username
            String m = info[1].trim(); //mode
            String t = info[2].trim(); //type
            String w = info[3].trim(); //wins
            String l = info[4].trim(); //looses
            String s = info[5].trim(); //score

            userInfo.add(u);
            userInfo.add(m);
            userInfo.add(t);
            userInfo.add(w);
            userInfo.add(l);
            userInfo.add(s);


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
        index++;
        return userInfo;
    }

    public File createAFile() throws IOException {
        File usersInfoFile = null;
        try {
            usersInfoFile = new File("C:\\AP\\final\\out\\production\\final\\network\\usersInfoFile.txt");
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