package cindy.drawable;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.parser.VRMaterial;
import cindy.parser.VRNode;
import cindy.parser.VRPointSet;
import cindy.parser.VRShape;

public class VRDPointSet extends VRPointSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedLineSet.class);

	public void draw(DisplayOptions dispOpt) {		
		VRMaterial mat=((VRShape)parent).appearance.material;
		GL gl=dispOpt.gl;
	    Vector3f[]	ver=coord.coord;
		if (color!=null){
			
			Vector3f[]	col=color.coord;
			gl.glBegin(GL.GL_POINTS);
			for (int i=0; i!=ver.length; i++){
				gl.glColor3f(col[i].x,col[i].y,col[i].z);
				gl.glVertex3f(ver[i].x,ver[i].y,ver[i].z);
			}
			gl.glEnd();
		}else{
			Vector3f color=mat.emissiveColor;
			gl.glBegin(GL.GL_POINTS);
			for (int i=0; i!=ver.length; i++){
				gl.glColor3f(color.x,color.y,color.z);
				gl.glVertex3f(ver[i].x,ver[i].y,ver[i].z);
			}
			gl.glEnd();
		}	
	}

	public int numOfDrawableChildren() {
		return 0;
	}

	public VRNode getNthChild(int n) {
		return null;
	}
}
