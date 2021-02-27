/*
 * AbstractStrategy.java
 *
 * Created on 26. prosinec 2007, 13:00
 */

package cz.srubarovi.teacher.strategy;

import cz.srubarovi.teacher.core.QuestionWithStats;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Stepan
 */
public abstract class AbstractStrategy implements Strategy {
    
    private static class TimeComparator implements Comparator<QuestionWithStats> {
        public int compare(QuestionWithStats o1, QuestionWithStats o2) {
            return Long.valueOf(o1.getLastSeenTime()).compareTo(o2.getLastSeenTime());
        }
    }
    
    private static final Strategy[] STRATEGIES=
            new Strategy[] {new DifficultyBasedStrategy(), new DifficultyBasedNoTestingStrategy(), new LastSeenStrategy()};
    
    public static final Strategy[] getStrategyList() {
        return STRATEGIES;
    }
    
    protected final void sortByTime(List<QuestionWithStats> questions) {
        Collections.sort(questions, new TimeComparator ());
    }

    protected final void randomize(List<QuestionWithStats> questions) {
        Collections.shuffle(questions);
    }
    
    protected final void sortByTimeRandom(List<QuestionWithStats> questions) {
        randomize(questions);
        sortByTime(questions);
    }

    private static final Pattern NAME_CHANGE=Pattern.compile("(.)([A-Z])");
    
    @Override
    public String toString() {
        String name=getClass().getSimpleName();
        
        Matcher m=NAME_CHANGE.matcher(name);
        
        return m.replaceAll("$1 $2");
    }
}
