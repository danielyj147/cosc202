package lab10;
public class IntervalInfo {
    private int ID;
    private int start;
    private int end;

    public IntervalInfo(int ID) {
        this.ID = ID;
    }

    public int getIdentifier() { return ID; }
    public int getStart() { return start; }
    public int getEnd() { return end; }

    public void setStart(int startTime) { this.start = startTime; }
    public void setEnd(int endTime) { this.end = endTime; }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IntervalInfo) {
            IntervalInfo i = (IntervalInfo) other;
            return (getIdentifier() == i.getIdentifier());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getIdentifier());
    }
}
