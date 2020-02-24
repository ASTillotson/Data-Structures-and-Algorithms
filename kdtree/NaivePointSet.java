package kdtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Naive nearest neighbor implementation using a linear scan.
 */
public class NaivePointSet implements PointSet {
    private List<Point> points;

    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public NaivePointSet(List<Point> points) {
        this.points = new ArrayList<Point>();
        for (int i = 0; i < points.size(); i++) {
            this.points.add(new Point(points.get(i).x(), points.get(i).y()));
        }
    }

    /**
     * Returns the point in this set closest to (x, y) in O(N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        Point ans = points.get(0);
        double dist = Math.sqrt(ans.distanceSquaredTo(x, y));
        for (int i = 1; i < points.size(); i++) {
            if (Math.sqrt(points.get(i).distanceSquaredTo(x, y)) <= dist) {
                ans = points.get(i);
                dist = Math.sqrt(points.get(i).distanceSquaredTo(x, y));
            }
        }
        return ans;
    }
}
