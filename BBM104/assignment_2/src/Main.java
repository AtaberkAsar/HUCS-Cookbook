public class Main {
    public static void main(String[] args){
        Squares[] squares = new Squares[40];
        ChanceCards[] chanceCards = new ChanceCards[6];
        CommunityChestCards[] communityChestCards = new CommunityChestCards[11];

        JSONReader.ListJsonReader(chanceCards, communityChestCards);
        JSONReader.PropertyJsonReader(squares);

        GameMaster gameMaster = new GameMaster(squares, chanceCards, communityChestCards);

        gameMaster.outputWriter(args[0]);

    }
}
