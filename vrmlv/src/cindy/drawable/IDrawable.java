package cindy.drawable;

import cindy.parser.VRNode;

public interface IDrawable {
	
	void draw(final DisplayOptions dispOpt);
	
	VRNode getNthChild(int n);

	int numOfDrawableChildren();
	
	NodeSettings getNodeSettings();
	
}
