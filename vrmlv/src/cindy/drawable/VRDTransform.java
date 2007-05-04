package cindy.drawable;

import java.util.Iterator;

import javax.media.opengl.GL;

import cindy.parser.VRNode;
import cindy.parser.VRTransform;

public class VRDTransform extends VRTransform implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		GL gl = dispOpt.gl;
		gl.glPushMatrix();
		
			//in ogl terms:
			//gl.glTranslatef(translation.x,translation.y,translation.z);
			//gl.glTranslatef(center.x,center.y,center.z);
			//gl.glRotatef(rotation.w*180/(float)Math.PI,rotation.x,rotation.y,rotation.z);	
			//gl.glRotatef(scaleOrientation.w*180/(float)Math.PI,scaleOrientation.x,scaleOrientation.y,scaleOrientation.z);
			//gl.glScalef(scale.x,scale.y,scale.z);
			//gl.glRotatef(-scaleOrientation.w*180/(float)Math.PI,scaleOrientation.x,scaleOrientation.y,scaleOrientation.z);
			//gl.glTranslatef(-center.x,-center.y,-center.z);
								
		
			gl.glMultMatrixf(getTransformMatrix().getMatrix(),0);
			
			Iterator<IDrawable> iter = (Iterator<IDrawable>) children.iterator();
			while(iter.hasNext()){
				iter.next().draw(dispOpt);
			}
		gl.glPopMatrix();		
	}

	public int numOfDrawableChildren() {
		return children.size();
	}
	
	public VRNode getNthChild(int n) {
		return (VRNode)children.get(n);
	}

}
