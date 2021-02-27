package cz.srubarovi.teacher.core;

import java.io.*;
import java.util.*;

public class Question extends AbstractList<Answer> {
    /** The question. */
    private String question;
    /** All correct answers. */
    protected ArrayList<Answer> answers;
    /** If this question is to be skipped. */
    private boolean skipDesired=false;
    
    private LanguageDefinition langDef=null;
    
    
    
    public Question(String question, Answer... answers) {
        this.setQuestion(question);
        this.answers=new ArrayList<Answer>();
        
        if(answers.length==0) {
            this.answers.add(new Answer(""));
        } else {
            for (int i = 0; i < answers.length; i++) {
                this.answers.add(answers[i]);
            }
        }
    }
    
    public Question(String question, LanguageDefinition langDef, Answer... answers) {
        this(question, answers);
        this.langDef=langDef;
    }
    
    @Override    
    public String toString() {
        return getQuestion();
    }
    
    public String getAnswer() {
        return answers.get(0).getAnswer();
    }
    
    public String getAnswerRaw() {
        return answers.get(0).getAnswerRaw();
    }
    
    public void setAnswer(String newAnswer) {
        answers.get(0).setAnswerRaw(newAnswer);
    }
    
    void write(Writer writer) throws IOException {
        if("".equals(question)) question=" ";
        writer.write(question);
        writer.write(DataTest.NEWLINE);
        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).write(writer);
        }
        writer.write(DataTest.NEWLINE);
    }
    
    static Question read(BufferedReader reader) throws IOException  {
        String question=reader.readLine();
        if(question==null || "".equals(question)) return null;
        
        ArrayList<Answer> answers=new ArrayList<Answer>();
        String line;
        do {
            line=reader.readLine();
            if(line!=null && !"".equals(line)) {
                answers.add(new Answer(line.trim()));
            }
        } while(line!=null && !"".equals(line));
        return new Question(question.trim(), answers.toArray(new Answer[answers.size()]));
    }
    
    private static final String DELIMITER=" ; ";
    public Question createReverse() {
        StringBuilder sb=new StringBuilder();
        for (Answer a : answers) {
            sb.append(a.toString());
            sb.append(DELIMITER);
        }
        
        //remove last delimiter
        sb.delete(sb.length()-DELIMITER.length(), sb.length());
        
        return new Question(sb.toString(), new ReversedAnswer(question));
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public LanguageDefinition getLangDef() {
        return langDef;
    }
    
//    public Iterator<Answer> iterator() {
//        return answers.iterator();
//    }
    
    public boolean isSkipDesired() {
        return skipDesired;
    }
    
    public void setSkipDesired(boolean skipDesired) {
        this.skipDesired = skipDesired;
    }
    
    public Answer getAnswerFor(String answer) {
        for (int i = 0; i < answers.size(); i++) {
            if(answers.get(i).matches(answer)) return answers.get(i);
        }
        return null;
    }
    
    /**
     * Determines if the specified answer is correct.
     */
    public boolean isCorrect(String answer) {
//        for (int i = 0; i < answers.size(); i++) {
//            if(answers.get(i).equals(answer)) return true;
//        }
        return getAnswerFor(answer)!=null;
    }

    public void setLangDef(LanguageDefinition langDef) {
        this.langDef = langDef;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ question.hashCode();
    }
    
    @Override
    public Answer get(int index) {
        return answers.get(index);
    }
    
    @Override
    public int size() {
        return answers.size();
    }
    
    @Override
    public Answer set(int index, Answer element) {
        return answers.set(index, element);
    }
    
    @Override
    public void add(int index, Answer element) {
        answers.add(index, element);
    }
    
    @Override
    public Answer remove(int index) {
        return answers.remove(index);
    }

    public boolean isEmptyQuestion() {
        if(question.trim().length()>0) return false;
        for(Answer a : answers) {
            if(a.getAnswer().length()>0) return false;
        }
        
        return true;
    }
    
    
    
}