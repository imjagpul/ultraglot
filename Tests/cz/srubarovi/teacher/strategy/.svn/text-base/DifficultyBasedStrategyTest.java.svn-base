/*
 * DifficultyBasedStrategyTest.java
 * JUnit based test
 *
 * Created on 26. prosinec 2007, 16:33
 */

package cz.srubarovi.teacher.strategy;

import java.util.Arrays;
import junit.framework.*;
import cz.srubarovi.teacher.QuestionFrame.Status;
import java.util.Collections;
import java.util.List;
import static cz.srubarovi.teacher.core.StatsManager.StatsItem;
import cz.srubarovi.teacher.core.StatsManager.StatsItem;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author Stepan
 */
public class DifficultyBasedStrategyTest extends TestCase {
    
    public DifficultyBasedStrategyTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of longToDay method, of class cz.srubarovi.teacher.strategy.DifficultyBasedStrategy.
     */
    public void testLongToDay() {
        System.out.println("longToDay");
        
        long timestamp = 0L;
        int expResult = 0;
        int result = DifficultyBasedStrategy.longToDay(timestamp);
        assertEquals(expResult, result);
        
        timestamp = 40L*3600L*1000L*24L + 35L;
        expResult = 40;
        result = DifficultyBasedStrategy.longToDay(timestamp);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of filterStats method, of class cz.srubarovi.teacher.strategy.DifficultyBasedStrategy.
     */
    public void testFilterStats() {
        System.out.println("filterStats");
        
        List<StatsItem> stats = Collections.emptyList();
        DifficultyBasedStrategy.filterStats(stats);
        
        stats = new ArrayList<StatsItem>();
        StatsItem a,b,c,d,e;
        
        stats.add(a=new StatsItem(40L*3600*1000*24 + 35, EnumSet.allOf(Status.class)));
        stats.add(b=new StatsItem(50L*3600*1000*24 + 35, EnumSet.noneOf(Status.class)));
        stats.add(c=new StatsItem(50L*3600*1000*24 + 123, EnumSet.of(Status.HELP_USED)));
        stats.add(d=new StatsItem(50L*3600*1000*24 + 35432, EnumSet.of(Status.WRONG_ACCENT)));
        stats.add(e=new StatsItem(43L*3600*1000*24 + 35432, EnumSet.of(Status.WRONG_ACCENT)));
        DifficultyBasedStrategy.filterStats(stats);
        
        assertEquals(3, stats.size());
        assertTrue(stats.containsAll(Arrays.asList(a,b,e)));
    }
    
}
