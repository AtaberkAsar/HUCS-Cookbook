public class Player implements Comparable<Player>{
    private String name;
    private Integer point;

    public Player(String name, Integer point){
        this.name = name;
        this.point = point;
    }

    @Override
    public int compareTo(Player newPlayer) {
        if(newPlayer.getPoint() != null) // if compare based on point, ie. while sorting the list
            return newPlayer.getPoint().compareTo(point);
        return newPlayer.getName().compareTo(name); // if compare based on name, ie. while retrieving the order of player
    }

    public String getName() {
        return name;
    }

    public Integer getPoint() {
        return point;
    }
}
