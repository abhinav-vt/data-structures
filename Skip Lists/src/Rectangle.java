/**
 * @author Abhinav Sethi
 * @version 2021-09-26
 *
 * Rectangle class that extends the built-in java Rectangle class.
 * The objective of the class is to override the default toString method
 * in order to print data in the required format.
 */

public class Rectangle extends java.awt.Rectangle {

    /**
     * Rectangle constructor that calls the constructor of the super class.
     * @param x x-coordinate of the rectangle
     * @param y y-coordinate of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     */
    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Overridden method to build string in the required format.
     * @return String
     */
    @Override
    public String toString() {
        return x + ", " + y + ", " + width + ", " + height;
    }
}
