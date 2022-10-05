public class midterm2Q4 {
}

class Person {
    String name;
    Boolean gender;
    int age;

    public Person(String name, String gender, int age){
        this.name = name;
        this.gender = gender.equals("Male");
        this.age = age;
    }
}

interface Staff{
    int getExperience();
}

interface Vehicle{
    String getModel();
    String getName();
}

class Pilot extends Person implements Staff{
    int licenceNum;
    int experienceYear;

    public Pilot(String name, String gender, int age, int licenseNum, int experienceYear){
        super(name, gender, age);
        this.licenceNum = licenseNum;
        this.experienceYear = experienceYear;
    }

    public int getExperience(){
        return experienceYear;
    }
}


class AirHostess extends Person implements Staff{
    int ssn;

    public AirHostess(String name, String gender, int age, int ssn){
        super(name, gender, age);
        this.ssn = ssn;
    }

    public int getExperience(){
        return 0;
    }
}

class Passenger extends Person{
    String eMail;
    int ID;

    public Passenger(String name, String gender, int age, String eMail, int ID){
        super(name, gender, age);
        this.eMail = eMail;
        this.ID = ID;
    }
}

class Airplane implements Vehicle{
    String model;
    String name;

    public Airplane(String model, String name){
        this.model = model;
        this.name = name;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getName() {
        return name;
    }
}

class Bus implements Vehicle{
    String model;
    String name;

    public Bus (String model, String name){
        this.model = model;
        this.name = name;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getName() {
        return name;
    }
}

