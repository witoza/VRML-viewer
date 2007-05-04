package cindy.drawable;

import cindy.parser.VRNode;

public interface IDrawable {
	
	void draw(DisplayOptions dispOpt);
	
	VRNode getNthChild(int n);

	int numOfDrawableChildren();
}
