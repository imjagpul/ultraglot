/*
 * StatsBrowser.java
 *
 * Created on 6. leden 2008, 13:03
 */
package cz.srubarovi.teacher;

import cz.srubarovi.teacher.core.Question;
import cz.srubarovi.teacher.core.QuestionWithStats;
import cz.srubarovi.teacher.core.StatsManager;
import cz.srubarovi.teacher.core.StatsManager.StatsItem;
import cz.srubarovi.teacher.strategy.DifficultyBasedStrategy;
import java.awt.CardLayout;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author  Stepan
 */
public class StatsBrowser extends javax.swing.JFrame {

    /** Creates new form StatsBrowser */
    public StatsBrowser() {
        initComponents();
    }

    public void setPathnames(List<String> pathnames) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        for (String path : pathnames) {
            DefaultMutableTreeNode pathChild = new DefaultMutableTreeNode(path);

            Collection<String> questions = StatsManager.INSTANCE.getAll(path);
            for (String question : questions) {
                DefaultMutableTreeNode questionChild = new DefaultMutableTreeNode(question);
                List<StatsManager.StatsItem> list = StatsManager.INSTANCE.get(path, question);

                for (StatsManager.StatsItem statsItem : list) {
                    DefaultMutableTreeNode statsChild = new DefaultMutableTreeNode(statsItem);

                    questionChild.add(statsChild);
                }

                pathChild.add(questionChild);
            }

            root.add(pathChild);
        }

        DefaultTreeModel newModel = new DefaultTreeModel(root, true);
        jTreeStats.setModel(newModel);

    }

    private String stampToString(long stamp) {
        Date date = new Date(stamp);
        String dateString = DateFormat.getDateInstance().format(date);
        String timeString = DateFormat.getTimeInstance().format(date);
        return dateString + " " + timeString;
    }

    private void showFile(String path) {
        ((CardLayout) jPanelDetails.getLayout()).show(jPanelDetails, "file");

        jTextFieldPath.setText(path);
    }

    private void showQuestion(String question, String path) {
        ((CardLayout) jPanelDetails.getLayout()).show(jPanelDetails, "question");

        QuestionWithStats q = new QuestionWithStats(new Question(question), path);

        jTextFieldQuestion.setText(question);

        jTextFieldLastSeen.setText(stampToString(q.getLastSeenTime()));
        jTextFieldDifficulty.setText(Integer.toString(DifficultyBasedStrategy.getDifficulty(q)));
        jTextFieldScheludedTime.setText(stampToString(DifficultyBasedStrategy.calculateScheludedTime(q)));
        jTextFieldTimesSeen.setText(Integer.toString(q.getStats().size()));
    }

    private void showStats(StatsItem item) {
        ((CardLayout) jPanelDetails.getLayout()).show(jPanelDetails, "stats");

        jLabelStats.setText(item.toString());

        jTextFieldTime.setText(stampToString(item.getTimestamp()));

        jTextFieldTimestamp.setText(Long.toString(item.getTimestamp()));
    }

    private void handlePath(TreePath path) {
        if (path == null) {
            return;
        }

        Object[] pathList = path.getPath();

        if (pathList.length == 2) { //file
            String stringPath = (String) ((DefaultMutableTreeNode) pathList[1]).getUserObject();
            showFile(stringPath);
        } else if (pathList.length == 3) {
            String stringPath = (String) ((DefaultMutableTreeNode) pathList[1]).getUserObject();
            String question = (String) ((DefaultMutableTreeNode) pathList[2]).getUserObject();
            showQuestion(question, stringPath);
        } else if (pathList.length == 4) {
            StatsManager.StatsItem item = (StatsManager.StatsItem) ((DefaultMutableTreeNode) pathList[3]).getUserObject();
            showStats(item);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneStats = new javax.swing.JScrollPane();
        jTreeStats = new javax.swing.JTree();
        jPanelDetails = new javax.swing.JPanel();
        jPanelFile = new javax.swing.JPanel();
        jLabelPath = new javax.swing.JLabel();
        jTextFieldPath = new javax.swing.JTextField();
        jPanelQuestion = new javax.swing.JPanel();
        jLabelLastSeenTime = new javax.swing.JLabel();
        jTextFieldLastSeen = new javax.swing.JTextField();
        jLabelQuestion = new javax.swing.JLabel();
        jTextFieldQuestion = new javax.swing.JTextField();
        jLabelDifficulty = new javax.swing.JLabel();
        jTextFieldDifficulty = new javax.swing.JTextField();
        jLabelScheludedTime = new javax.swing.JLabel();
        jTextFieldScheludedTime = new javax.swing.JTextField();
        jLabelTimesSeen = new javax.swing.JLabel();
        jTextFieldTimesSeen = new javax.swing.JTextField();
        jPanelStatsItem = new javax.swing.JPanel();
        jLabelStats = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jLabelTimestamp = new javax.swing.JLabel();
        jTextFieldTime = new javax.swing.JTextField();
        jTextFieldTimestamp = new javax.swing.JTextField();

        jTreeStats.setRootVisible(false);
        jTreeStats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeStatsMouseClicked(evt);
            }
        });
        jTreeStats.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTreeStatsKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTreeStatsKeyTyped(evt);
            }
        });
        jScrollPaneStats.setViewportView(jTreeStats);

        jPanelDetails.setLayout(new java.awt.CardLayout());

        jLabelPath.setText("Path:");

        jTextFieldPath.setEnabled(false);

        javax.swing.GroupLayout jPanelFileLayout = new javax.swing.GroupLayout(jPanelFile);
        jPanelFile.setLayout(jPanelFileLayout);
        jPanelFileLayout.setHorizontalGroup(
            jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldPath, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelFileLayout.setVerticalGroup(
            jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPath)
                    .addComponent(jTextFieldPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(211, Short.MAX_VALUE))
        );

        jPanelDetails.add(jPanelFile, "file");

        jLabelLastSeenTime.setText("Last seen time:");

        jTextFieldLastSeen.setEditable(false);

        jLabelQuestion.setText("Question:");

        jTextFieldQuestion.setEditable(false);

        jLabelDifficulty.setText("Difficulty:");

        jTextFieldDifficulty.setEditable(false);

        jLabelScheludedTime.setText("Scheluded time:");

        jTextFieldScheludedTime.setEditable(false);

        jLabelTimesSeen.setText("Times seen:");

        jTextFieldTimesSeen.setEditable(false);

        javax.swing.GroupLayout jPanelQuestionLayout = new javax.swing.GroupLayout(jPanelQuestion);
        jPanelQuestion.setLayout(jPanelQuestionLayout);
        jPanelQuestionLayout.setHorizontalGroup(
            jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelQuestion)
                    .addComponent(jLabelLastSeenTime)
                    .addComponent(jLabelDifficulty)
                    .addComponent(jLabelScheludedTime)
                    .addComponent(jLabelTimesSeen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jTextFieldLastSeen, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jTextFieldDifficulty, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jTextFieldScheludedTime, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jTextFieldTimesSeen, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelQuestionLayout.setVerticalGroup(
            jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQuestionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelQuestion)
                    .addComponent(jTextFieldQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLastSeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLastSeenTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDifficulty)
                    .addComponent(jTextFieldDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldScheludedTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelScheludedTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimesSeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTimesSeen))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jPanelDetails.add(jPanelQuestion, "question");

        jLabelStats.setText("[stats list here]");

        jLabelTime.setText("Time:");

        jLabelTimestamp.setText("Timestamp:");

        jTextFieldTime.setEnabled(false);

        jTextFieldTimestamp.setEnabled(false);

        javax.swing.GroupLayout jPanelStatsItemLayout = new javax.swing.GroupLayout(jPanelStatsItem);
        jPanelStatsItem.setLayout(jPanelStatsItemLayout);
        jPanelStatsItemLayout.setHorizontalGroup(
            jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatsItemLayout.createSequentialGroup()
                .addGroup(jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStatsItemLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTime, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelTimestamp, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldTimestamp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(jTextFieldTime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                    .addGroup(jPanelStatsItemLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelStats)))
                .addContainerGap())
        );
        jPanelStatsItemLayout.setVerticalGroup(
            jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatsItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStats)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTime)
                    .addComponent(jTextFieldTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStatsItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTimestamp)
                    .addComponent(jTextFieldTimestamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(165, Short.MAX_VALUE))
        );

        jPanelDetails.add(jPanelStatsItem, "stats");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jScrollPaneStats, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneStats, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jTreeStatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeStatsMouseClicked
        handlePath(jTreeStats.getPathForLocation(evt.getX(), evt.getY()));
    //handlePath(jTreeStats.getSelectionPath());
    }//GEN-LAST:event_jTreeStatsMouseClicked

    private void jTreeStatsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTreeStatsKeyReleased
        handlePath(jTreeStats.getSelectionPath());
    }//GEN-LAST:event_jTreeStatsKeyReleased

    private void jTreeStatsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTreeStatsKeyTyped
        handlePath(jTreeStats.getSelectionPath());
    }//GEN-LAST:event_jTreeStatsKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelDifficulty;
    private javax.swing.JLabel jLabelLastSeenTime;
    private javax.swing.JLabel jLabelPath;
    private javax.swing.JLabel jLabelQuestion;
    private javax.swing.JLabel jLabelScheludedTime;
    private javax.swing.JLabel jLabelStats;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTimesSeen;
    private javax.swing.JLabel jLabelTimestamp;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JPanel jPanelFile;
    private javax.swing.JPanel jPanelQuestion;
    private javax.swing.JPanel jPanelStatsItem;
    private javax.swing.JScrollPane jScrollPaneStats;
    private javax.swing.JTextField jTextFieldDifficulty;
    private javax.swing.JTextField jTextFieldLastSeen;
    private javax.swing.JTextField jTextFieldPath;
    private javax.swing.JTextField jTextFieldQuestion;
    private javax.swing.JTextField jTextFieldScheludedTime;
    private javax.swing.JTextField jTextFieldTime;
    private javax.swing.JTextField jTextFieldTimesSeen;
    private javax.swing.JTextField jTextFieldTimestamp;
    private javax.swing.JTree jTreeStats;
    // End of variables declaration//GEN-END:variables
}