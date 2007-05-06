package cindy.drawable;

import cindy.parser.VRMaterial;
import cindy.parser.VRNode;

public class VRDMaterial extends VRMaterial implements IDrawable{

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
