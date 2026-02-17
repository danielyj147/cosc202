public class PopOutput {

    private int year;
    private int population;

    public PopOutput(int year, int population) {

        if (population < 0)
            throw new IllegalArgumentException("population must be nonnegative");

        this.year = year;
        this.population = population;
    }

    public int getYear() { return year; }
    public int getPopulation() { return population; }

}
