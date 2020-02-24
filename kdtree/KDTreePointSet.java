package kdtree;

import java.util.ArrayList;
import java.util.List;

public class KDTreePointSet implements PointSet {
    private List<Point> points;
    private Node root;

    private static final class Node {
        public Node left;
        public Node right;
        private Point point;

        private Node(Point point) {
            this.point = point;
        }
    }

    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public KDTreePointSet(List<Point> points) {
        this.points = new ArrayList<Point>();
        this.root = new Node(points.get(0));
        for (int i = 0; i < points.size(); i++) {
            this.points.add(new Point(points.get(i).x(), points.get(i).y()));
            insert(this.points.get(i));
        }
    }

    private void insert(Point newPoint) {
        Node newNode = new Node(newPoint);
        xInsertHelper(root, newPoint, newNode);
    }

    private void xInsertHelper(Node location, Point destination, Node newNode) {
        if (location.point.x() < destination.x()) {
            if (location.right == null) {
                location.right = newNode;
            } else {
                location = location.right;
                yInsertHelper(location, destination, newNode);
            }
        } else if (location.point.x() > destination.x()) {
            if (location.left == null) {
                location.left = newNode;
            } else {
                location = location.left;
                yInsertHelper(location, destination, newNode);
            }
        }
    }

    private void yInsertHelper(Node location, Point destination, Node newNode) {
        if (location.point.y() < destination.y()) {
            if (location.right == null) {
                location.right = newNode;
            } else {
                location = location.right;
                xInsertHelper(location, destination, newNode);
            }
        } else if (location.point.y() > destination.y()) {
            if (location.left == null) {
                location.left = newNode;
            } else {
                location = location.left;
                xInsertHelper(location, destination, newNode);
            }
        }
    }
    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        Point destination = new Point(x, y);
        return xLocationHelper(this.root, destination, this.root).point;
    }

    private Node xLocationHelper(Node location, Point destination, Node best) {
        if (location == null) {
            return best;
        }
        if (Math.sqrt(location.point.distanceSquaredTo(destination)) <
                Math.sqrt(best.point.distanceSquaredTo(destination))) {
            best = location;
        }
        Node betterLocation = null;
        Node worstLocation = null;
        if (location.point.x() < destination.x()) {
            betterLocation = location.right;
            worstLocation = location.left;
        } else if (location.point.x() > destination.x()) {
            betterLocation = location.left;
            worstLocation = location.right;
        }
        best = yLocationHelper(betterLocation, destination, best);
        if (Math.abs(location.point.x() - destination.x()) < Math.sqrt(best.point.distanceSquaredTo(destination))) {
            best = yLocationHelper(worstLocation, destination, best);
        }
        return best;
    }

    private Node yLocationHelper(Node location, Point destination, Node best) {
        if (location == null) {
            return best;
        }
        if (Math.sqrt(location.point.distanceSquaredTo(destination)) <
                Math.sqrt(best.point.distanceSquaredTo(destination))) {
            best = location;
        }
        Node betterLocation = null;
        Node worstLocation = null;
        if (location.point.y() < destination.y()) {
            betterLocation = location.right;
            worstLocation = location.left;
        } else if (location.point.y() > destination.y()) {
            betterLocation = location.left;
            worstLocation = location.right;
        }
        best = xLocationHelper(betterLocation, destination, best);
        if (Math.abs(location.point.y() - destination.y()) < Math.sqrt(best.point.distanceSquaredTo(destination))) {
            best = xLocationHelper(worstLocation, destination, best);
        }
        return best;
    }
}
