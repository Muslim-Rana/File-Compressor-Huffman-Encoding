import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.BitSet;

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

    /*
     * Takes the encoded file's name and the root node of our tree. It will deserialize the index and the ByteArray and then
     * convert the ByteArray to a BitSet as well. It will then pass the BitSet, Index, tree, and a file name (for our output file)
     * to decodeFile which will do the meat of the work
     */
    public static void writeDecodedText (String filename, Node tree) throws Exception {
        byte[] bArray = null;
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int index = (int) in.readObject();
        bArray = (byte[]) in.readObject();
        in.close();
        fileIn.close();

        BitSet b = BitSet.valueOf(bArray);
        decodeFile(b,index,tree,"File.decoded");

    }

    /**
     * Creates File.decoded which should be identical to our original text piece which we encoded
     *
     * @param b The BitSet created from the encoded ByteArray in writeDecodedText
     * @param index The index (length) of the BitSet
     * @param tree The Huffman tree
     * @param filename The name of the file we want to create
     */
    public static void decodeFile (BitSet b, int index, Node tree, String filename) {
        try {
            Node currentNode = tree;
            FileOutputStream fileOut = new FileOutputStream(filename);
            //ObjectOutputStream out = new ObjectOutputStream(fileOut);


            for (int i = 0; i < index; i++) {

                if (b.get(i) == false) {
                    currentNode = currentNode.leftNode;
                }

                else {
                    currentNode = currentNode.rightNode;
                }

                //Writing out the character to the file once we reach a leaf node
                if (currentNode.leftNode == null && currentNode.rightNode == null) {
                    fileOut.write((byte)currentNode.value);

                    //Resetting the current node to the root node
                    currentNode = tree;
                }
            }
            //out.close();
            fileOut.close();
        }
        catch (Exception e){
            System.err.println (e.toString());

        }
    }

    public static void main (String [] args) {


        try {
            System.out.println("Deserializing the Huffman Tree...");
            Node tree = deserializeTree("Tree.ser");

            System.out.println("Writing decoded file...");
            writeDecodedText("File.encoded",tree);

            System.out.println("Decoding complete!");
        }


        catch (Exception e) {
            // Error code goes here when an I/O exception occurs
            System.err.println (e.toString());
        }


    }
}
