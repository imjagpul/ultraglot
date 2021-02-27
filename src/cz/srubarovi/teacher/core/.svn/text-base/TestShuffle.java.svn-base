/*
 * TestShuffle.java
 *
 * Created on 29. srpen 2006, 18:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Imjagpul
 */
public class TestShuffle extends AbstractTest {
    
    public TestShuffle(Test testToShuffle) {
        super(testToShuffle);
    }
    
    @Override
    protected List<Question> createList(final Test testToShuffle) {
        ArrayList<Question> arrlist=new ArrayList<Question>(testToShuffle.count());
        
        for (Question question : testToShuffle) {
            arrlist.add(question);
        }
        
        Collections.shuffle(arrlist);
        return arrlist;        
    }
}
