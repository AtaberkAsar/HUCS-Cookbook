package src;

public class Basketball {
    private String name;
    private int numOfMatches, numOfWin, numOfTie, numOfLoss, totalPoint;
    private int numOfGoalScored, numOfGoalConceded;

    public Basketball(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getNumOfMatches(){
        return this.numOfMatches;
    }

    public int getNumOfWin(){
        return this.numOfWin;
    }

    public int getNumOfTie(){
        return this.numOfTie;
    }

    public int getNumOfLoss(){
        return this.numOfLoss;
    }

    public int getTotalPoint(){
        return this.totalPoint;
    }

    public int getGoalScored(){
        return this.numOfGoalScored;
    }

    public int getGoalConceded(){
        return this.numOfGoalConceded;
    }

    public void setGoalScored(int numOfGoals){
        this.numOfGoalScored += numOfGoals;
    }

    public void setGoalConceded(int numOfGoals){
        this.numOfGoalConceded += numOfGoals;
    }

    public void Win(){
        this.numOfMatches++;
        this.numOfWin++;
    }
    public void Lose(){
        this.numOfMatches++;
        this.numOfLoss++;
    }
    public void Tie(){
        this.numOfMatches++;
        this.numOfTie++;
    }
    public int getScore(){
        this.totalPoint = 2 * this.numOfWin + this.numOfLoss;
        return this.totalPoint;
    }
}
