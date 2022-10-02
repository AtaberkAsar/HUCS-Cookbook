package SystemDataTypes;

import java.util.Objects;

public class Hall {
    private Film film;
    private String hallName;
    private int pricePerSeat;
    private int row,
                column;

    /**
     *
     * @param film film
     * @param hallName hall name
     * @param pricePerSeat price per seat, INTEGER
     * @param row number of rows
     * @param column number of columns
     */
    public Hall(Film film, String hallName, int pricePerSeat, int row, int column) {
        this.film = film;
        this.hallName = hallName;
        this.pricePerSeat = pricePerSeat;
        this.row = row;
        this.column = column;
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

    public int getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(int pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("hall\t%s\t%s\t%s\t%s\t%s", film.getFilmName(), hallName, pricePerSeat, row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(o instanceof Hall)
            return hallName.equals(((Hall) o).getHallName()) && film.equals(((Hall) o).getFilm());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallName, film.getFilmName()); // using Java's original hashcode method
    }
}
