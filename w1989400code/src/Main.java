import java.util.Scanner;
import java.io.File;
import java.time.Duration;
import java.time.Instant;

 class FindShortestPath {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();

        // Defining  the absolute paths to the "maze" and "examples" folders
        String mazeFolderPath = "C:/Users/User/Desktop/SD2prog/AlgorithmCW/puzzle file/";
        String examplesFolderPath = "C:/Users/User/Desktop/SD2prog/AlgorithmCW/example/";

        // Search for the file in the "maze" folder
        String mazeFilePath = mazeFolderPath + filename;
        if (new File(mazeFilePath).exists()) {
            processFile(mazeFilePath);
            return;
        }

        // Search for the file in the "examples" folder
        String examplesFilePath = examplesFolderPath + filename;
        if (new File(examplesFilePath).exists()) {
            processFile(examplesFilePath);
            return;
        }

        // If the file is not found in either folder, print an error message
        System.out.println("File not found: " );
    }

    private static void processFile(String filePath) {


        char[][] map = MapParser.readMapFromFile(filePath);

        int[] start = MapParser.findPosition(map, 'S');
        int[] finish = MapParser.findPosition(map, 'F');

        if (start == null || finish == null) {
            System.out.println("Starting or finishing positions not found in file: " + filePath);
            return;
        }

        MapSolver solver = new MapSolver(map, start, finish);

        System.out.println("\nMap for file: " );
        solver.printMap();

        System.out.println("\nShortest Path for file: " );
        Instant startTime = Instant.now();
        String result = solver.findShortestPath();
        Instant endTime = Instant.now();

        System.out.println(result);

        System.out.println("Execution time: " + Duration.between(startTime, endTime).toMillis() + " milliseconds");
    }
}
