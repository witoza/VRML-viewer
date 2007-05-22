package cindy.drawable;

import javax.media.opengl.GL;

import org.apache.log4j.Logger;

import cindy.parser.VRBox;
import cindy.parser.VRNode;

//TODO: implement

public class VRDBox extends VRBox implements IDrawable{
	
	private static Logger _LOG = Logger.getLogger(VRDBox.class);
	private NodeSettings ns;
	
	public void draw(DisplayOptions dispOpt) {
		if (getNodeSeetings().drawBBox){
			getNodeSeetings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1) return;
		GL gl = dispOpt.gl;
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		
		
		//gl.glBegin(GL.G)
		
	}

	public int numOfDrawableChildren() {
		return 0;
	}

	public VRNode getNthChild(int n) {
		return null;
	}

	public NodeSettings getNodeSeetings() {
		return new NodeSettings();
	}
}
