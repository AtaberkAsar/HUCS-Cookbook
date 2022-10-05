public class Main {
    public static void main(String[] args){
        ContactReader contactReader = new ContactReader(args[0]);
        ContactWriter contactWriter = new ContactWriter(contactReader.getContactArrayList(),
                contactReader.getContactHashSet(),
                contactReader.getContactTreeSet(),
                contactReader.getContactOrderedTreeSet(),
                contactReader.getPhoneNumContactHashMap());
    }
}
