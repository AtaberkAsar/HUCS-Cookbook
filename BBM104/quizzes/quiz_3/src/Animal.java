public class Animal {
    public void Move() {
        System.out.println("Animal is moving");
    }

    public static void main(String[] args){
        Object o = new Animal();
        Animal a = new WalkingMammal();
        WalkingMammal m = new Dog();
        Husky h = new Husky();
        Fish f= new Fish();

        m.Move();
        //m.Bark();
        ((Dog) m).Bark();
        h.Act();
        f.Move();
        h.Move();
    }
}

class Fish extends Animal {
    public void Move() {
        System.out.println("Fish is swimming");
        super.Move();
    }
}

class WalkingMammal extends Animal {
    public void Move() {
        System.out.println("Mammal is walking");
    }
}

class Dog extends WalkingMammal {
    public void Bark() {
        System.out.println("Dog is barking");
    }
    public void MoveBark() {
        Move();
        Bark();
    }
}

class Husky extends Dog {
    public void Act() {
        System.out.println("Husky is acting");
        super.MoveBark();
    }
}
