package src;

public class MatchResults {

    private String teamWinner, teamLoser;
    private int result;
    private int[] score;


    public MatchResults(String team0, String team1, int[] score){
        this.teamWinner = team0;
        this.teamLoser = team1;
        this.score = score;
        this.result = this.evaluateScore();
    }
    public String getTeamWinner(){
        return this.teamWinner;
    }
    public String getTeamLoser(){
        return this.teamLoser;
    }
    public int[] getScore(){
        return this.score;
    }

    public int getResult() {
        return this.result;
    }

    public int evaluateScore(){
        if(this.score[0] == this.score[1]){
            return 0;
        }
        else if(this.score[0] < this.score[1]){
            String tempWinner = this.teamWinner;
            this.teamWinner = this.teamLoser;
            this.teamLoser = tempWinner;
            int tempScore = this.score[0];
            this.score[0] = this.score[1];
            this.score[1] = tempScore;
        }
        return 1;
    }
}
