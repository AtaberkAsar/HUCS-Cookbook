package SystemDataTypes;

import java.util.ArrayList;

public class User {

    private String userName;
    private String hashedPassword; // in base64 format
    private boolean clubMemberStatus;
    private boolean adminStatus;
    private String profilePicturePath;
    private int ticketCounter;
    private int freeTicket;

    private ArrayList<Integer[]> freeSeats = new ArrayList<>();

    /**
     *
     * @param userName username, cannot be "null"
     * @param hashedPassword hashed password in base64 format
     * @param clubMemberStatus true if user is club member, false otherwise
     * @param adminStatus true if user is admin, false otherwise
     */
    public User(String userName, String hashedPassword, boolean clubMemberStatus, boolean adminStatus,
                String profilePicturePath, int ticketCounter, int freeTicket) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.clubMemberStatus = clubMemberStatus;
        this.adminStatus = adminStatus;
        this.profilePicturePath = profilePicturePath;
        this.ticketCounter =  ticketCounter;
        this.freeTicket = freeTicket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean getClubMemberStatus() {
        return clubMemberStatus;
    }

    public void setClubMemberStatus(boolean clubMemberStatus) {
        this.clubMemberStatus = clubMemberStatus;
    }

    public boolean getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public int getTicketCounter() {
        return ticketCounter;
    }

    public void setTicketCounter(int ticketCounter) {
        this.ticketCounter = ticketCounter;
    }

    public int getFreeTicket() {
        return freeTicket;
    }

    public void setFreeTicket(int freeTicket) {
        this.freeTicket = freeTicket;
    }

    public ArrayList<Integer[]> getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(ArrayList<Integer[]> freeSeats) {
        this.freeSeats = freeSeats;
    }

    public String luckyBackup(){
        return String.format("%s\t%s\t%s\t%s", userName, profilePicturePath, ticketCounter, freeTicket);
    }

    @Override
    public String toString() {
        return String.format("user\t%s\t%s\t%s\t%s", userName, hashedPassword, clubMemberStatus, adminStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(o instanceof User)
            return userName.equals(((User) o).getUserName());
        return false;
    }

    @Override
    public int hashCode() {
        return userName.hashCode(); // using Java's original hashcode method
    }
}
