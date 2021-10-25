/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * <p>
 * Class that defines all constant strings used in the program.
 * for the application.
 */
public class Constants {

    /**
     * Number of quadtree nodes visited
     */
    public static final String NODES_VISITED = "{0} QuadTree Nodes Visited";
    /**
     * Regex to accept only alphanumeric and '_' characters
     */
    static final String VALID_REGEX = "[a-zA-Z0-9_]+";

    /**
     * Rejection message for when given point is not valid
     */
    static final String REJECTED = "Point Rejected: {0}";

    /**
     * Rejection message for when given rectangle is not valid
     */
    static final String REJECTED_RECTANGLE = "Rectangle Rejected: ({0})";

    /**
     * Insert command accepted message
     */
    static final String INSERT_ACCEPTED = "Point Inserted: {0}";

    /**
     * Remove command output when point not found
     */
    static final String REMOVE_NOT_FOUND = "Point Not Removed: {0}";

    /**
     * Remove command output when point removed
     */
    static final String REMOVE_ACCEPTED = "Point {0} Removed";

    /**
     * Regionsearch command output
     */
    static final String REGIONSEARCH = "Points Intersecting Region: ({0})";

    /**
     * Regionsearch point found output
     */
    static final String REGION_SEARCH_FOUND = "Point Found: {0}";

    /**
     * DUPLICATES command output
     */
    static final String DUPLICATES = "Duplicate Points:";

    /**
     * Search command output when point not found
     */
    static final String SEARCH_NOT_FOUND = "Point Not Found: {0}";

    /**
     * Remove command output when point found
     */
    static final String SEARCH_FOUND = "Point Found {0}";

    /**
     * Dump command output header for list
     */
    static final String DUMP_HEADER_LIST = "SkipList Dump:";

    /**
     * Dump command output header for tree
     */
    static final String DUMP_HEADER_TREE = "QuadTree Dump:";

    /**
     * Dump command output with node details
     */
    static final String DUMP_DETAIL_LIST = "level: {0} Value: {1}";

    /**
     * Dump command output for skip list size
     */
    static final String DUMP_SIZE_LIST = "Size is: {0}";

    /**
     * Dump command output for tree size
     */
    static final String DUMP_SIZE_TREE =
            "QuadTree Size: {0} QuadTree Nodes Printed.";

}
