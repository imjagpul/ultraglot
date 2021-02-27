// FontChooser.java
// A font chooser that allows users to pick a font by name, size, style, and
// color.  The color selection is provided by a JColorChooser pane.  This
// dialog builds an AttributeSet suitable for use with JTextPane.
//
package cz.srubarovi.teacher;

import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class FontChooser extends JDialog implements ActionListener {

  JColorChooser colorChooser;
  JComboBox fontName;
  JCheckBox fontBold, fontItalic;
  JTextField fontSize;
  JLabel previewLabel;
  SimpleAttributeSet attributes;
  Font newFont;
  Color newColor;

  public FontChooser(Frame parent) {
      this(parent, "", null);
  }
  
  private String[] getSupportedFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();  
  }
  
  public FontChooser(Frame parent, String sampleText, Font startFont) {
    super(parent, "Font Chooser", true);
    init(sampleText, startFont);
  }

  public FontChooser(Dialog parent, String sampleText, Font startFont) {
    super(parent, "Font Chooser", true);
    init(sampleText, startFont);
  }
  
  private void init(String sampleText, Font startFont) {
    setSize(450, 450);
    attributes = new SimpleAttributeSet();

    if(startFont==null) startFont=getFont(); //if null use the default font
    
    // Make sure that any way the user cancels the window does the right thing
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        closeAndCancel();
      }
    });

    // Start the long process of setting up our interface
    Container c = getContentPane();
    
    JPanel fontPanel = new JPanel();
    
//    fontName = new JComboBox(new String[] {"TimesRoman", 
//                                           "Helvetica", "Courier",
//    Font.SANS_SERIF, Font.SERIF, Font.DIALOG_INPUT, Font.DIALOG, Font.MONOSPACED});
    fontName = new JComboBox(getSupportedFonts());
    int index=Arrays.binarySearch(getSupportedFonts(), startFont.getFamily());
    fontName.setSelectedIndex(index>=0 ? index : 0);
    
    fontName.addActionListener(this);
    fontSize = new JTextField(Integer.toString(startFont.getSize()), 4);
    fontSize.setHorizontalAlignment(SwingConstants.RIGHT);
    fontSize.addActionListener(this);
    fontBold = new JCheckBox("Bold");
    fontBold.setSelected(startFont.isBold());
    fontBold.addActionListener(this);
    fontItalic = new JCheckBox("Italic");
    fontItalic.setSelected(startFont.isItalic());
    fontItalic.addActionListener(this);

    fontPanel.add(fontName);
    fontPanel.add(new JLabel(" Size: "));
    fontPanel.add(fontSize);
    fontPanel.add(fontBold);
    fontPanel.add(fontItalic);

    c.add(fontPanel, BorderLayout.NORTH);
    
    // Set up the color chooser panel and attach a change listener so that color
    // updates get reflected in our preview label.
    colorChooser = new JColorChooser(Color.black);
    colorChooser.getSelectionModel()
                .addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        updatePreviewColor();
      }
    });
    c.add(colorChooser, BorderLayout.CENTER);

    JPanel previewPanel = new JPanel(new BorderLayout());
    previewLabel = new JLabel("<html>Here's a sample of this font.\n"+sampleText);
    previewLabel.setForeground(colorChooser.getColor());
    previewPanel.add(previewLabel, BorderLayout.CENTER);

    // Add in the Ok and Cancel buttons for our dialog box
    JButton okButton = new JButton("Ok");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        closeAndSave();
      }
    });
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        closeAndCancel();
      }
    });

    JPanel controlPanel = new JPanel();
    controlPanel.add(okButton);
    controlPanel.add(cancelButton);
    previewPanel.add(controlPanel, BorderLayout.SOUTH);

    // Give the preview label room to grow.
    previewPanel.setMinimumSize(new Dimension(100, 100));
    previewPanel.setPreferredSize(new Dimension(100, 100));

    c.add(previewPanel, BorderLayout.SOUTH);
    
    actionPerformed(null); //update the font
  }
  // Ok, something in the font changed, so figure that out and make a
  // new font for the preview label
  public void actionPerformed(ActionEvent ae) {
    // Check the name of the font
    if (!StyleConstants.getFontFamily(attributes)
                       .equals(fontName.getSelectedItem())) {
      StyleConstants.setFontFamily(attributes, 
                                   (String)fontName.getSelectedItem());
    }
    // Check the font size (no error checking yet)
    if (StyleConstants.getFontSize(attributes) != 
                                   Integer.parseInt(fontSize.getText())) {
      StyleConstants.setFontSize(attributes, 
                                 Integer.parseInt(fontSize.getText()));
    }
    // Check to see if the font should be bold
    if (StyleConstants.isBold(attributes) != fontBold.isSelected()) {
      StyleConstants.setBold(attributes, fontBold.isSelected());
    }
    // Check to see if the font should be italic
    if (StyleConstants.isItalic(attributes) != fontItalic.isSelected()) {
      StyleConstants.setItalic(attributes, fontItalic.isSelected());
    }
    // and update our preview label
    updatePreviewFont();
  }

  // Get the appropriate font from our attributes object and update
  // the preview label
  protected void updatePreviewFont() {
    String name = StyleConstants.getFontFamily(attributes);
    boolean bold = StyleConstants.isBold(attributes);
    boolean ital = StyleConstants.isItalic(attributes);
    int size = StyleConstants.getFontSize(attributes);

    //Bold and italic don't work properly in beta 4.
    Font f = new Font(name, (bold ? Font.BOLD : 0) +
                            (ital ? Font.ITALIC : 0), size);
    previewLabel.setFont(f);
  }

  // Get the appropriate color from our chooser and update previewLabel
  protected void updatePreviewColor() {
    previewLabel.setForeground(colorChooser.getColor());
    // Manually force the label to repaint
    previewLabel.repaint();
  }
    
  /**
   * Returns the new font, or null if canceled.
   */
  public Font getNewFont() { return newFont; }
  public Color getNewColor() { return newColor; }
  public AttributeSet getAttributes() { return attributes; }

  public void closeAndSave() {
    // Save font & color information
    newFont = previewLabel.getFont();
    newColor = previewLabel.getForeground();

    // Close the window
    setVisible(false);
  }

  public void closeAndCancel() {
    // Erase any font information and then close the window
    newFont = null;
    newColor = null;
    setVisible(false);
  }
}
