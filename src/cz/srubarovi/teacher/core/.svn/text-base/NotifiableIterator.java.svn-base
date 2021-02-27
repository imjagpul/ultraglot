/*
 * NotifiableIterator.java
 *
 * Created on 26. prosinec 2007, 12:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.QuestionFrame;
import java.util.EnumSet;
import java.util.Iterator;

/**
 *
 * @author Stepan
 */
public interface NotifiableIterator extends Iterator<Question> {
    void notify(EnumSet<QuestionFrame.Status> status);
    String getStatusText();
}
