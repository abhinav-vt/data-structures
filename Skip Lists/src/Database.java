import java.util.Iterator;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 *
 * @author CS Staff
 * @version 2021-08-23
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid
     * coordinates and dimensions, that is that the coordinates are
     * non-negative and that the rectangle object has some area
     * (not 0, 0, 0, 0). This insert will insert the KVPair specified into the
     * sorted SkipList appropriately
     *
     * @param pair the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle> pair) {
        // check if the given KVPair is valid
        if (!isKVPairValid(pair)) {
            System.out.println(format(Constants.REJECTED, pair));
            return;
        }
        list.insert(pair);
        System.out.println(format(Constants.INSERT_ACCEPTED, pair));
    }

    private static boolean isKVPairValid(KVPair<String, Rectangle> pair) {
        String name = pair.getKey();
        // validate key: only alphanumeric and '_' characters allowed
        if (!name.matches(Constants.VALID_REGEX)) {
            return false;
        }
        return isRectangleValid(pair.getValue());
    }

    private static boolean isRectangleValid(Rectangle r) {
        // validate rectangle: x, must be greater than or equal to 0.
        // height and width must be positive
        // and rectangle should be within 1024 * 1024
        return r.x >= 0
                && r.y >= 0
                && r.height > 0
                && r.width > 0
                && r.height <= 1024 - r.y
                && r.width <= 1024 - r.x;

    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     *
     * @param name the name of the rectangle to be removed
     */
    public void remove(String name) {
        KVPair<String, Rectangle> removedPair = list.remove(name);
        if (removedPair == null) {
            System.out.println(format(Constants.REMOVE_NOT_FOUND, name));
            return;
        }
        System.out.println(format(Constants.REMOVE_ACCEPTED, removedPair));
    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     *
     * @param x x-coordinate of the rectangle to be removed
     * @param y x-coordinate of the rectangle to be removed
     * @param w width of the rectangle to be removed
     * @param h height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        Rectangle tempRectangle = new Rectangle(x, y, w, h);
        // validate if rectangle is valid
        if (!isRectangleValid(tempRectangle)) {
            System.out.println(format(Constants.REJECTED, tempRectangle));
            return;
        }
        KVPair<String, Rectangle> removedPair =
                list.removeByValue(new Rectangle(x, y, w, h));
        if (removedPair == null) {
            System.out.println(format(Constants.REMOVE_NOT_FOUND,
                    tempRectangle));
            return;
        }
        System.out.println(format(Constants.REMOVE_ACCEPTED, removedPair));
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * You will need a SkipList Iterator for this
     *
     * @param x x-Coordinate of the region
     * @param y y-Coordinate of the region
     * @param w width of the region
     * @param h height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        Iterator<KVPair<String, Rectangle>> it = list.iterator();
        Rectangle tempRectangle = new Rectangle(x, y, w, h);
        // validate if the width and height of region is positive
        if (w <= 0 || h <= 0) {
            System.out.println(format(Constants.REJECTED, tempRectangle));
            return;
        }
        System.out.println(format(Constants.REGIONSEARCH, tempRectangle));
        while (it.hasNext()) {
            KVPair<String, Rectangle> pair = it.next();
            if (pair.getValue().intersects(tempRectangle)) {
                System.out.println(format("({0})", pair));
            }
        }
    }


    /**
     * Prints out all the rectangles that Intersect each other by calling the
     * SkipList method for intersections. You will need to use two SkipList
     * Iterators for this
     */
    public void intersections() {
        System.out.println(Constants.INTERSECTIONS);
        for (KVPair<String, Rectangle> pair1 : list) {
            for (KVPair<String, Rectangle> pair2 : list) {
                if (pair1 != pair2
                        && pair1.getValue().intersects(pair2.getValue())) {
                    System.out.println(format("({0} | {1})", pair1, pair2));
                }
            }
        }
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     *
     * @param name name of the Rectangle to be searched for
     */
    public void search(String name) {
        List<KVPair<String, Rectangle>> result = list.search(name);
        if (result.isEmpty()) {
            System.out.println(format(Constants.SEARCH_NOT_FOUND, name));
            return;
        }
        System.out.println(Constants.SEARCH_FOUND);
        for (KVPair<String, Rectangle> pair : result) {
            System.out.println(format("({0})", pair));
        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }

}
