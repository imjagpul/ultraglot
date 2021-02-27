/*
 * DifficultyBasedStrategy.java
 *
 * Created on 26. prosinec 2007, 14:40
 */

package cz.srubarovi.teacher.strategy;

import cz.srubarovi.teacher.QuestionFrame;
import cz.srubarovi.teacher.QuestionFrame.Status;
import cz.srubarovi.teacher.core.NotifiableIterator;
import cz.srubarovi.teacher.core.Question;
import cz.srubarovi.teacher.core.QuestionWithStats;
import cz.srubarovi.teacher.core.SmartTestIterator;
import cz.srubarovi.teacher.core.StatsManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static cz.srubarovi.teacher.core.StatsManager.StatsItem;
import cz.srubarovi.teacher.core.StatsManager.StatsItem;
import java.util.EnumSet;
/**
 *
 * @author Stepan
 */
public class DifficultyBasedStrategy extends AbstractStrategy {
    
    private static final long HOUR=3600*1000;
    private static final long DAY=HOUR*24;
    private static final long[] SCHELUDES=new long[] {DAY, 3*DAY, 7*DAY, 14*DAY, 30*DAY, 40*DAY};
    
    protected enum Stage {
        TESTING, TRAINING, OVERLEARNING
    }
    
    protected Stage stage;
    private String status;
    
    private int trainingDay;
    private NotifiableIterator iterator;
    private int overlearningTurn;
    
    static int longToDay(long timestamp) {
        return (int)(timestamp / DAY);
    }
    
    static void filterStats(List<StatsManager.StatsItem> stats) {
        //necha jenom prvni status z kazdeho dne a seradi podle casu
        
        //setridit podle casu
        Collections.sort(stats, new Comparator<StatsManager.StatsItem>() {
            public int compare(StatsManager.StatsItem o1, StatsManager.StatsItem o2) {
                return Long.valueOf(o1.getTimestamp()).compareTo(o2.getTimestamp());
            }
        });
        
        int lastday=-1;
        for (int i = 0; i < stats.size(); ) {
            StatsManager.StatsItem stat=stats.get(i);
            int day=longToDay(stat.getTimestamp());
            if(day==lastday) {
                stats.remove(i);
            } else {
                i++;
                lastday=day;
            }
        }
    }
    
    public static int getDifficulty(QuestionWithStats q) {
        List<StatsItem> stats=q.getStats();
        filterStats(stats);
        
        int wrong=0;
        int help=0;
        int correct=0;
        
        for(StatsItem item : stats) {
            EnumSet<Status> status=item.getStatus();
            if(status.contains(Status.HELP_USED)) help++;
            else if(status.contains(Status.WRONG_ANSWER)) wrong++;
            else correct++;
        }
        
        int difficulty=-wrong-2*help+correct;
        if(difficulty<0) difficulty=0;
        if(difficulty>=SCHELUDES.length) difficulty=SCHELUDES.length-1;
        return difficulty;
    }
    
    public static long calculateScheludedTime(QuestionWithStats q) {
        return q.getLastSeenTime()+SCHELUDES[getDifficulty(q)];
    }
    
    private static class ScheludeTimeComparator implements Comparator<QuestionWithStats> {
        public int compare(QuestionWithStats o1, QuestionWithStats o2) {
            return Long.valueOf(calculateScheludedTime(o1)).compareTo(calculateScheludedTime(o2));
        }
        
    }
    
    private List<QuestionWithStats> lastQuery=null;
    
    public Question getNext(List<QuestionWithStats> questions) {
        
        if (lastQuery != questions ||
                (!questions.containsAll(lastQuery) || 
                lastQuery.size() != questions.size())) {
            resetStrategy();
        }
        lastQuery=questions;
        
        if(stage==Stage.TESTING) {
            //zkusit jednou vsechny, ktere nebyly zkouseny v dostatecne dobe
            //(urcuje se podle obtiznosti - pomer correct/wrong/help)
            randomize(questions);
            Collections.sort(questions, new ScheludeTimeComparator());
            
            QuestionWithStats next=questions.get(0);
            
            if(calculateScheludedTime(next)>System.currentTimeMillis()) {
                stage=Stage.TRAINING;
            } else {
                
                int i;
                for (i = 0; i < questions.size(); i++) {
                    if(calculateScheludedTime(questions.get(i))>System.currentTimeMillis()) {
                        break;
                    }
                }
                
                //TODO : mozna vzit nahodne do i, aby to nebylo ve stejnem poradi
                //nebo mozna jenom s nejakou pravdepodobnosti, ty bliz nule vice pravdepodobne
                
                status="Testing stage ("+i+" left)";
                return next;
            }
        }
        
        if(trainingDay==0) {
            trainingDay=longToDay(System.currentTimeMillis());
        }
        
        if(stage==Stage.TRAINING) {
            //trenink: ty otazky, ktere byly zkouseny dneska a byla
            //v nich udelana chyba (pri prvnim pokusu)
            //normalni SmartIterator
            
            if(iterator==null) {
                sortByTimeRandom(questions);
                Collections.reverse(questions);
                
                int i;
                for (i = 0; i < questions.size(); i++) {
                    if(longToDay(questions.get(i).getLastSeenTime())<trainingDay)
                        break;
                }
                
                ArrayList<Question> toTrain=new ArrayList<Question>(i);
                for(QuestionWithStats q : questions.subList(0, i)) {
                    List<StatsManager.StatsItem> stats=q.getStats();
                    filterStats(stats);
                    
                    StatsManager.StatsItem last=stats.get(stats.size()-1);
                    
                    //je to setrizene podle casu, a uz jsou vyfiltrovane ty, co
                    //nebyly zkouseny dnes, takze posledni zaznam musi byt
                    //ze dnesniho dne
                    assert longToDay(last.getTimestamp())==trainingDay;
                    
                    if(!last.getStatus().isEmpty()) {
                        //jestli byla jakakoli chyba, trenovat
                        toTrain.add(q);
                    }
                }
                Collections.shuffle(toTrain);
                iterator=new SmartTestIterator(toTrain);
            }
            if(iterator.hasNext()) {
                status="Training ("+iterator.getStatusText()+")";
                return iterator.next();
            } else {
                stage=Stage.OVERLEARNING;
                iterator=null;
            }
        }
        
        //overlearning: zkouset porad dokola vsechny slovicka, SmartIterator
        if(iterator==null || !iterator.hasNext()) {
            overlearningTurn++;
            Collections.shuffle(questions);
            iterator=new SmartTestIterator(questions);
        }
        status="Over-learning (turn "+overlearningTurn+", "+iterator.getStatusText()+")";
        return iterator.next();
    }
    
    public String getStatusText() {
        return status;
    }
    
    public void notify(EnumSet<QuestionFrame.Status> status) {
        if(iterator!=null)
            iterator.notify(status);
    }
    
    protected void resetStrategy() {
        status="";
        stage=Stage.TESTING;
        trainingDay=0;
        overlearningTurn=0;
        iterator=null;
    }
    
}
