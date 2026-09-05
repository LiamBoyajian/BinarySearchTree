public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    @Override
    public void add(T data) throws NullPointerException {

    }

    @Override
    public boolean contains(Comparable<T> find) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    /**

*6. Organizational Requirements
*The binary search tree that you develop for this assignment MUST:
*
* X be a public instantiable class named BinarySearchTree that
* X is defined in its own file
* ? is defined in the default package (do not add any package statements to any of your java files)
* has a no-argument constructor, which can either be the default constructor or an explicitely defined one.
* does not contain any private members, instead we recommend using package-level (default) visibility to keep members accessible for testing in the future.
* X implement the provided SortedCollection interface.
*use a bounded generic type parameter that allows different instances of this class to store different types of data values. To ensure that all such data can be stored in sorted order, use java.lang.Comparable as the required bound for this generic type.
*use the provided BinaryNode class as the type for each node within your tree, and maintain both the parent and child references within each node as they are added to a tree. Leave BinaryNode in its own file and do not include it as a nested class in your BinarySearchTree.
*include a protected field named root with type BinaryNode (with an appropriate generic type argument) that references the root node within your tree. If the tree is empty (contains no values and nodes), the root field must be set to null. Ensure that your tree is empty immediately after initialization and before any value is added to it.
*contain working definitions for each of the methods defined within the provided SortedCollection interface, as described in the provided JavaDocs. All of these methods must rely exclusively on inspecting and modifying the tree referenced by your root field. Ensure that no other field besides root is defined in your class.
*contain and make use of a protected helper method with the following signature and behavior. This requirement is designed to help you extend this class with more functionality in a future week.
*
*     * Performs the naive binary search tree insert algorithm to recursively
*     * insert the provided newNode (which has already been initialized with a
*     * data value) into the provided tree/subtree. When the provided subtree
*     * is null, this method does nothing.
*
*    protected void addHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {
*        // TODO: define and make use of this method in your BinarySearchTree class
*    }
*accept duplicate values for insertion, and store those values in the left subtree of a parent with an equal value.
*be clearly organized and consistently styled and well-commented. We do not require the use of a specific style guide or standard for CS400 assignments. But if you are not familiar with how this can improve the clarity of your code, we recommend consulting an example like this one from Google for styling Java codeLinks to an external site.. And this piazza postLinks to an external site. clarifies commenting expectations.
*Your solution MAY:
*
*include additional fields (use package-level / default visibility), as long as they supplement rather than replace the meaning and use of your tree’s root field. Since your methods rely exclusively on this root field, it is NOT acceptable to make use of an extra field to store and return the size of your tree. This should instead be done by iterating through the nodes in your tree.
*include additional helper methods (use package-level / default visibility), and these methods can make use of recursion where that is helpful.
*throw unchecked exceptions where appropriate.
*make use other classes from the java standard library. This is likely most useful in your test methods, since each of your other methods operate on the nodes referenced through your root field.
*7. Testing Requirements
*In future weeks we’ll introduce and make use of the JUnit framework for developing and running tests on our software. For this week, we’ll instead make use of three or more instance methods that test whether our implementation produces the expected result under specific circumstances. These non-static and public test methods should be named test1, test2, test3, etc., they should NOT take any arguments, and each should return either true or false to indicate whether they have passed (behaved as expected) or not. These tests should be added directly to your BinarySearchTree class definition.
*
*At a minimum these tests should check for correct behavior under the following circumstances:
*
*Inserting multiple values as both left and right children in different orders to create differently shaped trees.
*Finding values that are both left and right leaves as well as values stored in the interior of a tree (including at the root position).
*Ensuring that the size and clear methods are working through the building and clearing of a few different trees worth of data.
*Each test should make use of differently shaped trees, and across your tests there should be at least one that holds Integers and one that holds Strings.
*A convenient way to run these tests is to add a static main method to your BinarySearchTree class that calls each of these methods and displays feedback about whether each method returns true (passing) vs false (failing).
*/
}
