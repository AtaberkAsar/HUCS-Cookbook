import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ContactReader {
    ArrayList<Contact> contactArrayList = new ArrayList<>();
    HashSet<Contact> contactHashSet = new HashSet<>();
    TreeSet<Contact> contactTreeSet = new TreeSet<>();
    TreeSet<Contact> contactOrderedTreeSet = new TreeSet<>(new LastNameComparator());
    HashMap<String, Contact> phoneNumContactHashMap = new HashMap<>();

    public ContactReader(String path){
        File file = new File(path);
        Scanner contactScanner = null;
        String[] line;
        Contact newContact;

        try {
            contactScanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        while(contactScanner.hasNextLine()){
            line = contactScanner.nextLine().trim().split(" "); // phoneNum, name, surName, ssn
            newContact = new Contact(line[3], line[1], line[2], line[0]);

            contactArrayList.add(newContact);
            contactHashSet.add(newContact);
            contactTreeSet.add(newContact);
            contactOrderedTreeSet.add(newContact);
            phoneNumContactHashMap.put(line[0], newContact);
        }
    }

    public ArrayList<Contact> getContactArrayList() {
        return contactArrayList;
    }

    public HashSet<Contact> getContactHashSet() {
        return contactHashSet;
    }

    public TreeSet<Contact> getContactTreeSet() {
        return contactTreeSet;
    }

    public TreeSet<Contact> getContactOrderedTreeSet() {
        return contactOrderedTreeSet;
    }

    public HashMap<String, Contact> getPhoneNumContactHashMap() {
        return phoneNumContactHashMap;
    }
}
