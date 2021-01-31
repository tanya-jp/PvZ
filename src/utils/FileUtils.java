package utils;

//import model.Note;

import java.io.*;
import java.util.Scanner;

/**
 * This class does things related to files, such as reading a file, writing a file
 * and other things that are clear in the methods.
 * @author Tanya Djavaherpour, Elaheh Akbari
 * @version 1.0 2021
 */
public class FileUtils {

    /**
     * Makes a file of all files in the path
     * @param path as path of wanted files
     * @return all files
     */
    public static File[] getFilesInDirectory(String path) {
        return new File(path).listFiles();
    }
    /**
     * Makes new folder.
     * @param folder as path that folder should be created there
     */
    public static void makeFolder(String folder)
    {
        boolean isSuccessful = new File(folder).mkdirs();
//        System.out.println("Creating " + folder + " directory is successful: " + isSuccessful);
    }
    /**
     * Write on a file using BufferedWriter
     * @param content String of content to be written on a file
     */
    public static void fileWriter(String content,String p) {
        String fileName = getProperFileName(content);
        File newFile = new File(p+fileName+".txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (writer != null)
                {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * Write on a file using BufferedWriter
     * @param content String of content to be written on a file
     */
    public static void gamesWriter(String content,String p) {
        String fileName = String.valueOf(getGameNumber(p));
        File newFile = new File(p+fileName+".txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (writer != null)
                {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
    public static void fileWriterByFileName(String content,String name, String p) {
        String fileName = name;
        File newFile = new File(p+fileName+".txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (writer != null)
                {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
    public static void networkFileWriter(String content,String p) {
        String fileName = "usersInfoFile";
        File newFile = new File(p+fileName+".txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (writer != null)
                {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * Read from files using Buffered reader
     * @param file File to be read
     * @return String of all the file content
     */
    public static String fileReader(File file) {
        String string = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int cnt;
            char[] buffer = new char[2048];
            while (reader.ready()) {
                cnt = reader.read(buffer);
                string += new String(buffer, 0, cnt);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
        return string;
    }

    public static int getGameNumber(String path)
    {
        File[] games = getFilesInDirectory(path);
        return games.length;
    }

    /**
     * Gets proper name to the file, this name is first line of the content of the text.
     * @param content as content that should be saved in the file
     * @return proper name for the file(proper name is first line of the text)
     */
    public static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
    /**
     * Scans a file and reads it line by line and finds the wanted line
     * @param file as file
     * @param lineNumber as number of wanted line
     * @return String of wanted line
     */
    public static String scanByLineNumber(File file, int lineNumber)
    {
        String result = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        int counter = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(counter == lineNumber)
                result = line;
            counter++;
        }
        scanner.close();
        return result;
    }

    /**
     * Scans a file and reads it line by line and finds the wanted lines
     * @param file as a file that should be read
     * @param str1 as string that starts
     * @param str2 as that starts next content.
     * @return wanted String
     */
    public static String scanByLineNumber(File file, String str1, String str2)
    {
        String result = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.equals(str1))
                result = line;
            else if(!line.equals(str2))
                result = result + "\n"+ line;
            else if(line.equals(str2))
            {
                scanner.close();
                break;
            }
        }
        scanner.close();
        return result;
    }
    public static String scanByName(File file, String userName, String type)
    {
        String result = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] res = line.split("-");
            if(res[0].equals(userName))
            {
                if(res[1].equals(type))
                {
                    result = line;
                    scanner.close();
                    return result;
                }
            }
        }
        scanner.close();
        return result;
    }

    public static String scanOtherInfo(File file, String str1, String type, int score)
    {
        String result = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] res = line.split("-");
            if(!res[0].equals(str1))
            {
                result = result + line + "\n";
            }
            else
            {
                if(!res[1].equals(type))
                {

                    result = result+res[0]+"-"+res[1]+"-"+res[2]+"-"+res[3]+"-"+res[4]+"-"+score+ "\n";
                }
            }
        }
        scanner.close();
        return result;
    }
}
