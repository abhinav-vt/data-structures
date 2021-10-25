/**
 * @author Abhinav Sethi
 * @version 2021-09-26
 * Enum class that defines all the supported commands as input
 * for the application.
 */
public enum Command {

    /**
     * Dump command to print out all the nodes in the skiplist.
     */
    DUMP,
    /**
     * Insert command to add a new SkipNode with the provided parameters.
     */
    INSERT,
    /**
     * Search command prints out all the rectangles with the specified name
     * in the SkipList.
     */
    SEARCH,
    /**
     * Remove command remove the specified SkipNode either by name or the
     * rectangle parameters depending on the number of provided values.
     */
    REMOVE,
    /**
     * Regionsearch command to print out all rectangles that lie with the
     * given rectangular coordinates.
     */
    REGIONSEARCH,
    /**
     * Intersections command to print out all possible pairs of rectangles
     * in the SkipList that intersect.
     */
    INTERSECTIONS
}
