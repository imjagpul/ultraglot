package cz.srubarovi.teacher.core;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Answer {
    /** The answer. */
    private String answer;
    private String answerRaw;
//    private static final Pattern pureAnswerpattern=Pattern.compile("([^.\\];?,\\[!,#%()\\\\\u3002]*)");
    //chars ending question, in regex format
    private static final String LETTERS_SEPARATOR=".\\];?\\[!#%()\\\\\u3002";
    private static final Pattern pureAnswerpattern=Pattern.compile("([^"+LETTERS_SEPARATOR+"]+,[^"+LETTERS_SEPARATOR+"]+)\\.+$|([^,"+LETTERS_SEPARATOR+"]*)");
    
    private int[] accents;
    private int[] wordTypes=null;
    
    
    public Answer(String answerRaw) {
        setAnswerRaw(answerRaw);
    }
    
    public Answer(String answerRaw, int[] wordTypes, int[] accents) {
        this(answerRaw);
        if(accents!=null && accents.length>0)
            this.accents=accents;
        
        if(wordTypes!=null && wordTypes.length>0)
            this.wordTypes=wordTypes;
    }
    
    
    public boolean matches(String str) {
//        if (getAnswerRaw()..equals(second.getAnswerRaw().toUpperCase())) return true;
        return getAnswer().equalsIgnoreCase(str) ||
                getAnswer().equalsIgnoreCase(new Answer(str).getAnswer());
    }
    
    @Deprecated
    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof Answer))return false;
        Answer second = (Answer) obj;
        
        if (getAnswerRaw().equalsIgnoreCase(second.getAnswerRaw())) return true;
        return getAnswer().equalsIgnoreCase(second.getAnswer());
    }
    
    public String getAnswerRaw() {
        return answerRaw;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    
    public int[] getWordTypes() {
        return wordTypes;
    }
    
    public int[] getAccents() {
        return accents;
    }
    
    public void setAccents(int[] accents) {
        this.accents = accents;
    }
    
    public void setWordTypes(int[] wordTypes) {
        this.wordTypes = wordTypes;
    }
    
    public void setAnswerRaw(String answerRaw) {
        this.answerRaw = answerRaw;
        String newPure = getPureFromRaw(answerRaw);
        if (newPure==null) newPure="?";
        answer=newPure;
    }
    
    private String getPureFromRaw(String raw) {
        if (raw==null) return null;
        Matcher matcher = pureAnswerpattern.matcher(raw);
        if (!matcher.find())
            return raw;
        
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String ret = matcher.group(i);
            if(ret!=null) return ret.trim();
        }
        return raw;
    }
    
    void write(Writer writer) throws IOException {
        if("".equals(answerRaw)) {
            writer.write(" ");
        } else
            writer.write(answerRaw);
        writer.write(DataTest.NEWLINE);
    }
    
    public String toString() {
        return answerRaw;
    }
    
}