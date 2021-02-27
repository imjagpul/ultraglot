/*
 * LastSeenStrategy.java
 *
 * Created on 26. prosinec 2007, 12:58
 */

package cz.srubarovi.teacher.strategy;

import cz.srubarovi.teacher.QuestionFrame;
import cz.srubarovi.teacher.core.QuestionWithStats;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Stepan
 */
public class LastSeenStrategy extends AbstractStrategy {
    
    public QuestionWithStats getNext(List<QuestionWithStats> questions) {
        sortByTimeRandom(questions);
        return questions.get(0);
    }

    public String getStatusText() {
        return "";
    }

    public void notify(EnumSet<QuestionFrame.Status> status) {
        //do nothing
    }
    
    
}
