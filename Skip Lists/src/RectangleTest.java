import student.TestCase;

/**
 * @author Abhinav Sethi
 * @version 2021-09-26
 * Test class to test main method by passing the path for 'testData.txt' file
 * as command line argument. The file is then parsed and the included commands
 * are executed.
 */

public class RectangleTest extends TestCase {

    /**
     * Negative test to check if invalid file message is printed if
     * invalid file is set as argument
     */
    public void testInvalidFile() {
        String[] args = {"src/Invalid.txt"};
        Rectangle1 test = new Rectangle1();
        Rectangle1.main(args);

        String output = systemOut().getHistory();
        assertEquals("Invalid file\n", output);
    }

    /**
     * Test to run a set of commands and assert the expected output.
     * Tests all possible commands.
     */
    public void testMain() {
        String[] args = {"src/testData.txt"};
        Rectangle1.main(args);

        assertTrue(systemOut().getHistory().contains("SkipList dump:\n" +
                "Node has depth 1, Value (null)\n" +
                "SkipList size is: 0\n" +
                "Rectangle rejected: (r_r, -1, -20, 3, 4)\n" +
                "Rectangle inserted: (r1, 10, 10, 5, 5)\n" +
                "Rectangle rejected: (11, 11, 0, 0)\n" +
                "Rectangle inserted: (r2, 15, 15, 5, 5)\n" +
                "Rectangle inserted: (r2, 15, 15, 5, 5)\n" +
                "Rectangle inserted: (r3, 7, 7, 10, 10)\n" +
                "Rectangle inserted: (r4, 20, 25, 7, 9)\n" +
                "Rectangle inserted: (r4, 20, 12, 3, 3)\n" +
                "Rectangle inserted: (r5, 6, 7, 11, 9)"));

        assertTrue(systemOut().getHistory().contains("SkipList size is: 7\n" +
                "Rectangles found:\n" +
                "(r4, 20, 12, 3, 3)\n" +
                "(r4, 20, 25, 7, 9)\n" +
                "Rectangle removed: (r4, 20, 12, 3, 3)\n" +
                "Rectangle removed: (r5, 6, 7, 11, 9)\n" +
                "Rectangle not removed: (r5)\n" +
                "Rectangles intersecting region (-5, -5, 20, 20):\n" +
                "(r1, 10, 10, 5, 5)\n" +
                "(r3, 7, 7, 10, 10)\n" +
                "Intersections pairs:\n" +
                "(r1, 10, 10, 5, 5 | r3, 7, 7, 10, 10)\n" +
                "(r2, 15, 15, 5, 5 | r2, 15, 15, 5, 5)\n" +
                "(r2, 15, 15, 5, 5 | r3, 7, 7, 10, 10)\n" +
                "(r2, 15, 15, 5, 5 | r2, 15, 15, 5, 5)\n" +
                "(r2, 15, 15, 5, 5 | r3, 7, 7, 10, 10)\n" +
                "(r3, 7, 7, 10, 10 | r1, 10, 10, 5, 5)\n" +
                "(r3, 7, 7, 10, 10 | r2, 15, 15, 5, 5)\n" +
                "(r3, 7, 7, 10, 10 | r2, 15, 15, 5, 5)"));

        assertTrue(systemOut().getHistory().contains("SkipList size is: 5\n" +
                "Rectangles found:\n" +
                "(r2, 15, 15, 5, 5)\n" +
                "(r2, 15, 15, 5, 5)\n" +
                "Rectangles found:\n" +
                "(r4, 20, 25, 7, 9)"));
    }
}
