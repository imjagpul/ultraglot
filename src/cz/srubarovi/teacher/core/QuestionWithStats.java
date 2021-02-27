/*
 * QuestionWithStats.java
 *
 * Created on 25. listopad 2007, 12:17
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.QuestionFrame;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Stepan
 */
public class QuestionWithStats extends Question {
    private Question question;
    private String path;
    
    
    
    /** Creates a new instance of QuestionWithStats */
    public QuestionWithStats(Question question, String path) {
        super(question.getQuestion(), question.getLangDef(), question.answers.toArray(new Answer[question.answers.size()]));
        
        this.question=question;
        this.path=path;
    }
    
    public void notify(EnumSet<QuestionFrame.Status> status) {
        StatsManager.INSTANCE.add(path, question.getQuestion(), status);
        
        lastSeenCache=System.currentTimeMillis();
    }
    
    public Question createReverse() {
        Question retValue;
        
        retValue = super.createReverse();
        return new QuestionWithStats(retValue, path);
    }
    
    private long lastSeenCache=-1;
    public long getLastSeenTime() {
        if(lastSeenCache==-1) {
            List<StatsManager.StatsItem> list=getStats();
            
            lastSeenCache=0;
            for(StatsManager.StatsItem item : list) {
                lastSeenCache=Math.max(item.getTimestamp(), lastSeenCache);
            }
        }
        
        return lastSeenCache;
    }
    
    public List<StatsManager.StatsItem> getStats() {
        return StatsManager.INSTANCE.get(path, question.getQuestion());
    }
}
