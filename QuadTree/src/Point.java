import java.util.Objects;

/**
 * @author Abhinav Sethi 
 * @version 2021-10-23
 * <p>
 * Point class that extends the built-in java Point class.
 * The objective of the class is to override the default toString method
 * and store the point name.
 */
public class Point extends java.awt.Point {

    private final String name;

    /**
     * Constructor to initialize Point with name and coordinates.
     *
     * @param name point name
     * @param x point x coordinate
     * @param y point y coordinate
     */
    public Point(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    /**
     * Method to return point name
     *
     * @return name of the point
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (name == null) {
            return "(" + x + ", " + y + ")";
        }
        return "(" + name + ", " + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Point point = (Point) o;
        return Objects.equals(name, point.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
