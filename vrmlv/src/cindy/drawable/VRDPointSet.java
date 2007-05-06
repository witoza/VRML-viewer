package cindy.drawable;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.parser.VRMaterial;
import cindy.parser.VRNode;
import cindy.parser.VRPointSet;
import cindy.parser.VRShape;

public class VRDPointSet extends VRPointSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedLineSet.class);

	public void draw(DisplayOptions dispOpt) {
		if (getNodeSeetings().drawBBox){
			getNodeSeetings().boundingBox.draw(dispOpt);
		}
		VRMaterial mat=((VRShape)parent).appearance.material;
		GL gl=dispOpt.gl;
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		
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
	
	NodeSettings ns;
	public NodeSettings getNodeSeetings() {
		if (ns == null){
			ns = new NodeSettings();
			ns.boundingBox = new BoundingBox();
			//compute bounding box
			if (coord!=null){
				for (int i=0; i!=coord.coord.length; i++){
					ns.boundingBox.mix(coord.coord[i]);
				}
			}
		}
		return ns;
	}
}
