/**
 * The PRQuadTree abstract class.
 * <p>
 * All nodes type like Internal, Leaf and Flyweight derive from this.
 * <p>
 * A QuadTree is a full tree that branches four way.
 * The key property of a Quadtree is its decomposition rule.
 * The decomposition rule for this project is:
 * A leaf node will split into four when
 * 1. There are more than three points in the node, such that ...
 * 2. not all of the points have the same x and y coordinates.
 * <p>
 * It is possible for a single insertion or deletion to cause a cascading
 * series of node splitting or merges.
 *
 * @author Abhinav Sethi 
 * @version 2021-10-23
 */

abstract class QuadTreeNode {

    /**
     * Insert a point record within the given quadrant
     *
     * @param point    to insert
     * @param quadrant to insert data within
     * @return instance of the node in which the point is inserted
     */
    public abstract QuadTreeNode insert(Point point, Quadrant quadrant);

    /**
     * Remove a point record from the given quadrant
     *
     * @param point    to remove
     * @param quadrant from which the point needs to be removed.
     * @return instance of the node from which the node is removed
     */
    public abstract QuadTreeNode remove(Point point, Quadrant quadrant);

    /**
     * Check if the given point exists within the current node instance
     *
     * @param point to check
     * @return boolean to indicate if point exists or not
     */
    public abstract boolean exists(Point point);

    /**
     * Check if the current node instance is a leaf node
     *
     * @return boolean to indicate if node is leaf
     */
    public abstract boolean isLeaf();

    /**
     * Check if a point with the same coordinates in the node.
     *
     * @param point    obj to search
     * @param quadrant area to search within
     * @return the instance of the point object found and null otherwise
     */
    public abstract Point search(Point point, Quadrant quadrant);

    /**
     * Print out all the points that exists within the given region
     * outlined within the quadrant.
     *
     * @param region   rectangle object representing the area
     * @param quadrant area to search within
     * @return number of nodes visited
     */
    public abstract int regionSearch(Rectangle region, Quadrant quadrant);

    /**
     * Print out all the duplicate points that exists within
     * the current node instance
     */
    public abstract void duplicates();

    /**
     * Print out all points details that exist in the given quadrant
     *
     * @param quadrant area within which to print points
     * @param depth    used for prints leading spaces
     * @return number of nodes printed
     */
    public abstract int dump(Quadrant quadrant, int depth);

    /**
     * Return the number of points under the node.
     * Used during decomposition rules calculations
     *
     * @return integer value for the size
     */
    public abstract int getSize();
}
