package cindy.drawable;

import cindy.parser.VRShape;

public class VRDShape extends VRShape implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		if (geometry!=null){
			((IDrawable)geometry).draw(dispOpt);
		}
	}

	public int numOfDrawableChildren() {
		return (geometry==null ? 0 : 1);
	}

}
