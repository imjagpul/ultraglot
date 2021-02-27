/*
 * ChoiceDialog.java
 *
 * Created on 16. zברם 2007, 11:41
 */

package cz.srubarovi.teacher.questioner;

import cz.srubarovi.teacher.AbstractChoiceDialog;
import java.awt.Frame;
import java.util.List;

/**
 *
 * @author Stepan
 */
class ChoiceDialog extends AbstractChoiceDialog {
    
    public ChoiceDialog(Frame parent) {
        super(parent);
    }
    
    public String run(List<String> possibilites, String title) {
        int i=super.runChoice(possibilites, title, null);
        if(i<0) return null;
        return possibilites.get(i);
    }
    
}
