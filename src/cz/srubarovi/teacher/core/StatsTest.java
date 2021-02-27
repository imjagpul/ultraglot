/*
 * StatsTest.java
 *
 * Created on 24. listopad 2007, 11:36
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.strategy.Strategy;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Stepan
 */
public class StatsTest implements Test {
    
    private QuestionWithStats[] data;
    private String name;
    private Strategy strategy;
    /** Creates a new instance of StatsTest */
    private StatsTest(Strategy strategy) {
        this.strategy=strategy;
    }
    
    public static StatsTest load(DataTest[] tests, String[] streamNames, boolean reverse, Strategy strategy) {
        assert tests.length==streamNames.length;
        
        StatsTest statsTest=new StatsTest(strategy);
        
        final String SEPARATOR="; ";
        StringBuilder nameBuilder=new StringBuilder();
        
        ArrayList<QuestionWithStats> questions=new ArrayList<QuestionWithStats>();
        
        for (int i = 0; i < streamNames.length; i++) {
            
            nameBuilder.append(tests[i].getName());
            nameBuilder.append(SEPARATOR);
            
            for (int j = 0; j < tests[i].count(); j++) {
                Question q=tests[i].getQuestionAt(j);                
                
                if(reverse) {
                    q=q.createReverse();
                }
                
                QuestionWithStats qs=new QuestionWithStats(q, streamNames[i]);                
                questions.add(qs);
            }
        }
        
        statsTest.data=new QuestionWithStats[questions.size()];
        questions.toArray(statsTest.data);
        
        //remove last separator from name
        nameBuilder.setLength(nameBuilder.length()-SEPARATOR.length());
        
        statsTest.name=nameBuilder.toString();
        
        return statsTest;
    }
    
    public int count() {
        return data.length;
    }
    
    public String getName() {
        return name;
    }
    
    public Iterator<Question> iterator() {
        return new StatsTestIterator(data, strategy);
    }
    
}
