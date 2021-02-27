/*
 * StatsManagerTest.java
 * JUnit based test
 *
 * Created on 25. prosinec 2007, 18:10
 */

package cz.srubarovi.teacher.core;

import junit.framework.*;
import cz.srubarovi.teacher.QuestionFrame.Status;
import java.util.EnumSet;

/**
 *
 * @author Stepan
 */
public class StatsManagerTest extends TestCase {
    
    public StatsManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public void testShortToStatusAndVicaVersa() {
        EnumSet<Status> set=EnumSet.noneOf(Status.class);
        assertEquals(set, StatsManager.StatsFile.shortToStatus(StatsManager.StatsFile.statusToShort(set)));
        
        set=EnumSet.allOf(Status.class);
        assertEquals(set, StatsManager.StatsFile.shortToStatus(StatsManager.StatsFile.statusToShort(set)));

        set=EnumSet.of(Status.WRONG_ACCENT, Status.HELP_USED);
        assertEquals(set, StatsManager.StatsFile.shortToStatus(StatsManager.StatsFile.statusToShort(set)));

        set=EnumSet.of(Status.TIME_OUT);
        assertEquals(set, StatsManager.StatsFile.shortToStatus(StatsManager.StatsFile.statusToShort(set)));
        
    }
    
}
