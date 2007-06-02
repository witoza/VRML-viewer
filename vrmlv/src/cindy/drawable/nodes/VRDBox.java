package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRBox;

//TODO: implement

public class VRDBox extends VRBox implements IDrawable {

	private static Logger _LOG = Logger.getLogger(VRDBox.class);

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
		
		gl.glPushName(dispOpt.pickingOptions.add(this));	

		float halfx = size.x / 2; 
		float halfy = size.y / 2;
		float halfz = size.z / 2;

		gl.glBegin(GL.GL_QUADS);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(halfx, halfy, -halfz);
		gl.glVertex3f(-halfx, halfy, -halfz);
		gl.glVertex3f(-halfx, halfy, halfz);
		gl.glVertex3f(halfx, halfy, halfz);

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(halfx, -halfy, halfz);
		gl.glVertex3f(-halfx, -halfy, halfz);
		gl.glVertex3f(-halfx, -halfy, -halfz);
		gl.glVertex3f(halfx, -halfy, -halfz);

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(halfx, halfy, halfz);
		gl.glVertex3f(-halfx, halfy, halfz);
		gl.glVertex3f(-halfx, -halfy, halfz);
		gl.glVertex3f(halfx, -halfy, halfz);

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(halfx, -halfy, -halfz);
		gl.glVertex3f(-halfx, -halfy, -halfz);
		gl.glVertex3f(-halfx, halfy, -halfz);
		gl.glVertex3f(halfx, halfy, -halfz);

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-halfx, halfy, halfz);
		gl.glVertex3f(-halfx, halfy, -halfz);
		gl.glVertex3f(-halfx, -halfy, -halfz);
		gl.glVertex3f(-halfx, -halfy, halfz);

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(halfx, halfy, -halfz);
		gl.glVertex3f(halfx, halfy, halfz);
		gl.glVertex3f(halfx, -halfy, halfz);
		gl.glVertex3f(halfx, -halfy, -halfz);
		gl.glEnd();
		gl.glFlush();
		
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
/*			if (coord!=null){
				for (int i=0; i!=coord.coord.length; i++){
					ns.boundingBox.mix(coord.coord[i]);
				}
			} */
		}
		return ns;
	}
}