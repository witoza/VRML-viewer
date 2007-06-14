package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRPointLight;

public class VRDPointLight extends VRPointLight implements IDrawable {

	private static Logger _LOG = Logger.getLogger(VRDPointLight.class);

	private NodeSettings ns;
	
	private boolean enabled = false;
	
	public void draw(DisplayOptions dispOpt) {
		if (getNodeSettings().drawBBox) {
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		
		if (ns.rendMode == -1)
			return;
		GL gl = dispOpt.gl;
		// GLU glu = dispOpt.glu;
		
		float[] ambient = { ambientIntensity, ambientIntensity, ambientIntensity , 1.0f};
		float[] lightColor  = { intensity * color.x, intensity * color.y, intensity * color.z, 1.0f };
		float[] position = { location.x, location.y, location.z, 1.0f };
		
		gl.glLightfv( GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0 );
		gl.glLightfv( GL.GL_LIGHT0, GL.GL_DIFFUSE, lightColor, 0 );
		gl.glLightfv( GL.GL_LIGHT0, GL.GL_POSITION, position, 0 );		
		
		if (on && (enabled == false)) {
			enabled = true;
			gl.glEnable(GL.GL_LIGHTING);
			gl.glEnable(GL.GL_LIGHT0);
		}
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
		}
		return ns;
	}
}
