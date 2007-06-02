package cindy.drawable;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import cindy.parser.nodes.VRMaterial;

public class DrawableHelper {
	
	static float[] toFloat(Vector3f vec){
		return new float[]{vec.x,vec.y,vec.z};
	}
	
	static float[] toFloat4(Vector3f vec){
		return new float[]{vec.x,vec.y,vec.z, 1.0f};
	}
	
	public static void putNormal(GL gl, Vector3f poly[]){
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
		gl.glNormal3f(vNormal.x,vNormal.y,vNormal.z);
	}
		
	
	//see: http://devernay.free.fr/cours/opengl/materials.html
	static public void setObjectPropertiesFromMaterial(GL gl, VRMaterial mat){
		if (mat==null) return ;
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR);
					
		
		
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, toFloat4(mat.diffuseColor), 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, toFloat(mat.emissiveColor), 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, toFloat(mat.specularColor), 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, mat.shininess * 128.0f);
	}
}