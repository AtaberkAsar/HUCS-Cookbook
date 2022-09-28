public class Player extends User{
    private Properties[] properties;
    private int pawn;
    private boolean playing = true;
    private int railroadCount = 0;
    private int inJail = -1;

    public Player(String name){
        super(name, 15000);
        properties = new Properties[28];
        pawn = 1;
    }



    public void setProperties(Properties newProperty){
        for(int i = 0; i < 28; i++){
            if(properties[i] == null){
                properties[i] = newProperty;
                break;
            }
        }
    }

    public void setRailroadCount(){
        railroadCount++;
    }

    public void resetPawn(int value){
        pawn = value;
    }

    public boolean setPawn(int dice){
        pawn += dice;
        if (pawn > 40){
            pawn = ((pawn - 1) % 40) + 1;
            return true;
        }
        pawn = ((pawn - 1) % 40) + 1; // if goes back...
        return false;
    }

    public void setPlaying() {
        playing = false;
    }

    public void setInJail(int num){
        inJail = num;
    }

    public Properties[] getProperties() {
        return properties;
    }

    public int getPawn(){
        return pawn;
    }

    public boolean getPlaying() {
        return playing;
    }

    public int getInJail() {
        return inJail;
    }

    public int getRailroadCount() {
        return railroadCount;
    }
}
