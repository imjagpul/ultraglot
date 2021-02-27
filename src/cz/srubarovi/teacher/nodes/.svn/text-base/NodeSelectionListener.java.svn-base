/*
 * NodeSelectionListener.java
 *
 * Created on 30. srpen 2006, 17:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.nodes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Imjagpul
 */
class NodeSelectionListener extends MouseAdapter {
    private JTree tree;
    
    NodeSelectionListener(JTree tree) {
      this.tree = tree;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      int row = tree.getRowForLocation(x, y);
      TreePath  path = tree.getPathForRow(row);
      //TreePath  path = tree.getSelectionPath();
      if (path != null) {
        CheckNode node = (CheckNode)path.getLastPathComponent();
        boolean isSelected = ! (node.isSelected());
        node.setSelected(isSelected);
        
//        if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
//          if ( isSelected ) {
//            tree.expandPath(path);
//          } else {
//            tree.collapsePath(path);
//          }
//        }
        
        ((DefaultTreeModel)tree.getModel()).nodeChanged(node);
        // I need revalidate if node is root.  but why?
        if (row == 0) {
          tree.revalidate();
        }
        tree.repaint();
      }
    }
  }
