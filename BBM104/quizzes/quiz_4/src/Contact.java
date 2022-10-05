import java.util.Comparator;

public class Contact implements Comparable<Contact>{

    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Contact(String socialSecurityNumber, String firstName, String lastName, String phoneNumber){
        this.socialSecurityNumber = socialSecurityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    /*
     * compareTo, equals, and hashCode methods overrided
     * to obtain desired ordered and unordered outputs
     */

    @Override
    public int compareTo(Contact otherContact) {
        return phoneNumber.compareTo(otherContact.getPhoneNumber());
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj instanceof Contact)
            return phoneNumber.equals(((Contact) obj).getPhoneNumber());
        return false;
    }

    @Override
    public int hashCode(){
        return Integer.parseInt(phoneNumber.replace("-", ""));
    }

    @Override
    public String toString(){
        return String.format("%s %s %s %s", phoneNumber, firstName, lastName, socialSecurityNumber);
    }

    public String getSocialSecurityNumber(){
        return socialSecurityNumber;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
}

class LastNameComparator implements Comparator<Contact>{
    public int compare(Contact contact1, Contact contact2){
        return contact1.getLastName().compareTo(contact2.getLastName());
    }
}
