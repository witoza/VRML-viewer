package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.core.Matrix4f;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRCylinder;

public class VRDCylinder extends VRCylinder implements IDrawable{
	
	private static Logger _LOG = Logger.getLogger(VRDCylinder.class);

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
			gl.glRotatef(270, 1, 0, 0);
			gl.glTranslatef(0,0,-height/2);
			
			gl.glColor3f(1,1,1);
			dispOpt.glu.gluCylinder(quadric, radius, radius, height, 12, 12);
		
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
			Vector3f v1 = new Vector3f(radius,radius,height);
			Vector3f v2 = new Vector3f(-radius,-radius,0);
			
			Matrix4f m=new Matrix4f();
			m.LoadIdent();
			m.Translate(0,0,-height/2);
			m.RotateX((float)(Math.PI/2.0f));
			v1 = m.VMultiply(v1);
			v2 = m.VMultiply(v2);
			
			ns.boundingBox.mix(v1);
			ns.boundingBox.mix(v2);
		}
		return ns;
	}
}
