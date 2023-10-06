import java.util.*;

public class Point  implements Comparable<Point>{
    public int x;
    public int y;

    public double cost = Double.POSITIVE_INFINITY;

    private static final List<String> DIRECTIONS;
    private static final List<String> E_DIRECTIONS;
    private static final Map<String, int[]> DIRECTION_ENUM;
    static {
        DIRECTIONS = List.of("W", "E", "N", "S", "SW", "NW", "SE", "NE");
        E_DIRECTIONS = List.of("E", "NE", "SE");
        DIRECTION_ENUM = Map.of("NW", new int[]{-1, -1}, "N", new int[]{-1, 0}, "NE", new int[]{-1, 1},
                "W", new int[]{0, -1}, "E", new int[]{0, 1},
                "SW", new int[]{1, -1}, "S", new int[]{1, 0}, "SE", new int[]{1, 1});
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // You can add additional variables and methods if necessary.

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public List<Point> getNeighbours(int width, int height) {
        List<Point> neighbours = new ArrayList<>();

        for (String direction: DIRECTIONS) {
            int[] d = DIRECTION_ENUM.get(direction);
            int x_prime = x + d[1];
            int y_prime = y + d[0];
            if(x_prime > -1 && x_prime < width && y_prime > -1 && y_prime < height)
                neighbours.add(new Point(x_prime, y_prime));
        }

        return neighbours;
    }

    public List<Point> getENeighbours(int width, int height) {
        List<Point> neighbours = new ArrayList<>();

        for (String direction: E_DIRECTIONS) {
            int[] d = DIRECTION_ENUM.get(direction);
            int x_prime = x + d[1];
            int y_prime = y + d[0];
            if(x_prime > -1 && y_prime > -1 && y_prime < height)
                neighbours.add(new Point(x_prime, y_prime));
        }

        return neighbours;
    }

    @Override
    public int compareTo(Point o) {
        return Double.compare(cost, o.cost);
    }
}
