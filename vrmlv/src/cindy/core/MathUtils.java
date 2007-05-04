package cindy.core;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

/**
 * Some useful math functions needed for ArcBall implementation
 * 
 */

public class MathUtils{
	
	static public void Matrix3fSetRotationFromQuat4f(Matrix3f NewObj,  Vector4f q1){
		
		float n, s;
		float xs, ys, zs;
		float wx, wy, wz;
		float xx, xy, xz;
		float yy, yz, zz;

		n = (q1.x* q1.x) + (q1.y  * q1.y ) + (q1.z * q1.z) + (q1.w * q1.w);
		s = (n > 0.0f) ? (2.0f / n) : 0.0f;

		xs = q1.x * s;
		ys = q1.y * s;
		zs = q1.z * s;
		wx = q1.w * xs;
		wy = q1.w * ys;
		wz = q1.w * zs;
		xx = q1.x * xs;
		xy = q1.x * ys;
		xz = q1.x * zs;
		yy = q1.y * ys;
		yz = q1.y * zs;
		zz = q1.z * zs;

		NewObj.m00 = 1.0f - (yy + zz); NewObj.m01 =         xy - wz;  NewObj.m02 =         xz + wy;
		NewObj.m10 =         xy + wz;  NewObj.m11 = 1.0f - (xx + zz); NewObj.m12 =         yz - wx;
		NewObj.m20 =         xz - wy;  NewObj.m21 =         yz + wx;  NewObj.m22 = 1.0f - (xx + yy);
	}


	static public void Matrix4fSetRotationScaleFromMatrix4f(Matrix4f NewObj,  Matrix4f m1){
		
		NewObj.m00 = m1.m00; NewObj.m01 = m1.m01; NewObj.m02 = m1.m02;
		NewObj.m10 = m1.m10; NewObj.m11 = m1.m11; NewObj.m12 = m1.m12;
		NewObj.m20 = m1.m20; NewObj.m21 = m1.m21; NewObj.m22 = m1.m22;
	}

	static public float Matrix4fSVD(Matrix4f NewObj, Matrix3f rot3, Matrix4f rot4){

		float s, n;

		s = (float)Math.sqrt(
				( (NewObj.m00 * NewObj.m00) + (NewObj.m10 * NewObj.m10) + (NewObj.m20 * NewObj.m20) + 
				(NewObj.m01 * NewObj.m01) + (NewObj.m11 * NewObj.m11) + (NewObj.m21 * NewObj.m21) +
				(NewObj.m02 * NewObj.m02) + (NewObj.m12 * NewObj.m12) + (NewObj.m22 * NewObj.m22) ) / 3.0f );

		if (rot3!=null){
			
			rot3.m00 = NewObj.m00; rot3.m10 = NewObj.m10; rot3.m20 = NewObj.m20;
			rot3.m01 = NewObj.m01; rot3.m11 = NewObj.m11; rot3.m21 = NewObj.m21;
			rot3.m02 = NewObj.m02; rot3.m12 = NewObj.m12; rot3.m22 = NewObj.m22;

			// zero-div may occur.

			n = 1.0f / (float)Math.sqrt( 	(NewObj.m00 * NewObj.m00) +
											(NewObj.m10 * NewObj.m10) +
											(NewObj.m20 * NewObj.m20) );
			rot3.m00 *= n;
			rot3.m10 *= n;
			rot3.m20 *= n;

			n = 1.0f / (float)Math.sqrt(	(NewObj.m01 * NewObj.m01) +
											(NewObj.m11 * NewObj.m11) +
											(NewObj.m21 * NewObj.m21) );
			rot3.m01 *= n;
			rot3.m11 *= n;
			rot3.m21 *= n;

			n = 1.0f / (float)Math.sqrt(	(NewObj.m02 * NewObj.m02) +
											(NewObj.m12 * NewObj.m12) +
											(NewObj.m22 * NewObj.m22) );
			rot3.m02 *= n;
			rot3.m12 *= n;
			rot3.m22 *= n;
		}

		if (rot4!=null){
			if (rot4 != NewObj){
				Matrix4fSetRotationScaleFromMatrix4f(rot4, NewObj);
			}

			// zero-div may occur.

			n = 1.0f / (float)Math.sqrt(	(NewObj.m00 * NewObj.m00) +
											(NewObj.m10 * NewObj.m10) +
											(NewObj.m20 * NewObj.m20) );
			rot4.m00 *= n;
			rot4.m10 *= n;
			rot4.m20 *= n;

			n = 1.0f / (float)Math.sqrt( 	(NewObj.m01 * NewObj.m01) +
											(NewObj.m11 * NewObj.m11) +
											(NewObj.m21 * NewObj.m21) );
			rot4.m01 *= n;
			rot4.m11 *= n;
			rot4.m21 *= n;

			n = 1.0f / (float)Math.sqrt( 	(NewObj.m02 * NewObj.m02) +
											(NewObj.m12 * NewObj.m12) +
											(NewObj.m22 * NewObj.m22) );
			rot4.m02 *= n;
			rot4.m12 *= n;
			rot4.m22 *= n;
		}

		return s;
	}

	static public void Matrix4fSetRotationScaleFromMatrix3f(Matrix4f NewObj,  Matrix3f m1){
		NewObj.m00 = m1.m00; NewObj.m01 = m1.m01; NewObj.m02 = m1.m02;
		NewObj.m10 = m1.m10; NewObj.m11 = m1.m11; NewObj.m12 = m1.m12;
		NewObj.m20 = m1.m20; NewObj.m21 = m1.m21; NewObj.m22 = m1.m22;
	}

	static public void Matrix4fMulRotationScale(Matrix4f NewObj, float scale){
		NewObj.m00 *= scale; NewObj.m01 *= scale; NewObj.m02 *= scale;
		NewObj.m10 *= scale; NewObj.m11 *= scale; NewObj.m12 *= scale;
		NewObj.m20 *= scale; NewObj.m21 *= scale; NewObj.m22 *= scale;
	}

	static public void Matrix4fSetRotationFromMatrix3f(Matrix4f NewObj,  Matrix3f m1){
		float scale= Matrix4fSVD(NewObj, null, null);
		Matrix4fSetRotationScaleFromMatrix3f(NewObj, m1);
		Matrix4fMulRotationScale(NewObj, scale);
	}
	
	public final static float Epsilon = 0.000001f;
}