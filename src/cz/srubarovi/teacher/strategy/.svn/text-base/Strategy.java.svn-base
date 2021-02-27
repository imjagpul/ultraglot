/*
 * Strategy.java
 *
 * Created on 26. prosinec 2007, 12:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.strategy;

import cz.srubarovi.teacher.QuestionFrame.Status;
import cz.srubarovi.teacher.core.Question;
import cz.srubarovi.teacher.core.QuestionWithStats;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Stepan
 */
public interface Strategy {
    Question getNext(List<QuestionWithStats> questions);

    String getStatusText();
    
    void notify(EnumSet<Status> status);
}
