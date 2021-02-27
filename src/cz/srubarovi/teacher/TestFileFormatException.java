/*
 * TestFileFormatException.java
 *
 * Created on 28. duben 2006, 17:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher;

/**
 * Thrown when the test file format is not in correct/known format.
 * @author Imjagpul
 */
public class TestFileFormatException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>TestFileFormatException</code> without detail message.
     */
    public TestFileFormatException() {
    }
    
    
    /**
     * Constructs an instance of <code>TestFileFormatException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TestFileFormatException(String msg) {
        super(msg);
    }
}
