import student.TestCase;

/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * Test class to test main method by passing the path for 'testData.txt' file
 * as command line argument. The file is then parsed and the included commands
 * are executed.
 */

public class PointTest extends TestCase {

    /**
     * Negative test to check if invalid file message is printed if
     * invalid file is set as argument
     */
    public void testInvalidFile() {
        String[] args = {"src/Invalid.txt"};
        Point2.main(args);

        String output = systemOut().getHistory();
        assertEquals("Invalid file\n", output);
    }

    /**
     * Test to run a set of commands and assert the expected output.
     * Tests all possible commands.
     */
    public void testMain() {
        String[] args = {"src/testData.txt"};
        Point2.main(args);

        assertTrue(systemOut().getHistory().contains("QuadTree Dump:\n" +
                "Node at 0, 0, 1024: Internal\n" +
                "  Node at 0, 0, 512: Internal\n" +
                "    Node at 0, 0, 256: Internal\n" +
                "      Node at 0, 0, 128:\n" +
                "      (p_p, 1, 20)\n" +
                "      (p, 10, 30)\n" +
                "      (p_42, 1, 20)\n" +
                "      Node at 128, 0, 128: Empty\n" +
                "      Node at 0, 128, 128: Empty\n" +
                "      Node at 128, 128, 128:\n" +
                "      (far_point, 200, 200)\n" +
                "    Node at 256, 0, 256: Empty\n" +
                "    Node at 0, 256, 256: Empty\n" +
                "    Node at 256, 256, 256: Empty\n" +
                "  Node at 512, 0, 512: Empty\n" +
                "  Node at 0, 512, 512: Empty\n" +
                "  Node at 512, 512, 512: Empty\n" +
                "QuadTree Size: 13 QuadTree Nodes Printed."));

        assertTrue(systemOut().getHistory().contains("Duplicate Points:\n" +
                "(1, 20)\n" +
                "Point Found (p_p, 1, 20)\n" +
                "Points Intersecting Region: (0, 0, 25, 25)\n" +
                "Point Found: (p_p, 1, 20)\n" +
                "Point Found: (p_42, 1, 20)\n" +
                "4 QuadTree Nodes Visited\n" +
                "Point (p_p, 1, 20) Removed\n" +
                "Point (p, 10, 30) Removed\n" +
                "Duplicate Points:"));

        assertTrue(systemOut().getHistory().contains("QuadTree Dump:\n" +
                "Node at 0, 0, 1024:\n" +
                "(p_42, 1, 20)\n" +
                "(far_point, 200, 200)\n" +
                "QuadTree Size: 1 QuadTree Nodes Printed."));
    }
}
