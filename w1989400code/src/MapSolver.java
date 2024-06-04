import java.util.LinkedList;
import java.util.Queue;

public class MapSolver {
        // The map grid
    private char[][] map;
        // Number of rows in the map
    private int rows;
       // Number of columns in the map
    private int cols;
       // Start position coordinates [row, col]
    private int[] startPos;
        // Finish position coordinates [row, col]
    private int[] finishPos;

    // parameterised constructor
    public MapSolver(char[][] map, int[] startPos, int[] finishPos) {
        this.map = map;
        this.rows = map.length;
        this.cols = map[0].length;
        this.startPos = startPos;
        this.finishPos = finishPos;
    }

    //  Method to check if a given row and column are valid within the map boundaries
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < map[row].length && map[row][col] != '0';
    }
    // Method for printing the map
    public void printMap() {
        for (char[] row : map) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    //This is a method to find the shortest path
    public String findShortestPath() {
        boolean[][] visited = new boolean[rows][cols];

        // Queue to perform breadth-first search
        Queue<Step> queue = new LinkedList<>();
        queue.add(new Step(startPos[0], startPos[1], 0, ""));

        // this shows the directions of the  array
        String[] directions = {"Move to UP", "Move to DOWN", "Move to LEFT", "Move to RIGHT"};
        // Offsets for moving in different directions
        int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //  its a Breadth-first search
        while (!queue.isEmpty()) {
            Step currentStep = queue.poll();
            if (currentStep.xCoordinate == finishPos[0] && currentStep.yCoordinate == finishPos[1]) {
                return currentStep.toString();
            }
        // for finding the adjacent cells

            for (int i = 0; i < offsets.length; i++) {
                int row = currentStep.xCoordinate;
                int col = currentStep.yCoordinate;
                int distance = currentStep.distance;
                String path = currentStep.path;

                //  Move until hitting a wall or reaching the finish position

                while (row >= 0 && row < rows &&
                        col >= 0 && col < cols &&
                        map[row][col] != '0' &&
                        (row != finishPos[0] || col != finishPos[1])) {

                    row = row + offsets[i][0];
                    col = col + offsets[i][1];
                    distance += 1;
                }
              // adjusting the position and distance

                if (row != finishPos[0] || col != finishPos[1]) {
                    row -= offsets[i][0];
                    col -= offsets[i][1];
                    distance -= 1;
                }
                // If the adjacent cell is not visited, add it to the queue

                if (!visited[row][col]) {
                    visited[currentStep.xCoordinate][currentStep.yCoordinate] = true;
                    queue.add(new Step(row, col, distance, path + directions[i]));
                }
            }
        }
        return "No path found!";
    }
   //   Its a  inner class representing a step in the path
    static class Step implements Comparable<Step> {
        int xCoordinate;
        int yCoordinate;
        String path;
        int distance;

        // This is a  Constructor to initialize the step with coordinates, distance, and path
        Step(int xCoordinate, int yCoordinate, int distance, String path) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
            this.distance = distance;
            this.path = path + " (" + (yCoordinate + 1) + ", " + (xCoordinate + 1) + ")\n";
        }

        // Its a method Override "String method" to represent the step's information as a string
        @Override
        public String toString() {
            return "TOTAL DISTANCE : " + distance + "\n" + " \nSTART: " + path + "Done!";
        }

        // Its a  method Override to compare steps based on their distances
        @Override
        public int compareTo(Step coordinate) {
            return this.distance == coordinate.distance ?
                    this.path.compareTo(coordinate.path) :
                    this.distance - coordinate.distance;
        }
    }
}
