package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRMaterial;
import cindy.parser.nodes.VRPointSet;
import cindy.parser.nodes.VRShape;

public class VRDPointSet extends VRPointSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDPointSet.class);

	public void draw(DisplayOptions dispOpt) {
		if (getNodeSettings().drawBBox){
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1) return;
		VRMaterial mat=((VRShape)parent).appearance.material;
		GL gl=dispOpt.gl;
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		if (coord==null || coord.coord==null){
			_LOG.debug("point set is empty");
			return;			
		}
		
		gl.glPushName(dispOpt.pickingOptions.add(this));	
		
	    Vector3f[]	ver=coord.coord;
		if (color!=null){
			
			Vector3f[]	col=color.coord;
			gl.glBegin(GL.GL_POINTS);
			for (int i=0; i!=ver.length; i++){
				gl.glColor3f(col[i].x,col[i].y,col[i].z);
				gl.glVertex3f(ver[i].x,ver[i].y,ver[i].z);
			}
			gl.glEnd();
		}else if (mat!=null){
			Vector3f color=mat.emissiveColor;
			gl.glBegin(GL.GL_POINTS);
			for (int i=0; i!=ver.length; i++){
				gl.glColor3f(color.x,color.y,color.z);
				gl.glVertex3f(ver[i].x,ver[i].y,ver[i].z);
			}
			gl.glEnd();
		}	
		gl.glPopName();
	}

	public int numOfDrawableChildren() {
		return 0;
	}

	public VRNode getNthChild(int n) {
		return null;
	}
	
	NodeSettings ns;
	public NodeSettings getNodeSettings() {
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
