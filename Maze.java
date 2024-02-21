import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Maze {

    public static void main(String[] args) {
        try {
            readMaze();
            if (startX == -1 || startY == -1) {
                System.out.println("Unable to find the starting position.");
                return;
            }
            if (solveMaze(startX, startY)) {
                System.out.println("The maze has been successfully solved!");
            } else {
                System.out.println("Sorry, we could not find a solution for this maze.");
            }
            printMaze();
        } catch (FileNotFoundException e) {
            System.out.println("The maze file was not found.");
        }
    }

    private static boolean solveMaze(int x, int y) {
        System.out.println("Currently Searching: (" + x + ", " + y + ")");
        if (x < 0 || y < 0 || x >= rows || y >= cols || maze[x][y] == 'X') {
            System.out.println("Blocked or already visited: (" + x + ", " + y + ")");
            return false;
        }
        if (maze[x][y] == '-') {
            System.out.println("Exit found at: (" + x + ", " + y + ")");
            return true;
        }
        if (maze[x][y] == '+') {

            if (x != startX || y != startY) {
                System.out.println("Blocked or already visited (except the start): (" + x + ", " + y + ")");
                return false;
            }
        }

        maze[x][y] = '+';
        System.out.println("Marking as a part of the path: (" + x + ", " + y + ")");

        if (solveMaze(x - 1, y) || solveMaze(x + 1, y) || solveMaze(x, y - 1) || solveMaze(x, y + 1)) {
            return true;
        }

        if (x != startX || y != startY) {
            maze[x][y] = '.';
            System.out.println("Marking as a dead end: (" + x + ", " + y + ")");
        }
        return false;
    }

    private static void readMaze() throws FileNotFoundException {
        File file = new File("maze.dat");
        Scanner scanner = new Scanner(file);
        rows = scanner.nextInt();
        cols = scanner.nextInt();
        scanner.nextLine();

        maze = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            maze[i] = line.toCharArray();
            if (startX == -1) {
                int startIdx = line.indexOf('+');
                if (startIdx != -1) {
                    startX = i;
                    startY = startIdx;
                }
            }
        }
        scanner.close();
    }

    private static void printMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();

        }

    }

    private static char[][] maze;
    private static int rows;
    private static int cols;
    private static int startX = -1;
    private static int startY = -1;
}
