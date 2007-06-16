package cindy.drawable.nodes;

import javax.media.opengl.GL;

import cindy.drawable.DisplayOptions;
import cindy.drawable.DrawableHelper;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRShape;

public class VRDShape extends VRShape implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		if (geometry != null) {
			dispOpt.gl.glDisable(GL.GL_COLOR_MATERIAL);
			DrawableHelper.setObjectPropertiesFromMaterial(dispOpt.gl, appearance.material);
			dispOpt.gl.glPushMatrix();
			((IDrawable) geometry).draw(dispOpt);
			dispOpt.gl.glPopMatrix();
		}
	}
	
	public VRNode getNthChild(int n) {
		return geometry;
	}

	public int numOfDrawableChildren() {
		return (geometry==null ? 0 : 1);
	}

	public NodeSettings getNodeSettings() {
		if (geometry==null){
			return null;
		}
		return ((IDrawable)geometry).getNodeSettings();
	}
}
