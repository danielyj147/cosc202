import java.util.*;

public class MaxPop {

    public static PopOutput maxpop(HashMap<Integer, LifeYears> data) {
        if (data == null)
            return null;
        HashMap<Integer, Integer> years = new HashMap<>();
        int maxPop = 0;
        int maxYear = 0;
        
        for (Map.Entry<Integer, LifeYears> person : data.entrySet()) {
            int birth = person.getValue().getBirth();
            int death = person.getValue().getDeath();
            
            years.merge(birth, 1, Integer::sum);
            years.merge(death + 1, -1, Integer::sum);

        }
        List<Integer> sortedYears = years.keySet().stream().sorted().toList();
        int currentPop = 0;
        for (int year : sortedYears) {
            currentPop += years.get(year);
            if (currentPop > maxPop) {
                maxPop = currentPop;
                maxYear = year;
            }
            
        }
        PopOutput maxPopOutput = new PopOutput(maxYear, maxPop);
        return maxPopOutput;
    }

    public static void main(String[] args) {
        HashMap<Integer, LifeYears> data = new HashMap<>();
        data.put(1, new LifeYears(1900, 1950));
        data.put(2, new LifeYears(1920, 1980));
        data.put(3, new LifeYears(1940, 1990));
        
        PopOutput result = maxpop(data);
        System.out.printf("(%d, %d)%n", result.getYear(), result.getPopulation());    
    }
}