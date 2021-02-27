/*
 * TestJoin.java
 *
 * Created on 30. srpen 2006, 18:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Imjagpul
 */
public class TestJoin implements Test {
    private List<Question> list;
    private final String name;
    
    /** Creates a new instance of TestJoin */
    public TestJoin(Collection<? extends Test> tests) {
        final String SEPARATOR="; ";
        ArrayList<Question> arrlist=new ArrayList<Question>();
        StringBuilder nameBuilder=new StringBuilder();
        for (Test test : tests) {
            
            nameBuilder.append(test.getName());
            nameBuilder.append(SEPARATOR);
            
            for (Question q : test) {
                arrlist.add(q);
            }
        }
        
        this.list=arrlist;
        
        //remove last separator from name
        nameBuilder.setLength(nameBuilder.length()-SEPARATOR.length());
        
        name=nameBuilder.toString();
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
