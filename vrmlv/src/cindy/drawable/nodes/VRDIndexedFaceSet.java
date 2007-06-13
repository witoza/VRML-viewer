package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.DrawableHelper;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.drawable.VRMLDrawableModel;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRIndexedFaceSet;
import cindy.parser.nodes.VRMaterial;
import cindy.parser.nodes.VRShape;

public class VRDIndexedFaceSet extends VRIndexedFaceSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedFaceSet.class);
	
	private boolean texturesProcessed = false;
	private int texture = -1;
	
	
	private void putTextCoordForVertex(GL gl, int i){

		if (texCoord!=null && texCoord.point!=null){
			Vector2f point = null;
			if (texCoordIndex!=null){
				point = texCoord.point[texCoordIndex[i]];									
			}else{
				point = texCoord.point[coordIndex[i]];
			}
			gl.glTexCoord2f(point.x, point.y);
		}
	}
	
	public void draw(DisplayOptions dispOpt) {

		if (coord==null || coord.coord==null || coordIndex==null){			
			return; 
		}
		
		if (getNodeSettings().drawBBox){
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1) return;
		
		GL gl = dispOpt.gl;		
		
		if (!texturesProcessed) {
			texturesProcessed = true;
			if (((VRShape) parent).appearance != null && ((VRShape) parent).appearance.texture != null) {
				String str = ((VRShape) parent).appearance.texture.url.element();
				if (str!=null && str.length()>2){
					if (str.startsWith("\""))
						str = str.substring(1, str.length()-1);
					_LOG.info("binding texture: "+str);
					texture = ((VRMLDrawableModel)model).getOGLTextureId(str, gl, dispOpt.glu);
				}				
			}
		}
		
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		gl.glPushName(dispOpt.pickingOptions.add(this));
		
		gl.glEnable(GL.GL_COLOR_MATERIAL);

		if (texture != -1) {
			gl.glEnable(GL.GL_TEXTURE_2D);
			gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
			gl.glColor3f(1, 1, 1);
			gl.glDisable(GL.GL_COLOR_MATERIAL);
		}

		//gl.glFrontFace(ccw ? GL.GL_CCW : GL.GL_CW);
	
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
							
							putTextCoordForVertex(gl,i);
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
							putTextCoordForVertex(gl,i);
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
							putTextCoordForVertex(gl,i);
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
							putTextCoordForVertex(gl,i);
							gl.glColor3f(col[c].x, col[c].y, col[c].z);
							gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
						}
					}
					gl.glEnd();	
					
				}

			}
		}else{
			if (coordIndex!=null){
				VRMaterial mat = null;
				if (((VRShape)parent).appearance!=null){
					mat = ((VRShape)parent).appearance.material;
				}
				if (mat==null) throw new UnsupportedOperationException("error - check vrml spec");
				
				gl.glColor3f(mat.diffuseColor.x,mat.diffuseColor.y,mat.diffuseColor.z);
				
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
						gl.glVertex3f(ver[ind_ver].x, ver[ind_ver].y, ver[ind_ver].z);
						putTextCoordForVertex(gl,i);
					}
				}
				gl.glEnd();
			}			
		}

		if (texture!=-1){
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
			 gl.glDisable(GL.GL_TEXTURE_2D);
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
