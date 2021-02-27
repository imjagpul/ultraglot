package cz.srubarovi.teacher.nodes;


import javax.swing.JTree;
import javax.swing.tree.*;

/**
 *
 */
public class CheckNode extends DefaultMutableTreeNode {
    
//  public final static int SINGLE_SELECTION = 0;
//  public final static int DIG_IN_SELECTION = 4;
//  protected int selectionMode;
    protected boolean isSelected;
    
    public CheckNode() {
        this(null);
    }
    
    public CheckNode(Object userObject) {
        this(userObject, true, false);
    }
    
    public CheckNode(Object userObject, boolean allowsChildren
            , boolean isSelected) {
        super(userObject, allowsChildren);
        this.isSelected = isSelected;
        
//    setSelectionMode(DIG_IN_SELECTION);
    }
    
    
//  public void setSelectionMode(int mode) {
//    selectionMode = mode;
//  }
//
//  public int getSelectionMode() {
//    return selectionMode;
//  }
    
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        
        if (children != null) {
            for (Object objnode : children) {
                CheckNode node=(CheckNode) objnode;
                node.setSelected(isSelected);
            }
        }
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public static void initTree(JTree tree) {
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.setCellRenderer(new CheckRenderer());
        tree.addMouseListener(new NodeSelectionListener(tree));
    }
    
    // If you want to change "isSelected" by CellEditor,
  /*
  public void setUserObject(Object obj) {
    if (obj instanceof Boolean) {
      setSelected(((Boolean)obj).booleanValue());
    } else {
      super.setUserObject(obj);
    }
  }
   */
    
}


