/*
 * ItalianEnglish.java
 *
 * Created on 16. prosinec 2007, 12:09
 */

package cz.srubarovi.teacher.questioner;

import cz.srubarovi.teacher.core.Question;
import java.awt.Frame;
import java.util.List;

/**
 *
 * @author Stepan
 */
public class SpanishEnglish extends Questioner {
    
    /** Creates a new instance of ItalianEnglish */
    public SpanishEnglish() {
        super("esen.txt");
    }

    @Override
    public void edit(Question question, Frame parentComponent) {
        List<String> translations=checkDictionary(question.getAnswer());
        showChoiceBox(translations, question, parentComponent);
    }

    @Override    
    public String getSrcLangCode() {
        return LANGCODE_EN;
    }

    @Override    
    public String getForeignLangCode() {
        return LANGCODE_ES;
    }    
}
