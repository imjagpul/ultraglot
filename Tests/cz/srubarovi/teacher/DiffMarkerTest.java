/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.srubarovi.teacher;

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Stepan
 */
public class DiffMarkerTest extends TestCase {

    public DiffMarkerTest(String testName) {
        super(testName);
    }

    public void testGetMostSimilar() {
        String answer = "bill";
        String exp1 = "kill";
        String exp2 = "grill";

        String result = DiffMarker.getMostSimilar(answer, exp1, exp2);

        assertEquals(exp1, result);

        result = DiffMarker.getMostSimilar(answer, exp2);
        assertEquals(exp2, result);

        answer = "abc";
        exp1 = "ac";
        exp2 = "ad";

        result = DiffMarker.getMostSimilar(answer, exp1, exp2);
        assertEquals(exp1, result);
    }

    public void testMarkDiff() {
        ArrayList<Integer> diffMissing=new ArrayList<Integer>();
        ArrayList<Integer> diffWrong=new ArrayList<Integer>();
        ArrayList<Integer> diffRedundant=new ArrayList<Integer>();
    
        //
        String expected="abcdewrqasdf";
        String answer="abc";
        
        DiffMarker.markDiff(diffMissing, diffWrong, diffRedundant, answer, expected);
        
        assertEquals(1, diffMissing.size());
        assertEquals(0, diffWrong.size());
        assertEquals(0, diffRedundant.size());
        
        assertEquals(Integer.valueOf(3), diffMissing.get(0));

        //
        expected="abc";
        answer="abcdewrqasdf";
        
        DiffMarker.markDiff(diffMissing, diffWrong, diffRedundant, answer, expected);

        assertEquals(0, diffMissing.size(), 0);
        assertEquals(0, diffWrong.size());
        assertEquals(9, diffRedundant.size());
        
        assertEquals(Integer.valueOf(3), diffRedundant.get(0));
        assertEquals(Integer.valueOf(4), diffRedundant.get(1));
        assertEquals(Integer.valueOf(11), diffRedundant.get(8));
        
        expected="abc";
        answer="axc";
        
        DiffMarker.markDiff(diffMissing, diffWrong, diffRedundant, answer, expected);

        assertEquals(0, diffMissing.size(), 0);
        assertEquals(1, diffWrong.size());
        assertEquals(0, diffRedundant.size());
        
        assertEquals(Integer.valueOf(1), diffWrong.get(0));
        
    }

    public void testLCSAlgorithm() {
        System.out.println("LCSAlgorithm");
        String a = "abcd";
        String b = "aaad";
        String expResult = "ad";
        String result = DiffMarker.LCSAlgorithm(a, b);
        assertEquals(expResult, result);

        a = "abcd";
        b = "a";
        expResult = "a";
        result = DiffMarker.LCSAlgorithm(a, b);
        assertEquals(expResult, result);

        a = "abcd";
        b = "bcd";
        expResult = "bcd";
        result = DiffMarker.LCSAlgorithm(a, b);
        assertEquals(expResult, result);

        a = "abcd";
        b = "aaaaaaaaaa";
        expResult = "a";
        result = DiffMarker.LCSAlgorithm(a, b);
        assertEquals(expResult, result);

    }
}
