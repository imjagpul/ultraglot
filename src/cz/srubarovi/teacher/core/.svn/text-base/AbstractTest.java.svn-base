/*
 * NewClass.java
 *
 * Created on 30. srpen 2006, 18:49
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
abstract class AbstractTest implements Test {
    
    private List<Question> list;
    private final String name;
    
    public AbstractTest(Test srcTest) {
        name=srcTest.getName();
        list=createList(srcTest);
    }

    protected abstract List<Question> createList(final Test srcTest) ;
    
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
