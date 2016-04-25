package edu.csula.datascience.jsouptest;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A mock implementation of collector for testing
 */
public class MockCollector implements Collector<BasketballObject, BasketballObject> {
    @Override
    public Collection<BasketballObject> mungee(Collection<BasketballObject> src) {
        Collection<BasketballObject> clean = new ArrayList<BasketballObject>();

        for (BasketballObject stat : src) {
            // Determine cleaning process
            try {
                if (!stat.getTeam().contentEquals("") && stat.getPoints_per_game() > 0.0) {
                    boolean duplicate = false;
                    
                    for (BasketballObject cStat : clean) {
                        if (stat.getTeam().contentEquals(cStat.getTeam())) {
                            duplicate = true;
                            break;
                        }
                    }
                    
                    if (!duplicate) {
                        System.out.println(stat.getTeam() + ": " + stat.getPoints_per_game());
                        clean.add(stat);
                    }
                }
            } catch (Exception ex) {
            }
        }

        return clean;
    }

    @Override
    public void save(Collection<BasketballObject> data) {
    }
}
