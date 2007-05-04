package cindy.drawable;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.parser.VRIndexedLineSet;
import cindy.parser.VRMaterial;
import cindy.parser.VRNode;
import cindy.parser.VRShape;


public class VRDIndexedLineSet extends VRIndexedLineSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedLineSet.class);
	
	public void draw(DisplayOptions dispOpt) {	
		GL gl = dispOpt.gl;		
		VRMaterial mat = null;
		if (((VRShape)parent).appearance!=null){
			mat = ((VRShape)parent).appearance.material;
		}
		if (coordIndex!=null){
			Vector3f[] ver=coord.coord;
			int 		indVer;
			
			if (color!=null){
				Vector3f[]	col=color.coord;
				int			indCol;
				if (colorPerVertex){				
					if (colorIndex!=null){
						gl.glBegin(GL.GL_LINE_STRIP);					
						for (int i=0; i!=coordIndex.length; i++){			
							indVer = coordIndex[i];
							indCol = colorIndex[i];
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
						gl.glBegin(GL.GL_LINE_STRIP);
						for (int i=0; i!=coordIndex.length; i++){			
							indVer = coordIndex[i];
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
				}else{
					throw new UnsupportedOperationException("unsuported path in draw for VRIndexedLineSet 1\n");
				}
			}else {
				Vector3f	color=new Vector3f(1,1,1);
				if (mat!=null)
					color=mat.emissiveColor;
				if (colorPerVertex){
					gl.glBegin(GL.GL_LINE_STRIP);
					for (int i=0; i!=coordIndex.length; i++){			
						indVer = coordIndex[i];
						if (indVer==-1){
							gl.glEnd();
							gl.glBegin(GL.GL_LINE_STRIP);
							continue;
						}
						gl.glColor3f (color.x,color.y,color.z);					
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);					
					}
					gl.glEnd();	
				}else{
					throw new UnsupportedOperationException("unsuported path in draw for VRIndexedLineSet 2\n");					
				}
			}
		}
	}

	public int numOfDrawableChildren() {
		return 0;
	}

	public VRNode getNthChild(int n) {
		return null;
	}
	
}
