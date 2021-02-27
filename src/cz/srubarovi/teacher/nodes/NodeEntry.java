/*
 * NodeEntry.java
 *
 * Created on 27. leden 2007, 20:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.nodes;

import cz.srubarovi.teacher.TestFileFormatException;
import cz.srubarovi.teacher.TestIsXmlFileFormatException;
import cz.srubarovi.teacher.core.DataTest;
import cz.srubarovi.teacher.core.Test;
import cz.srubarovi.teacher.core.XmlDataTest;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Icon;

/**
 *
 * @author Stepan
 */
public interface NodeEntry {
    enum TestType {
        TESTv1 {
            public DataTest loadTest(NodeEntry entry) throws IOException, TestFileFormatException {
                
                try{
                    return DataTest.fromStream(entry.getInputStream() , entry.getPathname());
                } catch(TestIsXmlFileFormatException exc) {
                    return XmlDataTest.fromStream(entry.getInputStream() , entry.getPathname());
                }
            }
        },
        XML { public DataTest loadTest(NodeEntry entry) throws IOException, TestFileFormatException {
            return XmlDataTest.fromStream(entry.getInputStream() , entry.getPathname());
        }};
        
        public abstract DataTest loadTest(NodeEntry entry) throws IOException, TestFileFormatException;
    };
    
    TestType getType();
    InputStream getInputStream();
    String toString();
    String getPathname();
    Icon getIcon();
}

