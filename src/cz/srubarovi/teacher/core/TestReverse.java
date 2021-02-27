/*
 * TestReverse.java
 *
 * Created on 2. zברם 2006, 10:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Imjagpul
 */
public class TestReverse extends AbstractTest {
    
    /** Creates a new instance of TestReverse */
    public TestReverse(Test inputTest) {
        super(inputTest);
    }

    protected List<Question> createList(final Test srcTest) {
        ArrayList<Question> result=new ArrayList<Question>(srcTest.count());
        
        for (Question q : srcTest) {
            result.add(q.createReverse());
        }
        
        return result;
    }
    
}
