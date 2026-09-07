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

    @Override
    public boolean contains(Comparable<T> find) {
        return binarySearchHelper(root, find) != null;
    }

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

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<T>();
    }

    public class MyIterator<T> implements Iterator<T> {
        private int iteratorVersion = -1;
        private BinaryNode<T> currentNode;

        private final Stack<BinaryNode<T>> bookmarkedNodes = new Stack<>();

        private void EnsureVersionMatch(){
            if (version != iteratorVersion) throw new ConcurrentModificationException();
        }
        MyIterator(){
            iteratorVersion = version;
            currentNode = null;
        }

        public boolean hasCurrent(){
            return currentNode != null;
        }
        public T current(){
            EnsureVersionMatch();
            if (currentNode == null) throw new NoSuchElementException("No current ");

            return currentNode.getEntry();
        }
        @Override
        public boolean hasNext() {
            EnsureVersionMatch();
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


    /**

*6. Organizational Requirements
*The binary search tree that you develop for this assignment MUST:
*
* X be a public instantiable class named BinarySearchTree that
* X is defined in its own file
* ? is defined in the default package (do not add any package statements to any of your java files)
* X has a no-argument constructor, which can either be the default constructor or an explicitely defined one.
* does not contain any private members, instead we recommend using package-level (default) visibility to keep members accessible for testing in the future.
* X implement the provided SortedCollection interface.
* X use a bounded generic type parameter that allows different instances of this class to store different types of data values. To ensure that all such data can be stored in sorted order, use java.lang.Comparable as the required bound for this generic type.
* X use the provided BinaryNode class as the type for each node within your tree, and maintain both the parent and child references within each node as they are added to a tree. Leave BinaryNode in its own file and do not include it as a nested class in your BinarySearchTree.
* X include a protected field named root with type BinaryNode (with an appropriate generic type argument) that references the root node within your tree. If the tree is empty (contains no values and nodes), the root field must be set to null. Ensure that your tree is empty immediately after initialization and before any value is added to it.
* X contain working definitions for each of the methods defined within the provided SortedCollection interface, as described in the provided JavaDocs. All of these methods must rely exclusively on inspecting and modifying the tree referenced by your root field. Ensure that no other field besides root is defined in your class.
* X contain and make use of a protected helper method with the following signature and behavior. This requirement is designed to help you extend this class with more functionality in a future week.
*
*
* X accept duplicate values for insertion, and store those values in the left subtree of a parent with an equal value.
* be clearly organized and consistently styled and well-commented. We do not require the use of a specific style guide or standard for CS400 assignments. But if you are not familiar with how this can improve the clarity of your code, we recommend consulting an example like this one from Google for styling Java codeLinks to an external site.. And this piazza postLinks to an external site. clarifies commenting expectations.
* Your solution MAY:
*
* include additional fields (use package-level / default visibility), as long as they supplement rather than replace the meaning and use of your tree’s root field. Since your methods rely exclusively on this root field, it is NOT acceptable to make use of an extra field to store and return the size of your tree. This should instead be done by iterating through the nodes in your tree.
* include additional helper methods (use package-level / default visibility), and these methods can make use of recursion where that is helpful.
* throw unchecked exceptions where appropriate.
* make use other classes from the java standard library. This is likely most useful in your test methods, since each of your other methods operate on the nodes referenced through your root field.
* 7. Testing Requirements
* In future weeks we’ll introduce and make use of the JUnit framework for developing and running tests on our software. For this week, we’ll instead make use of three or more instance methods that test whether our implementation produces the expected result under specific circumstances. These non-static and public test methods should be named test1, test2, test3, etc., they should NOT take any arguments, and each should return either true or false to indicate whether they have passed (behaved as expected) or not. These tests should be added directly to your BinarySearchTree class definition.
*
* At a minimum these tests should check for correct behavior under the following circumstances:
*
* Inserting multiple values as both left and right children in different orders to create differently shaped trees.
* Finding values that are both left and right leaves as well as values stored in the interior of a tree (including at the root position).
* X Ensuring that the size and clear methods are working through the building and clearing of a few different trees worth of data.
* X Each test should make use of differently shaped trees, and across your tests there should be at least one that holds Integers and one that holds Strings.
* A convenient way to run these tests is to add a static main method to your BinarySearchTree class that calls each of these methods and displays feedback about whether each method returns true (passing) vs false (failing).
*/
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
        System.out.print("MY values in order: ");
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
        System.out.print("MY values in order: ");
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

        System.out.print("MY values in order: ");
        for (String s : _treeString){
            System.out.print(s + " , ");
        }
        System.out.println();

        _treeString.clear();
        System.out.println("Length (expected 0) is " + _treeString.size());
    }
}

