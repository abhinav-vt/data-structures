import java.text.MessageFormat;
import java.util.List;

/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * <p>
 * Quad Tree internal node class that extends the abstract class QuadTreeNode
 * The objective of the class is to store four child nodes representing
 * the different quadrants to store the points objects within.
 */
public class QuadTreeInternalNode extends QuadTreeNode {

    /**
     * array of children of size 4 representing the 4 quadrants
     */
    private final QuadTreeNode[] children;

    /**
     * public constructor to initialize the internal node
     * with array of 4 children set to flyweight node.
     */
    public QuadTreeInternalNode() {
        children = new QuadTreeNode[NUM_QUADRANTS];
        for (int i = 0; i < NUM_QUADRANTS; ++i) {
            children[i] = QuadTreeFlyweightNode.getInstance();
        }
    }

    /**
     * Insert a point record within the given quadrant
     *
     * @param point    to insert
     * @param quadrant to insert data within
     * @return instance of the node in which the point is inserted
     */
    @Override
    public QuadTreeNode insert(Point point, Quadrant quadrant) {
        for (int i = 0; i < NUM_QUADRANTS; ++i) {
            Quadrant subQuadrant = quadrant.getNewQuadrant(i);
            if (subQuadrant.contains(point)) {
                children[i] = children[i].insert(point, subQuadrant);
                break;
            }
        }
        return this;
    }

    /**
     * Remove a point record from the given quadrant
     * First, remove the point.
     * Then check if according to the decomposition rules,
     * the child nodes need to be merged.
     * If yes, find all the point records from the children
     * Then insert them to a new leaf node and return the leaf node.
     * Else, return the current instance without any changes.
     *
     * @param point    to remove
     * @param quadrant from which the point needs to be removed.
     * @return instance of the current node or new leaf node
     */
    @Override
    public QuadTreeNode remove(Point point, Quadrant quadrant) {
        for (int i = 0; i < NUM_QUADRANTS; ++i) {
            Quadrant subQuadrant = quadrant.getNewQuadrant(i);
            if (subQuadrant.contains(point)) {
                children[i] = children[i].remove(point, subQuadrant);
                break;
            }
        }
        // check if the node needs to be merged
        if (!shouldMerge()) {
            return this;
        }

        // accumulate all the points objects in the children and
        // insert them to a single leaf node
        QuadTreeLeafNode newLeafNode = new QuadTreeLeafNode();
        for (int i = 0; i < NUM_QUADRANTS; ++i) {
            if (children[i] instanceof QuadTreeLeafNode) {
                List<Point> points =
                        ((QuadTreeLeafNode) children[i]).getRecords();
                for (Point p : points) {
                    newLeafNode.insert(p, quadrant);
                }
            }
        }
        return newLeafNode;
    }

    /**
     * Check if the given point exists within the current node instance
     *
     * @param point to check
     * @return boolean to indicate if point exists or not
     */
    @Override
    public boolean exists(Point point) {
        for (QuadTreeNode node : children) {
            if (node.exists(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current node instance is a leaf node
     *
     * @return false for internal nodes
     */
    @Override
    public boolean isLeaf() {
        return false;
    }

    /**
     * Check if the point coordinates  in the node.
     *
     * @return the instance of the point object found and null otherwise
     */
    @Override
    public Point search(Point point, Quadrant quadrant) {
        for (int i = 0; i < NUM_QUADRANTS; ++i) {
            Quadrant subQuadrant = quadrant.getNewQuadrant(i);
            if (subQuadrant.contains(point)) {
                return children[i].search(point, subQuadrant);
            }
        }
        return null;
    }

    /**
     * Print out all the points that exists within the given region
     * outlined within the quadrant.
     *
     * @return number of nodes visited
     */
    @Override
    public int regionSearch(Rectangle region, Quadrant quadrant) {
        int numVisited = 1;
        for (int i = 0; i < children.length; ++i) {
            Quadrant subQuadrant = quadrant.getNewQuadrant(i);
            if (region.intersects(subQuadrant)) {
                numVisited += children[i].regionSearch(region, subQuadrant);
            }
        }
        return numVisited;
    }

    /**
     * Print out all the duplicate points that exists within
     * the current node instance
     */
    @Override
    public void duplicates() {
        for (QuadTreeNode child : children) {
            child.duplicates();
        }
    }

    /**
     * Print out all points details that exist in the given quadrant
     *
     * @param quadrant area within which to print points
     * @param depth    used for prints leading spaces
     * @return number of nodes printed
     */
    @Override
    public int dump(Quadrant quadrant, int depth) {
        int numVisited = 1;
        System.out.println(MessageFormat.format(
                "Node at {0}: Internal", quadrant));
        for (int i = 0; i < children.length; ++i) {
            Quadrant subQuadrant = quadrant.getNewQuadrant(i);
            for (int j = 0; j < depth; j++) {
                System.out.print("  ");
            }
            numVisited += children[i].dump(subQuadrant, depth + 1);
        }
        return numVisited;
    }

    /**
     * Return the number of points under the node.
     * Used during decomposition rules calculations
     *
     * @return 0 in this case as internal nodes do not hold points data.
     */
    @Override
    public int getSize() {
        return 0;
    }

    private boolean shouldMerge() {
        // if any child is an internal node then no need to merge
        for (QuadTreeNode child : children) {
            if (!child.isLeaf()) {
                return false;
            }
        }
        // count the number of points in the children
        // if the number of points is less than or equal to 3 then merge
        int numPoints = 0;
        for (QuadTreeNode child : children) {
            numPoints += child.getSize();
        }
        return numPoints <= 3;
    }

    private static final short NUM_QUADRANTS = 4;
}
