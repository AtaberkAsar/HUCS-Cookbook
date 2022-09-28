public abstract class User {
    private final String name;
    private int money;

    public User(String name, int money){
        this.name = name;
        this.money = money;
    }

    public void setMoney(int value){
        money += value;
    }

    public String getName(){
        return name;
    }

    public int getMoney(){
        return money;
    }
}
