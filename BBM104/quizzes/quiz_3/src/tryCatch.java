public class tryCatch {

    public static void main(String[] args) {

        Integer integer = new Integer(25);
        Integer integer1 = new Integer(50);

        swapper(integer, integer1);
        System.out.println(integer);
        System.out.println(integer1);

        Integer tempInteger = integer;
        integer = integer1;
        integer1 = tempInteger;
        System.out.println(integer);
        System.out.println(integer1);

    }

    public static void swapper(Integer integer, Integer integer1){
        Integer tempInteger = integer;
        integer = integer1;
        integer1 = tempInteger;

    }
}

class Super
{
    public Integer getLength()
    {
        return new Integer(4);
    }
}

class Sub extends Super
{
   /* public Long getLength()
    {
        return new Long(5);
    }

    public static void main(String[] args)
    {
        Super sooper = new Super();
        Sub sub = new Sub();
        System.out.println(
                sooper.getLength().toString() + "," + sub.getLength().toString() );
    }*/
}