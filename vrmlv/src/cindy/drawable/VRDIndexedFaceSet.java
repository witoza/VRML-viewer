package cindy.drawable;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.parser.VRIndexedFaceSet;
import cindy.parser.VRMaterial;
import cindy.parser.VRShape;

public class VRDIndexedFaceSet extends VRIndexedFaceSet implements IDrawable{

	private static Logger _LOG = Logger.getLogger(VRDIndexedFaceSet.class);
	
	public void draw(DisplayOptions dispOpt) {
		VRMaterial mat = null;
		if (((VRShape)parent).appearance!=null){
			mat = ((VRShape)parent).appearance.material;
		}
		GL gl = dispOpt.gl;		
		gl.glEnable(GL.GL_NORMALIZE);				
		Vector3f[] ver=null;
		if (coord!=null){
			ver=coord.coord;
		}
		int indVer;
		int triangles=0;
		if (color!=null){
			Vector3f[]	col=color.coord;
			int			indCol;
			
			if (colorPerVertex){
				if (colorIndex!=null){
					Vector3f[] poly=new Vector3f[3];
				
					gl.glBegin(GL.GL_TRIANGLES);
					int i=0;
					while (i<coordIndex.length){
						triangles++;
						poly[0]=ver[coordIndex[i+2]];
						poly[1]=ver[coordIndex[i+1]];
						poly[2]=ver[coordIndex[i+0]];
						
						Vector3f vVector1=new Vector3f();
						vVector1.x=poly[2].x-poly[0].x;
						vVector1.y=poly[2].y-poly[0].y;
						vVector1.z=poly[2].z-poly[0].z;
						
						Vector3f vVector2=new Vector3f();
						vVector2.x=poly[1].x-poly[0].x;
						vVector2.y=poly[1].y-poly[0].y;
						vVector2.z=poly[1].z-poly[0].z;
						
						Vector3f vNormal=new Vector3f();
						vNormal.cross(vVector1,vVector2);
						vNormal.normalize();
									    	
						
						//TODO: check normals
						indCol=colorIndex[i];
						indVer=coordIndex[i];
						gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
						gl.glColor3f(col[indCol].x,col[indCol].y,col[indCol].z);
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
						i++;
						indCol=colorIndex[i];
						indVer=coordIndex[i];
						//gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
						gl.glColor3f(col[indCol].x,col[indCol].y,col[indCol].z);
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
						i++;
						indCol=colorIndex[i];
						indVer=coordIndex[i];
						//gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
						gl.glColor3f(col[indCol].x,col[indCol].y,col[indCol].z);
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
						i+=2;
					}
					gl.glEnd();
				}else{					
					
					Vector3f[] poly=new Vector3f[3];
					gl.glBegin(GL.GL_TRIANGLES);
					int i=0;
					Vector3f vVector1=new Vector3f();
					Vector3f vVector2=new Vector3f();
					Vector3f vNormal=new Vector3f();
					while (i<coordIndex.length){
						triangles++;
						poly[0]=ver[coordIndex[i+2]];
						poly[1]=ver[coordIndex[i+1]];
						poly[2]=ver[coordIndex[i+0]];
						
						vVector1.x=poly[2].x-poly[0].x;
						vVector1.y=poly[2].y-poly[0].y;
						vVector1.z=poly[2].z-poly[0].z;						
						
						vVector2.x=poly[1].x-poly[0].x;
						vVector2.y=poly[1].y-poly[0].y;
						vVector2.z=poly[1].z-poly[0].z;
												
						vNormal.cross(vVector1,vVector2);
						vNormal.normalize();
						
						//TODO: check normals
						indVer=coordIndex[i];
						gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
						gl.glColor3f(col[indVer].x,col[indVer].y,col[indVer].z);
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
						i++;
						indVer=coordIndex[i];
						//gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
						gl.glColor3f(col[indVer].x,col[indVer].y,col[indVer].z);
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
						i++;
						indVer=coordIndex[i];
						//gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
						gl.glColor3f(col[indVer].x,col[indVer].y,col[indVer].z);
						gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
						i+=2;
					}
					gl.glEnd();
				}
				
			}else{
				throw new UnsupportedOperationException("unsuported path in draw for VRIndexedFaceSet 1");
			}
		}else{
			if (coordIndex!=null){
				if (mat==null)
					throw new UnsupportedOperationException("error - check vrml spec");
				gl.glColor3f(mat.diffuseColor.x,mat.diffuseColor.y,mat.diffuseColor.z);
				Vector3f[] poly=new Vector3f[3];				
				gl.glBegin(GL.GL_TRIANGLES);
				int i=0;
				while (i<coordIndex.length){
					triangles++;
					poly[0]=ver[coordIndex[i+2]];
					poly[1]=ver[coordIndex[i+1]];
					poly[2]=ver[coordIndex[i+0]];
					
					Vector3f vVector1=new Vector3f();
					vVector1.x=poly[2].x-poly[0].x;
					vVector1.y=poly[2].y-poly[0].y;
					vVector1.z=poly[2].z-poly[0].z;
					
					Vector3f vVector2=new Vector3f();
					vVector2.x=poly[1].x-poly[0].x;
					vVector2.y=poly[1].y-poly[0].y;
					vVector2.z=poly[1].z-poly[0].z;
					
					Vector3f vNormal=new Vector3f();
					vNormal.cross(vVector1,vVector2);
					vNormal.normalize();
					
					indVer=coordIndex[i];
					gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
					
					gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
					i++;
					indVer=coordIndex[i];
					//gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
					gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
					i++;
					indVer=coordIndex[i];
					//gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
					gl.glVertex3f(ver[indVer].x,ver[indVer].y,ver[indVer].z);
					i+=2;
				}
				gl.glEnd();
			}			
		}	    	
	}

	public int numOfDrawableChildren() {
		return 0;
	}

}
