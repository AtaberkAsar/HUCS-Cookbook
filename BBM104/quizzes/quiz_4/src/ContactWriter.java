import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class ContactWriter {

    public ContactWriter (ArrayList<Contact> contactArrayList, HashSet<Contact> contactHashSet, TreeSet<Contact> contactTreeSet,
                          TreeSet<Contact> contactOrderedTreeSet, HashMap<String, Contact> phoneNumContractHashMap){

        naturalOrderWriter("contactsArrayList.txt", contactArrayList);

        /*
        Collections.sort(contactArrayList, new LastNameComparator()); // sorting arrayList with my comparator
        naturalOrderWriter("contactsArrayListOrderByLastName.txt", contactArrayList);
        */
        orderWriter("contactsArrayListOrderByLastName.txt", contactArrayList);

        naturalOrderWriter("contactsHashSet.txt", contactHashSet);
        naturalOrderWriter("contactsTreeSet.txt", contactTreeSet);
        naturalOrderWriter("contactsTreeSetOrderByLastName.txt", contactOrderedTreeSet);
        hashMapWriter("contactsHashMap.txt", phoneNumContractHashMap);

    }

    public void naturalOrderWriter(String path, Collection<Contact> contactCollection){
        File file = new File(path);
        PrintStream collectionStream;
        Iterator<Contact> collectionIterator = contactCollection.iterator();

        try {
            collectionStream = new PrintStream(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return;
        }
        while(collectionIterator.hasNext())
            collectionStream.println(collectionIterator.next());
        collectionStream.close();
    }

    public void orderWriter(String path, ArrayList<Contact> contactCollection){
        File file = new File(path);
        PrintStream collectionStream;


        Contact test;
        try {
            collectionStream = new PrintStream(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return;
        }
        Collections.sort(contactCollection, new LastNameComparator());

        //iterator eski halini mi iterate etmeye çalışıyor o zaman?
        //evet

        Iterator<Contact> collectionIterator = contactCollection.iterator();
        contactCollection.add(new Contact("1", "1", "1", "1"));
        while(collectionIterator.hasNext())
            test = collectionIterator.next();
        collectionStream.close();
    }

    public void hashMapWriter(String path, HashMap<String, Contact> phoneNumContactHashMap){

        File file = new File(path);
        PrintStream hashMapStream;
        Iterator<Map.Entry<String, Contact>> contactIterator = phoneNumContactHashMap.entrySet().iterator();

        try {
            hashMapStream = new PrintStream(file);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return;
        }
        while(contactIterator.hasNext())
            hashMapStream.println(contactIterator.next().getValue());
        hashMapStream.close();
    }
}
