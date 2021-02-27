/*
 * ReversedAnswer.java
 *
 * Created on 3. listopad 2007, 17:44
 */

package cz.srubarovi.teacher.core;

import java.util.regex.Pattern;

/**
 *
 * @author Stepan
 */
public class ReversedAnswer extends Answer {
    public static final String separatorChars=",;/";
    public static final Pattern separatorPattern=Pattern.compile("\\s*["+separatorChars+"]\\s*");
    
    /** Creates a new instance of ReversedAnswer */
    public ReversedAnswer(String answerRaw) {
        super(answerRaw);
    }
    
    @Override
    public boolean matches(String str) {
        String[] answers=separatorPattern.split(getAnswerRaw());
        
        for (int i = 0; i < answers.length; i++) {
            if(answers[i].equalsIgnoreCase(str.trim())) return true;
        }
        
        return super.matches(str);
    }
    
    
}
