import java.text.MessageFormat;
import java.util.*;

import static java.text.MessageFormat.format;

/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * <p>
 * Quad Tree leaf node class that extends the abstract class QuadTreeNode
 * The objective of the class is to store the point objects as a list.
 * The node can then be decomposed depending on the outlined rules.
 */
public class QuadTreeLeafNode extends QuadTreeNode {

    /**
     * List of point records that the leaf node holds.
     * Can hold as many records as long as the
     * decomposition rule is followed.
     */
    private final List<Point> records;

    /**
     * Constructor to initialize Leaf node
     */
    public QuadTreeLeafNode() {
        this.records = new LinkedList<>();
    }

    /**
     * Constructor to initialize Leaf node with a point object.
     *
     * @param p point to insert after initialization
     */
    public QuadTreeLeafNode(Point p) {
        this.records = new LinkedList<>();
        this.records.add(p);
    }

    /**
     * To get all the point records held by the current node.
     *
     * @return list of records
     */
    public List<Point> getRecords() {
        return records;
    }


    /**
     * Insert a point record to the records list.
     * After insertion check if the node needs to be split
     * according to the decomposition rules.
     * And return the new internal node or the
     * same instance depending on the outcome.
     *
     * @param point    to insert
     * @param quadrant to insert data within
     * @return instance of the node in which the point is inserted
     */
    @Override
    public QuadTreeNode insert(Point point, Quadrant quadrant) {
        records.add(point);

        // check if node needs to be split
        if (shouldSplit()) {
            // insert all records in a new internal node and return it
            QuadTreeNode newNode = new QuadTreeInternalNode();
            for (Point p : records) {
                newNode.insert(p, quadrant);
            }
            return newNode;
        }
        // return the same node if did not split
        return this;
    }

    private boolean shouldSplit() {
        // no need to split if number of records less than 3
        if (records.size() <= 3) {
            return false;
        }
        // split if there is any non-duplicate point
        Iterator<Point> it = records.iterator();
        Point prev = it.next();
        while (it.hasNext()) {
            Point curr = it.next();
            if (prev.x != curr.x || prev.y != curr.y) {
                return true;
            }
        }
        // don't split if all points are duplicates
        return false;
    }

    /**
     * Remove a point record from the current records if it exists.
     * Then if the records list is empty, then return flyweight instance,
     * else return current instance.
     *
     * @param point    to remove
     * @param quadrant from which the point needs to be removed.
     * @return instance of the current node or flyweight node
     */
    @Override
    public QuadTreeNode remove(Point point, Quadrant quadrant) {
        records.removeIf(point1 -> point1.equals(point));
        if (records.isEmpty()) {
            return QuadTreeFlyweightNode.getInstance();
        }
        return this;
    }

    /**
     * Check if the given point exists within the current node instance
     *
     * @param point to check
     * @return boolean to indicate if point exists or not
     */
    @Override
    public boolean exists(Point point) {
        for (Point p : records) {
            if (point.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current node instance is a leaf node
     *
     * @return true for leaf nodes
     */
    @Override
    public boolean isLeaf() {
        return true;
    }

    /**
     * Check if the point exists in the node.
     *
     * @return the instance of the point object found and null otherwise
     */
    @Override
    public Point search(Point point, Quadrant quadrant) {
        for (Point p : records) {
            if (point.x == p.x && point.y == p.y) {
                return p;
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
        for (Point p : records) {
            if (region.contains(p)) {
                System.out.println(format(Constants.REGION_SEARCH_FOUND, p));
            }
        }
        return 1;
    }

    /**
     * Print out all the duplicate points that exists within
     * the current node instance
     */
    @Override
    public void duplicates() {
        HashMap<String, List<Point>> pointsMap = new HashMap<>();
        // create a map of coordinates with a list of point objects
        for (Point p : records) {
            String key = format("({0}, {1})", p.x, p.y);
            if (!pointsMap.containsKey(key)) {
                pointsMap.put(key, new ArrayList<>());
            }
            pointsMap.get(key).add(p);
        }
        // print all points having more than 1 occurrence
        for (Map.Entry<String, List<Point>> entry : pointsMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println(entry.getKey());
            }
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
        System.out.println(MessageFormat.format("Node at {0}:", quadrant));
        for (Point p : records) {
            for (int j = 0; j < depth - 1; j++) {
                System.out.print("  ");
            }
            System.out.println(p);
        }
        return 1;
    }

    /**
     * Return the number of points under the node.
     * Used during decomposition rules calculations
     *
     * @return size of records list
     */
    @Override
    public int getSize() {
        return records.size();
    }
}
