import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class GameMaster {
    private Player player1;
    private Player player2;
    private Banker banker;

    private Squares[] gameMap;
    private ChanceCards[] chanceCards;
    private CommunityChestCards[] communityChestCards;

    private static int chanceCardsCounter = 0;
    private static int communityChestCardsCounter = 0;
    private int dice;

    private boolean gameOver = false;

    private String line;

    public GameMaster(Squares[] gameMap, ChanceCards[] chanceCards, CommunityChestCards[] communityChestCards){
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        banker = new Banker();

        this.gameMap = gameMap;
        this.chanceCards = chanceCards;
        this.communityChestCards = communityChestCards;
    }

    public void newTurn(String[] command){
        dice = Integer.parseInt(command[1]);

        line = "";

        if(command[0].equals("Player 2") & player2.getPlaying()){ //round of player2
            if(player2.getInJail() > 0) // if player in jail
                player2.setInJail(player2.getInJail() - 1);
            else if(player2.setPawn(dice)){ // if player goes from start
                player2.setMoney(200);
                banker.setMoney(-200);
            }
            else if(player2.getInJail() == 0) {
                // player2.setPawn(dice);
                player2.setInJail(-1); // Make sure that player is not in jail
            }
            onNewSquare(player2, player1);
            line = String.format("%s\t%d\t%d\t%d\t%d\t%s", player2.getName(), dice, player2.getPawn(),
                    player1.getMoney(), player2.getMoney(), line);
        }
        else if(command[0].equals("Player 1") & player1.getPlaying()){ //round of player1
            if(player1.getInJail() > 0) // if player in jail
                player1.setInJail(player1.getInJail() - 1);
            else if(player1.setPawn(dice)){ // if player goes from start
                player1.setMoney(200);
                banker.setMoney(-200);
            }
            else if(player1.getInJail() == 0){
                // player1.setPawn(dice);
                player1.setInJail(-1); // Make sure that player is not in jail
            }
            onNewSquare(player1, player2);
            line = String.format("%s\t%d\t%d\t%d\t%d\t%s", player1.getName(), dice, player1.getPawn(),
                    player1.getMoney(), player2.getMoney(), line);
        }
        line += "\n";
        if((!player1.getPlaying()) | (!player2.getPlaying())){ // game over
            gameOver = true;
        }
    }

    public void show(){
        line = "-----------------------------------------------------------------------------------------------------------\n";
        line += String.format("Player 1\t%d\thave: ", player1.getMoney());
        // Print Land first
        for(Squares square: gameMap){
            if(square instanceof Land){
                if(!((Land) square).getPayable()){
                    if(((Land) square).getOwner().equals("Player 1")) {
                        line += square.getName() + ",";
                    }
                }
            }
        }
        // Then print railroads
        for(Squares square: gameMap){
            if(square instanceof RailRoads){
                if(!((RailRoads) square).getPayable()){
                    if(((RailRoads) square).getOwner().equals("Player 1")) {
                        line += square.getName() + ",";
                    }
                }
            }
        }
        // Then print companies
        for(Squares square: gameMap){
            if(square instanceof Company){
                if(!((Company) square).getPayable()){
                    if(((Company) square).getOwner().equals("Player 1")) {
                        line += square.getName() + ",";
                    }
                }
            }
        }
        line = line.substring(0, line.length() - 1);

        line += String.format("\nPlayer 2\t%d\thave: ", player2.getMoney());
        // Print Land first
        for(Squares square: gameMap){
            if(square instanceof Land){
                if(!((Land) square).getPayable()){
                    if(((Land) square).getOwner().equals("Player 2")) {
                        line += square.getName() + ",";
                    }
                }
            }
        }
        // Then print railroads
        for(Squares square: gameMap){
            if(square instanceof RailRoads){
                if(!((RailRoads) square).getPayable()){
                    if(((RailRoads) square).getOwner().equals("Player 2")) {
                        line += square.getName() + ",";
                    }
                }
            }
        }
        // Then print companies
        for(Squares square: gameMap){
            if(square instanceof Company){
                if(!((Company) square).getPayable()){
                    if(((Company) square).getOwner().equals("Player 2")) {
                        line += square.getName() + ",";
                    }
                }
            }
        }

        line = line.substring(0, line.length() - 1);
        line += String.format("\nBanker\t%d\nWinner %s\n", banker.getMoney(), (player1.getMoney() > player2.getMoney()) ? "Player 1" : "Player 2");
        line += "-----------------------------------------------------------------------------------------------------------\n";
    }

    public void onNewSquare(Player turnPlayer, Player opponentPlayer){

        if(turnPlayer.getInJail() >= 0) {
            if(turnPlayer.getPawn() == 21)
                line += String.format("%s is in free parking (count=%d)", turnPlayer.getName(), 1 - turnPlayer.getInJail());
            else
                line += String.format("%s in jail (count=%d)", turnPlayer.getName(), 3 - turnPlayer.getInJail());
        }

        else if(gameMap[turnPlayer.getPawn() - 1] instanceof Properties){ // player is on a property
            if(((Properties) gameMap[turnPlayer.getPawn() - 1]).getPayable()){ // property is not occupied
                buyProperty(turnPlayer, (Properties) gameMap[turnPlayer.getPawn() - 1]);
            }
            else{ // property is occupied
                boolean enable = true;
                for(Properties property: turnPlayer.getProperties()){
                    if(property == null)
                        break;
                    else if(property.getName().equals(((Properties) gameMap[turnPlayer.getPawn() - 1]).getName())){
                        line += String.format("%s has %s", turnPlayer.getName(), property.getName());
                        enable = false;
                        break;
                    }
                }
                payRent(turnPlayer ,((Properties) gameMap[turnPlayer.getPawn() - 1]), opponentPlayer, enable);
            }
        }

        else if((gameMap[turnPlayer.getPawn() - 1].getName()).equals("Chance")){ // player is on chance cards
            line += String.format("%s draw %s", turnPlayer.getName(), chanceCards[chanceCardsCounter].getText());
            switch(chanceCards[chanceCardsCounter].getText()){
                case "Advance to Go (Collect $200)":
                    turnPlayer.resetPawn(1);
                    turnPlayer.setMoney(200);
                    banker.setMoney(-200);
                    break;
                case "Advance to Leicester Square":
                    if(turnPlayer.getPawn() > 27){ // player passes through go square
                        turnPlayer.setMoney(200);
                        banker.setMoney(-200);
                    }
                    turnPlayer.resetPawn(27);
                    line += " ";
                    onNewSquare(turnPlayer, opponentPlayer);
                    break;
                case "Go back 3 spaces":
                    turnPlayer.setPawn(-3);
                    line += " ";
                    onNewSquare(turnPlayer, opponentPlayer);
                    break;
                case "Pay poor tax of $15":
                    if(turnPlayer.getMoney() >= 15) {
                        turnPlayer.setMoney(-15);
                        banker.setMoney(15);
                        if(turnPlayer.getMoney() == 0)
                            turnPlayer.setPlaying();
                        break;
                    }
                    turnPlayer.setPlaying();
                    break;
                case "Your building loan matures - collect $150":
                    turnPlayer.setMoney(150);
                    banker.setMoney(-150);
                    break;
                case "You have won a crossword competition - collect $100 ":
                    turnPlayer.setMoney(100);
                    banker.setMoney(-100);
                    break;
            }
            chanceCardsCounter = ++chanceCardsCounter %  6; // cards continue from start after 6 cards
        }
        else if((gameMap[turnPlayer.getPawn() - 1].getName()).equals("Community Chest")){ // player is on community chest cards
            switch(communityChestCards[communityChestCardsCounter].getText()){
                case "Advance to Go (Collect $200)":
                    turnPlayer.resetPawn(1);
                    turnPlayer.setMoney(200);
                    banker.setMoney(-200);
                    line += String.format("%s draw Community Chest -advance to go ", turnPlayer.getName());
                    break;
                case "Bank error in your favor - collect $75":
                    turnPlayer.setMoney(75);
                    banker.setMoney(-75);
                    line += String.format("%s draw Community Chest -bank error in your favor - collect $75 ", turnPlayer.getName());
                    break;
                case "Doctor's fees - Pay $50":
                    line += String.format("%s draw Community Chest -doctor's fees - Pay $50 ", turnPlayer.getName());
                    if(turnPlayer.getMoney() >= 50){
                        turnPlayer.setMoney(-50);
                        banker.setMoney(50);
                        if(turnPlayer.getMoney() == 0)
                            turnPlayer.setPlaying();
                        break;
                    }
                    turnPlayer.setPlaying();
                    break;
                case "Pay School Fees of $50":
                    line += String.format("%s draw Community Chest -pay school fees of $50 ", turnPlayer.getName());
                    if(turnPlayer.getMoney() >= 50){
                        turnPlayer.setMoney(-50);
                        banker.setMoney(50);
                        if(turnPlayer.getMoney() == 0)
                            turnPlayer.setPlaying();
                        break;
                    }
                    turnPlayer.setPlaying();
                    break;
                case "It is your birthday Collect $10 from each player":
                    line += String.format("%s draw Community Chest -it is your birthday Collect $10 from each player ", turnPlayer.getName());
                    if(opponentPlayer.getMoney() >= 10){
                        turnPlayer.setMoney(10);
                        opponentPlayer.setMoney(-10);
                        if(opponentPlayer.getMoney() == 0)
                            opponentPlayer.setPlaying();
                        break;
                    }
                    opponentPlayer.setPlaying();
                    break;
                case "Grand Opera Night - collect $50 from every player for opening night seats":
                    line += String.format("%s draw Community Chest -grand opera night - collect $50 from every player for opening night seats ", turnPlayer.getName());
                    if(opponentPlayer.getMoney() >= 50){
                        turnPlayer.setMoney(50);
                        opponentPlayer.setMoney(-50);
                        if(opponentPlayer.getMoney() == 0)
                            opponentPlayer.setPlaying();
                        break;
                    }
                    opponentPlayer.setPlaying();
                    break;
                case "From sale of stock you get $50":
                    line += String.format("%s draw Community Chest -from sale of stock you get $50 ", turnPlayer.getName());
                    turnPlayer.setMoney(50);
                    banker.setMoney(-50);
                    break;
                case "Income Tax refund - collect $20":
                    line += String.format("%s draw Community Chest -income tax refund - collect $200 ", turnPlayer.getName());
                    turnPlayer.setMoney(20);
                    banker.setMoney(-20);
                    break;
                case "Life Insurance Matures - collect $100":
                    line += String.format("%s draw Community Chest -life insurance matures - collect $100 ", turnPlayer.getName());
                    turnPlayer.setMoney(100);
                    banker.setMoney(-100);
                    break;
                case "You inherit $100":
                    line += String.format("%s draw Community Chest -you inherit $100 ", turnPlayer.getName());
                    turnPlayer.setMoney(100);
                    banker.setMoney(-100);
                    break;
                case "Pay Hospital Fees of $100":
                    line += String.format("%s draw Community Chest -pay hospital fees of $100 ", turnPlayer.getName());
                    if(turnPlayer.getMoney() >= 100){
                        turnPlayer.setMoney(-100);
                        banker.setMoney(100);
                        if(turnPlayer.getMoney() == 0)
                            turnPlayer.setPlaying();
                        break;
                    }
                    turnPlayer.setPlaying();
                    break;
            }
            communityChestCardsCounter = ++communityChestCardsCounter % 11; // cards continue from start after 11 cards
        }
        else if((gameMap[turnPlayer.getPawn() - 1].getName()).equals("Jail")) {
            turnPlayer.setInJail(3);
            line += String.format("%s went to jail", turnPlayer.getName());
        }
        else if(gameMap[turnPlayer.getPawn() - 1] instanceof TaxSquares){
            if(turnPlayer.getMoney() >= 100){
                turnPlayer.setMoney(-100);
                banker.setMoney(100);
                line += String.format("%s paid Tax", turnPlayer.getName());
                if(turnPlayer.getMoney() == 0)
                    turnPlayer.setPlaying();
            }
            else {
                line += String.format("%s cannot pay Tax", turnPlayer.getName());
                turnPlayer.setPlaying();
            }
        }
        else if((gameMap[turnPlayer.getPawn() - 1].getName()).equals("Free Parking")) {
            turnPlayer.setInJail(1);
            line += String.format("%s is in free parking", turnPlayer.getName());
        }
        else if((gameMap[turnPlayer.getPawn() - 1].getName()).equals("Go to Jail")) {
            turnPlayer.resetPawn(11);
            turnPlayer.setInJail(3);
            line += String.format("%s went to jail", turnPlayer.getName());
        }
        else if((gameMap[turnPlayer.getPawn() - 1].getName()).equals("GO")) {
            line += String.format("%s is in GO square", turnPlayer.getName());
        }

    }

    public void buyProperty(Player turnPlayer, Properties newProperty){
        if(turnPlayer.getMoney() >= newProperty.getCost()){
            turnPlayer.setMoney(- newProperty.getCost());
            banker.setMoney(newProperty.getCost()); // paying to banker

            newProperty.setPayable(turnPlayer.getName());
            turnPlayer.setProperties(newProperty);

            if(newProperty instanceof RailRoads) // buying a railroad
                turnPlayer.setRailroadCount();

            line += String.format("%s bought %s", turnPlayer.getName(), newProperty.getName());
            return;
        }
        line += String.format("%s goes bankrupt", turnPlayer.getName());
        turnPlayer.setPlaying(); // Not enough money
    }

    public void payRent(Player turnPlayer, Properties newProperty, Player opponentPlayer, boolean enable){
        if(!enable)
            return;

        if(newProperty instanceof Land){
            newProperty.setRent(newProperty.getCost(), 0); // factor not needed for land, thus given 0
        }

        else if(newProperty instanceof Company){
            newProperty.setRent(0, dice); // rent not needed for company, thus given 0
        }

        else{
            newProperty.setRent(0, opponentPlayer.getRailroadCount()); // rent not needed for railroads, thus given 0
        }

        if(turnPlayer.getMoney() >= newProperty.getRent()){
            turnPlayer.setMoney(- newProperty.getRent()); // paying the rent
            opponentPlayer.setMoney(newProperty.getRent());

            line += String.format("%s paid rent for %s", turnPlayer.getName(), newProperty.getName());
            return;
        }
        line += String.format("%s goes bankrupt", turnPlayer.getName());
        turnPlayer.setPlaying(); // Not enough money
    }

    public void outputWriter(String path){
        String[] command;

        File commandFile = new File(path);
        Scanner commandScanner;

        File outputFile = new File("output.txt");
        PrintStream outputWriter;
        try{
            commandScanner = new Scanner(commandFile);
        }catch(IOException e){
            return;
        }
        try{
            outputWriter = new PrintStream(outputFile);
        }catch (IOException e){
            return;
        }
        while(commandScanner.hasNextLine()){

            command = commandScanner.nextLine().split(";");
            if(command.length == 1){
                show();
                outputWriter.print(line);
            }
            else{
                newTurn(command);
                outputWriter.print(line);
                if(gameOver){
                    break;
                }
            }
        }
        show();
        outputWriter.print(line);
        outputWriter.close();
        commandScanner.close();

        String file = "";
        Scanner outputScanner;

        try{
            outputScanner = new Scanner(outputFile);
        }catch(IOException e){
            return;
        }
        while(outputScanner.hasNextLine()){
            file += outputScanner.nextLine() + "\n";
        }
        outputScanner.close();
        file = file.substring(0, file.length() - 1);

        try{
            outputWriter = new PrintStream(outputFile);
        }catch (IOException e){
            return;
        }

        outputWriter.print(file);
        outputWriter.close();
    }
}
