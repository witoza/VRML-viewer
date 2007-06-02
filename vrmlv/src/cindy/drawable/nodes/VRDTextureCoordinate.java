package cindy.drawable.nodes;

import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRTextureCoordinate;

public class VRDTextureCoordinate extends VRTextureCoordinate implements IDrawable{
	
	public void draw(DisplayOptions dispOpt) {
		
	}

	public int numOfDrawableChildren() {
		return 0;
	}
	
	public VRNode getNthChild(int n) {
		return null;
	}

	public NodeSettings getNodeSettings() {
		return null;
	}

}
