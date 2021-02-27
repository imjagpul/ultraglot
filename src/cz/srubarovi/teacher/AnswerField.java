/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.srubarovi.teacher;

import cz.srubarovi.teacher.core.Answer;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JTextPane;
import cz.srubarovi.teacher.core.Question;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.util.Iterator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 *
 */
public class AnswerField extends JTextPane {

    private boolean diffActive = false;
    private ArrayList<Integer> markedAccents = new ArrayList<Integer>(6);
    private ArrayList<Integer> diffMissing = new ArrayList<Integer>(6);
    private ArrayList<Integer> diffWrong = new ArrayList<Integer>(6);
    private ArrayList<Integer> diffRedundant = new ArrayList<Integer>(6);
    private String diffText;

    public void setDiffActive(boolean diffActive) {
        this.diffActive = diffActive;
        repaint();
    }

    public void resetAttributes(Question currentQuestion, boolean markDiffs) {

        //remove out of range marks and on invalid characters
        for (Iterator<Integer> it = markedAccents.iterator(); it.hasNext();) {
            Integer elem = it.next();
            if (elem >= getText().length() || (currentQuestion.getLangDef() != null && !currentQuestion.getLangDef().isAccentLegal(getText().charAt(elem)))) {
                it.remove();
            }
        }

        //show all marked accents as bold
        StyledEditorKit kit = (StyledEditorKit) getEditorKit();

        MutableAttributeSet set = new SimpleAttributeSet();
        StyledDocument doc = (StyledDocument) getDocument();

        StyleConstants.setBold(set, false);
        StyleConstants.setStrikeThrough(set, false);
        StyleConstants.setUnderline(set, false);
        StyleConstants.setBackground(set, getBackground());

        //remove all attributes
        doc.setCharacterAttributes(0, doc.getLength(), set, false);

        //mark accents
//        if (testAccents) {
        StyleConstants.setBold(set, true);

        for (Integer i : markedAccents) {
            doc.setCharacterAttributes(i, 1, set, false);
        }
//        }

        //mark diff
        if (diffActive && markDiffs) {
            synchronized (this) {
                diffText = getText();

                diffMissing = new ArrayList<Integer>(6);
                diffWrong = new ArrayList<Integer>(6);
                diffRedundant = new ArrayList<Integer>(6);

                DiffMarker.markDiff(diffMissing, diffWrong, diffRedundant, diffText, currentQuestion);
            }
            StyleConstants.setBackground(set, Color.orange);
            StyleConstants.setBold(set, false);
            StyleConstants.setStrikeThrough(set, true);
            for (Integer i : diffRedundant) {
                doc.setCharacterAttributes(i, 1, set, false);
            }

            StyleConstants.setStrikeThrough(set, false);
//            StyleConstants.setUnderline(set, true);
//            for (Integer i : diffMissing) {
//                doc.setCharacterAttributes(i, 1, set, false);
//            }

            StyleConstants.setBackground(set, new Color(255,170,0));
            StyleConstants.setUnderline(set, false);
//            StyleConstants.setTabSet(set, new TabSet(new TabStop[] {new TabStop(.5f)}));
            //StyleConstants.setIcon(set, new ImageIcon("X:/missing2.png"));
//            StyleConstants.setLeftIndent(set, .3f);
            //StyleConstants.set
            for (Integer i : diffWrong) {
                doc.setCharacterAttributes(i, 1, set, false);
            }
        }

        kit.getInputAttributes().removeAttribute(StyleConstants.Bold);
        kit.getInputAttributes().removeAttribute(StyleConstants.Background);
        kit.getInputAttributes().removeAttribute(StyleConstants.IconAttribute);
        kit.getInputAttributes().removeAttribute(StyleConstants.IconElementName);
    }

    public void markAccent(Question currentQuestion, boolean markDiffs) {
        int selStart = getSelectionStart();
        int selEnd = getSelectionEnd();

        int accentIndex = selStart - 1;
        boolean found = false;
        for (Integer i : markedAccents) {
            if (accentIndex == i) { //already marked - unmark

                markedAccents.remove(i);
                found = true;
                break;
            }
        }

        if (!found) //not marked - mark
        {
            markedAccents.add(accentIndex);
        }
        resetAttributes(currentQuestion, markDiffs);

    }

    @Override
    public void paint(Graphics g) {
        invalidate();
        super.paint(g);
        g.setColor(Color.PINK);

        if (diffActive) {
            if (g instanceof Graphics2D) {
                Graphics2D gr = (Graphics2D) g;
                FontRenderContext frc = gr.getFontRenderContext();

                synchronized (this) {
                    for (Integer charIndex : diffMissing) {
//                        if (charIndex != 0) {
//                            TextLayout textLayout = new TextLayout(diffText.substring(0, charIndex), getFont(), frc);
                            //Rectangle2D rect = textLayout.getBounds();
                            
//                        Rectangle2D rect = getFont().getStringBounds();
//                            int x = (int) rect.getMaxX();
//                            int minY = (int) rect.getMinY();
//                            int maxY = (int) rect.getMaxY();


                            //textLayout.
                            
//                            Rectangle r = textLayout.getPixelBounds(frc, 0f, 0f);
//                            int x = r.x + r.width;
//                            int minY = r.y + r.height;
//                            int maxY = minY + r.height;

                            FontMetrics metrics=getFontMetrics(getFont());
                            int swidth=metrics.stringWidth(diffText.substring(0, charIndex))+2;
                            
                            int x=swidth%getWidth();
                            int line=swidth/getWidth();

                            int minY = +4 + metrics.getHeight()*line;
                            int maxY = -0 + metrics.getHeight()*(line+1);

                            g.fillRect(x, minY, 2, maxY-minY);
                            //g.drawLine(x, minY, x, maxY);
//                        } else {
//                        }

                    }
                }
            }
        }
    }

//    @Override //need to synchronize because of paint
//    public synchronized void setText(String t) {
//        super.setText(t);
//    }
    public void clearAccents() {
        markedAccents.clear();
    }

    public boolean checkAccents(Answer enteredAnswer) {
        boolean correct = enteredAnswer.getAccents().length == markedAccents.size();
        for (int i = 0; correct && i < enteredAnswer.getAccents().length; i++) {
            if (!markedAccents.contains(enteredAnswer.getAccents()[i])) {
                correct = false;
            }
        }

        return correct;
    }
}
