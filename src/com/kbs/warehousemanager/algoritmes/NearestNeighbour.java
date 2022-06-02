package com.kbs.warehousemanager.algoritmes;
import java.util.ArrayList;
import java.awt.*;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class NearestNeighbour {

    private int currentSolutionLength;
    private int x;
    private int y;
    private ArrayList<Point> currentIteration = new ArrayList<>(25);
    private ArrayList<Point> solution = new ArrayList<>(25);

    // generate path using orderlist
    public ArrayList<Point> generatePath(ArrayList<Point> orderList) {
        ThreadLocalRandom lclRnd = ThreadLocalRandom.current();
        ArrayList<Integer> visitedPos = new ArrayList<>(25);
        int oListSize = orderList.size();

        // set starting position for beginning for loop, add as starting point of solution
        int currentPos = lclRnd.nextInt(0, oListSize);
        solution.clear();
        solution.add(orderList.get(currentPos));
        visitedPos.add(currentPos);

        // calculating loop
        for (int i = 0; i < oListSize-1; i++) {
            Point workingPos = orderList.get(currentPos);

            // populate currentIteration for current iteration of calculations
            currentIteration.clear();
            for (int i2 = 0; i2 < oListSize; i2++) {
                if (visitedPos.contains(i2)) {
                    currentIteration.add(i2, null);
                } else {
                    currentIteration.add(i2, orderList.get(i2));
                }
            }

            // calculate distances from workingPos to all remaining positions
            ArrayList<Integer> distances = new ArrayList<>(25);
            for (int i3 = 0; i3 < currentIteration.size(); i3++) {
                if(currentIteration.get(i3) == null) {
                    distances.add(i3, 999999);
                    continue;
                }
                Point calcPos = currentIteration.get(i3);
                int calcX = workingPos.x - calcPos.x;
                int calcY = workingPos.y - calcPos.y;
                int distance = Math.abs(calcX) + Math.abs(calcY);
                distances.add(i3, distance);
                // System.out.println(calcPos + ", " + distance);
            }
            // System.out.println(i + ": Huidige positie " + workingPos + "\n---- ITERATIE IS KLAAR ----\n");

            // now that loop is finished check what the next smallest distance is, find out what position it's assigned to
            // then add that position as the next in the solution list
            int winningPos = distances.indexOf(Collections.min(distances));
            distances.clear();
            solution.add(orderList.get(winningPos));
            visitedPos.add(winningPos);
            currentPos = winningPos;
        }
        return solution;
    }

    public void printOrderList()
    {
        System.out.println("Solution list\n[");
        for (int id = 0; id < solution.size(); id++)
        {
            Point point = solution.get(id);
            System.out.format("\t%d = (%d, %d)\n", id, point.x, point.y);
        }
        System.out.println("]");
    }
}
