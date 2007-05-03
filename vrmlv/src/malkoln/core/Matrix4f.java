package malkoln.core;
import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

/**
 * Represents Matrix which has 4 rows and 4 columns
 * 
 * It is nice to OpenGL as it is an array of floats and OpenGL internal matrixes
 * can be updated in single line:
 * 
 * <code> gl.glMultMatrixf(getMatrix(),0); </code>
 * 
 * It is needed especialy by Transform VRML node as it has several properties
 * such as: rotation, scale, transform and the proper matrix multiplication need
 * to be applied for all children nodes when drawing
 * 
 */

public class Matrix4f{
	
	/**
	 * Logger
	 */
	private static final Logger _LOG = Logger.getLogger(Matrix4f.class);
	
	private float[] B=new float[16];
	
	public float[] getMatrix(){
		return B;
	}
	
	private float[] C=new float[16];
	private float[] T=new float[16];
	
	private void multiply(){
		C[0] = T[0] * B[0] + T[4] * B[1] + T[8] * B[2] + T[12] * B[3];
		C[4] = T[0] * B[4] + T[4] * B[5] + T[8] * B[6] + T[12] * B[7];
		C[8] = T[0] * B[8] + T[4] * B[9] + T[8] * B[10] + T[12] * B[11];
		C[12] = T[0] * B[12] + T[4] * B[13] + T[8] * B[14] + T[12] * B[15];

		C[1] = T[1] * B[0] + T[5] * B[1] + T[9] * B[2] + T[13] * B[3];
		C[5] = T[1] * B[4] + T[5] * B[5] + T[9] * B[6] + T[13] * B[7];
		C[9] = T[1] * B[8] + T[5] * B[9] + T[9] * B[10] + T[13] * B[11];
		C[13] = T[1] * B[12] + T[5] * B[13] + T[9] * B[14] + T[13] * B[15];

		C[2] = T[2] * B[0] + T[6] * B[1] + T[10] * B[2] + T[14] * B[3];
		C[6] = T[2] * B[4] + T[6] * B[5] + T[10] * B[6] + T[14] * B[7];
		C[10] = T[2] * B[8] + T[6] * B[9] + T[10] * B[10] + T[14] * B[11];
		C[14] = T[2] * B[12] + T[6] * B[13] + T[10] * B[14] + T[14] * B[15];

		C[3] = T[3] * B[0] + T[7] * B[1] + T[11] * B[2] + T[15] * B[3];
		C[7] = T[3] * B[4] + T[7] * B[5] + T[11] * B[6] + T[15] * B[7];
		C[11] = T[3] * B[8] + T[7] * B[9] + T[11] * B[10] + T[15] * B[11];
		C[15] = T[3] * B[12] + T[7] * B[13] + T[11] * B[14] + T[15] * B[15];

		//why not loop?
		B[0] = C[0];
		B[4] = C[4];
		B[8] = C[8];
		B[12] = C[12];
		B[1] = C[1];
		B[5] = C[5];
		B[9] = C[9];
		B[13] = C[13];
		B[2] = C[2];
		B[6] = C[6];
		B[10] = C[10];
		B[14] = C[14];
		B[3] = C[3];
		B[7] = C[7];
		B[11] = C[11];
		B[15] = C[15];
	}
	
	private float cosf(float a){
		return (float)Math.cos(a);
	}
	
	private float sinf(float a){
		return (float)Math.sin(a);
	}
	
	public void MMultiply(Matrix4f M){
		for (int a=0; a!=16; a++)
			T[a]=M.T[a];
		multiply();
	}
	
	public void LoadIdent(){
		B[0]=1;		B[4]=0;		B[8]=0;		B[12]=0;
		B[1]=0;		B[5]=1;		B[9]=0;		B[13]=0;
		B[2]=0;		B[6]=0;		B[10]=1;	B[14]=0;
		B[3]=0;		B[7]=0;		B[11]=0;	B[15]=1;
	}
	
	void RotateX(float a){
		T[0]=1;			T[4]=0;			T[8]=0;		 	T[12]=0;
		T[1]=0;			T[5]=cosf(a);	T[9]=-sinf(a);	T[13]=0;
		T[2]=0;			T[6]=sinf(a);	T[10]=cosf(a);	T[14]=0;
		T[3]=0;			T[7]=0;			T[11]=0;		T[15]=1;
		multiply();
	}
	
	void RotateY(float a){
		T[0]=cosf(a);	T[4]=0;			T[8]=sinf(a);	T[12]=0;
		T[1]=0;			T[5]=1;			T[9]=0;			T[13]=0;
		T[2]=-sinf(a);	T[6]=0;			T[10]=cosf(a);	T[14]=0;
		T[3]=0;			T[7]=0;			T[11]=0;		T[15]=1;
		multiply();
	}
	
	void RotateZ(float a){
		T[0]=cosf(a);	T[4]=-sinf(a);	T[8]=0;		T[12]=0;
		T[1]=sinf(a);	T[5]=cosf(a);	T[9]=0;		T[13]=0;
		T[2]=0;			T[6]=0;			T[10]=1;	T[14]=0;
		T[3]=0;			T[7]=0;			T[11]=0;	T[15]=1;
		multiply();
	}
	
	public void Scale(float x,float y,float z){
		T[0]=x;		T[4]=0;		T[8]=0;		T[12]=0;
		T[1]=0;		T[5]=y;		T[9]=0;		T[13]=0;
		T[2]=0;		T[6]=0;		T[10]=z;	T[14]=0;
		T[3]=0;		T[7]=0;		T[11]=0;	T[15]=1;
		multiply();
	}
	
	public void Translate(float x,float y,float z)	{
		T[0]=1;		T[4]=0;		T[8]=0;		T[12]=x;
		T[1]=0;		T[5]=1;		T[9]=0;		T[13]=y;
		T[2]=0;		T[6]=0;		T[10]=1;	T[14]=z;
		T[3]=0;		T[7]=0;		T[11]=0;	T[15]=1;
		multiply();
	}
	
	public void Rotate(float a,float x,float y, float z){
		RotateX(a*x);
		RotateY(a*y);
		RotateZ(a*z);
	}
	
	public Vector3f VMultiply(Vector3f S){
		Vector3f res=new Vector3f();
		float w;
	 	res.x = B[0]*S.x+B[4]*S.y+B[8]*S.z+B[12];
	 	res.y = B[1]*S.x+B[5]*S.y+B[9]*S.z+B[13];
	 	res.z = B[2]*S.x+B[6]*S.y+B[10]*S.z+B[14];
	 	w	  = B[3]*S.x+B[7]*S.y+B[11]*S.z+B[15];
	 	res.x/=w;
	 	res.y/=w;
	 	res.z/=w;
	 	return res;
	}
}