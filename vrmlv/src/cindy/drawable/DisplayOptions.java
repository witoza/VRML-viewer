package cindy.drawable;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class DisplayOptions {
	public GL gl;
	public GLU glu;
	
	public DisplayOptions(GL gl, GLU glu) {
		super();
		this.gl = gl;
		this.glu = glu;
	}
	
}
