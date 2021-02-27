/*
 * RemoveDuplicatesTest.java
 *
 * Created on 24. zברם 2007, 18:00
 */

package cz.srubarovi.teacher.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author Stepan
 */
public class RemoveDuplicatesTest extends AbstractTest {
    
    public RemoveDuplicatesTest(Test testToShuffle) {
        super(testToShuffle);
    }
    
    @Override
    protected List<Question> createList(final Test testToShuffle) {
        LinkedHashSet<Question> set=new LinkedHashSet<Question>();
        for (Question question : testToShuffle) {
            set.add(question);
        }

        return new ArrayList<Question>(set);
    }    
}
