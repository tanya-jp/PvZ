package utils;

//import model.Note;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private static final String NOTES_PATH = "./notes/";
    private static final String SERIALIZED_NOTES_PATH = "./serialized notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
        boolean isSuccessfulNew = new File(SERIALIZED_NOTES_PATH).mkdirs();
        System.out.println("Creating " + SERIALIZED_NOTES_PATH + " directory is successful: " + isSuccessfulNew);
    }

    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }

    /**
     * Gets list of files in serialized notes folder
     * @return File(SERIALIZED_NOTES_PATH).listFiles()
     */
    public static File[] getSerializedFilesInDirectory() {
        return new File(SERIALIZED_NOTES_PATH).listFiles();
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
    /**
     * Write on a file using BufferedWriter
     * @param content String of content to be written on a file
     */
    public static void fileWriter(String content) {
        String fileName = getProperFileName(content);
        File newFile = new File(NOTES_PATH+fileName+".txt");
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
                    writer.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
//
//    //TODO: Phase1: define method here for reading file with InputStream
//    /**
//     * Read from files using InputStream
//     * @param file File to be read
//     * @return String of all the file content
//     */
//    public static String streamFileReader(File file){
//        String string = "";
//        try(FileInputStream in = new FileInputStream(file)) {
//            int c;
//            while ((c = in.read()) != -1){
//                string = string + (char)c;
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return string;
//    }
//    //TODO: Phase1: define method here for writing file with OutputStream
//    /**
//     * Write on a file using FileOutputStream
//     * @param content String of content to be written on a file
//     */
//    public static void streamFileWriter(String content){
//        String fileName = getProperFileName(content);
//        File newFile = new File(NOTES_PATH+fileName+".txt");
//        try (PrintWriter p = new PrintWriter(new FileOutputStream(newFile))) {
//            p.print(content);
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    //TODO: Phase2: proper methods for handling serialization
//    /**
//     * Write a serializable object on file
//     * @param content String of content to be written on a file
//     */
//    public static void writeObject(String content){
//        Date date = new Date();
//        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(date);
//        String fileName = getProperFileName(content);
//        Note note = new Note(fileName,content,dateString);
//        File f = new File(SERIALIZED_NOTES_PATH+fileName+" "+dateString+".txt");
//        try(FileOutputStream fs = new FileOutputStream(f)){
//            ObjectOutputStream os = new ObjectOutputStream(fs);
//            os.writeObject(note);
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//    /**
//     * Read a file as an object
//     * @param file File to read from
//     * @return String of object information
////     */
//    public static String readObject(File file){
//        try(FileInputStream fi = new FileInputStream(file)){
//            ObjectInputStream os = new ObjectInputStream(fi);
//            Note note =(Note) os.readObject();
//            return note.toString();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}
