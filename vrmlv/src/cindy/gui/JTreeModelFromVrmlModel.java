package cindy.gui;

import java.util.Collections;
import java.util.LinkedList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import cindy.drawable.IDrawable;
import cindy.parser.VRGroup;
import cindy.parser.VRNode;

/**
 * Tree model for VRML based tree
 */

public class JTreeModelFromVrmlModel implements TreeModel{
	
	private final VRNode root;
	public JTreeModelFromVrmlModel(VRGroup mainGroup) {
		root = mainGroup;
	}
	
	public Object getRoot(){
		return root;
	}
	
	public Object[] getPathToRoot(VRNode node){
		VRNode nd=node;
		LinkedList objs=new LinkedList();
		while(nd!=null){
			objs.add(nd);
			nd=nd.parent;			
		}
		Collections.reverse(objs);
		return objs.toArray();
	}
	
	public Object getChild(Object node, int index) {
		return ((IDrawable)node).getNthChild(index);
	}

	public int getChildCount(Object node) {
		return ((IDrawable)node).numOfDrawableChildren();
	}

	public boolean isLeaf(Object node) {
		return ((IDrawable)node).numOfDrawableChildren()==0;
	}
	
	public void valueForPathChanged(TreePath path, Object newValue) {}

	public int getIndexOfChild(Object parent, Object child) {
		return 0;
	}
	
	public void addTreeModelListener(TreeModelListener l) {}
	public void removeTreeModelListener(TreeModelListener l) {}

}
