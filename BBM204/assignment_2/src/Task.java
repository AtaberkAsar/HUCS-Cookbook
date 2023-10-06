import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        // YOUR CODE HERE

        String[] startSplit = start.split(":");
        int startHr = Integer.parseInt(startSplit[0]);
        int startMin = Integer.parseInt(startSplit[1]);
        int endHr = startHr + duration;

        String endHR = endHr < 10 ? "0" + endHr : "" + endHr;
        String endMIN = startMin < 10 ? "0" + startMin : "" + startMin;

        return endHR + ":" + endMIN;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        // YOUR CODE HERE

        return importance * (urgent ? 2000.0 : 1.0) / duration;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        // YOUR CODE HERE

        if (o == null)
            return -1;

        Task other = (Task) o;
        String[] otherEndSplit = other.getFinishTime().split(":");
        String[] thisEndSplit = getFinishTime().split(":");

        if (thisEndSplit[0].compareTo(otherEndSplit[0]) < 0)
            return -1;
        if (thisEndSplit[0].compareTo(otherEndSplit[0]) > 0)
            return 1;
        return thisEndSplit[1].compareTo(otherEndSplit[1]);
    }

    @Override
    public String toString() {
        return "At " + start + ", " + name + ".";
    }
}
