
/**
 * Quadrant represents the two-dimensional sheet where point objects are
 * inserted by creating new sub quadrants as required.
 *
 * @author Abhinav Sethi 
 * @version 2021-10-23
 */
public class Quadrant extends Rectangle {
    /**
     * Quadrant constructor that calls the constructor of the super class.
     *
     * @param x    x-coordinate of the rectangle
     * @param y    y-coordinate of the rectangle
     * @param size size of the quadrant
     */
    public Quadrant(int x, int y, int size) {
        super(x, y, size, size);
    }


    /**
     * Return a new quadrant depending on the given index.
     * Index 0 represent the NW quadrant
     * Index 1 represent the NE quadrant
     * Index 2 represent the SW quadrant
     * Index 3 represent the SE quadrant
     *
     * @param index to find the quadrant
     * @return new quadrant instance
     */
    public Quadrant getNewQuadrant(int index) {
        // new quadrant will have half the size
        int newSize = (int) getHeight() / 2;

        if (index == 0) {
            // return NW quadrant
            return new Quadrant(x, y, newSize);
        }
        else if (index == 1) {
            // return NE quadrant
            return new Quadrant(x + newSize, y, newSize);
        }
        else if (index == 2) {
            // return SW quadrant
            return new Quadrant(x, y + newSize, newSize);
        }
        else {
            // return SE quadrant
            return new Quadrant(x + newSize, y + newSize, newSize);
        }
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + width;
    }
}
