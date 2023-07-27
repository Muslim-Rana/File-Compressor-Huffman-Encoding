import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class Encode {
    public static void main(String[] args) {
        //Initial variables
        FileInputStream in;
        Hashtable<Character, Integer> ht = new Hashtable<Character, Integer>();


        try {

            int c;
            in = new FileInputStream("warandpeace.txt");

            System.out.println("Began reading the file");

            //Storing the frequencies of each character in a hashtable alongside the character itself
            while (true) {
                try {
                    c = in.read();
                    if (c < 0) break;
                    if (ht.get((char) c) == null) {
                        ht.put((char) c, 1);
                    }
                    else {
                        ht.replace((char) c, ht.get((char) c) + 1);
                    }


                }
                catch (IOException e) {
                    System.err.println("I/O ERROR OCCURRED");

                }
                catch (NullPointerException e) {
                    System.err.println("NULL POINTER ERROR OCCURRED");
                }
            }
            in.close();
            System.out.println("Finished reading the file");


        }
        catch (FileNotFoundException e) {
            System.err.println("FILE NOT FOUND" + e.toString());
        }
        catch (IOException e) {
            System.err.println("I/O ERROR OCCURRED" + e.toString());
        }
    }
}