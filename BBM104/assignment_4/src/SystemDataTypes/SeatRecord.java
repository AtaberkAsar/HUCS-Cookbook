package SystemDataTypes;

import java.util.Objects;

public class SeatRecord {
    private Film film;
    private Hall hall;
    private int rowOfSeat,
                columnOfSeat;
    private User user;
    private int priceItHasBeenBought;

    public SeatRecord(Film film, Hall hall, int rowOfSeat, int columnOfSeat, User user, int priceItHasBeenBought) {
        this.film = film;
        this.hall = hall;
        this.rowOfSeat = rowOfSeat;
        this.columnOfSeat = columnOfSeat;
        this.user = user;
        this.priceItHasBeenBought = priceItHasBeenBought;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getRowOfSeat() {
        return rowOfSeat;
    }

    public void setRowOfSeat(int rowOfSeat) {
        this.rowOfSeat = rowOfSeat;
    }

    public int getColumnOfSeat() {
        return columnOfSeat;
    }

    public void setColumnOfSeat(int columnOfSeat) {
        this.columnOfSeat = columnOfSeat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPriceItHasBeenBought() {
        return priceItHasBeenBought;
    }

    public void setPriceItHasBeenBought(int priceItHasBeenBought) {
        this.priceItHasBeenBought = priceItHasBeenBought;
    }

    @Override
    public String toString() {
        return String.format("seat\t%s\t%s\t%s\t%s\t%s\t%s", film.getFilmName(), hall.getHallName(),
                rowOfSeat, columnOfSeat, user == null ? "null" : user.getUserName(), priceItHasBeenBought);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(o instanceof SeatRecord) {
            SeatRecord tempSeat = (SeatRecord) o;
            return hall.equals(tempSeat.getHall()) && (tempSeat.getRowOfSeat() == rowOfSeat) &&
                    (tempSeat.getColumnOfSeat() == columnOfSeat);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hall.hashCode(), rowOfSeat, columnOfSeat); // using Java's original hashcode method
    }
}
