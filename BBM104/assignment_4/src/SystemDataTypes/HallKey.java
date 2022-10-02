package SystemDataTypes;

import java.util.Objects;

public class HallKey {
    // This object used to store Halls in a hashMap

    private Film film;
    private String hallName;

    public HallKey(Film film, String hallName) {
        this.film = film;
        this.hallName = hallName;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(o instanceof HallKey)
            return film.equals(((HallKey) o).getFilm()) && hallName.equals(((HallKey) o).getHallName());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, hallName); // Using Java's original hashcode method
    }
}
