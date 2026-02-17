public class LifeYears {

    private int birthYear;
    private int deathYear;

    public LifeYears(int birth, int death) {

        if (death < birth)
            throw new IllegalArgumentException("death year cannot precede birth year");

        this.birthYear = birth;
        this.deathYear = death;
    }

    public int getBirth() { return birthYear; }
    public int getDeath() { return deathYear; }

    public String toString() { return String.format("(%d, %d)", birthYear, deathYear); }
}