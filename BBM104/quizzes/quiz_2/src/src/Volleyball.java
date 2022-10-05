package src;

public class Volleyball {

    private String name;
    private int numOfMatches, numOfWin, numOfTie, numOfLoss, totalPoint;
    private int numOfGoalScored, numOfGoalConceded;

    public Volleyball(String name){
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

    public void setScore(int numOfGoalScoredNow, int numOfGoalConcededNow){
        this.setGoalScored(numOfGoalScoredNow);
        this.setGoalConceded(numOfGoalConcededNow);
        int diff = numOfGoalScoredNow - numOfGoalConcededNow;
        switch(diff){
            case 3:
            case 2:
                this.totalPoint +=3;
                break;
            case 1:
                this.totalPoint += 2;
                break;
            case -1:
                this.totalPoint += 1;
                break;
            default:
                break;
        }
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
}
