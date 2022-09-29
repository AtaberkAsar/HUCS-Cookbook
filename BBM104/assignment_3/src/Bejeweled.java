import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Bejeweled {
    private static int score = 0;
    private static int totalScore = 0;
    private static int[] mapSize;
    private ArrayList<ArrayList<Jewel>> gameMap = new ArrayList<>();
    private int rowCount = 0,
                colCount = 0;
    private ArrayList<Player> leaderBoardArrayList = new ArrayList<>();

    /**
     * Creates a new Bejeweled Game
     * @param gameGridFile gameGrid.txt
     * @param commandFile command.txt
     * @param leaderBoardFile leaderboard.txt
     */
    public Bejeweled(String gameGridFile, String commandFile, String leaderBoardFile){
        readGameGridFile(gameGridFile);
        readLeaderBoard(leaderBoardFile);
        readCommandFile(commandFile, leaderBoardFile);
    }

    private void readGameGridFile(String gameGridFile){
        File gameGrid = new File(gameGridFile);
        Scanner gameGridScanner;
        try {
            gameGridScanner = new Scanner(gameGrid);
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("\ngameGrid file not found");
            return;
        }
        while(gameGridScanner.hasNextLine()){
            String[] row = gameGridScanner.nextLine().trim().split(" ");
            ArrayList<Jewel> jewelRow = new ArrayList<>();
            colCount = 0;
            for(String jewel: row){
                switch(jewel){
                    case "D":
                        jewelRow.add(new Diamond());
                        break;
                    case "S":
                        jewelRow.add(new Square());
                        break;
                    case "T":
                        jewelRow.add(new Triangle());
                        break;
                    case "W":
                        jewelRow.add(new Wildcard());
                        break;
                    case "/":
                        jewelRow.add(new Slash());
                        break;
                    case "-":
                        jewelRow.add(new Dash());
                        break;
                    case "+":
                        jewelRow.add(new Plus());
                        break;
                    case "\\":
                        jewelRow.add(new Backslash());
                        break;
                    case "|":
                        jewelRow.add(new Pipe());
                        break;
                }
                colCount++;
            }
            rowCount++;
            gameMap.add(jewelRow);
        }
        mapSize = new int[] {rowCount, colCount};
        gameGridScanner.close();
    }

    private void readCommandFile(String commandFile, String leaderboardFile){
        File command = new File(commandFile);
        File monitoring = new File("monitoring.txt");
        Scanner commandScanner;
        PrintStream monitoringWriter;
        try {
            commandScanner = new Scanner(command);
            monitoringWriter = new PrintStream(monitoring);
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("\nError encountered while opening commandFile or monitoringFile");
            return;
        }
        monitoringWriter.println("Game grid:\n");
        monitoringWriter.println(gameMapPrinter());

        while(commandScanner.hasNextLine()){
            String[] commandLine = commandScanner.nextLine().trim().split(" ");

            if(commandLine[0].equals("E")){
                String playerName = commandScanner.nextLine().trim();
                monitoringWriter.println("Select coordinate or enter E to end the game: E\n");
                monitoringWriter.printf("Total score: %d points\n\n", totalScore);
                monitoringWriter.printf("Enter name: %s\n\n", playerName);

                leaderBoardArrayList.add(new Player(playerName, totalScore));
                Collections.sort(leaderBoardArrayList);

                int size = leaderBoardArrayList.size();
                int i = Collections.binarySearch(leaderBoardArrayList, new Player(playerName, null)) + 1;
                if(i == 1)
                    monitoringWriter.printf("Your rank is %d/%d, your score is %d points higher than %s\n\n", i,
                                            size,
                                            totalScore - leaderBoardArrayList.get(1).getPoint(),
                                            leaderBoardArrayList.get(1).getName());
                else if(i == size)
                    monitoringWriter.printf("Your rank is %d/%d, your score is %d points lower than %s\n\n", i,
                                            size,
                                            leaderBoardArrayList.get(i - 2).getPoint() - totalScore,
                                            leaderBoardArrayList.get(i - 2).getName());
                else
                    monitoringWriter.printf("Your rank is %d/%d, your score is %d points lower than %s and %d points higher than %s\n\n", i,
                                            size,
                                            leaderBoardArrayList.get(i - 2).getPoint() - totalScore,
                                            leaderBoardArrayList.get(i - 2).getName(),
                                            totalScore - leaderBoardArrayList.get(i).getPoint(),
                                            leaderBoardArrayList.get(i).getName());

                monitoringWriter.print("Good bye!");
                monitoringWriter.close();

                try {
                    Files.write(Paths.get(leaderboardFile),
                                String.format("\n%s %d", playerName, totalScore).getBytes(),
                                StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.err.println("\nI/O error occurs while writing to leaderboard.txt");
                    return;
                }
                break;
            }
            monitoringWriter.printf("Select coordinate or enter E to end the game: %s %s\n\n", commandLine[0], commandLine[1]);
            try {
                makeMove(commandLine);
            } catch (NullJewelException e) {
                monitoringWriter.println("Please enter a valid coordinate\n");
                continue;
            }
            monitoringWriter.println(gameMapPrinter());
            monitoringWriter.printf("Score: %d points\n\n", score);
            totalScore += score;
            score = 0;
        }
    }

    private void readLeaderBoard(String leaderBoardFile){
        File leaderBoard = new File(leaderBoardFile);
        Scanner leaderBoardScanner;
        try {
            leaderBoardScanner = new Scanner(leaderBoard);
        } catch (FileNotFoundException e) {
            System.err.println("\nleaderboardFile not found");
            return;
        }
        while(leaderBoardScanner.hasNextLine()){
            String[] nameAndPoints = leaderBoardScanner.nextLine().trim().split(" ");
            leaderBoardArrayList.add(new Player(nameAndPoints[0], Integer.parseInt(nameAndPoints[1])));
        }
        leaderBoardScanner.close();
    }

    /**
     * Makes a valid move every turn
     * @param commandLine commands can be either "rowNum ColNum", or "E"
     * @throws NullJewelException in case of player tries to make a move on a null jewel cell
     */
    private void makeMove(String[] commandLine) throws NullJewelException{
        int[] coordinate = new int[] {Integer.parseInt(commandLine[0]), Integer.parseInt(commandLine[1])};
        gameMap.get(coordinate[0]).get(coordinate[1]).popJewel(gameMap, coordinate[0], coordinate[1]);
        updateGameMap();
    }

    /**
     * updates gameMap so that it moves empty cells upwards after every turn
     */
    private void updateGameMap(){
        transposeGameMap();
        for(ArrayList<Jewel> jewels: gameMap)
            jewels.sort(new JewelComparator());
        // sorting transposed map rows, and then re-transposing the map,
        // so that empty jewels will be moved upwards by JewelComparator
        transposeGameMap();
    }

    private void transposeGameMap(){
        ArrayList<ArrayList<Jewel>> tempGameMap = new ArrayList<>();
        ArrayList<Jewel> tempJewelRow;

        for(int i = 0; i < colCount; i++){
            tempJewelRow = new ArrayList<>();
            for(int j = 0; j < rowCount; j++){
                tempJewelRow.add(gameMap.get(j).get(i));
            }
            tempGameMap.add(tempJewelRow);
        }
        gameMap = tempGameMap;
    }

    /**
     * gameMapPrinter function uses ArrayList's original toString method to print desired output
     * @return StringBuilder gameMap
     */
    private StringBuilder gameMapPrinter(){
        StringBuilder stringBuilder = new StringBuilder();
        for(ArrayList<Jewel> jewels: gameMap){
            stringBuilder.append(jewels.toString().replaceAll("\\[|\\]|,", "")).append("\n");
            // ArrayList's original toString prints with [, ], and , characters
        }
        return stringBuilder;
    }

    public static void setScore(int score) {
        Bejeweled.score = score;
    }

    public static int[] getMapSize() {
        return mapSize;
    }
}
