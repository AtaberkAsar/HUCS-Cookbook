public class Student {
    protected int no;
    public String name;
    public Student() {no = -1;}
    public void setNo(int pNo) { //
        System.out.println ("No number is assigned!");
    }
    public int getNo() { //
        System.out.println ("Number cannot be read!");
        return -1;
    }
}

class Graduate extends Student{ //
    private String supervisor; //
    public Graduate() {super();} //
    public void setNo(int pNo) { //
        no = pNo;
        System.out.println (no + " is assigned for Graduate.");
    }
    public int getNo() { //
        System.out.println (no + " is read for Graduate.");
        return no;
    }
}

class Undergrad extends Student{ //
    private String advisor; //
    public Undergrad() {super();} //
    public void setNo(int pNo) { //
        no = pNo;
        System.out.println (no + " is assigned for Undergrad.");
    }
    public int getNo() { //
        System.out.println (no + " is read for Undergrad.");
        return no;
    }
}