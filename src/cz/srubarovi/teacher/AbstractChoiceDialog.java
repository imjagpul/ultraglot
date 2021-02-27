/*
 * ChoiceDialog.java
 *
 * Created on 11. zברם 2007, 14:41
 */

package cz.srubarovi.teacher;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Stepan
 */
public abstract class AbstractChoiceDialog extends JDialog {
    
    protected JPanel content=new JPanel();
    protected int result=FASTACCESS_INVALID;
    protected List<String> currentChoices;
    protected int[] customKeys=null;
//    protected boolean autohide=true;
    
    public AbstractChoiceDialog(Frame parent) {
        super(parent);
        setModalityType(ModalityType.DOCUMENT_MODAL);
        
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        
        add(content, BorderLayout.CENTER);
//        add(, BorderLayout.SOUTH);
        
        //listen to keys
        KeyAdapter keyListener=new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                AbstractChoiceDialog.this.keyPressed(e);
            }
        };
        
        addKeyListener(keyListener);
        content.addKeyListener(keyListener);
    }
    
    private static final int FASTACCESS_INVALID=(-1);
    private static final int FASTACCESS_ESCAPE=(-2);
    private int convertKeyEventToFastAccess(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_NUMPAD0 ||  e.getKeyCode()==KeyEvent.VK_0) return 0;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD1 ||  e.getKeyCode()==KeyEvent.VK_1) return 1;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD2 ||  e.getKeyCode()==KeyEvent.VK_2) return 2;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD3 ||  e.getKeyCode()==KeyEvent.VK_3) return 3;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD4 ||  e.getKeyCode()==KeyEvent.VK_4) return 4;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD5 ||  e.getKeyCode()==KeyEvent.VK_5) return 5;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD6 ||  e.getKeyCode()==KeyEvent.VK_6) return 6;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD7 ||  e.getKeyCode()==KeyEvent.VK_7) return 7;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD8 ||  e.getKeyCode()==KeyEvent.VK_8) return 8;
        else if(e.getKeyCode()==KeyEvent.VK_NUMPAD9 ||  e.getKeyCode()==KeyEvent.VK_9) return 9;
        else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) return FASTACCESS_ESCAPE;
        else if(e.getKeyCode()==KeyEvent.VK_Q) return 10;
        else if(e.getKeyCode()==KeyEvent.VK_W) return 11;
        else if(e.getKeyCode()==KeyEvent.VK_E) return 12;
        else if(e.getKeyCode()==KeyEvent.VK_R) return 13;
        else if(e.getKeyCode()==KeyEvent.VK_T) return 14;
        else if(e.getKeyCode()==KeyEvent.VK_Z || e.getKeyCode()==KeyEvent.VK_Y) return 15;
        else if(e.getKeyCode()==KeyEvent.VK_U) return 16;
        else if(e.getKeyCode()==KeyEvent.VK_I) return 17;
        else if(e.getKeyCode()==KeyEvent.VK_O) return 18;
        else if(e.getKeyCode()==KeyEvent.VK_P) return 19;
        else if(e.getKeyCode()==KeyEvent.VK_A) return 20;
        else if(e.getKeyCode()==KeyEvent.VK_S) return 21;
        else if(e.getKeyCode()==KeyEvent.VK_D) return 22;
        else if(e.getKeyCode()==KeyEvent.VK_F) return 23;
        else if(e.getKeyCode()==KeyEvent.VK_G) return 24;
        else if(e.getKeyCode()==KeyEvent.VK_H) return 25;
        else if(e.getKeyCode()==KeyEvent.VK_J) return 26;
        else if(e.getKeyCode()==KeyEvent.VK_K) return 27;
        else if(e.getKeyCode()==KeyEvent.VK_L) return 28;
        else if(e.getKeyCode()==KeyEvent.VK_X) return 29;
        else if(e.getKeyCode()==KeyEvent.VK_C) return 20;
        else if(e.getKeyCode()==KeyEvent.VK_V) return 21;
        else if(e.getKeyCode()==KeyEvent.VK_B) return 22;
        else if(e.getKeyCode()==KeyEvent.VK_N) return 23;
        else if(e.getKeyCode()==KeyEvent.VK_M) return 24;        
        else return FASTACCESS_INVALID;
    }
    
    private static final char[] names="0123456789QWERTYUIOPASDFGHJKLXCVBNM".toCharArray();
    private String keyToName(int key) {
        if(key>=names.length) return Integer.toString(key);
        else return Character.toString(names[key]);
    }
    
    protected void keyPressed(KeyEvent e) {
        int k=convertKeyEventToFastAccess(e);
        if(k==FASTACCESS_INVALID) return;
        else if(k==FASTACCESS_ESCAPE) {
            result=FASTACCESS_ESCAPE;
//            if(autohide)
            setVisible(false);
        } else if(currentChoices.size()>k) {
            result=k;
//            if(autohide)
            setVisible(false);
        }
    }
    
    /**
     *
     * @param choices       choices shown in the dialog
     * @param title         the title of the choice dialog
     * @param customKeys    custom fastaccess keys or null
     * @return              key of selected choice, or {@link #FASTACCESS_ESCAPE}
     */
    protected final int runChoice(List<String> choices, String title, int[] customKeys) {
        setTitle(title);
        
        content.removeAll();
        
        JLabel label=new JLabel("<html><b>Esc:  {}</b>");
        label.setFont(Settings.INSTANCE.getFont());
        content.add(label);
        
        this.customKeys=customKeys;
        this.currentChoices=choices;
        
        for (int i = 0; i < choices.size(); i++) {
            String choice=choices.get(i);
            
            int key;
            if(customKeys!=null)
                key=customKeys[i];
            else
                key=i;
            
            label=new JLabel("<html><b>"+keyToName(key)+": </b>"+choice);
            label.setFont(Settings.INSTANCE.getFont());
            content.add(label);
        }
        
        pack();
        
        this.setVisible(true);
        
        return result;
        
    }
    
}
