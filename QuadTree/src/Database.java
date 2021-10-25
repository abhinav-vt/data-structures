import java.util.List;

import static java.text.MessageFormat.format;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList and QuadTree. The responsibility of this class is to further
 * interpret variations of commands and do some error checking of those
 * commands. This class further interpreting the command means that the two
 * types of remove will be overloaded methods for if we are removing by name
 * or by coordinates. Many of these methods will simply call the appropriate
 * version of the SkipList and QuadTree method after some preparation.
 *
 * @author CS Staff
 * @version 2021-08-23
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the point and then
    // a point object, these are stored in a KVPair,
    // see the KVPair class for more information
    private final SkipList<String, Point> list;

    /**
     * Root node of the quad tree.
     */
    private QuadTreeNode root;

    /**
     * Base quadrant having 1024 size
     */
    private final Quadrant worldQuadrant;


    /**
     * The constructor for this class initializes a SkipList object with String
     * and point a its parameters.
     */
    public Database() {
        list = new SkipList<String, Point>();
        root = QuadTreeFlyweightNode.getInstance();
        worldQuadrant = new Quadrant(0, 0, 1024);
    }


    /**
     * Inserts the KVPair in the SkipList if the point has valid
     * coordinates and dimensions, that is that the coordinates are
     * non-negative and that the point object has some area
     * (not 0, 0, 0, 0). This insert will insert the KVPair specified into the
     * sorted SkipList appropriately
     *
     * @param point the Point to be inserted
     */
    public void insert(Point point) {
        // check if the given Point is valid
        if (isPointInvalid(point)) {
            System.out.println(format(Constants.REJECTED, point));
            return;
        }

        // reject insert of point with same name
        // and coordinates already exists
        if (root.exists(point)) {
            System.out.println(format(Constants.REJECTED, point));
            return;
        }

        list.insert(new KVPair<>(point.getName(), point));
        root = root.insert(point, worldQuadrant);
        System.out.println(format(Constants.INSERT_ACCEPTED, point));
    }

    private static boolean isPointInvalid(Point point) {
        String name = point.getName();
        // validate key: only alphanumeric and '_' characters allowed
        if (name != null && !name.matches(Constants.VALID_REGEX)) {
            return true;
        }
        return point.x < 0
                || point.y < 0
                || point.x >= 1024
                || point.y >= 1024;
    }


    /**
     * Removes a point with the name "name" if available. If not an error
     * message is printed to the console.
     *
     * @param name the name of the point to be removed
     */
    public void remove(String name) {
        KVPair<String, Point> removedPair = list.remove(name);
        if (removedPair == null) {
            System.out.println(format(Constants.REMOVE_NOT_FOUND, name));
            return;
        }
        root = root.remove(removedPair.getValue(), worldQuadrant);
        System.out.println(format(Constants.REMOVE_ACCEPTED,
                removedPair.getValue()));
    }


    /**
     * Removes a point with the specified coordinates if available. If not
     * an error message is printed to the console.
     *
     * @param x x-coordinate of the point to be removed
     * @param y x-coordinate of the point to be removed
     */
    public void remove(int x, int y) {
        Point tempPoint = new Point(null, x, y);
        // validate if Point is valid
        if (isPointInvalid(tempPoint)) {
            System.out.println(format(Constants.REJECTED, tempPoint));
            return;
        }

        Point searchResult = root.search(tempPoint, worldQuadrant);
        if (searchResult == null) {
            System.out.println(format(Constants.SEARCH_NOT_FOUND,
                    tempPoint));
            return;
        }
        list.remove(searchResult.getName());
        root = root.remove(searchResult, worldQuadrant);
        System.out.println(format(Constants.REMOVE_ACCEPTED, searchResult));
    }


    /**
     * Displays all the Points inside the specified region.
     *
     * @param x x-Coordinate of the region
     * @param y y-Coordinate of the region
     * @param w width of the region
     * @param h height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        Rectangle tempRegion = new Rectangle(x, y, w, h);
        // validate if the width and height of region is positive
        if (w <= 0 || h <= 0) {
            System.out.println(format(Constants.REJECTED_RECTANGLE,
                    tempRegion));
            return;
        }
        System.out.println(format(Constants.REGIONSEARCH, tempRegion));
        int numVisited = root.regionSearch(tempRegion, worldQuadrant);
        System.out.println(format(Constants.NODES_VISITED, numVisited));
    }


    /**
     * Prints out all the Points that Intersect each other by calling the
     * Quadtree method for duplicates.
     */
    public void duplicates() {
        System.out.println(Constants.DUPLICATES);
        root.duplicates();
    }


    /**
     * Prints out all the Points with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     *
     * @param name name of the Point to be searched for
     */
    public void search(String name) {
        List<KVPair<String, Point>> result = list.search(name);
        if (result.isEmpty()) {
            System.out.println(format(Constants.SEARCH_NOT_FOUND, name));
            return;
        }
        for (KVPair<String, Point> pair : result) {
            System.out.println(format(Constants.SEARCH_FOUND, pair.getValue()));
        }
    }


    /**
     * Prints out a dump of the SkipList and QuadTree which includes
     * information about the size of the SkipList and QuadTree and shows
     * all of the contents.
     */
    public void dump() {
        list.dump();

        System.out.println(Constants.DUMP_HEADER_TREE);
        int numVisited = root.dump(worldQuadrant, 1);
        System.out.println(format(Constants.DUMP_SIZE_TREE, numVisited));
    }

}
