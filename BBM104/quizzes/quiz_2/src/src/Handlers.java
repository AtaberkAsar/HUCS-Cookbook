package src;

public class Handlers {
    public static void footballMatchHandler(Football[] footballTeams, MatchResults matchResult){
        for(int i = 0; i < 4; i++){ // for winning team
            if(footballTeams[i] == null){ // if football team does not exist
                footballTeams[i] = new Football(matchResult.getTeamWinner());
                if(matchResult.getResult() == 1){
                    footballTeams[i].Win();
                }
                else{
                    footballTeams[i].Tie();
                }
                footballTeams[i].setGoalScored(matchResult.getScore()[0]);
                footballTeams[i].setGoalConceded(matchResult.getScore()[1]);
                break;
            }
            else if(footballTeams[i].getName().equals(matchResult.getTeamWinner())){ // if football team exists
                if(matchResult.getResult() == 1){
                    footballTeams[i].Win();
                }
                else{
                    footballTeams[i].Tie();
                }
                footballTeams[i].setGoalScored(matchResult.getScore()[0]);
                footballTeams[i].setGoalConceded(matchResult.getScore()[1]);
                break;
            }
        }
        for(int i = 0; i < 4; i++) { // for losing team
            if (footballTeams[i] == null) { // if football team does not exist
                footballTeams[i] = new Football(matchResult.getTeamLoser());
                if (matchResult.getResult() == 1) {
                    footballTeams[i].Lose();
                } else {
                    footballTeams[i].Tie();
                }
                footballTeams[i].setGoalConceded(matchResult.getScore()[0]);
                footballTeams[i].setGoalScored(matchResult.getScore()[1]);
                break;
            }
            else if (footballTeams[i].getName().equals(matchResult.getTeamLoser())) { // if football team exists
                if (matchResult.getResult() == 1) {
                    footballTeams[i].Lose();
                } else {
                    footballTeams[i].Tie();
                }
                footballTeams[i].setGoalConceded(matchResult.getScore()[0]);
                footballTeams[i].setGoalScored(matchResult.getScore()[1]);
                break;
            }
        }
    }
    public static void handballMatchHandler(Handball[] handballTeams, MatchResults matchResult){
        for(int i = 0; i < 4; i++){ // for winning team
            if(handballTeams[i] == null){ // if handball team does not exist
                handballTeams[i] = new Handball(matchResult.getTeamWinner());
                if(matchResult.getResult() == 1){
                    handballTeams[i].Win();
                }
                else{
                    handballTeams[i].Tie();
                }
                handballTeams[i].setGoalScored(matchResult.getScore()[0]);
                handballTeams[i].setGoalConceded(matchResult.getScore()[1]);
                break;
            }
            else if(handballTeams[i].getName().equals(matchResult.getTeamWinner())){ // if handball team exists
                if(matchResult.getResult() == 1){
                    handballTeams[i].Win();
                }
                else{
                    handballTeams[i].Tie();
                }
                handballTeams[i].setGoalScored(matchResult.getScore()[0]);
                handballTeams[i].setGoalConceded(matchResult.getScore()[1]);
                break;
            }
        }
        for(int i = 0; i < 4; i++){ // for losing team
            if(handballTeams[i] == null){ // if handball team does not exist
                handballTeams[i] = new Handball(matchResult.getTeamLoser());
                if(matchResult.getResult() == 1){
                    handballTeams[i].Lose();
                }
                else{
                    handballTeams[i].Tie();
                }
                handballTeams[i].setGoalConceded(matchResult.getScore()[0]);
                handballTeams[i].setGoalScored(matchResult.getScore()[1]);
                break;
            }
            else if(handballTeams[i].getName().equals(matchResult.getTeamLoser())){ // if handball team exists
                if(matchResult.getResult() == 1){
                    handballTeams[i].Lose();
                }
                else{
                    handballTeams[i].Tie();
                }
                handballTeams[i].setGoalConceded(matchResult.getScore()[0]);
                handballTeams[i].setGoalScored(matchResult.getScore()[1]);
                break;
            }
        }
    }
    public static void volleyballMatchHandler(Volleyball[] volleyballTeams, MatchResults matchResult){
        for(int i = 0; i < 4; i++){
            if(volleyballTeams[i] == null){ // if volleyball team does not exist
                volleyballTeams[i] = new Volleyball(matchResult.getTeamWinner());
                if(matchResult.getResult() == 1){
                    volleyballTeams[i].Win();
                }
                else{
                    volleyballTeams[i].Tie();
                }
                volleyballTeams[i].setScore(matchResult.getScore()[0], matchResult.getScore()[1]);
                break;
            }
            else if(volleyballTeams[i].getName().equals(matchResult.getTeamWinner())){ // if volleyball team exists
                if(matchResult.getResult() == 1){
                    volleyballTeams[i].Win();
                }
                else{
                    volleyballTeams[i].Tie();
                }
                volleyballTeams[i].setScore(matchResult.getScore()[0], matchResult.getScore()[1]);
                break;
            }
        }
        for(int i = 0; i < 4; i++){
            if(volleyballTeams[i] == null){
                volleyballTeams[i] = new Volleyball(matchResult.getTeamLoser());
                if(matchResult.getResult() == 1){
                    volleyballTeams[i].Lose();
                }
                else{
                    volleyballTeams[i].Tie();
                }
                volleyballTeams[i].setScore(matchResult.getScore()[1], matchResult.getScore()[0]);
                break;
            }
            else if(volleyballTeams[i].getName().equals(matchResult.getTeamLoser())){
                if(matchResult.getResult() == 1){
                    volleyballTeams[i].Lose();
                }
                else{
                    volleyballTeams[i].Tie();
                }
                volleyballTeams[i].setScore(matchResult.getScore()[1], matchResult.getScore()[0]);
                break;
            }
        }
    }
    public static void basketballMatchHandler(Basketball[] basketballTeams, MatchResults matchResult){
        for(int i = 0; i < 4; i++){
            if(basketballTeams[i] == null){ // if basketball team does not exist
                basketballTeams[i] = new Basketball(matchResult.getTeamWinner());
                if(matchResult.getResult() == 1){
                    basketballTeams[i].Win();
                }
                else{
                    basketballTeams[i].Tie();
                }
                basketballTeams[i].setGoalScored(matchResult.getScore()[0]);
                basketballTeams[i].setGoalConceded(matchResult.getScore()[1]);
                break;
            }
            else if(basketballTeams[i].getName().equals(matchResult.getTeamWinner())){ // if basketball team exists
                if(matchResult.getResult() == 1){
                    basketballTeams[i].Win();
                }
                else{
                    basketballTeams[i].Tie();
                }
                basketballTeams[i].setGoalScored(matchResult.getScore()[0]);
                basketballTeams[i].setGoalConceded(matchResult.getScore()[1]);
                break;
            }
        }
        for(int i = 0; i < 4; i++){
            if(basketballTeams[i] == null){
                basketballTeams[i] = new Basketball(matchResult.getTeamLoser());
                if(matchResult.getResult() == 1){
                    basketballTeams[i].Lose();
                }
                else{
                    basketballTeams[i].Tie();
                }
                basketballTeams[i].setGoalConceded(matchResult.getScore()[0]);
                basketballTeams[i].setGoalScored(matchResult.getScore()[1]);
                break;
            }
            else if(basketballTeams[i].getName().equals(matchResult.getTeamLoser())){
                if(matchResult.getResult() == 1){
                    basketballTeams[i].Lose();
                }
                else{
                    basketballTeams[i].Tie();
                }
                basketballTeams[i].setGoalConceded(matchResult.getScore()[0]);
                basketballTeams[i].setGoalScored(matchResult.getScore()[1]);
                break;
            }
        }
    }

    public static void sortFootball(Football[] footballTeams){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4 - i - 1; j++){
                if(footballTeams[j].getScore() < footballTeams[j + 1].getScore()){
                    Football temp = footballTeams[j];
                    footballTeams[j] = footballTeams[j + 1];
                    footballTeams[j + 1] = temp;
                }
                else if(footballTeams[j].getScore() == footballTeams[j + 1].getScore()){
                    if((footballTeams[j].getGoalScored() <= footballTeams[j].getGoalConceded()) &&
                        (footballTeams[j + 1].getGoalScored() > footballTeams[j + 1].getGoalConceded())){
                        Football temp = footballTeams[j];
                        footballTeams[j] = footballTeams[j + 1];
                        footballTeams[j + 1] = temp;
                    }
                    else{
                        if(footballTeams[j].getName().compareTo(footballTeams[j + 1].getName()) > 0){
                            Football temp = footballTeams[j];
                            footballTeams[j] = footballTeams[j + 1];
                            footballTeams[j + 1] = temp;
                        }
                    }
                }
            }
        }
    }
    public static void sortHandball(Handball[] handballTeams){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4 - i - 1; j++){
                if(handballTeams[j].getScore() < handballTeams[j + 1].getScore()){
                    Handball temp = handballTeams[j];
                    handballTeams[j] = handballTeams[j + 1];
                    handballTeams[j + 1] = temp;
                }
                else if(handballTeams[j].getScore() == handballTeams[j + 1].getScore()){
                    if((handballTeams[j].getGoalScored() <= handballTeams[j].getGoalConceded()) &&
                        (handballTeams[j + 1].getGoalScored() > handballTeams[j + 1].getGoalConceded())){
                        Handball temp = handballTeams[j]; //TO DO this shit!!!!!!!!!!!!
                        handballTeams[j] = handballTeams[j + 1];
                        handballTeams[j + 1] = temp;
                    }
                    else{
                        if(handballTeams[j].getName().compareTo(handballTeams[j + 1].getName()) > 0){
                            Handball temp = handballTeams[j];
                            handballTeams[j] = handballTeams[j + 1];
                            handballTeams[j + 1] = temp;
                        }
                    }
                }
            }
        }
    }
    public static void sortVolleyball(Volleyball[] volleyballTeams){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4 - i - 1; j++){
                if(volleyballTeams[j].getTotalPoint() < volleyballTeams[j + 1].getTotalPoint()){
                    Volleyball temp = volleyballTeams[j];
                    volleyballTeams[j] = volleyballTeams[j + 1];
                    volleyballTeams[j + 1] = temp;
                }
                else if(volleyballTeams[j].getTotalPoint() == volleyballTeams[j + 1].getTotalPoint()){
                    if((volleyballTeams[j].getGoalScored() <= volleyballTeams[j].getGoalConceded()) &&
                        (volleyballTeams[j + 1].getGoalScored() > volleyballTeams[j + 1].getGoalConceded())){
                        Volleyball temp = volleyballTeams[j];
                        volleyballTeams[j] = volleyballTeams[j + 1];
                        volleyballTeams[j + 1] = temp;
                    }
                    else{
                        if(volleyballTeams[j].getName().compareTo(volleyballTeams[j + 1].getName()) > 0){
                            Volleyball temp = volleyballTeams[j];
                            volleyballTeams[j] = volleyballTeams[j + 1];
                            volleyballTeams[j + 1] = temp;
                        }
                    }
                }
            }
        }
    }
    public static void sortBasketball(Basketball[] basketballTeams){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4 - i - 1; j++){
                if(basketballTeams[j].getScore() < basketballTeams[j + 1].getScore()){
                    Basketball temp = basketballTeams[j];
                    basketballTeams[j] = basketballTeams[j + 1];
                    basketballTeams[j + 1] = temp;
                }
                else if(basketballTeams[j].getScore() == basketballTeams[j + 1].getScore()){
                    if((basketballTeams[j].getGoalScored() <= basketballTeams[j].getGoalConceded()) &&
                        (basketballTeams[j + 1].getGoalScored() > basketballTeams[j + 1].getGoalConceded())){
                        Basketball temp = basketballTeams[j];
                        basketballTeams[j] = basketballTeams[j + 1];
                        basketballTeams[j + 1] = temp;
                    }
                    else{
                        if(basketballTeams[j].getName().compareTo(basketballTeams[j + 1].getName()) > 0){
                            Basketball temp = basketballTeams[j];
                            basketballTeams[j] = basketballTeams[j + 1];
                            basketballTeams[j + 1] = temp;
                        }
                    }
                }
            }
        }
    }
}
