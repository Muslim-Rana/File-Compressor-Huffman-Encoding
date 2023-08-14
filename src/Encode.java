import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
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

    /**
     * Takes an ArrayList of Nodes and makes a binary tree out of it based on frequency.
     * It returns the root node.
     *
     * @param nodes_array The sorted (lowest to greatest frequencies) ArrayList of Nodes
     * @return The root node of the created binary tree.
     */
    public static Node buildTree(ArrayList<Node> nodes_array) {
        if (nodes_array.size() == 0) {
            return null;
        }
        else if (nodes_array.size() == 1) {
            return nodes_array.get(0);
        }

        while (nodes_array.size() > 1) {
            //Creating the initial 2 leaf nodes and combining them under a branch Node
            Node n0 = nodes_array.remove(0);
            Node n1 = nodes_array.remove(0);
            Node nBranch = new Node(n1,n0);

            int i = 0;
            for (; i < nodes_array.size(); i++) {
                //Inserting the branch node back into our sorted ArrayList of Nodes
                if (nodes_array.get(i).freq >= nBranch.freq) {
                    nodes_array.add(i, nBranch);
                    break;
                }
            }
            if (i == nodes_array.size()) {
                nodes_array.add(nBranch);
            }
        }
        return nodes_array.get(0);
    }

    /**
     * A method for storing the binary representations of different characters based on a binary tree sorted by frequencies.
     * @param n The root Node of your binary tree
     * @param bits An initial empty string to which 0s and 1s will be added
     * @param ht The hashtable where each character will be stored alongside its binary representation (the binary representation will be a String)
     */
    public static void buildStringSet (Node n, String bits, Hashtable<Character, String> ht) {
        //Storing the value once we have reached a leaf node.
        if (n.leftNode == null && n.rightNode == null) {
            ht.put(n.value, bits);
        }

        if (n.leftNode != null) {
            buildStringSet(n.leftNode, bits + "0", ht);
        }
        if (n.rightNode != null) {
            buildStringSet(n.rightNode, bits + "1", ht);
        }
    }

    /**
     * This method writes our encoded file. It needs us to name the file we want to read and then encode
     * and it needs us to provide it with a hashtable of the binary representation of each character
     *
     * @param fileName The file we want to encode
     * @param stringSetTable The characters in that file and their binary representations
     */
    public static void writeEncode(String fileName,Hashtable <Character, String> stringSetTable ) {
        try {
            int c;
            BitSet bSet = new BitSet();
            int index = 0;
            FileInputStream in = new FileInputStream(fileName);


            while (true) {
                c = in.read();
                if (c<0) break;

                //Getting the binary representation of the current character we are reading
                String bitString = stringSetTable.get((char) c);


                if (bitString == null) {
                    System.out.println(c);
                    throw new Exception("Invalid Character Found!");
                }

                //Transitioning from a bitString (binary representation of a character as a string of 0s and 1s) to a BitSet
                //We either set the BitSet to true or false based on whether we have a 1 or 0 in our string
                /*The for loop runs from 0 to the end of the bitString. When the while true loop runs again our index would have
                 * already been incremented this means that we will start setting 0s and 1s from where we left off
                 */
                for (int i = 0; i < bitString.length(); i++) {
                    if (bitString.charAt(i) == '1') {
                        bSet.set(index);
                    }
                    else if (bitString.charAt(i) == '0') {
                        bSet.set(index, false);
                    }
                    index++;
                }
            }
            in.close();

            //Writing the encoded file as a ByteArray
            /*We are also writing the index out, the reasoning for this is that neither BitSet.length() nor BitSet.size() were
             * giving us the correct size (equal to the index). This caused trouble during the decoding portion. In order to counter this we will just send
             * over the index as well and use it in our for loop conditions instead of BitSet.size() and BitSet.length()
             *
             */
            FileOutputStream fileOut = new FileOutputStream("File.encoded");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(index);
            out.writeObject(bSet.toByteArray());
            out.close();
            fileOut.close();
        }

        catch (Exception e){
            System.err.println (e.toString());

        }
    }

    public static void main(String[] args) {
        //Initial variables
        FileInputStream in;
        Hashtable<Character, Integer> ht = new Hashtable<Character, Integer>();
        Hashtable <Character, String> stringSetTable = new Hashtable<Character,String>();
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

            ht.forEach((k,v) -> {
                nodes_array.add(new Node (k,v));
            });
            sortArray(nodes_array);

            System.out.println("Nodes stored in arraylist and sorted");

            //Building the Huffman tree and producing the root node.
            Node tree = buildTree(nodes_array);

            System.out.println("Huffman Tree built");

            //Serializing the tree
            FileOutputStream fileOut = new FileOutputStream("Tree.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tree);
            out.close();
            fileOut.close();

            System.out.println("Tree Serialized");

            buildStringSet(tree, "", stringSetTable);
            writeEncode("warandpeace.txt", stringSetTable);
            System.out.println("Encoding complete!");




        }
        catch (FileNotFoundException e) {
            System.err.println("FILE NOT FOUND" + e.toString());
        }
        catch (IOException e) {
            System.err.println("I/O ERROR OCCURRED" + e.toString());
        }
    }
}