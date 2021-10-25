import student.TestCase;

/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * <p>
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
        database.insert(new Point("r1", 10, 10));
        database.insert(new Point("r2", 100, 100));
        database.insert(new Point("r3", 1000, 1000));
        database.insert(new Point("r3", 1000, 1000));
        String output = systemOut().getHistory();
        assertEquals("Point Inserted: (r1, 10, 10)\n" +
                "Point Inserted: (r2, 100, 100)\n" +
                "Point Inserted: (r3, 1000, 1000)\n" +
                "Point Rejected: (r3, 1000, 1000)\n", output);
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
        database.insert(new Point("r1", -10, -10));
        database.insert(new Point("invalid.name", 10, 10));

        database.insert(new Point("r2", 1, -1));
        database.insert(new Point("r2", 10, 10));

        String output = systemOut().getHistory();
        assertEquals("Point Rejected: (r1, -10, -10)\n" +
                "Point Rejected: (invalid.name, 10, 10)\n" +
                "Point Rejected: (r2, 1, -1)\n" +
                "Point Inserted: (r2, 10, 10)\n", output);
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
                "Point Not Removed: invalid"));
        database.remove("r1");
        assertTrue(systemOut().getHistory().contains(
                "Point (r1, 10, 10) Removed"));
        database.remove("r3");
        assertTrue(systemOut().getHistory().contains(
                "Point (r3, 200, 150) Removed"));
        database.remove("r4");
        assertTrue(systemOut().getHistory().contains(
                "Point (r4, 300, 100) Removed"));
        database.remove("r3");
        assertTrue(systemOut().getHistory().contains(
                "Point (r3, 200, 150) Removed"));
        database.remove("r2");
        assertTrue(systemOut().getHistory().contains(
                "Point (r2, 100, 100) Removed"));
    }

    /**
     * Test to remove nodes by value from the SkipList.
     * 1. Negative test where no node exists for the given value.
     * 2. Positive tests where value exists.
     */
    public void testRemoveByValue() {
        insertData();

        database.remove(-101, 100);
        assertTrue(systemOut().getHistory().contains(
                "Point Rejected: (-101, 100)"));
        database.remove(101, 100);
        assertTrue(systemOut().getHistory().contains(
                "Point Not Found: (101, 100)"));
        database.remove(200, 150);
        assertTrue(systemOut().getHistory().contains(
                "Point (r3, 200, 150) Removed"));
        database.remove(300, 100);
        assertTrue(systemOut().getHistory().contains(
                "Point (r4, 300, 100) Removed"));
        database.remove(100, 100);
        assertTrue(systemOut().getHistory().contains(
                "Point (r2, 100, 100) Removed"));
        database.remove(100, 200);
        assertTrue(systemOut().getHistory().contains(
                "Point (r5, 100, 200) Removed"));
        database.remove(10, 10);
        assertTrue(systemOut().getHistory().contains(
                "Point (r1, 10, 10) Removed"));
        database.remove(10, 10);
        assertTrue(systemOut().getHistory().contains(
                "Point Not Found: (10, 10)"));
    }


    /**
     * Test to check regionsearch command output.
     * Should print all rectangles that lie in the given region.
     */
    public void testRegionSearch() {
        insertData();

        database.regionsearch(5, 5, 200, 200);
        assertTrue(systemOut().getHistory().contains(
                "Points Intersecting Region: (5, 5, 200, 200)\n" +
                        "Point Found: (r1, 10, 10)\n" +
                        "Point Found: (r2, 100, 100)\n" +
                        "Point Found: (r5, 100, 200)\n" +
                        "Point Found: (r3, 200, 150)\n" +
                        "6 QuadTree Nodes Visited\n"));
    }

    /**
     * Negative test to check regionsearch is rejected
     * for rectangle with height or width as non-positive.
     */
    public void testRegionSearchRejected() {
        insertData();

        database.regionsearch(5, 5, -1, 200);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle Rejected: (5, 5, -1, 200)\n"));
        database.regionsearch(5, 5, 200, 0);
        assertTrue(systemOut().getHistory().contains(
                "Rectangle Rejected: (5, 5, 200, 0)\n"));
    }

    /**
     * Test to check intersections command output.
     * Should print all pairs of rectangles that intersect each other.
     */
    public void testDuplicates() {
        database.insert(new Point("r1", 10, 10));
        database.insert(new Point("r2", 100, 100));
        database.insert(new Point("r3", 100, 100));
        database.insert(new Point("r4", 100, 100));
        database.insert(new Point("r5", 100, 100));
        database.insert(new Point("r6", 10, 10));
        database.insert(new Point("r7", 100, 100));
        database.insert(new Point("r8", 100, 100));
        database.insert(new Point("r9", 100, 100));
        database.insert(new Point("r10", 100, 150));

        database.duplicates();
        assertTrue(systemOut().getHistory().contains(
                "Duplicate Points:\n" +
                        "(10, 10)\n" +
                        "(100, 100)"));


    }

    /**
     * Test to check search command output.
     * Should print all rectangles matching the given key as input.
     */
    public void testSearch() {
        insertData();

        database.search("r3");
        assertTrue(systemOut().getHistory().contains(
                "Point Found (r3, 200, 150)\n"));

        database.search("r1");
        assertTrue(systemOut().getHistory().contains(
                "Point Found (r1, 10, 10)"));

        database.search("r5");
        assertTrue(systemOut().getHistory().contains(
                "Point Found (r5, 100, 200)"));

        database.search("r10");
        assertTrue(systemOut().getHistory().contains(
                "Point Not Found: r10"));

    }

    /**
     * Utility method to insert 5 nodes to the skiplist.
     */
    private void insertData() {
        database.insert(new Point("r1", 10, 10));
        database.insert(new Point("r2", 100, 100));
        database.insert(new Point("r3", 200, 150));
        database.insert(new Point("r4", 300, 100));
        database.insert(new Point("r5", 100, 200));
    }

}
