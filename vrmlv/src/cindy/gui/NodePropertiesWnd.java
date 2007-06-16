package cindy.gui;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import cindy.parser.VRNode;

public class NodePropertiesWnd extends JFrame {
	final VRNode node;

	public NodePropertiesWnd(final VRNode node) throws HeadlessException {
		super(node.getNodeInternalName()+"/" + node.name);
		this.node = node;
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
				
		setSize(200,300);		
	}	
}
