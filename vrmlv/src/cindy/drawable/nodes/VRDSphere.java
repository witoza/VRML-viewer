package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRSphere;

public class VRDSphere extends VRSphere implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDSphere.class);

	private NodeSettings ns;
	
	public void draw(DisplayOptions dispOpt) {
		if (getNodeSettings().drawBBox) {
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1)
			return;
		GL gl = dispOpt.gl;
		
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		
		gl.glPushName(dispOpt.pickingOptions.add(this));		

			GLUquadric quadric = dispOpt.glu.gluNewQuadric();			
			dispOpt.glu.gluSphere(quadric, radius, 12, 12);
		
		gl.glPopName();
		
	}

	public int numOfDrawableChildren() {
		return 0;
	}

	public VRNode getNthChild(int n) {
		return null;
	}

	public NodeSettings getNodeSettings() {
		if (ns == null){
			ns = new NodeSettings();
			ns.boundingBox = new BoundingBox();
			//compute bounding box
			ns.boundingBox.mix(new Vector3f(radius,radius,radius));
			ns.boundingBox.mix(new Vector3f(-radius,-radius,-radius));
		}
		return ns;
	}
}
