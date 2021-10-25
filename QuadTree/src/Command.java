/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * Enum class that defines all the supported commands as input
 * for the application.
 */
public enum Command {

    /**
     * Dump command to print out all the nodes in the skiplist and quadtree.
     */
    DUMP,
    /**
     * Insert command to add a new SkipNode and tree node with the provided
     * parameters.
     */
    INSERT,
    /**
     * Search command prints out all the points with the specified name
     * in the tree.
     */
    SEARCH,
    /**
     * Remove command remove the specified Point either by name or the
     * coordinates parameters depending on the number of provided values.
     */
    REMOVE,
    /**
     * Regionsearch command to print out all points that lie with the
     * given point coordinates.
     */
    REGIONSEARCH,
    /**
     * Duplicates command to print out all points having multiple instances
     * of the same coordinates.
     */
    DUPLICATES
}
