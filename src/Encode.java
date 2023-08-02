import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Encode {

    /**
     * Sorts a Node array based on frequency, from least to greatest
     * @param n the Node ArrayList
     */
    public static void sortArray (ArrayList<Node> n) {

        //Traversing the Node array from the start until the final index
        for (int i = 0; i < n.size(); i++) {

            for (int j = 0; j< n.size() - 1 - i; j++) {

                //Comparing the Node at index j with the following Node
                //Swapping them if the Node at j+1 has a smaller frequency
                if (n.get(j).freq > n.get(j+1).freq) {
                    char tempV = n.get(j+1).value;
                    int tempF = n.get(j+1).freq;

                    n.get(j+1).value = n.get(j).value;
                    n.get(j+1).freq = n.get(j).freq;

                    n.get(j).value = tempV;
                    n.get(j).freq = tempF;

                }
            }
        }

    }

    public static void main(String[] args) {
        //Initial variables
        FileInputStream in;
        Hashtable<Character, Integer> ht = new Hashtable<Character, Integer>();
        ArrayList<Node> nodes_array = new ArrayList<Node>();


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

        ht.forEach((k,v) -> {
            nodes_array.add(new Node (k,v));
        });
        sortArray(nodes_array);

        System.out.println("Nodes stored in arraylist and sorted");
    }
}