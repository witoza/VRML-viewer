package cindy.drawable;

import java.util.Iterator;

import javax.media.opengl.GL;

import cindy.parser.VRLOD;

public class VRDLOD extends VRLOD implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		GL gl = dispOpt.gl;
		gl.glPushMatrix();
		//TODO: check if this should be transformed ex: gl.glTranslatef(center.x,center.y,center.z);			
			Iterator<IDrawable> iter = (Iterator<IDrawable>) level.iterator();
			while(iter.hasNext()){
				iter.next().draw(dispOpt);
			}
		gl.glPopMatrix();
	}

	public int numOfDrawableChildren() {
		return level.size();
	}

}
