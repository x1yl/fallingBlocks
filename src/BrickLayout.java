import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {

    private final ArrayList<Brick> bricks;
    private final ArrayList<Brick> droppedBricks;
    private final ArrayList<Integer> droppedRows;
    private int[][] grid;
    private int brickNum;

    public BrickLayout(String inputFile) {
        ArrayList<String> fileData = getFileData(inputFile);
        bricks = new ArrayList<Brick>();
        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
        }
        droppedBricks = new ArrayList<>();
        droppedRows = new ArrayList<>();
        grid = new int[30][40];
        brickNum = 0;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void dropOneBrick() {
        if (brickNum < bricks.size()) {
            int height = 0;
            int start = bricks.get(brickNum).getStart();
            int end = bricks.get(brickNum).getEnd();
            for (int j = 0; j < brickNum; j++) {
                for (int k = start; k <= end; k++) {
                    if (grid[grid.length - j - 1][k] == 1) {
                        height = j + 1;
                        System.out.println(height);
                        break;
                    }
                }
            }
            for (int j = start; j <= end; j++) {
                grid[grid.length - height - 1][j] = 1;
            }
            brickNum++;
        }
    }

    public void dropBricks() {
        grid = new int[30][40];

        for (int i = 0; i < droppedBricks.size(); i++) {
            Brick brick = droppedBricks.get(i);
            int row = droppedRows.get(i) + 1;
            boolean down = row < grid.length;
            int start = brick.getStart();
            int end = brick.getEnd();

            if (down) {
                for (int col = start; col <= end; col++) {
                    if (grid[row][col] != 0) {
                        down = false;
                        break;
                    }
                }
            }

            if (down) {
                droppedRows.set(i, row);
            }

            for (int col = start; col <= end; col++) {
                grid[droppedRows.get(i)][col] = 1;
            }
        }

        if (brickNum < bricks.size()) {
            Brick brick = bricks.get(brickNum);
            int start = brick.getStart();
            int end = brick.getEnd();

            droppedBricks.add(brick);
            droppedRows.add(0);

            for (int col = start; col <= end; col++) {
                grid[0][col] = 1;
            }

            brickNum++;
        }
    }



    public ArrayList<String> getFileData(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine()) fileData.add(s.nextLine());

        return fileData;
    }
}
