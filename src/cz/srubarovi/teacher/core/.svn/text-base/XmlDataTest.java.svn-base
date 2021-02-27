/*
 * XmlDataTest.java
 *
 * Created on 7. zברם 2007, 13:56
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.TestFileFormatException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import cz.srubarovi.teacher.xml.Query;
import java.io.File;

/**
 *
 * @author Stepan
 */
//tahle trida se nakonec nepouziva, misto ni se vytvari hned DataTest,
//takze existuje jenom kvuli statickym metodam
public class XmlDataTest /*extends DataTest */{
    public static final JAXBContext CONTEXT;
    
    private XmlDataTest() {}
    
    static {
        JAXBContext context=null;
        try {
            context=JAXBContext.newInstance("cz.srubarovi.teacher.xml");
        } catch(JAXBException exc) {
            exc.printStackTrace();;
        }
        
        CONTEXT=context;
    }
    
    public static DataTest fromStream(InputStream stream, String pathname) throws TestFileFormatException {
        try {
            Unmarshaller unmarshaller=CONTEXT.createUnmarshaller();
            cz.srubarovi.teacher.xml.Test test= (cz.srubarovi.teacher.xml.Test) unmarshaller.unmarshal(stream);
            DataTest result=new DataTest(test.getName(), pathname);

            LanguageDefinition lang=LangDefManager.INSTANCE.getLang(test.getLangcode());
            
            for(Query q : test.getQuery()) {
                Answer[] answers=new Answer[q.getAnswer().size()];
                for (int j = 0; j < answers.length; j++) {
                    
                    cz.srubarovi.teacher.xml.Answer a=q.getAnswer().get(j);
                    int[] wordTypes=new int[a.getWordType().size()];
                    for (int i = 0; i < wordTypes.length; i++) {
                        wordTypes[i]=a.getWordType().get(i).intValue();
                    }
                    
                    
                    int[] accents=new int[a.getAccents().size()];
                    for (int i = 0; i < accents.length; i++) {
                        accents[i]=a.getAccents().get(i).intValue();
                    }
                    
                    answers[j]=new Answer(a.getValue(), wordTypes, accents);
                }
                Question question=new Question(q.getQuestion(), lang, answers);
                //Question question=new Question(q.getQuestion(), q. q.getAnswer().toArray(new Answer[q.getAnswer().size()]));
                result.questions.add(question);
            }
            
            return result;
        } catch(JAXBException exc) {
            throw new TestFileFormatException();
        }
    }
    
    public static void saveAsXml(Test test, File file) throws IOException {
        cz.srubarovi.teacher.xml.Test root=new cz.srubarovi.teacher.xml.Test();
        root.setName(test.getName());
        
        Question first=test.iterator().next();
        LanguageDefinition langDef=null;
        if(first!=null) {
            langDef=first.getLangDef();
            if(langDef!=null) {
                root.setLangcode(langDef.getLangcode());
            }
        }
        
        for(Question q : test) {
            
            //assert that all questions are of the same language
            assert q.getLangDef()==langDef;
            
            Query xmlQuery=new Query();
            xmlQuery.setQuestion(q.getQuestion());
            
            for(Answer a : q) {
                cz.srubarovi.teacher.xml.Answer xmlAnswer=new cz.srubarovi.teacher.xml.Answer();
                xmlAnswer.setValue(a.getAnswerRaw());
                
                if(a.getWordTypes()!=null) {
                    for (int i = 0; i < a.getWordTypes().length; i++) {
                        xmlAnswer.getWordType().add((short) a.getWordTypes()[i]);
                    }
                }
                
                if(a.getAccents()!=null) {
                    for (int i = 0; i < a.getAccents().length; i++) {
                        xmlAnswer.getAccents().add(BigInteger.valueOf(a.getAccents()[i]));
                    }
                }
                
                xmlQuery.getAnswer().add(xmlAnswer);
            }
            
            root.getQuery().add(xmlQuery);
        }
        
        //write to xml file
        OutputStream out=null;
        try {
            Marshaller marshaller=CONTEXT.createMarshaller();
            
            out=new FileOutputStream(file);
            marshaller.marshal(root, out);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } finally {
            if(out!=null)
                out.close();
        }
    }
    
    public static boolean needsDegradationForTestv1(Test test) {
//        boolean result=false;
        for(Question q : test) {
            for(Answer a : q) {
                if(a.getWordTypes()!=null || a.getAccents()!=null)
                    return true;
            }
        }
        
        return false;
    }
}
