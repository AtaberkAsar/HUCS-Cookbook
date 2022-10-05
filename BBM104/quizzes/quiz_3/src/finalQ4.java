public class finalQ4 {

    public static void main(String[] args) {
        Score bill = new Score("Bill", 70);
        Score ali = new Score("Ali", 80);

        try {
            System.out.println(bill.compareTo(ali));
            System.out.println(bill.compareTo(50));
        } catch (Exception exc) {
            System.out.println(exc.toString());
        }
    }
}

class Score implements Comparable <Score>{
    private String name;
    private Integer testScore;

    public Score (String name, int testScore){
        this.name = name;
        this.testScore = testScore;
    }

    public int compareTo(Score o) {
        return testScore.compareTo(o.getTestScore());
    }

    public int compareTo(Integer o) throws Exception{
        throw new Exception();
    }

    public Integer getTestScore() {
        return testScore;
    }
}
