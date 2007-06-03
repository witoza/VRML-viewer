package cindy.drawable.nodes;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRIndexedLineSet;
import cindy.parser.nodes.VRMaterial;
import cindy.parser.nodes.VRShape;


public class VRDIndexedLineSet extends VRIndexedLineSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedLineSet.class);
	
	public void draw(DisplayOptions dispOpt) {	
		if (getNodeSettings().drawBBox){
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		if (ns.rendMode == -1) return;
		if (coordIndex==null){
			return;
		}
		GL gl = dispOpt.gl;
		gl.glLineWidth(ns.lineWidth);
		gl.glShadeModel(ns.shadeModel);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, ns.rendMode);
		gl.glPushName(dispOpt.pickingOptions.add(this));	
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_COLOR_MATERIAL);

		
		if (color!=null && color.coord!=null){
			if (!colorPerVertex){
				
				if (colorIndex!=null){
					int c = 0;
					Vector3f[]	col = color.coord;
					Vector3f[] 	ver = coord.coord;
					gl.glBegin(GL.GL_LINE_STRIP);					
					for (int i=0; i!=coordIndex.length; i++){			
						int indVer = coordIndex[i];
						int indCol = colorIndex[c];
						if (indVer==-1){
							gl.glEnd();
							gl.glBegin(GL.GL_LINE_STRIP);
							c++;
							continue;
						}
						gl.glColor3f( col[indCol].x, col[indCol].y, col[indCol].z);					
						gl.glVertex3f(ver[indVer].x, ver[indVer].y, ver[indVer].z);
					}
					gl.glEnd();
				}else{
					Vector3f[]	col = color.coord;
					Vector3f[] 	ver = coord.coord;
					gl.glBegin(GL.GL_LINE_STRIP);
					int c = 0;
					for (int i=0; i<coordIndex.length; i++){			
						int indVer = coordIndex[i];
						
						if (indVer==-1){
							gl.glEnd();
							gl.glBegin(GL.GL_LINE_STRIP);
							c++;
							continue;
						}
						gl.glColor3f (col[c].x,col[c].y,col[c].z);					
						gl.glVertex3f(ver[c].x,ver[c].y,ver[c].z);					
					}
					gl.glEnd();
				}				
				
			}else{
				if (colorIndex!=null){
					Vector3f[]	col = color.coord;
					Vector3f[] 	ver = coord.coord;
					gl.glBegin(GL.GL_LINE_STRIP);					
					for (int i=0; i!=coordIndex.length; i++){			
						int indVer = coordIndex[i];
						int indCol = colorIndex[i];
						if (indVer==-1){
							gl.glEnd();
							gl.glBegin(GL.GL_LINE_STRIP);
							continue;
						}
						gl.glColor3f( col[indCol].x, col[indCol].y, col[indCol].z);					
						gl.glVertex3f(ver[indVer].x, ver[indVer].y, ver[indVer].z);
					}
					gl.glEnd();
				}else{
					Vector3f[]	col = color.coord;
					Vector3f[] 	ver = coord.coord;
					gl.glBegin(GL.GL_LINE_STRIP);
					for (int i=0; i<coordIndex.length; i++){			
						int indVer = coordIndex[i];
						if (indVer==-1){
							gl.glEnd();
							gl.glBegin(GL.GL_LINE_STRIP);
							continue;
						}
						gl.glColor3f (col[indVer].x,col[indVer].y,col[indVer].z);					
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);					
					}
					gl.glEnd();	
				}
			}
		}else{
			VRMaterial mat = null;
			if (((VRShape)parent).appearance!=null){
				mat = ((VRShape)parent).appearance.material;
			}
			Vector3f color=new Vector3f(1,1,1);
			if (mat!=null){
				color=mat.emissiveColor;
			}
			
			Vector3f[] 	ver = coord.coord;
			gl.glBegin(GL.GL_LINE_STRIP);
			for (int i=0; i!=coordIndex.length; i++){			
				int indVer = coordIndex[i];
				if (indVer==-1){
					gl.glEnd();
					gl.glBegin(GL.GL_LINE_STRIP);
					continue;
				}
				gl.glColor3f (color.x,color.y,color.z);					
				gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);					
			}
			gl.glEnd();
			
		}
		
		gl.glPopName();
		gl.glEnable(GL.GL_LIGHTING);
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
			if (coordIndex!=null && coord!=null){
				for (int i=0; i!=coordIndex.length; i++){
					int indVer=coordIndex[i];
					if (indVer==-1)
						continue;
					ns.boundingBox.mix(coord.coord[indVer]);
				}
			}
		}
		return ns;
	}
}
