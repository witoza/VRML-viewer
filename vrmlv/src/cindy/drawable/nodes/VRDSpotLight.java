package cindy.drawable.nodes;

import javax.media.opengl.GL;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRSpotLight;

public class VRDSpotLight extends VRSpotLight implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDSpotLight.class);

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
		
		float[] ambient = { ambientIntensity, ambientIntensity, ambientIntensity, 1.0f };
		float[] lightColor  = { intensity * color.x, intensity * color.y, intensity * color.z, 1.0f };
		float[] position = { location.x, location.y, location.z, 1.0f };
		float[] direct = { direction.x, direction.y, direction.z };
		float[] spec = {1,1,1,1};

		// cutOff * (180/pi)
		float lightCone = cutOffAngle * 57.29577951308f;
		// float intenseCone = beamWidth * 57.29577951308f;
		
		// skala okolo 0-400 (w teori 0-128)
		float intenseDegree = (beamWidth / cutOffAngle) * 400;
		
		gl.glLightfv( GL.GL_LIGHT1, GL.GL_SPECULAR, spec, 0 );
		gl.glLightfv( GL.GL_LIGHT1, GL.GL_AMBIENT, ambient, 0 );
		gl.glLightfv( GL.GL_LIGHT1, GL.GL_DIFFUSE, lightColor, 0 );
		gl.glLightfv( GL.GL_LIGHT1, GL.GL_POSITION, position, 0 );
		
		// parametry stozka swiatla
		gl.glLightfv( GL.GL_LIGHT1, GL.GL_SPOT_DIRECTION, direct, 0 );
		gl.glLightf( GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, lightCone);
		//gl.glLightf( GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 20f);
		
		gl.glLightf( GL.GL_LIGHT1, GL.GL_SPOT_EXPONENT, intenseDegree);
		//gl.glLightf( GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 100f);

        gl.glLightf(GL.GL_LIGHT1, GL.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_LINEAR_ATTENUATION, 0.0f);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_QUADRATIC_ATTENUATION, 0.0f);		
		
		if (on && !enabled) {
			enabled = true;
			//gl.glEnable(GL.GL_LIGHTING);
			gl.glEnable(GL.GL_LIGHT1);
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
