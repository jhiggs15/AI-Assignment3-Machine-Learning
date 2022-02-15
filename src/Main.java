import java.io.IOException;

public class Main {
    // Real main method

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        Coordinate start = new Coordinate(0,0);
        Coordinate goal = new Coordinate(0,0);

        if(args.length != 0) {
            String filename = args[0];
            int[][] board = main.createBoard(filename, start, goal);
            int heuristic = 0;
            try {
                heuristic = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Second argument must be an integer 1-6.");
                return;
            }
            main.runAStar(heuristic, board, start, goal, false);
        }
        else {
            main.generateDataForAllHeuristics(0, start, goal);

//            for(int fileNumber = 1; fileNumber <= 10; fileNumber++ ) {
//                main.generateDataForAllHeuristics(fileNumber, start, goal);
//                System.out.println();
//
//            }

        }


    }

    public void generateDataForAllHeuristics(int fileNumber, Coordinate start, Coordinate goal) {
        int[][] board = createBoard("random" + fileNumber + ".txt", start, goal);

        System.out.println("Board" + fileNumber);
        StringBuilder columnNames = new StringBuilder();
        columnNames.append("Heuristic \t");
        columnNames.append("Number Of Nodes Expanded \t");
        columnNames.append("Effective Branching Factor \t");
        columnNames.append("Num Actions \t");
        columnNames.append("Score \t");
        columnNames.append("Path \t");
        columnNames.append("Memory Usage \t");
        columnNames.append("Runtime (s)");
        System.out.println(columnNames);

        for(int heuristic = 1 ; heuristic <= 6; heuristic++) {
            System.out.print(heuristic + "\t");
            runAStar(heuristic, board, start, goal, true);
            if(heuristic != 6) System.out.println();
        }
    }

    public void runAStar(int heuristic, int[][] board, Coordinate start, Coordinate goal, boolean isTest) {
        long startTimer = System.currentTimeMillis();

        AStar test = AStarFactory.produceAstarWithSpecificHeuristics(heuristic);
        Result result = test.findPath(board, start, goal);

        long endTimer = System.currentTimeMillis();

        double timeElapsed = (endTimer - startTimer) / 1000.0;
        long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if(!isTest) {
            System.out.println(result);
            System.out.println("Program took " + timeElapsed + " seconds");
            System.out.println("End Main Memory Used: " + mem2); // To print out memory use for analysis
        }
        else {
            StringBuilder values = result.getValues();
            values.append(mem2 + "\t");
            values.append(timeElapsed/1000.0);
            System.out.print(values);
        }


    }

    public int[][] createBoard(String filename, Coordinate start, Coordinate goal) {
        BoardReader boardReader = new BoardReader();

        try {
            return boardReader.read(filename, start, goal);
        } catch (IOException e) {
            System.out.println("Could not find file.");
            return null;

        }
    }

}
