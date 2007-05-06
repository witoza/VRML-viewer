package cindy.drawable;

import cindy.parser.VRNode;
import cindy.parser.VRShape;

public class VRDShape extends VRShape implements IDrawable{

	public void draw(DisplayOptions dispOpt) {		
		if (geometry!=null){
			((IDrawable)geometry).draw(dispOpt);
		}
	}
	
	public VRNode getNthChild(int n) {
		return geometry;
	}

	public int numOfDrawableChildren() {
		return (geometry==null ? 0 : 1);
	}

	public NodeSettings getNodeSeetings() {
		if (geometry==null){
			return null;
		}
		return ((IDrawable)geometry).getNodeSeetings();
	}
}
