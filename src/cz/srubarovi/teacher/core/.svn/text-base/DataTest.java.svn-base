/*
 * Test.java
 *
 * Created on 25. duben 2006, 13:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.TestFileFormatException;
import cz.srubarovi.teacher.TestIsXmlFileFormatException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A single test.
 * @author Imjagpul
 */
public class DataTest implements Test {
    
    protected List<Question> questions;
    /** The name of the test. */
    protected String name;
    static final char NEWLINE='\n';//don't change!
    private static final String HEADER="TESTv1";
    private static final String XMLHEADER="<?xml ";
    private static final Charset CHARSET=Charset.forName("utf8");
    
    private String path;
    
    /** Creates a new instance of Test */
    public DataTest(String name, Collection<Question> questions, String path) {
        this.name=name;
        this.path=path;
        this.questions=new ArrayList<Question>(questions);
    }
    
    public DataTest(String name) {
        this(name, null);
    }
    
    public DataTest(String name, String path) {
        this(name, new ArrayList<Question>(), path);
    }
    
    public void addQuestion(Question q) {
        questions.add(q);
    }
    
    public Question getQuestionAt(int index) {
        return questions.get(index);
    }
    
    public void removeQuestionAt(int index) {
        questions.remove(index);
    }
    
    public void removeQuestion(Question q) {
        questions.remove(q);
    }
    
    public void writeFile(File file) throws IOException {
        Writer fw=null;
        try {
            fw=new OutputStreamWriter(new FileOutputStream(file), CHARSET);
            fw.write(HEADER);
            fw.write(name);
            fw.write(NEWLINE);
            for(Question q : questions) {
                q.write(fw);
            }
        } finally {
            if(fw!=null) fw.close();
        }
    }
    
    public int count() {
        return questions.size();
    }
    
    /**
     * Reads the test from a file
     * @param file          the file to read
     * @throws              java.io.IOException
     *                      when an IO error occurs
     * @throws              cz.srubarovi.teacher.TestFileFormatException
     *                      if the file was not in correct/known format
     * @return              the read Test
     */
    public static DataTest fromStream(InputStream fis, String pathname) throws IOException, TestFileFormatException {
        BufferedReader fr=null;
        ArrayList<Question> questions=null;
        String name=null;
        try {
            
            fr=new BufferedReader(new InputStreamReader(fis, CHARSET));
            
            //header
            char[] buf=new char[HEADER.length()];
            fr.read(buf);
            String readHeader=new String(buf);
            
            
            if(!HEADER.equals(readHeader)) { //spatna hlavicka
                if(XMLHEADER.equals(readHeader)) { //je to xml soubor, bez .xml pripony
                    //zkusit nacist xml test
                    
                    //je to takovy blby hack
                    throw new TestIsXmlFileFormatException();
                }
                
                throw new TestFileFormatException(readHeader);
            }
            
            //name
            name=fr.readLine();
            
            questions=new ArrayList<Question>();
            Question q;
            
            do {
                q=Question.read(fr);
                if(q!=null) {
                    questions.add(q);
                }
            } while (q!=null);
            
        } finally {
            if(fr!=null) fr.close();
        }
        
        if(questions==null) return null;
        return new DataTest(name, questions, pathname);
    }
    
    public String toString() {
        return name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Iterator<Question> iterator() {
        return questions.iterator();
    }
    
    public void insertQuestion(Question question, int i) {
        questions.add(i, question);
    }
    
    public String getPath() {
        return path;
    }
}
