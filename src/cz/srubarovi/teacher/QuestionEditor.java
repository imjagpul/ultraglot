/*
 * AnswerEditor.java
 *
 * Created on 19. leden 2007, 17:40
 */

package cz.srubarovi.teacher;

import cz.srubarovi.teacher.core.Answer;
import cz.srubarovi.teacher.core.Question;
import cz.srubarovi.teacher.resources.Resources;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 * Edits a single question in place.
 * @author  Stepan
 */
public class QuestionEditor extends javax.swing.JDialog {
    private Question question;
    private Action actionMarkAccent=new ActionMarkAccent();
    
    /** Creates new form AnswerEditor */
    public QuestionEditor() {
        initComponents();
        answersPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), actionMarkAccent);
        
        setModalityType(ModalityType.DOCUMENT_MODAL);
    }
    
    public void setQuestion(Question question) {
        this.question=question;
        
        jTextFieldQuestion.setText(question.getQuestion());
//        jTextFieldAnswer.setText(question.getAnswerRaw());
//        jTextFieldAnswerTest.setText(question.getAnswer());
        
        accentMap.clear();
        
        StringBuilder sb=new StringBuilder();
        int i=0;
        for(Answer a : question) {
            if(a.getAccents()!=null) {
                List<Integer> accents=new ArrayList<Integer>(a.getAccents().length);
                for(Integer ac : a.getAccents())
                    accents.add(ac);
                accentMap.put(i, accents);
            }
            
            sb.append(a.getAnswerRaw());
            sb.append('\n');
            
            i++;
        }
        answersPane.setText(sb.toString());
        
        resetAttributes();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jTextFieldQuestion = new javax.swing.JTextField();
        jLabelQuestion = new javax.swing.JLabel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        answersPane = new javax.swing.JTextPane();

        setTitle("Edit answer");
        setIconImage(Resources.APP);

        jLabelQuestion.setText("Question:");

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel1.setText("Answers:");

        jScrollPane2.setViewportView(answersPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelQuestion)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(jTextFieldQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonCancel)
                        .addGap(7, 7, 7)
                        .addComponent(jButtonOk)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelQuestion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonOk))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        //hide the dialog
        setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        //update the actual question from the textfields
        
        String[] newAnswers=answersPane.getText().split("\n");
        
        boolean cleared=false;
        
//        for(String newAnswer : newAnswers) {
        for (int i = 0; i < newAnswers.length; i++) {
            String newAnswer=newAnswers[i].trim();
            if(newAnswer.length()>0) {
                if(!cleared) {
                    question.clear();
                    cleared=true;
                }
                
                Answer ans=new Answer(newAnswer);
                List<Integer> marked=accentMap.get(i);
                if(marked!=null) {
                    int[] accents=new int[marked.size()];
                    for (int j = 0; j < accents.length; j++) {
                        accents[j]=marked.get(j);
                    }
                    
                    ans.setAccents(accents);
                }
                question.add(ans);
            }
        }
        
        if(!cleared) {
            JOptionPane.showMessageDialog(this, "Fill the answer field", "Answer field is empty", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        question.setQuestion(jTextFieldQuestion.getText());
        
        //hide the dialog
        setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuestionEditor().setVisible(true);
            }
        });
    }
    
    private void resetAttributes() {
        
        String[] lines=answersPane.getText().split("\n");
        
        
        //count lines
//        int linecount=0;
//        int newline=0;
//        while(newline<answersPane.getText().length()) {
//            newline=answersPane.getText().indexOf('\n', newline);
//
//            if(newline!=-1)
//                linecount++;
//            else break;
//
//        }
//
//
//
        //remove out of range marks and on invalid characters
        for (Iterator<Integer> it = accentMap.keySet().iterator(); it.hasNext();) {
            Integer line = (Integer) it.next();
            
            if(line>=lines.length) {
                it.remove();
            } else {
                List<Integer> marked=accentMap.get(line);
                
                // !getLangDef().isAccentLegal(answerField.getText().charAt(elem)))
                for (Iterator<Integer> itLine = marked.iterator(); itLine.hasNext();) {
                    Integer ac=itLine.next();
                    if(ac>=lines[line].length()) {
                        itLine.remove();
                    }
                }
            }
        }
        
        //show all marked accents as bold
        StyledEditorKit kit = (StyledEditorKit) answersPane.getEditorKit();
        
        MutableAttributeSet set=new SimpleAttributeSet();
        StyledDocument doc = (StyledDocument) answersPane.getDocument();
        
        StyleConstants.setBold(set, false);
        
        //remove all bold attributes
        doc.setCharacterAttributes(0, doc.getLength(), set, false);
        
        StyleConstants.setBold(set, true);
        
        int linesLength=0;
        for (int i = 0; i < lines.length; i++) {
            
            List<Integer> marked=accentMap.get(i);
            if(marked!=null) {
                for (Integer elem : marked) {
                    doc.setCharacterAttributes(elem+linesLength, 1, set, false);
                }
            }
            linesLength += lines[i].length()+1; //plus newline
        }
//        for(Integer line : accentMap.keySet()) {
//            for (Integer i : markedAccents) {
//                doc.setCharacterAttributes(i, 1, set, false);
//            }
//        }
        
        kit.getInputAttributes().removeAttribute(StyleConstants.Bold);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane answersPane;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelQuestion;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldQuestion;
    // End of variables declaration//GEN-END:variables
    
//    private ArrayList<Integer> markedAccents=new ArrayList<Integer>();
    private Map<Integer, List<Integer>> accentMap=new HashMap<Integer, List<Integer>>();
    
    private class ActionMarkAccent extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            
            
            int selStart=answersPane.getSelectionStart();
            int selEnd=answersPane.getSelectionEnd();
            
            int selLine=0;
            int lastNewline=(-1);
            int newline=0;
            int length=0;
            
            while(newline<selStart) {
                newline=answersPane.getText().indexOf('\n', newline+1);
                
                if(newline==-1) {
                    break;
                }
                
                if(newline<selStart) {
                    lastNewline=newline;
                    selLine++;
                }
            }
            
            int accentIndex=selStart-1-lastNewline;
            boolean found=false;
            
//            answersPane.getSelectionStart()
            List<Integer> markedAccents=accentMap.get(selLine);
            
            if(markedAccents!=null) {
                for (Integer i : markedAccents) {
                    if(accentIndex==i) { //already marked - unmark
                        markedAccents.remove(i);
                        found=true;
                        break;
                    }
                }
            } else {
                markedAccents=new ArrayList<Integer>();
                accentMap.put(selLine, markedAccents);
            }
            
            if(!found) //not marked - mark
                markedAccents.add(accentIndex);
            
            resetAttributes();
        }
        
    }
    
}
