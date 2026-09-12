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
        if (parentOfParent == null) throw new IllegalStateException("parent has no parent and isn't the root node");


        if (parent.left == child){
            //right rotation
            parent.left = child.right;
            child.right = parent;
        } else if (parent.right == child){
            //left rotation
            parent.left = child.left;
            child.left = parent;
        }else{
            throw new IllegalArgumentException("provided nodes are not parent and child");
        }

        parent.up = child;

        if (parentOfParent.left == parent){
            parentOfParent.left = child;
        }else if (parentOfParent.right == parent){
            parentOfParent.right = child;
        }else{
            throw new IllegalStateException("parent node's parent does not link to the parent node");
        }

        child.up = parentOfParent;


    }

    protected void instantiateTestTreeHelper(){
        root = new BinaryNode<T>(null);
        root.left = new BinaryNode<T>(null);
        root.left.up = root;

        root.left.left = new BinaryNode<T>(null);
        root.left.left.up = root.left;
        root.left.right = new BinaryNode<T>(null);
        root.left.right.up = root.left.right;

        root.left.left.left = new BinaryNode<T>(null);
        root.left.left.left.up = root.left.left;
        root.left.left.right = new BinaryNode<T>(null);
        root.left.left.right.up = root.left.left;
        root.left.right.left = new BinaryNode<T>(null);
        root.left.right.left.up = root.left.right;
        root.left.right.right = new BinaryNode<T>(null);
        root.left.right.right.up = root;

        root.right = new BinaryNode<T>(null);
        root.right.up = root;
    }
    public boolean test1(){
        instantiateTestTreeHelper();
        //test rotations on non-root node

        BinaryNode<T> parentOfParent = root.left;
        BinaryNode<T> parent = root.left.left;
        BinaryNode<T> child = root.left.left.right;

        rotate(child, parent);

        if (parentOfParent.left != child) return false;
        if (parent.up != child) return false;
        if (child.up != parentOfParent) return false;
        if (child.left != parent) return false;


        //child = root.left.left.left;

        //rotate(child, parent);


        return true;
    }
    public boolean test2(){
        instantiateTestTreeHelper();


        return true;
    }
    public boolean test3(){
        instantiateTestTreeHelper();


        return true;
    }


    public static void main(String args[]){
        BSTRotation<Integer> aga = new BSTRotation<Integer>();
        System.out.println(aga.test1());
    }
}
