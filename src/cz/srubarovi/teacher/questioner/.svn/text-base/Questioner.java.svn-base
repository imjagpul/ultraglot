/*
 * EnglishCzech.java
 *
 * Created on 11. zברם 2007, 14:00
 */

package cz.srubarovi.teacher.questioner;

import cz.srubarovi.teacher.Settings;
import cz.srubarovi.teacher.core.Answer;
import cz.srubarovi.teacher.core.Question;
import cz.srubarovi.teacher.core.ReversedAnswer;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stepan
 */
public abstract class Questioner {
    
    private BufferedReader reader=null;
    private BufferedReader reverseReader=null;
    private String filename;
    private String reverseFilename;
    
    public static final String LANGCODE_CZ="cz";
    public static final String LANGCODE_EN="en";
    public static final String LANGCODE_ES="es";
    public static final String LANGCODE_RU="ru";
    public static final String LANGCODE_DE="de";
    public static final String LANGCODE_FR="fr";
    
    Questioner(String filename) {
        this(filename, null);
    }

    Questioner(String filename, String reverseFilename) {
        this.filename=filename;
        this.reverseFilename=reverseFilename;
        resetReader();
        resetReverseReader();
    }
    
    public abstract void edit(Question question, Frame parentComponent);
    
    public abstract String getSrcLangCode();
    public abstract String getForeignLangCode();
    
    protected void showChoiceBox(List<String> possibilites, Question question, Frame parentComponent) {
        showChoiceBox(possibilites, question, parentComponent, false);
    }
    
    protected void showChoiceBox(List<String> possibilites, Question question, Frame parentComponent, boolean reverse) {
        if(possibilites!=null && possibilites.size()>0) {
            
            ChoiceDialog choiceDialog=new ChoiceDialog(parentComponent);
            
            String result=choiceDialog.run(possibilites, question.getAnswer());
            handleResult(result, question, reverse);
        }
    }
    
    @SuppressWarnings("fallthrough")
    protected void handleResult(String result, Question question, boolean reverse) {
        //do nothing
        if(result==null) return;
        
        if(!reverse) {
        modeSwitch:
            switch(Settings.INSTANCE.getSuggestMode()) {
                case APPENDING:
                    //if something was entered already, append
                    String oldQuestion=question.getQuestion().trim();
                    if(oldQuestion.length()>0) {
                        
                        //check if not already present in the answer
                        String[] oldQuestionParts=ReversedAnswer.separatorPattern.split(oldQuestion);
                        
                        for (int i = 0; i < oldQuestionParts.length; i++) {
                            if(oldQuestionParts[i].equals(result)) {
                                //the item selected is already present
                                break modeSwitch;
                            }
                        }
                        
                        //append result seperated by a comma 
                        //(if a separator is present, use it instead)
                        oldQuestion=oldQuestion.trim();
                        
                        if(!ReversedAnswer.separatorChars.contains(
                                Character.toString(oldQuestion.charAt(
                                oldQuestion.length()-1)))) {
                            oldQuestion+=",";
                        }
                        
                        question.setQuestion(oldQuestion+" "+result);
                        break;
                    }
                    //nothing entered, fall through

                case SINGLE:
                    question.setQuestion(result);
                    break;
            }
        } else {
            if(question.getAnswerFor(result)==null) {
//                question.add(new Answer(result));
                question.setAnswer(result);
            }
        }
    }
    
    protected List<String> checkDictionary(String q) {
        return checkDictionary(q, false);
    }
    
    protected List<String> checkDictionary(String q, boolean reverse) {
        if(reverse && reverseFilename==null) return null;
        
        ArrayList<String> possibilites=null;
        
        BufferedReader dictionaryReader=reverse ? this.reverseReader : this.reader;
        
        try {
            String line;
            
            do { //find first occurence in file (note it has to be sorted)
                line=dictionaryReader.readLine();
            } while(line!=null && !line.startsWith(q));
            
            
            if(line!=null) {
                //something found (not neccesarily a match
                //(e.g. searching for 'adve' can find 'advect',
                //which is not a match (exact matches only)
                
                possibilites=new ArrayList<String>();
                
                while(line!=null && line.startsWith(q) && line.charAt(q.length())=='\t') {
                    if(line.length()!=q.length()+1) //untransleted word in db
                        possibilites.add(line.substring(q.length()+1));
                    line=dictionaryReader.readLine();
                }
            }
            
            //reset reader to zero
            if(!reverse) {
                resetReader();
            } else {
                resetReverseReader();
            }
        } catch(IOException exc) {
            exc.printStackTrace();
        }
        
        return possibilites;
    }
    
    public void dispose() {
        try {
            if(reader!=null)
                reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        reader=null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        dispose();
    }
    
    
    private void resetReader() {
        if(reader!=null) {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            reader=null;
        }
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream("res/"+filename), Charset.forName("UTF8")));
        } catch (FileNotFoundException ex) {
            try {
                reader=new BufferedReader(new InputStreamReader(new FileInputStream("../res/"+filename), Charset.forName("UTF8")));
            } catch (FileNotFoundException exc) {
                ex.printStackTrace();
            }
        }
    }
    
    private void resetReverseReader() {
        if(reverseFilename==null) return;
        
        if(reverseReader!=null) {
            try {
                reverseReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            reverseReader=null;
        }
        try {
            reverseReader=new BufferedReader(new InputStreamReader(new FileInputStream("res/"+reverseFilename), Charset.forName("UTF8")));
        } catch (FileNotFoundException ex) {
            try {
                reverseReader=new BufferedReader(new InputStreamReader(new FileInputStream("../res/"+reverseFilename), Charset.forName("UTF8")));
            } catch (FileNotFoundException exc) {
                ex.printStackTrace();
            }
        }
    }
    
    protected String readUrl(String url) throws MalformedURLException {
        return readUrl(new URL(url));
    }
    
    protected String readUrl(URL url) {
        //URL url=new URL();
        try {
            URLConnection con=url.openConnection();
            con.connect();
            Object content=con.getContent();
            if(content instanceof String)
                return (String)content;
            
            if(content instanceof InputStream) {
                String enc=con.getContentEncoding();
                if(enc==null) enc="UTF8";
                
                BufferedReader readerUrl=new BufferedReader(new InputStreamReader((InputStream) content, enc));
                StringBuilder b=new StringBuilder();
                String line;
                do {
                    line=readerUrl.readLine();
                    if(line!=null)
                        b.append(line);
                    else
                        break;
                } while(line!=null);
                
                readerUrl.close();
                return b.toString();
            }
            
            System.out.println(content.getClass().toString());
        } catch(IOException exc) {
            exc.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return getForeignLangCode()+" 2 "+getSrcLangCode();
    }
    
}
