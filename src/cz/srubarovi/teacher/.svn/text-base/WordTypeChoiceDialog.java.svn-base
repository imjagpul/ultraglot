/*
 * ChoiceForm.java
 *
 * Created on 10. zברם 2007, 20:11
 */

package cz.srubarovi.teacher;

import cz.srubarovi.teacher.xml.WordType;
import cz.srubarovi.teacher.xml.WordTypes;
import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author  Stepan
 */
public class WordTypeChoiceDialog extends AbstractChoiceDialog {
    
    private Color defBg;
    private JComponent indicator;
    private QuestionFrame owner;
    private boolean correctYet=true;
    
    /** Creates new form ChoiceForm */
    public WordTypeChoiceDialog(Frame parent) {
        super(parent);
        
        indicator=content;
        defBg=indicator.getBackground();
    }
    
    /**
     * Gets WordType with specified fastaccess from the specified list.
     */
    private WordType getByFastAccess(List<WordType> choices, int fastaccess) {
        for (WordType choice : choices) {
            if(choice.getFastAccess().intValue()==fastaccess)
                return choice;
        }
        
        return null;
    }
    
    public boolean runChoice(WordTypes wordTypes, int[] correct) {
        correctYet=true;
        
        List<WordType> choices=wordTypes.getWordType();
        String title=wordTypes.getSubName();
        
        
        WordType correctChoice=getByFastAccess(choices, correct[0]);
        
        for (int activeLevel = 0; activeLevel < correct.length; activeLevel++) {
            showChoices(choices, title, correct[activeLevel]);
            
            correctChoice=getByFastAccess(choices, correct[activeLevel]);
            choices=correctChoice.getWordType();
            title=correctChoice.getSubName();
        }
        
        return correctYet;
    }
        
    private void showChoices(List<WordType> choices, String title, int correctChoiceKey) {
        indicator.setBackground(defBg);
        List<String> ch=new ArrayList<String>(choices.size());
        int[] keys=new int[choices.size()];
        
        for (int i = 0; i < choices.size(); i++) {
            WordType c=choices.get(i);
            ch.add(c.getName());
            keys[i]=c.getFastAccess().intValue();
        }
            
        
        while(true) {
            int r=super.runChoice(ch, title, keys);
            
            if(r==correctChoiceKey) //correct choice
                break;
            
            //incorrect choice
            correctYet=false;
            indicator.setBackground(Color.RED);
            
        }
        
    }
    
    
//    private void formKeyReleased(java.awt.event.KeyEvent evt) {
//        String c=evt.getKeyChar()+"";
//
//        for(WordType choice : choices) {
//            if(choice.getFastAccess().toString().equals(c)) {
//
//                if(c.equals(correct[activeLevel]+"")) {
//                    //correct selection
//                    activeLevel++;
//                    if(activeLevel<correct.length) {
//                        //move to next level
//                        setTitle(choice.getSubName());
//                        showChoices(choice.getWordType());
//                    } else {
//                        //word finished correctly
//                        setVisible(false);
//                        owner.choiceFinished(correctYet);
//                    }
//                } else {
//                    //incorrect selection
//                    correctYet=false;
//                    indicator.setBackground(Color.RED);
//                }
//            }
//        }
//
//
//        //different key - ignore
//    }
    
}
