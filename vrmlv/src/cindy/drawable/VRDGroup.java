package cindy.drawable;

import java.util.Iterator;

import cindy.parser.VRGroup;

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

}
