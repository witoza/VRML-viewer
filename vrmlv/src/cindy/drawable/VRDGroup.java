package cindy.drawable;

import java.util.Iterator;

import cindy.parser.VRGroup;
import cindy.parser.VRNode;

public class VRDGroup extends VRGroup implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		Iterator<IDrawable> iter = (Iterator<IDrawable>) children.iterator();
		while(iter.hasNext()){
			iter.next().draw(dispOpt);
		}
	}

	public int numOfDrawableChildren() {
		return children.size();
	}
	
	public VRNode getNthChild(int n) {
		return (VRNode)children.get(n);
	}


}
