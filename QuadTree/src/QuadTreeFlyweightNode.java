import java.text.MessageFormat;

/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * <p>
 * QuadTree node flyweight class that extends the abstract class QuadTreeNode
 * The objective of the class is to represent a null value node in order to
 * keep the implementation generic.
 */

public class QuadTreeFlyweightNode extends QuadTreeNode {


    private static QuadTreeFlyweightNode node = null;

    /**
     * Private constructor to prevent class instantiation.
     */
    private QuadTreeFlyweightNode() {
    }

    /**
     * Get instance method to make sure only one instance
     * of the class gets created.
     *
     * @return the node instance.
     */
    public static QuadTreeNode getInstance() {
        if (node == null) {
            node = new QuadTreeFlyweightNode();
        }
        return node;
    }

    /**
     * If insertion at flyweight node is required then a Leaf node
     * instance is returned.
     *
     * @param point    record to insert
     * @param quadrant quadrant within the tree to insert at
     * @return new Leaf node instance
     */
    @Override
    public QuadTreeNode insert(Point point, Quadrant quadrant) {
        return new QuadTreeLeafNode(point);
    }

    /**
     * No operation required if remove is called on the flyweight node.
     * And  the same instance is returned.
     * @param point obj to remove
     * @param quadrant quadrant from which to remove point
     * @return same flyweight instance
     */
    @Override
    public QuadTreeNode remove(Point point, Quadrant quadrant) {
        return this;
    }

    /**
     * Check if the point object exists. Always returns false
     * @param point obj to check
     * @return false
     */
    @Override
    public boolean exists(Point point) {
        return false;
    }

    /**
     * Check if the instance is leaf node. Always returns true.
     * @return true
     */
    @Override
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if the point exists in the node.
     * Always returns null as flyweight holds no data.
     * @return null
     */
    @Override
    public Point search(Point point, Quadrant quadrant) {
        return null;
    }

    /**
     * Print out all the points that exists within the given region
     * outlined within the quadrant.
     * @return number of nodes visited (always 0 in this case)
     */
    @Override
    public int regionSearch(Rectangle region, Quadrant quadrant) {
        return 0;
    }

    /**
     * Print out all the duplicate points that exists
     */
    @Override
    public void duplicates() {
        // No data exists
    }

    /**
     * Print out all points details that exist in the given quadrant
     * @param quadrant area within which to print points
     * @param depth used for prints leading spaces
     * @return number of nodes printed (always 0 in this case)
     */
    @Override
    public int dump(Quadrant quadrant, int depth) {
        System.out.println(MessageFormat.format(
                "Node at {0}: Empty", quadrant));
        return 1;
    }

    /**
     * Return the number of points under the node.
     * Used during decomposition rules calculations
     * @return 0 in this case.
     */
    @Override
    public int getSize() {
        return 0;
    }
}
