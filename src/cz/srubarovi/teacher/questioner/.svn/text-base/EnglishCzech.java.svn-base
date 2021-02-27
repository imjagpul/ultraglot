/*
 * EnglishCzech.java
 *
 * Created on 11. zברם 2007, 17:05
 */

package cz.srubarovi.teacher.questioner;

import cz.srubarovi.teacher.core.Question;
import java.awt.Frame;
import java.util.List;

/**
 *
 * @author Stepan
 */
public class EnglishCzech extends Questioner {
    public EnglishCzech() {
        super("encz.txt", "czen.txt");
    }
    
    @Override
    public void edit(Question question, Frame parentComponent) {
        List<String> translations;
        String q=question.getAnswer();
        boolean reverse=(q==null || q.isEmpty());
        if(reverse){
            translations=checkDictionary(question.getQuestion(), true);
        } else {
            translations=checkDictionary(q);
        }
        
        showChoiceBox(translations, question, parentComponent, reverse);
    }

    @Override    
    public String getSrcLangCode() {
        return LANGCODE_CZ;
    }

    @Override    
    public String getForeignLangCode() {
        return LANGCODE_EN;
    }
}
