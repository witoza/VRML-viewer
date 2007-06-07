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
import cindy.parser.nodes.VRIndexedFaceSet;
import cindy.parser.nodes.VRMaterial;
import cindy.parser.nodes.VRShape;

public class VRDIndexedFaceSet extends VRIndexedFaceSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedFaceSet.class);
	
	public void draw(DisplayOptions dispOpt) {
		if (getNodeSettings().drawBBox){
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1) return;
		GL gl = dispOpt.gl;
		
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		gl.glPushName(dispOpt.pickingOptions.add(this));
		VRMaterial mat = null;
		if (((VRShape)parent).appearance!=null){
			mat = ((VRShape)parent).appearance.material;
		}

		gl.glFrontFace(ccw ? GL.GL_CCW : GL.GL_CW);
		
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glEnable(GL.GL_NORMALIZE);
		
		if (coord==null || coord.coord==null || coordIndex==null){
			
			return; 
		}
		Vector3f[] ver =coord.coord;
		int ind_ver;
		
		Vector3f[] poly = new Vector3f[3];
		boolean normalWrited = false;
		
		if (color!=null && color.coord!=null){
			Vector3f[]	col = color.coord;
			
			if (colorPerVertex){
				if (colorIndex!=null){
					
					gl.glBegin(GL.GL_POLYGON);
					for (int i = 0; i < coordIndex.length; i++) {
						if (coordIndex[i] == -1) {
							normalWrited = false;
							gl.glEnd();
							gl.glBegin(GL.GL_POLYGON);

						} else {
							ind_ver = coordIndex[i];
							if (!normalWrited) {
								poly[0] = ver[coordIndex[i + 2]];
								poly[1] = ver[coordIndex[i + 1]];
								poly[2] = ver[coordIndex[i]];
								DrawableHelper.putNormal(gl, poly);
								normalWrited = true;
							}
							int col_ind = colorIndex[i];
							gl.glColor3f(col[col_ind].x, col[col_ind].y, col[col_ind].z);
							gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
						}
					}
					gl.glEnd();
					
				}else{

					gl.glBegin(GL.GL_POLYGON);
					for (int i = 0; i < coordIndex.length; i++) {
						if (coordIndex[i] == -1) {
							normalWrited = false;
							gl.glEnd();
							gl.glBegin(GL.GL_POLYGON);

						} else {
							ind_ver = coordIndex[i];
							if (!normalWrited) {
								poly[0] = ver[coordIndex[i + 2]];
								poly[1] = ver[coordIndex[i + 1]];
								poly[2] = ver[coordIndex[i]];
								DrawableHelper.putNormal(gl, poly);
								normalWrited = true;
							}
							gl.glColor3f(col[ind_ver].x, col[ind_ver].y, col[ind_ver].z);
							gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
						}
					}
					gl.glEnd();
					
				}	
			}else{
				if (colorIndex!=null){
					int c=0;
					
					gl.glBegin(GL.GL_POLYGON);
					for (int i = 0; i < coordIndex.length; i++) {
						if (coordIndex[i] == -1) {
							normalWrited = false;
							gl.glEnd();
							gl.glBegin(GL.GL_POLYGON);
							c++;
						} else {
							ind_ver = coordIndex[i];
							if (!normalWrited) {
								poly[0] = ver[coordIndex[i + 2]];
								poly[1] = ver[coordIndex[i + 1]];
								poly[2] = ver[coordIndex[i]];
								DrawableHelper.putNormal(gl, poly);
								normalWrited = true;
							}
							int col_ind = colorIndex[c];							
							gl.glColor3f(col[col_ind].x, col[col_ind].y, col[col_ind].z);
							gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
						}
					}
					gl.glEnd();	
					
				}else{
					
					
					int c=0;
					gl.glBegin(GL.GL_POLYGON);
					for (int i = 0; i < coordIndex.length; i++) {
						if (coordIndex[i] == -1) {
							normalWrited = false;
							gl.glEnd();
							gl.glBegin(GL.GL_POLYGON);
							c++;
						} else {
							ind_ver = coordIndex[i];
							if (!normalWrited) {
								poly[0] = ver[coordIndex[i + 2]];
								poly[1] = ver[coordIndex[i + 1]];
								poly[2] = ver[coordIndex[i]];
								DrawableHelper.putNormal(gl, poly);
								normalWrited = true;
							}						
							gl.glColor3f(col[c].x, col[c].y, col[c].z);
							gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
						}
					}
					gl.glEnd();	
					
				}

			}
		}else{
			if (coordIndex!=null){
				
				if (mat==null) throw new UnsupportedOperationException("error - check vrml spec");
				
				gl.glColor3f(mat.diffuseColor.x,mat.diffuseColor.y,mat.diffuseColor.z);
				
				gl.glBegin(GL.GL_TRIANGLES);
				for (int i = 0; i < coordIndex.length; i++) {
					if (coordIndex[i] == -1) {
						normalWrited = false;
						gl.glEnd();
						gl.glBegin(GL.GL_TRIANGLES);

					} else {
						ind_ver = coordIndex[i];
						if (!normalWrited) {
							poly[0] = ver[coordIndex[i + 2]];
							poly[1] = ver[coordIndex[i + 1]];
							poly[2] = ver[coordIndex[i]];
							DrawableHelper.putNormal(gl, poly);
							normalWrited = true;
						}
						gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
					}
				}
				gl.glEnd();
			}			
		}
		gl.glPopName();
	}
	
	public VRNode getNthChild(int n) {
		return null;
	}


	public int numOfDrawableChildren() {
		return 0;
	}

	NodeSettings ns;
	
	public NodeSettings getNodeSettings() {
		if (ns==null){
			ns = new NodeSettings();
			//compute bounding box
			ns.boundingBox = new BoundingBox();
			if (coordIndex!=null && coord!=null){
				for (int i=0; i!=coordIndex.length; i++){
					int indVer = coordIndex[i];
					if (indVer==-1)
						continue;
					ns.boundingBox.mix(coord.coord[indVer]);
				}
			}
		}
		return ns;
	}

}
