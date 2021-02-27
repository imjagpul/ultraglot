/*
 * NewInterface.java
 *
 * Created on 29. srpen 2006, 18:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

/**
 *
 * @author Imjagpul
 */
public interface Test extends Iterable<Question> {
    int count();
    String getName();
//    String getPath();
}
