import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {
    public static char[][] readMapFromFile(String filename) {
        char[][] map = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            List<String> lines = new ArrayList<>();
            String line;

            // Read lines from the file
            while ((line = reader.readLine()) != null) {
                // Checks  if there's an  empty line
                if (line.trim().isEmpty()) {
                    throw new IOException("Empty line found in the input file.");
                }
                lines.add(line);
            }

            // Check for inconsistent row lengths
            int numRows = lines.size();
            if (numRows == 0) {
                throw new IOException("Input file is empty.");
            }
            int numCols = lines.get(0).length();
            for (int i = 1; i < numRows; i++) {
                if (lines.get(i).length() != numCols) {
                    throw new IOException("Inconsistent row lengths found in the input file.");
                }
            }

            // Initializes the map array with the correct dimensions
            map = new char[numRows][numCols];

            // Populate the map array
            for (int i = 0; i < numRows; i++) {
                line = lines.get(i);
                for (int j = 0; j < numCols; j++) {
                    map[i][j] = line.charAt(j);
                }
            }

            // Find start and finish positions
            int startCount = 0, finishCount = 0;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    char cell = map[i][j];
                    if (cell == 'S') {
                        startCount++;
                    } else if (cell == 'F') {
                        finishCount++;
                    }
                }
            }
            if (startCount != 1 || finishCount != 1) {
                throw new IOException("Input file must contain exactly one start ('S') and one finish ('F') position.");
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
        return map;
    }

    //To find the position for the map
    public static int[] findPosition(char[][] map, char target) {
        if (map == null) {
            return null;
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}