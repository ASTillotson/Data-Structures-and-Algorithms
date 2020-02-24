package astar;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private TreeMapMinPQ<Vertex> PQ;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private SolverOutcome outcome;
    private int numStates;
    private List<Vertex> solution;
    private double timeSpent;

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solution = new ArrayList<>();
        numStates = 0;
        PQ = new TreeMapMinPQ<Vertex>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        PQ.add(start, 0);
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        while (!PQ.isEmpty()) {
            if (PQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }
            if (sw.elapsedTime() >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            Vertex curr = PQ.removeSmallest();
            numStates++;
            List<WeightedEdge<Vertex>> neighbors = input.neighbors(curr);
            //relaxing curr's edges and checking them
            for (WeightedEdge<Vertex> edge : neighbors) {
                //check if its in the PQ already
                if (PQ.contains(edge.to())) {
                    //it is, so check if the new path is faster than whats stored
                    double oldPriority = distTo.get(edge.to());
                    double newPriority = edge.weight() + distTo.get(curr);
                    //If the new way to this node is faster than the old way to this node
                    if (newPriority < oldPriority) {
                        PQ.changePriority(edge.to(), distTo.get(curr) + edge.weight()
                                + input.estimatedDistanceToGoal(edge.to(), end));
                        distTo.replace(edge.to(), distTo.get(curr) + edge.weight());
                        edgeTo.replace(edge.to(), curr);
                    }
                    //its not already in there, so set its data and add it to the PQ.
                } else if (!edgeTo.containsKey(edge.to())) {
                    PQ.add(edge.to(), distTo.get(curr) + edge.weight() + input.estimatedDistanceToGoal(edge.to(), end));
                    distTo.put(edge.to(), distTo.get(curr) + edge.weight());
                    edgeTo.put(edge.to(), curr);
                }
            }
        }
        if (outcome == null) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        //Create the solution list
        if (outcome == SolverOutcome.SOLVED) {
            List<Vertex> reverseSolution = new ArrayList<>();
            Vertex curr = end;
            while (curr != null) {
                reverseSolution.add(curr);
                curr = edgeTo.get(curr);
            }
            for (int i = reverseSolution.size() - 1; i >= 0; i--) {
                solution.add(reverseSolution.get(i));
            }
        }
        timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        if (outcome == SolverOutcome.SOLVED) {
            return distTo.get(solution.get(solution.size() - 1));
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return numStates;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
