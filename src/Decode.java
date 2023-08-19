import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Decode {

    /**
     * Deserializes the tree and gives us the root node of our binary tree
     * @param filename The name of the file where the tree has been stored
     * @return The root node of the tree
     * @throws Exception Catches any general exception found using catch Exception e
     */
    public static Node deserializeTree (String filename) throws Exception {
        Node tree = null;
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        tree = (Node) in.readObject();
        in.close();
        fileIn.close();
        return tree;
    }



    public static void main (String [] args) {

        try {
            System.out.println("Deserializing the Huffman Tree...");
            Node tree = deserializeTree("Tree.ser");
            System.out.println("Tree deserialized");
        }


        catch (Exception e) {
            // Error code goes here when an I/O exception occurs
            System.err.println (e.toString());
        }


    }
}
