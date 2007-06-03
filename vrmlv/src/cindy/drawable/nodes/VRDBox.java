package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.DrawableHelper;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRBox;

public class VRDBox extends VRBox implements IDrawable {

	private static Logger _LOG = Logger.getLogger(VRDBox.class);

	private NodeSettings ns;
		
	void putQuad(GL gl, Vector3f poly[]){		
		
		DrawableHelper.putNormal(gl,poly);
		gl.glVertex3f(poly[0].x,poly[0].y,poly[0].z);
		gl.glVertex3f(poly[1].x,poly[1].y,poly[1].z);
		gl.glVertex3f(poly[2].x,poly[2].y,poly[2].z);
		gl.glVertex3f(poly[3].x,poly[3].y,poly[3].z);		
	}

	public void draw(DisplayOptions dispOpt) {
		if (getNodeSettings().drawBBox) {
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1) return;
		GL gl = dispOpt.gl;
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		
		gl.glPushName(dispOpt.pickingOptions.add(this));	

		float halfx = size.x / 2; 
		float halfy = size.y / 2;
		float halfz = size.z / 2;

		gl.glDisable(GL.GL_COLOR_MATERIAL);

		gl.glFrontFace(GL.GL_CW);
		
		gl.glEnable(GL.GL_NORMALIZE);
			gl.glBegin(GL.GL_QUADS);
				gl.glColor3f(1.0f, 0.0f, 1.0f);
				
				Vector3f poly[] = new Vector3f[4];
				poly[3]=new Vector3f(halfx, halfy, -halfz);
				poly[2]=new Vector3f(-halfx, halfy, -halfz);
				poly[1]=new Vector3f(-halfx, halfy, halfz);
				poly[0]=new Vector3f(halfx, halfy, halfz);							
				putQuad(gl,poly);
							
				poly[3]=new Vector3f(halfx, -halfy, halfz);
				poly[2]=new Vector3f(-halfx, -halfy, halfz);
				poly[1]=new Vector3f(-halfx, -halfy, -halfz);
				poly[0]=new Vector3f(halfx, -halfy, -halfz);				
				putQuad(gl,poly);
		
				poly[3]=new Vector3f(halfx, halfy, halfz);
				poly[2]=new Vector3f(-halfx, halfy, halfz);
				poly[1]=new Vector3f(-halfx, -halfy, halfz);
				poly[0]=new Vector3f(halfx, -halfy, halfz);				
				putQuad(gl,poly);
				
				poly[3]=new Vector3f(halfx, -halfy, -halfz);
				poly[2]=new Vector3f(-halfx, -halfy, -halfz);
				poly[1]=new Vector3f(-halfx, halfy, -halfz);
				poly[0]=new Vector3f(halfx, halfy, -halfz);				
				putQuad(gl,poly);
				
				poly[3]=new Vector3f(-halfx, halfy, halfz);
				poly[2]=new Vector3f(-halfx, halfy, -halfz);
				poly[1]=new Vector3f(-halfx, -halfy, -halfz);
				poly[0]=new Vector3f(-halfx, -halfy, halfz);				
				putQuad(gl,poly);
	
				poly[3]=new Vector3f(halfx, halfy, -halfz);
				poly[2]=new Vector3f(halfx, halfy, halfz);
				poly[1]=new Vector3f(halfx, -halfy, halfz);
				poly[0]=new Vector3f(halfx, -halfy, -halfz);				
				putQuad(gl,poly);
				
			gl.glEnd();
		
		gl.glPopName();
		gl.glFrontFace(GL.GL_CCW);
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
						
			ns.boundingBox.mix(new Vector3f(size.x/2, size.y/2, size.z/2));
			ns.boundingBox.mix(new Vector3f(-size.x/2, -size.y/2, -size.z/2));
			
		}
		return ns;
	}
}
