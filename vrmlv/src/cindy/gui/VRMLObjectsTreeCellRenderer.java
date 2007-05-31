package cindy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Tree Cell Renderer 
 *
 */
public class VRMLObjectsTreeCellRenderer extends DefaultTreeCellRenderer{

	protected Font plainFont = new Font("Dialog",Font.PLAIN,12);
	protected Font boldFont = new Font("Dialog",Font.BOLD,12);
	
	public Icon getIcon(){
		return null;
	}
	
	public VRMLObjectsTreeCellRenderer(){
		super();			
		setLayout(new BorderLayout()); 
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean expanded, boolean selected, boolean leaf, int row, boolean hasFocus) {	
		super.getTreeCellRendererComponent(tree,value,expanded,selected,leaf,row,hasFocus);
		setFont(plainFont);
		setForeground(Color.BLACK);
		
		if (tree.isRowSelected(row)){
			this.selected = true;
			setForeground(textSelectionColor);
		}				
		else{
			this.selected = false;
		}

		return this;
	}		
}
