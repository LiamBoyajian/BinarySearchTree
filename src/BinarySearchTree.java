import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T>, Iterable<T> {

    protected BinaryNode<T> root = null;
    protected int version = 0;
    BinarySearchTree(){

    }
    @Override
    public void add(T data) throws NullPointerException {
        var newNode = new BinaryNode<T>(data);
        if (root == null){
            root = new BinaryNode<T>(data);
        }else{
            addHelper(newNode, root);
        }
    }

    /**
     * Whether the tree contains a particular value.
     * @param find the value to check for in the collection.
     * @return bool if found or not.
     */
    @Override
    public boolean contains(Comparable<T> find) {
        return binarySearchHelper(root, find) != null;
    }

    /**
     * Returns the size of the tree.
     * @return
     */
    @Override
    public int size() {
        if (isEmpty()) return 0;
        int result = 0;
        for (Iterator<T> it = iterator(); it.hasNext(); ) {
            T node = it.next();
            ++result;

        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
    }


    /**
     * Helper method for performing a search through the tree.
     * @param node : Node to start search from.
     * @param find : Comparable to compare tree node values against.
     * @return : The found node or null if no node found.
     */
    protected BinaryNode<T> binarySearchHelper(BinaryNode<T> node, Comparable<T> find){
        int compareValue = find.compareTo(node.getEntry());
        if (compareValue == 0){
            return node;
        }else if (compareValue > 0){

            if (node.left != null){
                return binarySearchHelper(node.left, find);
            }else{
                return null;
            }

        }else {
            if (node.right != null){
                return binarySearchHelper(node.right, find);
            }else{
                return null;
            }
        }
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree. When the provided subtree
     * is null, this method does nothing.
     */
    protected void addHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {
        if (subtree == null) return;
        if (newNode == null) return;


        if (subtree.getEntry().compareTo(newNode.getEntry()) < 0){
            //greater than
            if (subtree.right != null){
                addHelper(newNode, subtree.right);
            }else{
                subtree.right = newNode;
            }
        }else {
            //less than
            if (subtree.left != null){
                addHelper(newNode, subtree.left);
            }else{
                subtree.left = newNode;
            }
        }
    }

    /**
     * Creates an iterator that progresses through the tree in-order
     * @return the iterator linked to this tree
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<T>();
    }

    public class MyIterator<T> implements Iterator<T> {
        private int iteratorVersion = -1;
        private BinaryNode<T> currentNode;

        private final Stack<BinaryNode<T>> bookmarkedNodes = new Stack<>();

        /**
         *
         */
        private void EnsureVersionMatch() throws ConcurrentModificationException{
            if (version != iteratorVersion) throw new ConcurrentModificationException();
        }
        MyIterator(){
            iteratorVersion = version;
            currentNode = null;
        }
        @Override
        public boolean hasNext() {
            if (version != iteratorVersion) return false;
            if (currentNode == null) return root != null;
            if (!bookmarkedNodes.isEmpty()) return true;
            return currentNode.right != null;
        }


        @Override
        public T next() {
            EnsureVersionMatch();
            if (!hasNext()) throw new NoSuchElementException("No further elements remaining");

            BinaryNode<T> pointerNode;

            //go down the tree, add each passed node to bookmarks to return to
            if (currentNode == null) pointerNode = (BinaryNode<T>) root;
            else{

                if (currentNode.right != null){
                    pointerNode = currentNode.right;
                }else{
                    currentNode = bookmarkedNodes.pop();
                    return currentNode.getEntry();
                }
            }

            while (pointerNode.left != null){
                bookmarkedNodes.add(pointerNode);
                pointerNode = pointerNode.left;
            }
            currentNode = pointerNode;
            return currentNode.getEntry();
        }
    }
    public static void main(String args[]){

        BinarySearchTree<Integer> _treeInteger = new BinarySearchTree<Integer>();
        BinarySearchTree<String> _treeString = new BinarySearchTree<String>();

        //TEST 1
        System.out.println("TEST 1 --------------");
        _treeInteger.add(1);
        System.out.println("(root = 1): " + _treeInteger.root);
        _treeInteger.add(1);
        System.out.println("(left = 1): " + _treeInteger.root.left);

        _treeInteger.add(-1);
        System.out.println("(left left = -1): " + _treeInteger.root.left.left);
        _treeInteger.add(0);
        System.out.println("(left left right = 0): " + _treeInteger.root.left.left.right);

        _treeInteger.add(2);
        System.out.println("(right = 2): " + _treeInteger.root.right);
        _treeInteger.add(10);
        System.out.println("(right right = 10): " + _treeInteger.root.right.right);
        _treeInteger.add(4);
        System.out.println("(right right left = 4): " + _treeInteger.root.right.right.left);


        System.out.println("Length (expected 7) is " + _treeInteger.size());
        System.out.print("My values in order: ");
        for (Integer i : _treeInteger){
            System.out.print(i + " , ");
        }
        System.out.println();
        _treeInteger.clear();
        System.out.println("Length (expected 0) is " + _treeInteger.size());



        //TEST 2 : same values, different order.
        System.out.println("TEST 2 --------------");
        System.out.println("TEST 1 --------------");
        _treeInteger.add(1);
        System.out.println("(root = 1): " + _treeInteger.root);
        _treeInteger.add(-1);
        System.out.println("(left = -1): " + _treeInteger.root.left);

        _treeInteger.add(1);
        System.out.println("(left right = 1): " + _treeInteger.root.left.right);
        _treeInteger.add(0);
        System.out.println("(left right left = 0): " + _treeInteger.root.left.right.left);

        _treeInteger.add(4);
        System.out.println("(right = 4): " + _treeInteger.root.right);
        _treeInteger.add(10);
        System.out.println("(right right = 10): " + _treeInteger.root.right.right);
        _treeInteger.add(2);
        System.out.println("(right left = 2): " + _treeInteger.root.right.left);


        System.out.println("Length (expected 7) is " + _treeInteger.size());
        System.out.print("My values in order: ");
        for (Integer i : _treeInteger){
            System.out.print(i + " , ");
        }
        System.out.println();
        _treeInteger.clear();
        System.out.println("Length (expected 0) is " + _treeInteger.size());



        //TEST 3 : strings
        System.out.println("TEST 3 --------------");

        _treeString.add("hijklmnop");
        _treeString.add("2");
        _treeString.add("3");
        _treeString.add("4");
        _treeString.add("5");
        _treeString.add("alphabet");
        _treeString.add("zebra");

        System.out.println("Length (expected 7) is " + _treeString.size());

        System.out.print("My values in order: ");
        for (String s : _treeString){
            System.out.print(s + " , ");
        }
        System.out.println();

        _treeString.clear();
        System.out.println("Length (expected 0) is " + _treeString.size());
    }
}

