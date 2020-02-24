package seamcarving;

import astar.AStarGraph;
import astar.AStarSolver;
import astar.WeightedEdge;
import edu.princeton.cs.algs4.Picture;
import org.apache.commons.math3.geometry.spherical.twod.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
            throw new IndexOutOfBoundsException();
        }
        double xEnergy = xEnergyGradient(x, y);
        double yEnergy = yEnergyGradient(x, y);
        return Math.sqrt(xEnergy + yEnergy);
    }

    private double xEnergyGradient(int x, int y) {
        Color xPlusOne;
        Color xMinusOne;
        if (this.width() == 1) {
            xPlusOne = this.get(0, y);
            xMinusOne = this.get(0, y);
        } else if (x == 0) {
            xPlusOne = this.get(x + 1, y);
            xMinusOne = this.get(this.width() - 1, y);
        } else if (x == this.width() - 1) {
            xPlusOne = this.get(0, y);
            xMinusOne = this.get(x - 1, y);
        } else {
            xPlusOne = this.get(x + 1, y);
            xMinusOne = this.get(x - 1, y);
        }
        double redX = (xPlusOne.getRed() - xMinusOne.getRed()) * (xPlusOne.getRed() - xMinusOne.getRed());
        double greenX = (xPlusOne.getGreen() - xMinusOne.getGreen()) * (xPlusOne.getGreen() - xMinusOne.getGreen());
        double blueX = (xPlusOne.getBlue() - xMinusOne.getBlue()) * (xPlusOne.getBlue() - xMinusOne.getBlue());
        return redX + greenX + blueX;
    }

    private double yEnergyGradient(int x, int y) {
        Color yPlusOne;
        Color yMinusOne;
        if (this.height() == 1) {
            yPlusOne = this.get(x, 0);
            yMinusOne = this.get(x, 0);
        } else if (y == 0) {
            yPlusOne = this.get(x, y + 1);
            yMinusOne = this.get(x, this.height() - 1);
        } else if (y == this.height() - 1) {
            yPlusOne = this.get(x, 0);
            yMinusOne = this.get(x, y - 1);
        } else {
            yPlusOne = this.get(x, y + 1);
            yMinusOne = this.get(x, y - 1);
        }
        double redY = (yPlusOne.getRed() - yMinusOne.getRed()) * (yPlusOne.getRed() - yMinusOne.getRed());
        double greenY = (yPlusOne.getGreen() - yMinusOne.getGreen()) * (yPlusOne.getGreen() - yMinusOne.getGreen());
        double blueY = (yPlusOne.getBlue() - yMinusOne.getBlue()) * (yPlusOne.getBlue() - yMinusOne.getBlue());
        return redY + greenY + blueY;
    }

    public class Vertex {
        private int x;
        private int y;
        private double weight;

        public Vertex(int x, int y, double weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Vertex vertex = (Vertex) o;
            return x == vertex.x &&
                    y == vertex.y &&
                    Double.compare(vertex.weight, weight) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, weight);
        }
    }

    public class PixelGraph implements AStarGraph<Vertex> {
        AStarSeamCarver carver;

        public PixelGraph(AStarSeamCarver carver) {
            this.carver = carver;
        }

        public List<WeightedEdge<Vertex>> neighbors(Vertex v) {
            List<WeightedEdge<Vertex>> theNeighbors = new ArrayList<>();
            Vertex leftNeighbor;
            Vertex middleNeighbor;
            Vertex rightNeighbor;
            if (v.x == -1 && v.y == -1) {
                for (int i = 0; i < this.carver.picture.width(); i++) {
                    Vertex neighbor = new Vertex(i, v.y + 1, this.carver.energy(i, v.y + 1));
                    theNeighbors.add(new WeightedEdge(v, neighbor, neighbor.weight));
                }
            }
            if (v.y == this.carver.picture.height() - 1) {
                Vertex neighbor = new Vertex(this.carver.picture.width(), this.carver.picture.height(), 0);
                theNeighbors.add(new WeightedEdge(v, neighbor, neighbor.weight));
            }
            if ((v.x != -1 && v.x != 0) && (v.y != carver.picture.height() && v.y != carver.picture.height() - 1)) {
                leftNeighbor = new Vertex(v.x - 1, v.y + 1, carver.energy(v.x - 1, v.y + 1));
                theNeighbors.add(new WeightedEdge(v, leftNeighbor, leftNeighbor.weight));
            }
            if ((v.x != -1 && v.y != -1) && (v.y != carver.picture.height() && v.y != carver.picture.height() - 1)) {
                middleNeighbor = new Vertex(v.x, v.y + 1, carver.energy(v.x, v.y + 1));
                theNeighbors.add(new WeightedEdge(v, middleNeighbor, middleNeighbor.weight));
            }
            if (v.x != -1 && v.y != -1 && v.x != carver.picture.width() - 1 && v.y != carver.picture.height() &&
                    v.y != carver.picture.height() - 1) {
                rightNeighbor = new Vertex(v.x + 1, v.y + 1, carver.energy(v.x + 1, v.y + 1));
                theNeighbors.add(new WeightedEdge(v, rightNeighbor, rightNeighbor.weight));
            }
            return theNeighbors;
        }

        @Override
        public double estimatedDistanceToGoal(Vertex s, Vertex goal) {
            return 0;
        }
    }

    public int[] findHorizontalSeam() {
        Picture original = this.picture();
        Picture rotatedPic = new Picture(this.picture.height(), this.picture.width());
        for (int i = 0; i <= this.picture.height() - 1; i++) {
            for (int j = this.picture.width() - 1; j >= 0; j--) {
                rotatedPic.set((this.picture.height() - 1) - i, (this.picture.width() - 1) - j,
                        this.picture.get((this.picture.width() - 1) - j, i));
            }
        }
        this.setPicture(rotatedPic);
        AStarSolver solver = seamFinder();
        int[] ans = solutionTranslator(solver, true);
        this.setPicture(original);
        return ans;
    }

    private AStarSolver seamFinder() {
        AStarGraph graph = new PixelGraph(this);
        Vertex start = new Vertex(-1, -1, 0);
        Vertex end = new Vertex(this.width(), this.height(), 0);
        AStarSolver solver = new AStarSolver(graph, start, end, 30);
        return solver;
    }

    public int[] findVerticalSeam() {
        AStarSolver solver = seamFinder();
        return solutionTranslator(solver, false);
    }

    private int[] solutionTranslator(AStarSolver solver, boolean horizontal) {
        int[] ans = new int[solver.solution().size() - 2];
        List<Vertex> solution = solver.solution();
        int iterator = 1;
        for (int i = 0; i < solution.size() - 2; i++) {
            if (horizontal) {
                ans[i] = (this.picture.width() - 1) - solution.get(iterator).x;
            } else {
                ans[i] = solution.get(iterator).x;
            }
            iterator++;
        }
        return ans;
    }
}