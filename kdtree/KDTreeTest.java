package kdtree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KDTreeTest {

    @Test
    public void slowTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(4, 4);
        Point p6 = new Point(1, 5);
        PointSet points = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6));
        PointSet fastPoints = new KDTreePointSet(List.of(p1, p2, p3, p4, p5, p6));
        Point location = new Point(0, 7);
        Point nearestPoint = points.nearest(location.x(), location.y());
        Point fastNearPoint = fastPoints.nearest(location.x(), location.y());
        double x = nearestPoint.x();
        double y = nearestPoint.y();
        double fastX = fastNearPoint.x();
        double fastY = fastNearPoint.y();
        System.out.println(fastX + "," + fastY);
        System.out.println(x + "," + y);
    }

    @Test
    public void randomizedTest() {
        Random random = new Random(42);
        List<Point> nodes = new ArrayList<Point>();
        for (int i = 0; i < 2000; i++) {
            Point newPoint = new Point(random.nextDouble(), random.nextDouble());
            nodes.add(newPoint);
        }
        PointSet slowPoints = new NaivePointSet(nodes);
        PointSet fastPoints = new KDTreePointSet(nodes);
        Point location = new Point(random.nextDouble(), random.nextDouble());
        Point slowNearestPoint = slowPoints.nearest(location.x(), location.y());
        Point fastNearestPoint = fastPoints.nearest(location.x(), location.y());
        double slowX = slowNearestPoint.x();
        double slowY = slowNearestPoint.y();
        double fastX = fastNearestPoint.x();
        double fastY = fastNearestPoint.y();
        System.out.println("Target for nearest " + location.x() + "," + location.y());
        System.out.println("NaivePointSet x,y " + slowX + "," + slowY);
        System.out.println("KDTreePointSet x,y " + fastX + "," + fastY);
        System.out.println("Naive distance from goal " + Math.sqrt(slowNearestPoint.distanceSquaredTo(location)));
        System.out.println("KDTree distance from goal " + Math.sqrt(fastNearestPoint.distanceSquaredTo(location)));

    }
}
