import src.Handlers;
import src.MatchResults;
import src.Football;
import src.Handball;
import src.Volleyball;
import src.Basketball;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args){
        Football[] footballTeams = new Football[4];
        Handball[] handballTeams = new Handball[4];
        Volleyball[] volleyballTeams = new Volleyball[4];
        Basketball[] basketballTeams = new Basketball[4];

        File fixtures = new File(args[0]);
        Scanner scanner;
        try {
            scanner = new Scanner(fixtures);
        } catch (FileNotFoundException fileNotFoundException) {
            return;
        }
        while(scanner.hasNext()){
            String[] match = scanner.nextLine().split("\t");
            int[] score = Stream.of(match[3].split(":")).mapToInt(Integer::parseInt).toArray(); // get score in int array
            MatchResults matchResult = new MatchResults(match[1], match[2], score);
            switch(match[0].charAt(0)){
                case 'F': // Football Matches
                    Handlers.footballMatchHandler(footballTeams, matchResult);
                    break;
                case 'H': // Handball Matches
                    Handlers.handballMatchHandler(handballTeams, matchResult);
                    break;
                case 'V': // Volleyball Matches
                    Handlers.volleyballMatchHandler(volleyballTeams, matchResult);
                    break;
                default: // Basketball Matches
                    Handlers.basketballMatchHandler(basketballTeams, matchResult);
                    break;
            }
        }

        Handlers.sortFootball(footballTeams);
        Handlers.sortHandball(handballTeams);
        Handlers.sortVolleyball(volleyballTeams);
        Handlers.sortBasketball(basketballTeams);

        try {
            File football = new File("Football.txt");
            File handball = new File("Handball.txt");
            File volleyball = new File("Volleyball.txt");
            File basketball = new File("Basketball.txt");

            FileWriter footballWriter = new FileWriter(football);
            FileWriter handballWriter = new FileWriter(handball);
            FileWriter volleyballWriter = new FileWriter(volleyball);
            FileWriter basketballWriter = new FileWriter(basketball);

            for(int i = 0; i < 4; i++){
                Football team = footballTeams[i];
                footballWriter.write(String.valueOf(i + 1) + "." + "\t" +
                        team.getName() + "\t" + String.valueOf(team.getNumOfMatches()) + "\t" +
                        String.valueOf(team.getNumOfWin()) + "\t" + String.valueOf(team.getNumOfTie()) + "\t" +
                        String.valueOf(team.getNumOfLoss()) + "\t" + String.valueOf(team.getGoalScored()) + ":" +
                        String.valueOf(team.getGoalConceded()) + "\t" + String.valueOf(team.getTotalPoint()) + "\n");
            }
            footballWriter.close();

            for(int i = 0; i < 4; i++){
                Handball team = handballTeams[i];
                handballWriter.write(String.valueOf(i + 1) + "." + "\t" +
                        team.getName() + "\t" + String.valueOf(team.getNumOfMatches()) + "\t" +
                        String.valueOf(team.getNumOfWin()) + "\t" + String.valueOf(team.getNumOfTie()) + "\t" +
                        String.valueOf(team.getNumOfLoss()) + "\t" + String.valueOf(team.getGoalScored()) + ":" +
                        String.valueOf(team.getGoalConceded()) + "\t" + String.valueOf(team.getTotalPoint()) + "\n");
            }
            handballWriter.close();

            for(int i = 0; i < 4; i++){
                Volleyball team = volleyballTeams[i];
                volleyballWriter.write(String.valueOf(i + 1) + "." + "\t" +
                        team.getName() + "\t" + String.valueOf(team.getNumOfMatches()) + "\t" +
                        String.valueOf(team.getNumOfWin()) + "\t" + String.valueOf(team.getNumOfTie()) + "\t" +
                        String.valueOf(team.getNumOfLoss()) + "\t" + String.valueOf(team.getGoalScored()) + ":" +
                        String.valueOf(team.getGoalConceded()) + "\t" + String.valueOf(team.getTotalPoint()) + "\n");
            }
            volleyballWriter.close();

            for(int i = 0; i < 4; i++){
                Basketball team = basketballTeams[i];
                basketballWriter.write(String.valueOf(i + 1) + "." + "\t" +
                        team.getName() + "\t" + String.valueOf(team.getNumOfMatches()) + "\t" +
                        String.valueOf(team.getNumOfWin()) + "\t" + String.valueOf(team.getNumOfTie()) + "\t" +
                        String.valueOf(team.getNumOfLoss()) + "\t" + String.valueOf(team.getGoalScored()) + ":" +
                        String.valueOf(team.getGoalConceded()) + "\t" + String.valueOf(team.getTotalPoint()) + "\n");
            }
            basketballWriter.close();
        } catch (IOException ioException) {}
    }
}
