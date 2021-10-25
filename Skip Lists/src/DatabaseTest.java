import student.TestCase;

/**
 * @author Abhinav Sethi
 * @version 2021-09-26
 *
 * Test class to test all the database commands that are invoked by
 * the CommandProcessor class. Assertions are made based on the printed
 * output and the expected values.
 */

public class DatabaseTest extends TestCase {

    /**
     * database object that stores the SkipList.
     * Object is re-instantiated before every test case.
     */
    private Database database;


    /**
     * Set up method for instantiating database object before every test case.
     */
    public void setUp() {
        database = new Database();
    }

    /**
     * Test to insert 3 Nodes in the SkipList.
     */
    public void testInsert() {
        database.insert(new KVPair<>("r1", new Rectangle(10, 10, 5, 5)));
        database.insert(new KVPair<>("r2", new Rectangle(100, 100, 50, 5)));
        database.insert(new KVPair<>("r3", new Rectangle(1000, 1000, 24, 24)));
        String output = systemOut().getHistory();
        assertEquals("Rectangle inserted: (r1, 10, 10, 5, 5)\n" +
                "Rectangle inserted: (r2, 100, 100, 50, 5)\n" +
                "Rectangle inserted: (r3, 1000, 1000, 24, 24)\n", output);
    }

    /**
     * Test to check if invalid insert commands are getting rejected correctly.
     * 1. Rejections can be due to invalid rectangle name
     * (contains restricted characters)
     * 2. Negative x or y coordinates.
     * 3. Non-positive height or width
     * 4. Rectangle exceeding the outer limit of 1024 * 1024
     */
    public void testInsertRejected() {
        database.insert(new KVPair<>("r1", new Rectangle(-10, -10, 5, 5)));
        database.insert(new KVPair<>("invalid.name",
                new Rectangle(10, 10, 5, 5)));
        database.insert(new KVPair<>("invalid*name",
                new Rectangle(10, 10, 5, 5)));
        database.insert(new KVPair<>("r2", new Rectangle(1, -1, 1, 2)));
        database.insert(new KVPair<>("r2", new Rectangle(10, 10, -1, 2)));
        database.insert(new KVPair<>("r2", new Rectangle(10, 10, 1, -2)));
        database.insert(new KVPair<>("r2", new Rectangle(-10, 10, 5, 5)));
        database.insert(new KVPair<>("r2", new Rectangle(10, -10, 5, 5)));
        database.insert(new KVPair<>("r3", new Rectangle(100, 100, 1000, 10)));
        String output = systemOut().getHistory();
        assertEquals("Rectangle rejected: (r1, -10, -10, 5, 5)\n" +
                "Rectangle rejected: (invalid.name, 10, 10, 5, 5)\n" +
                "Rectangle rejected: (invalid*name, 10, 10, 5, 5)\n" +
                "Rectangle rejected: (r2, 1, -1, 1, 2)\n" +
                "Rectangle rejected: (r2, 10, 10, -1, 2)\n" +
                "Rectangle rejected: (r2, 10, 10, 1, -2)\n" +
                "Rectangle rejected: (r2, -10, 10, 5, 5)\n" +
                "Rectangle rejected: (r2, 10, -10, 5, 5)\n" +
                "Rectangle rejected: (r3, 100, 100, 1000, 10)\n", output);
    }

    /**
     * Test to remove nodes by key from the SkipList.
     * 1. Negative test where key does not exist.
     * 2. Positive tests where key exists.
     */
    public void testRemove() {
        insertData();

        database.remove("invalid");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle not removed: (invalid)"));
        database.remove("r1");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r1, 10, 10, 5, 5)"));
        database.remove("r3");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r3, 300, 100, 50, 30)"));
        database.remove("r4");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r4, 100, 200, 50, 25)"));
        database.remove("r3");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r3, 200, 150, 50, 20)"));
        database.remove("r2");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r2, 100, 100, 20, 5)"));
    }

    /**
     * Test to remove nodes by value from the SkipList.
     * 1. Negative test where no node exists for the given value.
     * 2. Positive tests where value exists.
     */
    public void testRemoveByValue() {
        insertData();

        database.remove(101, 100, -20, 5);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle rejected: (101, 100, -20, 5)"));
        database.remove(101, 100, 20, 5);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle not removed: (101, 100, 20, 5)"));
        database.remove(200, 150, 50, 20);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r3, 200, 150, 50, 20)"));
        database.remove(300, 100, 50, 30);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r3, 300, 100, 50, 30)"));
        database.remove(100, 100, 20, 5);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r2, 100, 100, 20, 5)"));
        database.remove(100, 200, 50, 25);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r4, 100, 200, 50, 25)"));
        database.remove(10, 10, 5, 5);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle removed: (r1, 10, 10, 5, 5)"));
    }


    /**
     * Test to check regionsearch command output.
     * Should print all rectangles that lie in the given region.
     */
    public void testRegionSearch() {
        insertData();

        database.regionsearch(5, 5, 200, 200);
        assertTrue(systemOut().getHistory().contains(
                "Rectangles intersecting region (5, 5, 200, 200):\n" +
                        "(r1, 10, 10, 5, 5)\n" +
                        "(r2, 100, 100, 20, 5)\n" +
                        "(r3, 200, 150, 50, 20)\n" +
                        "(r4, 100, 200, 50, 25)"));
    }

    /**
     * Negative test to check regionsearch is rejected
     * for rectangle with height or width as non-positive.
     */
    public void testRegionSearchRejected() {
        insertData();

        database.regionsearch(5, 5, -1, 200);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle rejected: (5, 5, -1, 200)\n"));
        database.regionsearch(5, 5, 200, 0);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle rejected: (5, 5, 200, 0)\n"));
    }

    /**
     * Test to check intersections command output.
     * Should print all pairs of rectangles that intersect each other.
     */
    public void testIntersections() {
        database.insert(new KVPair<>("r1", new Rectangle(10, 10, 500, 500)));
        database.insert(new KVPair<>("r2", new Rectangle(100, 100, 20, 5)));
        database.insert(new KVPair<>("r3", new Rectangle(200, 150, 50, 20)));
        database.insert(new KVPair<>("r3", new Rectangle(300, 100, 50, 30)));
        database.insert(new KVPair<>("r4", new Rectangle(100, 150, 200, 250)));

        database.intersections();
        assertTrue(systemOut().getHistory().contains(
                "Intersections pairs:\n" +
                        "(r1, 10, 10, 500, 500 | r2, 100, 100, 20, 5)\n" +
                        "(r1, 10, 10, 500, 500 | r3, 300, 100, 50, 30)\n" +
                        "(r1, 10, 10, 500, 500 | r3, 200, 150, 50, 20)\n" +
                        "(r1, 10, 10, 500, 500 | r4, 100, 150, 200, 250)\n" +
                        "(r2, 100, 100, 20, 5 | r1, 10, 10, 500, 500)\n" +
                        "(r3, 300, 100, 50, 30 | r1, 10, 10, 500, 500)\n" +
                        "(r3, 200, 150, 50, 20 | r1, 10, 10, 500, 500)\n" +
                        "(r3, 200, 150, 50, 20 | r4, 100, 150, 200, 250)\n" +
                        "(r4, 100, 150, 200, 250 | r1, 10, 10, 500, 500)\n" +
                        "(r4, 100, 150, 200, 250 | r3, 200, 150, 50, 20)"));


    }

    /**
     * Test to check search command output.
     * Should print all rectangles matching the given key as input.
     */
    public void testSearch() {
        insertData();

        database.search("r3");
        assertTrue(systemOut().getHistory().contains(
                "Rectangles found:\n" +
                        "(r3, 300, 100, 50, 30)\n" +
                        "(r3, 200, 150, 50, 20)"));

        database.search("r1");
        assertTrue(systemOut().getHistory().contains(
                "Rectangles found:\n" +
                        "(r1, 10, 10, 5, 5)"));

        database.search("r5");
        assertTrue(systemOut().getHistory().contains(
                "Rectangle not found: r5"));

    }

    /**
     * Utility method to insert 5 nodes to the skiplist.
     */
    private void insertData() {
        database.insert(new KVPair<>("r1", new Rectangle(10, 10, 5, 5)));
        database.insert(new KVPair<>("r2", new Rectangle(100, 100, 20, 5)));
        database.insert(new KVPair<>("r3", new Rectangle(200, 150, 50, 20)));
        database.insert(new KVPair<>("r3", new Rectangle(300, 100, 50, 30)));
        database.insert(new KVPair<>("r4", new Rectangle(100, 200, 50, 25)));
    }

}
