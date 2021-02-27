/*
 * StatsTestIterator.java
 *
 * Created on 25. listopad 2007, 16:26
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.QuestionFrame;
import cz.srubarovi.teacher.strategy.Strategy;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Stepan
 */
public class StatsTestIterator implements NotifiableIterator {
    
    private List<QuestionWithStats> data;
    private Strategy strategy;
    private QuestionWithStats current;
    
    /** Creates a new instance of StatsTestIterator */
    public StatsTestIterator(QuestionWithStats[] data, Strategy strategy) {
        this.strategy=strategy;
        this.data=Arrays.asList(data);
    }
    
    
    public void notify(EnumSet<QuestionFrame.Status> status) {
        if(current!=null)
            current.notify(status);
        strategy.notify(status);
    }
    
    public boolean hasNext() {
        return true;
    }
    
    public Question next() {
        Question result=strategy.getNext(data);
        
        if(result instanceof QuestionWithStats) {
            current=(QuestionWithStats) result;
        }
        
        return result;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
        
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public String getStatusText() {
        return strategy.getStatusText();
    }
}
