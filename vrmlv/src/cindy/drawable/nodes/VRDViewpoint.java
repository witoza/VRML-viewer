package cindy.drawable.nodes;

import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRViewpoint;

public class VRDViewpoint extends VRViewpoint implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		
	}

	public int numOfDrawableChildren() {
		return 0;
	}

	public VRNode getNthChild(int n) {
		return null;
	}
	
	public NodeSettings getNodeSeetings() {
		return null;
	}
}
