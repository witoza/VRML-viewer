package cindy.drawable.nodes;

import javax.media.opengl.GL;

import org.apache.log4j.Logger;

import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRDirectionalLight;

public class VRDDirectionalLight extends VRDirectionalLight implements IDrawable {

	private static Logger _LOG = Logger.getLogger(VRDDirectionalLight.class);

	private NodeSettings ns;

	public void draw(DisplayOptions dispOpt) {
		if (getNodeSettings().drawBBox) {
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		
		if (ns.rendMode == -1)
			return;
		GL gl = dispOpt.gl;
		
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
