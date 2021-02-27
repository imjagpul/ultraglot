/*
 * TestEditorFrame.java
 *
 * Created on 25. duben 2006, 14:30
 */

package cz.srubarovi.teacher;

import cz.srubarovi.teacher.core.LangDefManager;
import cz.srubarovi.teacher.core.LanguageDefinition;
import cz.srubarovi.teacher.core.Question;
import cz.srubarovi.teacher.core.DataTest;
import static cz.srubarovi.teacher.Settings.i18nText;
import cz.srubarovi.teacher.core.XmlDataTest;
import cz.srubarovi.teacher.questioner.Questioner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Imjagpul
 */
public class TestEditorFrame extends javax.swing.JFrame {
    
    
    /** The last file used. */
    private File lastfile=null;
    /** The edited test. */
    private DataTest test=new DataTest(""); // NOI18N
    private boolean idle=false;
    
    private boolean modified;
    
    private QuestionEditor qe=new QuestionEditor();
    private Questioner currentQuestioner=Settings.INSTANCE.getDefaultQuestioner();
    
    private FileFilter TESTV1_FILE_FILTER=new FileFilter() {
        public boolean accept(File f) {
            //all file names are accepted, except for xml files
//            return !XML_FILE_FILTER.accept(f);
            return !f.getName().endsWith(".xml") && !f.getName().startsWith(".");  // NOI18N
        }
        public String getDescription() {
            return i18nText("testeditor_save_testv1format");
        }
    };
//    private FileFilter XML_FILE_FILTER=new FileNameExtensionFilter(i18nText("testeditor_save_xmlformat"), "xml");
    
    private FileFilter XML_FILE_FILTER=new FileFilter() {
        public boolean accept(File f) {
            //show directories also
            if(f.isDirectory()) return true;
            
            return f.getName().endsWith(".xml") && !f.getName().startsWith("."); // NOI18N
        }
        public String getDescription() {
            return i18nText("testeditor_save_xmlformat");
        }
    };
    
    /** Creates new form TestEditorFrame */
    public TestEditorFrame() {
        initComponents();
        
        jFileChooserTest.addChoosableFileFilter(XML_FILE_FILTER);
        jFileChooserTest.addChoosableFileFilter(TESTV1_FILE_FILTER);
        
        if(Settings.INSTANCE.getDictDir()!=null)
            jFileChooserTest.setCurrentDirectory(Settings.INSTANCE.getDictDir());
        
        
        jComboBoxLanguage.addItem(null);
        for(LanguageDefinition langdef : LangDefManager.INSTANCE.getAllLoaded()) {
            jComboBoxLanguage.addItem(langdef);
        }
        
        
        jTableQuestions.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                cellChanged(e);
            }
        });
        
        setModified(false);
        
        //init choice in menu
        jRadioButtonMenuItemSuggestModeSingle.setSelected(Settings.INSTANCE.getSuggestMode()==Settings.SuggestMode.SINGLE);
        jRadioButtonMenuItemSuggestModeAppend.setSelected(Settings.INSTANCE.getSuggestMode()==Settings.SuggestMode.APPENDING);
    }
    
    DefaultTableModel dtb;
    private DefaultTableModel getTableModel() {
        if(dtb==null) {
            dtb=new javax.swing.table.DefaultTableModel(
                    new Object[][] {},
                    new String[] {i18nText("testeditor_column_question"), i18nText("testeditor_column_answer_raw"), i18nText("testeditor_column_answer_test")}) {
                Class[] types = new Class [] {String.class, String.class, String.class};
                boolean[] editable = new boolean[] {true, true, false};
                
                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
                
                @Override
                public boolean isCellEditable(int row, int column) {
                    return editable[column];
                }
            };
        }
        return dtb;
    }
    
    /**
     *
     * @param e
     */
    private void cellChanged(TableModelEvent e) {
        
        if(idle) return;
        
        switch(e.getType()) {
            case TableModelEvent.INSERT: {
                //test.addQuestion(new Question("")); // NOI18N
                Question q=new Question("");// NOI18N
                q.setLangDef((LanguageDefinition) jComboBoxLanguage.getSelectedItem());
                test.insertQuestion(q, e.getFirstRow());
                break;
            }
            case TableModelEvent.UPDATE: {
                int rowChanged=e.getLastRow();
                Question q=test.getQuestionAt(rowChanged);
                
                String question=(String) jTableQuestions.getValueAt(rowChanged, 0);
                q.setQuestion(question!=null ? question.toString() : ""); // NOI18N
                
                String answer=(String) jTableQuestions.getValueAt(rowChanged, 1);
                q.setAnswer(answer!=null ? answer.toString() : ""); // NOI18N
                
                //show the formatted answer to the third column
                DefaultTableModel tm = getTableModel();
                idle=true; //jinak se zacykli
                tm.setValueAt(q.getAnswer(), rowChanged, 2);
                idle=false;
                
                break;
            }
            case TableModelEvent.DELETE: //called once for each row removed
                test.removeQuestionAt(e.getLastRow());
                break;
        }
        
        setModified(true);
    }
    
    private void updateName() {
        test.setName(jTextFieldName.getText());
    }
    
    private void saveAs(File file) {
        
        boolean degradationNeeded=XmlDataTest.needsDegradationForTestv1(test);
        
        if(file==null) {
            jFileChooserTest.setAcceptAllFileFilterUsed(false);
            
            if(jFileChooserTest.getFileFilter()==null) {
                if(degradationNeeded)
                    jFileChooserTest.setFileFilter(XML_FILE_FILTER);
                else
                    jFileChooserTest.setFileFilter(TESTV1_FILE_FILTER);
            }
            
            if(jFileChooserTest.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
                file=jFileChooserTest.getSelectedFile();
                lastfile=file;
            } else return;
        }
        
        
        updateName();
        
        try {
            if(jFileChooserTest.getFileFilter()==XML_FILE_FILTER)
                XmlDataTest.saveAsXml(test, file);
            else {
                if(degradationNeeded) {
                    //TODO : offer export dialog or sth
                }
                
                test.writeFile(file);
            }
        } catch(IOException exc) {
            JOptionPane.showMessageDialog(this, i18nText("testeditor_File_writing_error"));
            exc.printStackTrace();
        }
        
        setModified(false);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooserTest = new javax.swing.JFileChooser();
        jPopupMenuTable = new javax.swing.JPopupMenu();
        jMenuItemAddTableItem = new javax.swing.JMenuItem();
        jMenuItemRemoveTableItem = new javax.swing.JMenuItem();
        buttonGroupSuggestMode = new javax.swing.ButtonGroup();
        jTextFieldName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableQuestions = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButtonSuggest = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jComboBoxLanguage = new javax.swing.JComboBox();
        jLabelLanguage = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuTest = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jMenuItemLoad = new javax.swing.JMenuItem();
        jMenuItemCheck = new javax.swing.JMenuItem();
        jMenuSettings = new javax.swing.JMenu();
        jMenuItemShowSetttings = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jRadioButtonMenuItemSuggestModeSingle = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemSuggestModeAppend = new javax.swing.JRadioButtonMenuItem();

        jFileChooserTest.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooserTest.setFileFilter(XML_FILE_FILTER);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/srubarovi/teacher/properties"); // NOI18N
        jMenuItemAddTableItem.setText(bundle.getString("editor_add")); // NOI18N
        jMenuItemAddTableItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddTableItemActionPerformed(evt);
            }
        });
        jPopupMenuTable.add(jMenuItemAddTableItem);

        jMenuItemRemoveTableItem.setText(bundle.getString("editor_removetest")); // NOI18N
        jMenuItemRemoveTableItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveTableItemActionPerformed(evt);
            }
        });
        jPopupMenuTable.add(jMenuItemRemoveTableItem);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextFieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNameKeyTyped(evt);
            }
        });

        jLabelName.setText(bundle.getString("testname")); // NOI18N

        jScrollPane1.setComponentPopupMenu(jPopupMenuTable);

        jTableQuestions.setModel(getTableModel());
        jScrollPane1.setViewportView(jTableQuestions);

        jButtonAdd.setMnemonic('a');
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonRemove.setMnemonic('R');
        jButtonRemove.setText("Remove");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        jButtonSuggest.setMnemonic('u');
        jButtonSuggest.setText("Suggest");
        jButtonSuggest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuggestActionPerformed(evt);
            }
        });

        jButtonEdit.setMnemonic('e');
        jButtonEdit.setText("Full edit");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        jComboBoxLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLanguageActionPerformed(evt);
            }
        });

        jLabelLanguage.setText("Language");

        jLabelStatus.setText("Status");

        jMenuTest.setMnemonic('t');
        jMenuTest.setText(bundle.getString("menuedit_test")); // NOI18N

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setMnemonic('n');
        jMenuItemNew.setText(bundle.getString("menuedit_new")); // NOI18N
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuTest.add(jMenuItemNew);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setMnemonic('s');
        jMenuItemSave.setText(bundle.getString("menuedit_save")); // NOI18N
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuTest.add(jMenuItemSave);

        jMenuItemSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSaveAs.setMnemonic('a');
        jMenuItemSaveAs.setText(bundle.getString("menuedit_saveas")); // NOI18N
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveAsActionPerformed(evt);
            }
        });
        jMenuTest.add(jMenuItemSaveAs);

        jMenuItemLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemLoad.setMnemonic('l');
        jMenuItemLoad.setText(bundle.getString("menuedit_load")); // NOI18N
        jMenuItemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoadActionPerformed(evt);
            }
        });
        jMenuTest.add(jMenuItemLoad);

        jMenuItemCheck.setText("Check");
        jMenuItemCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCheckActionPerformed(evt);
            }
        });
        jMenuTest.add(jMenuItemCheck);

        jMenuBarMain.add(jMenuTest);

        jMenuSettings.setMnemonic('s');
        jMenuSettings.setText("Settings");

        jMenuItemShowSetttings.setMnemonic('s');
        jMenuItemShowSetttings.setText("Show...");
        jMenuItemShowSetttings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSettingsActionPerformed(evt);
            }
        });
        jMenuSettings.add(jMenuItemShowSetttings);
        jMenuSettings.add(jSeparator1);

        buttonGroupSuggestMode.add(jRadioButtonMenuItemSuggestModeSingle);
        jRadioButtonMenuItemSuggestModeSingle.setText("Suggest - single");
        jRadioButtonMenuItemSuggestModeSingle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemSuggestModeActionPerformed(evt);
            }
        });
        jMenuSettings.add(jRadioButtonMenuItemSuggestModeSingle);

        buttonGroupSuggestMode.add(jRadioButtonMenuItemSuggestModeAppend);
        jRadioButtonMenuItemSuggestModeAppend.setText("Suggest - append");
        jRadioButtonMenuItemSuggestModeAppend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemSuggestModeActionPerformed(evt);
            }
        });
        jMenuSettings.add(jRadioButtonMenuItemSuggestModeAppend);

        jMenuBarMain.add(jMenuSettings);

        setJMenuBar(jMenuBarMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButtonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(jButtonEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSuggest))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabelName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelLanguage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelStatus, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jComboBoxLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLanguage)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonRemove)
                    .addComponent(jButtonSuggest)
                    .addComponent(jButtonEdit)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jRadioButtonMenuItemSuggestModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemSuggestModeActionPerformed
        if(jRadioButtonMenuItemSuggestModeSingle.isSelected())
            Settings.INSTANCE.setSuggestMode(Settings.SuggestMode.SINGLE);
        else if(jRadioButtonMenuItemSuggestModeAppend.isSelected())
            Settings.INSTANCE.setSuggestMode(Settings.SuggestMode.APPENDING);
        
    }//GEN-LAST:event_jRadioButtonMenuItemSuggestModeActionPerformed
    
    
    private void jTextFieldNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNameKeyTyped
        updateName();
        setModified(true);
    }//GEN-LAST:event_jTextFieldNameKeyTyped
    
    private void jComboBoxLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLanguageActionPerformed
        LanguageDefinition langDef=(LanguageDefinition) jComboBoxLanguage.getSelectedItem();
        for(Question q : test) {
            q.setLangDef(langDef);
        }
        
        //try to find corresponding questioner
        if(langDef!=null) {
            for(Questioner q : Settings.INSTANCE.getQuestioners()) {
                if(q.getForeignLangCode().equals(langDef.getLangcode())) {
                    currentQuestioner=q;
                    return;
                }
            }
            
        }
        //if null or if not found, use default
        currentQuestioner=Settings.INSTANCE.getDefaultQuestioner();
        
    }//GEN-LAST:event_jComboBoxLanguageActionPerformed
    
    private void jMenuItemCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCheckActionPerformed
        
        boolean modifiedByCheck=false;
        
        //look for queries with equal question string
        HashSet<String> found = new HashSet<String>(test.count());
        HashSet<String> duplicates=new HashSet<String>();
        
        for (int i = 0; i < test.count(); i++) {
            String q=test.getQuestionAt(i).getQuestion();
            
            if(!found.add(q)) {
                //duplicate question string
                duplicates.add(q);
            }
        }
        
        boolean mergeAll=false;
        duplicateQuestionsLoop:
            for(String dupl : duplicates) {
                //get all questions with equal strings
                ArrayList<Question> toJoin=new ArrayList<Question>();
                for (int i = 0; i < test.count(); i++) {
                    if(dupl.equals(test.getQuestionAt(i).getQuestion())) {
                        toJoin.add(test.getQuestionAt(i));
                    }
                }
                
                //offer merge to user
                StringBuilder sb=new StringBuilder();
                sb.append(i18nText("testeditor_joinoffer_part1"));
                sb.append(dupl);
                sb.append(i18nText("testeditor_joinoffer_part2"));
                
                for (Question elem : toJoin) {
                    sb.append(elem.getAnswer());
                    sb.append('\n');
                }
                String msg=sb.toString();
                
                String[] options=new String[] {i18nText("testeditor_joinoffer_join"), i18nText("testeditor_joinoffer_dontjoin"), i18nText("testeditor_joinoffer_joinalways"), i18nText("testeditor_joinoffer_neverjoin")};
                
                int result;
                if(!mergeAll)
                    result=JOptionPane.showOptionDialog(this, msg, i18nText("testeditor_joinoffer_title"), 0, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                else
                    result=0; //join
                
                switch(result) {
                    case 2: //join always
                        mergeAll=true;
                        //fallthrough
                    case 0: //join
                        modifiedByCheck=true;
                        for (int i = toJoin.size()-1; i >0; i--) {
                            Question q=toJoin.get(i);
                            toJoin.get(0).addAll(q);
                            test.removeQuestion(q);
                        }
                        
                        break;
                    case 3: //never join
                        break duplicateQuestionsLoop;
                    case 1: //don't join
                        break;
                }
            }
            
            if(modifiedByCheck) {
                resetTable();
                setModified(true);
            }
            
    }//GEN-LAST:event_jMenuItemCheckActionPerformed
    
    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
        int selRow=jTableQuestions.getSelectedRow();
        if(selRow<0) selRow=0;
        
        //finish editing if being edited now
        if(jTableQuestions.isEditing())
            jTableQuestions.removeEditor();
        
        qe.setQuestion(test.getQuestionAt(selRow));
        qe.setVisible(true);
        resetTable();
    }//GEN-LAST:event_jButtonEditActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        warnModified();
        //TODO : make cancel work
        //TODO : refresh main window
    }//GEN-LAST:event_formWindowClosing
    
    private void jMenuSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSettingsActionPerformed
        SettingsDialog settingsDialog=new SettingsDialog(this);
        settingsDialog.setVisible(true);
    }//GEN-LAST:event_jMenuSettingsActionPerformed
    
    
    private void jButtonSuggestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuggestActionPerformed
        int[] selected=jTableQuestions.getSelectedRows();
        if(selected!=null) {
            
            //finish editing if being edited now
            if(jTableQuestions.isEditing())
                jTableQuestions.removeEditor();
            
            //Arrays.sort(selected);
            for (int i = 0; i < selected.length; i++) {
                Question q=test.getQuestionAt(selected[i]);
                currentQuestioner.edit(q, this);
                
                idle=true;
                getTableModel().setValueAt(q.getQuestion(), selected[i], 0);
                getTableModel().setValueAt(q.getAnswerRaw(), selected[i], 1);
                getTableModel().setValueAt(q.getAnswer(), selected[i], 2);
                idle=false;
            }
        }
        jTableQuestions.requestFocusInWindow();
    }//GEN-LAST:event_jButtonSuggestActionPerformed
    
    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        removeSelectedRows();
    }//GEN-LAST:event_jButtonRemoveActionPerformed
    
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        addRow();
    }//GEN-LAST:event_jButtonAddActionPerformed
    
    private void addRow() {
        int selRow=jTableQuestions.getSelectedRow()+1;
//        if(selRow<0) selRow=0;
        getTableModel().insertRow(selRow, (Object[])null);
    }
    
    private void removeSelectedRows() {
        int[] selected=jTableQuestions.getSelectedRows();
        
        if(selected!=null) {
            Arrays.sort(selected);
            
            if(jTableQuestions.isEditing()) {
                //see if the edited cell is selected for removal
                //note that the array has to be sorted
                int result=Arrays.binarySearch(selected, jTableQuestions.getEditingRow());
                
                if(result>=0) { //found, finish editing, because the cell is being removed
                    jTableQuestions.removeEditor();
                }
            }
            
            for (int i = selected.length-1; i >= 0; i--) {
                getTableModel().removeRow(selected[i]);
            }
        }
        
    }
    
    private void jMenuItemRemoveTableItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveTableItemActionPerformed
        removeSelectedRows();
    }//GEN-LAST:event_jMenuItemRemoveTableItemActionPerformed
    
    private void jMenuItemAddTableItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddTableItemActionPerformed
        addRow();
    }//GEN-LAST:event_jMenuItemAddTableItemActionPerformed
    
    /**
     * Regenerates visual table. Set variable 'test' first.
     */
    private void resetTable() {
        idle=true;
        
        jTextFieldName.setText(test.getName());
        
        getTableModel().setRowCount(0);
        for(Question question : test) {
            getTableModel().addRow(new Object[] {question.getQuestion(), question.getAnswerRaw(), question.getAnswer()});
        }
        idle=false;
    }
    
    /**
     *
     * @param evt
     */
    private void jMenuItemLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoadActionPerformed
        warnModified();
        jFileChooserTest.setAcceptAllFileFilterUsed(true);
        
        if(jFileChooserTest.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
            lastfile=jFileChooserTest.getSelectedFile();
        } else return;
        
        idle=true;
        try {
            InputStream inputStream=new FileInputStream(lastfile);
            
            String testname=ProfileManager.getTestPathname(lastfile);
            
            if(lastfile.getName().endsWith(".xml")) {
                test=XmlDataTest.fromStream(inputStream, testname);
                jFileChooserTest.setFileFilter(XML_FILE_FILTER);
            } else {
                test=DataTest.fromStream(inputStream, testname);
                jFileChooserTest.setFileFilter(TESTV1_FILE_FILTER);
            }
            
            LanguageDefinition langDef=null;
            if(test.count()>0) {
                langDef=test.iterator().next().getLangDef();
            }
            
            jComboBoxLanguage.setSelectedItem(langDef);
            
            //reset table
            resetTable();
            
            setModified(false);
        } catch(IOException exc) {
            JOptionPane.showMessageDialog(this, i18nText("testeditor_File_reading_error"));
            exc.printStackTrace();
        } catch (TestFileFormatException exc) {
            JOptionPane.showMessageDialog(this, i18nText("testeditor_Unknown_or_invalid_file_format"));
            exc.printStackTrace();
        } finally {
            idle=false;
        }
    }//GEN-LAST:event_jMenuItemLoadActionPerformed
    
    private void jMenuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveAsActionPerformed
        saveAs(null);
    }//GEN-LAST:event_jMenuItemSaveAsActionPerformed
    
    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        saveAs(lastfile);
    }//GEN-LAST:event_jMenuItemSaveActionPerformed
    
    /**
     *
     * @param evt
     */
    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        if(!warnModified()) return;
        
        test=new DataTest("");
        jTextFieldName.setText("");
        
        //jFileChooserTest.
        jFileChooserTest.setSelectedFile(null);
        
//        if(lastfile==null || lastfile.getParentFile()==null) {
//            jFileChooserTest.setCurrentDirectory(Settings.INSTANCE.getDictDir());
//        } else {
//            jFileChooserTest.setCurrentDirectory(lastfile.getParentFile());
//            
//        }
        
        lastfile=null;
        
        idle=true;
        for (int i=getTableModel().getRowCount() ; i>0; i--) {
            getTableModel().removeRow(0);
        }
        idle=false;
        
        setModified(false);
    }//GEN-LAST:event_jMenuItemNewActionPerformed
    
    /**
     * Shows confirmation dialog if current test was modified.
     * @return          true if and only if load/new should proceed
     */
    private boolean warnModified() {
        if(isModified()) {
            int result=JOptionPane.showConfirmDialog(this, i18nText("testeditor_confirmclosedialog_content"), i18nText("testeditor_confirmclosedialog_title"), JOptionPane.YES_NO_CANCEL_OPTION);
            
            if(result==JOptionPane.CANCEL_OPTION) return false; //cancel
            else if(result==JOptionPane.NO_OPTION) return true; //discard changes
            else { //save changes and continue
                saveAs(lastfile);
                return true;
            }
        } else return true;
    }
    
    private boolean isModified() {
        return modified;
    }
    
    private void setModified(boolean modified) {
        this.modified = modified;

        resetStatus();
        setTitle("Test editor - "+jTextFieldName.getText()+ (modified ? "*" : ""));
    }
    
    private void resetStatus() {
        int emptyCount=0;
        
        for(Question q : test) {
            if(q.isEmptyQuestion()) emptyCount++;
        }
        
        boolean degradationNeeded=XmlDataTest.needsDegradationForTestv1(test);        
        
        String statusText=(test.count()-emptyCount) +" items  "
                + (emptyCount>0 ? (("(+"+emptyCount+" empty)  ")) : "")
                + "Suggest: "+currentQuestioner
                + (isModified() ? " * " : "   ")
                + (degradationNeeded ? " XML " : "     ");
        jLabelStatus.setText(statusText);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupSuggestMode;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonSuggest;
    private javax.swing.JComboBox jComboBoxLanguage;
    private javax.swing.JFileChooser jFileChooserTest;
    private javax.swing.JLabel jLabelLanguage;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JMenuBar jMenuBarMain;
    private javax.swing.JMenuItem jMenuItemAddTableItem;
    private javax.swing.JMenuItem jMenuItemCheck;
    private javax.swing.JMenuItem jMenuItemLoad;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemRemoveTableItem;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenuItem jMenuItemShowSetttings;
    private javax.swing.JMenu jMenuSettings;
    private javax.swing.JMenu jMenuTest;
    private javax.swing.JPopupMenu jPopupMenuTable;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemSuggestModeAppend;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemSuggestModeSingle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableQuestions;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
    
}
