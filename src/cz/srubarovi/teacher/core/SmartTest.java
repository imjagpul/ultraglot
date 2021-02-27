/*
 * SmartTest.java
 *
 * Created on 16. prosinec 2006, 11:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Imjagpul
 */
public class SmartTest implements Test {
    
    private List<Question> list;
    private final String name;
    
    /** Creates a new instance of SmartTest */
    public SmartTest(Test srcTest) {
        name=srcTest.getName();
        list=createList(srcTest);   
    }
    
    protected List<Question> createList(final Test srcTest) {
        ArrayList<Question> arrlist=new ArrayList<Question>(srcTest.count());
        
        for (Question question : srcTest) {
            arrlist.add(question);
        }
        
        return arrlist;
    }
    
    @Override
    public Iterator<Question> iterator() {
        return new SmartTestIterator(list);
    }
    
    @Override
    public int count() {
        return list.size();
    }
    
    @Override
    public String getName() {
        return name;
    }
}
