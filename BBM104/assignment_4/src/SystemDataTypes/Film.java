package SystemDataTypes;

public class Film {
    private String filmName;
    private String trailerPath; // relative path
    private String duration; // in minutes

    /**
     *
     * @param filmName film name
     * @param trailerPath relative path of trailer file
     * @param duration duration in minutes
     */
    public Film(String filmName, String trailerPath, String duration) {
        this.filmName = filmName;
        this.trailerPath = trailerPath;
        this.duration = duration;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("film\t%s\t%s\t%s", filmName, trailerPath, duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(o instanceof Film)
            return filmName.equals(((Film) o).getFilmName());
        return false;
    }

    @Override
    public int hashCode() {
        return filmName.hashCode(); // using Java's original hashcode method
    }
}
