/*
 * TestProblematicOnly.java
 *
 * Created on 29. srpen 2006, 19:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Imjagpul
 */
public class TestCut implements Test {
    
    private List<Question> list;
    private final String name;
    
    public TestCut(Test inputTest, List<Integer> indicies) {
        name=inputTest.getName();
        
        ArrayList<Question> list=new ArrayList<Question>();
        int i=0;
        for (Question q : inputTest) {
            i++;
            if(indicies.contains(i)) list.add(q);
        }
        this.list=list;
    }

    
    public int count() {
        return list.size();
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public Iterator<Question> iterator() {
        return list.iterator();
    }

    
}
