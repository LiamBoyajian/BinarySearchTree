public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T>{


    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     *
     * @param child is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     */
    protected void rotate(BinaryNode<T> child, BinaryNode<T> parent) throws IllegalArgumentException{
        if (child == null) throw new IllegalArgumentException("child is null");
        if (parent == null) throw new IllegalArgumentException("parent is null");

        BinaryNode<T> parentOfParent = parent.up;
        if (parentOfParent == null && parent != root) throw new IllegalStateException("parent has no parent and isn't the root node");

        if (parent.left == child){
            //right rotation
            parent.left = child.right;
            child.right = parent;
        } else if (parent.right == child){
            //left rotation
            parent.right = child.left;
            child.left = parent;
        }else{
            throw new IllegalArgumentException("provided nodes are not parent and child");
        }

        parent.up = child;

        if (parentOfParent != null) {
            if (parentOfParent.left == parent) {
                parentOfParent.left = child;
            } else if (parentOfParent.right == parent) {
                parentOfParent.right = child;
            } else {
                throw new IllegalStateException("parent node's parent does not link to the parent node");
            }
        }

        if (parent == root)
            root = child;

        child.up = parentOfParent;
    }

    /**
     * Helper method to create a standardized binary search tree for tests.
     * @return the root node which contains a binary search tree
     */
    protected static BinaryNode<Integer> _instantiateTestTreeHelper(){
        BinaryNode<Integer> result = new BinaryNode<Integer>(10);

        result.left = new BinaryNode<Integer>(5);
        result.left.up = result;
        result.right = new BinaryNode<Integer>(15);
        result.right.up = result;

        result.left.left = new BinaryNode<Integer>(2);
        result.left.left.up = result.left;
        result.left.right = new BinaryNode<Integer>(6);
        result.left.right.up = result.left.right;


        result.left.left.left = new BinaryNode<Integer>(1);
        result.left.left.left.up = result.left.left;
        result.left.left.right = new BinaryNode<Integer>(3);
        result.left.left.right.up = result.left.left;

        result.left.right.left = new BinaryNode<Integer>(4);
        result.left.right.left.up = result.left.right;
        result.left.right.right = new BinaryNode<Integer>(7);
        result.left.right.right.up = result.left.right;

        result.right.right = new BinaryNode<Integer>(30);
        result.right.right.up = result.right;

        result.right.right.right = new BinaryNode<Integer>(45);
        result.right.right.right.up = result.right.right;

        return result;
    }
    public boolean test1(){
        BSTRotation<Integer> _tree = new BSTRotation<Integer>();
        _tree.root = _instantiateTestTreeHelper();


        //test left rotation on non-root node
        BinaryNode<Integer> parentOfParent = _tree.root.left;
        BinaryNode<Integer> parent = _tree.root.left.left;
        BinaryNode<Integer> child = _tree.root.left.left.right;

        _tree.rotate(child, parent);

        if (parentOfParent.left != child) return false;
        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.left != parent) return false;
        if (child.getEntry() < parent.getEntry() ) return false;


        //test right rotation on root child node
        parentOfParent = _tree.root;
        parent = _tree.root.left;
        child = _tree.root.left.left;

        _tree.rotate(child, parent);

        if (parentOfParent.left != child) return false;
        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.right != parent) return false;



        return true;
    }
    public boolean test2(){
        BSTRotation<Integer> _tree = new BSTRotation<Integer>();
        _tree.root = _instantiateTestTreeHelper();

        //test right rotation on root node
        BinaryNode<Integer> parentOfParent = null;
        BinaryNode<Integer> parent = _tree.root;
        BinaryNode<Integer> child = _tree.root.left;

        _tree.rotate(child, parent);

        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.right != parent) return false;

        //test left rotation on root node
        parentOfParent = null;
        parent = _tree.root;
        child = _tree.root.right;

        _tree.rotate(child, parent);

        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.left != parent) return false;


        return true;
    }
    public boolean test3(){
        BSTRotation<Integer> _tree = new BSTRotation<Integer>();
        _tree.root = _instantiateTestTreeHelper();



        //rotation on 0 shared child nodes
        BinaryNode<Integer> parentOfParent = _tree.root.right;
        BinaryNode<Integer> parent = _tree.root.right.right;
        BinaryNode<Integer> child = _tree.root.right.right.right;

        _tree.rotate(child, parent);

        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.left != parent) return false;
        if (child.right != null) return false;
        if (parent.right != null) return false;
        if (parent.left != null) return false;


        //rotation on 1 shared child nodes
        parentOfParent = _tree.root;
        parent = _tree.root.right;
        child = _tree.root.right.right;

        _tree.rotate(child, parent);

        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.left != parent) return false;
        if (child.left == null) return false;
        if (child.right != null) return false;
        if (parent.right == null) return false;
        if (parent.left != null) return false;

        //rotation on 2 shared child nodes
        parentOfParent = null;
        parent = _tree.root;
        child = _tree.root.right;

        _tree.rotate(child, parent);

        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.left != parent) return false;
        if (child.left == null) return false;
        if (child.right != null) return false;
        if (parent.right == null) return false;
        if (parent.left == null) return false;

        //rotation on 3 shared child nodes
        parentOfParent = _tree.root;
        parent = _tree.root.left;
        child = _tree.root.left.left;

        _tree.rotate(child, parent);

        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.right != parent) return false;
        if (child.left == null) return false;
        if (child.right == null) return false;
        if (parent.right == null) return false;
        if (parent.left == null) return false;


        return true;
    }


    public static void main(String args[]){
        BSTRotation<Integer> tempTree = new BSTRotation<>();
        System.out.println("test 1: " + tempTree.test1());
        System.out.println("test 2: " + tempTree.test2());
        System.out.println("test 3: " + tempTree.test3());
    }
}
