package lab10;
public class Statement {
    private int first;
    private int second;
    private boolean overlap;

    public Statement(boolean overlap, int first, int second) {
        this.overlap = overlap;
        this.first = first;
        this.second = second;
    }

    public boolean isOverlap() { return overlap; }

    public int firstInterval() { return first; }

    public int secondInterval() { return second; }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Statement) {
            Statement s = (Statement) other;

            return (isOverlap() == s.isOverlap() && firstInterval() == s.firstInterval() && secondInterval() == s.secondInterval());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(overlap) + Integer.hashCode(firstInterval()) + Integer.hashCode(secondInterval());
    }
}
