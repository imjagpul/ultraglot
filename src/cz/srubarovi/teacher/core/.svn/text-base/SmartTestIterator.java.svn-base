/*
 * SmartTestIterator.java
 *
 * Created on 16. prosinec 2006, 11:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.QuestionFrame.Status;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author Imjagpul
 */
public class SmartTestIterator implements NotifiableIterator {
    
    protected static class Step {
        int index;
        int level=0;
        
        public Step getNextLevel() {
            Step newStep=new Step();
            newStep.index = index;
            newStep.level = level + 1;
            return newStep;
        }
    }
    
    private List<? extends Question> list;
    private List<Step> steps;
    
    private void createInitialSteps() {
        steps=new ArrayList<Step>(list.size()+15);
        for (int i = 0; i < list.size(); i++) {
            Step step=new Step();
            step.index=i;
            steps.add(step);
        }
    }
    
    /** Creates a new instance of SmartTestIterator */
    public SmartTestIterator(List<? extends Question> list) {
        this.list=list;
        createInitialSteps();
    }
    
    /**
     * Index of element to be returned by previous call to next.
     */
    int cursor = -1;
    
    
    public boolean hasNext() {
        return cursor + 1 != steps.size();
    }
    
    protected Step getCurrentStep() {
        try {
            return steps.get(cursor);
        } catch(IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }
    
    protected Question getCurrentQuestion() {
        return list.get(getCurrentStep().index);
    }
    
    public Question next() {
        cursor++;
        Step step=getCurrentStep();
        
        assert step.index < list.size();
        return list.get(step.index);
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    private static final Random random=new Random();
    
    /**
     * This method will schelude the current step after specified number of
     * steps.
     */
    private void askAgainIn(int stepsPassed) {
        assert stepsPassed > 0;
        Step current=getCurrentStep();
        Step newStep=current.getNextLevel();
        
        int desiredIndex=cursor+stepsPassed+1;
        
        while(desiredIndex>steps.size()) {
            //schelude out of bounds, fill with random steps from the begining
            steps.add(steps.get(random.nextInt(steps.size())));
        }
        
        steps.add(desiredIndex, newStep);
    }
    
    private void askAgain() {
        int level=getCurrentStep().level;
        switch(level) {
            case 0: askAgainIn(5); askAgainIn(14); break;
            case 1: askAgainIn(4); askAgainIn(14); break;
            default: askAgainIn(5); askAgainIn(14);  break;
        }
    }
    
    public void notify(EnumSet<Status> status) {
        
        if(status.isEmpty()) return;
        
        if(status.contains(Status.HELP_USED) || status.contains(Status.WRONG_ANSWER))
            askAgain();
        else //less serious mistakes
            askAgainIn(3);
    }
    
    public String getStatusText() {
        return ((cursor+1) * 100 / steps.size())+"%";
    }
}
