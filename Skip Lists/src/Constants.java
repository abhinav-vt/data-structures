/**
 * @author Abhinav Sethi
 * @version 2021-09-26
 *
 * Class that defines all constant strings used in the program.
 * for the application.
 */
public class Constants {

    /**
     * Regex to accept only alphanumeric and '_' characters
     */
    static final String VALID_REGEX = "[a-zA-Z0-9_]+";

    /**
     * Rejection message for when given rectangle is not valid
     */
    static final String REJECTED = "Rectangle rejected: ({0})";

    /**
     * Insert command accepted message
     */
    static final String INSERT_ACCEPTED = "Rectangle inserted: ({0})";

    /**
     * Remove command output when rectangle not found
     */
    static final String REMOVE_NOT_FOUND = "Rectangle not removed: ({0})";

    /**
     * Remove command output when rectangle removed
     */
    static final String REMOVE_ACCEPTED = "Rectangle removed: ({0})";

    /**
     * Regionsearch command output
     */
    static final String REGIONSEARCH = "Rectangles intersecting region ({0}):";

    /**
     * Intersections command output
     */
    static final String INTERSECTIONS = "Intersections pairs:";

    /**
     * Search command output when rectangle not found
     */
    static final String SEARCH_NOT_FOUND = "Rectangle not found: {0}";

    /**
     * Remove command output when rectangle found
     */
    static final String SEARCH_FOUND = "Rectangles found:";

    /**
     * Dump command output header
     */
    static final String DUMP_HEADER = "SkipList dump:";

    /**
     * Dump command output with node details
     */
    static final String DUMP_DETAIL = "Node has depth {0}, Value ({1})";

    /**
     * Dump command output for skip list size
     */
    static final String DUMP_SIZE = "SkipList size is: {0}";

}
