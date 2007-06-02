package cindy.core;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import cindy.drawable.DisplayOptions;

/**
 * BoundingBox - smallest box which entirely encloses the object
 * 
 */
public class BoundingBox{
	
	private static final float MAX_FLOAT = Float.MAX_VALUE;	
	private	static Color color = Color.RED;
			
	private boolean isValid;
	
	private Vector3f min = new Vector3f(MAX_FLOAT,MAX_FLOAT,MAX_FLOAT);
	private Vector3f max = new Vector3f(-MAX_FLOAT,-MAX_FLOAT,-MAX_FLOAT);
    
    private Vector3f[] v = new Vector3f[8];
    
    public void reset() {
		min = new Vector3f(MAX_FLOAT, MAX_FLOAT, MAX_FLOAT);
		max = new Vector3f(-MAX_FLOAT, -MAX_FLOAT, -MAX_FLOAT);
	}
    
    public BoundingBox() {
		isValid = false;
		for (int i = 0; i != 8; i++){
			v[i] = new Vector3f();
		}
	}  
    
    /*
     * add node to bounding box, if needed it has to be extended
     */
    public void mix(final Vector3f v){
    	isValid = true;
        if (v.x < min.x) min.x = v.x;
        if (v.y < min.y) min.y = v.y;
        if (v.z < min.z) min.z = v.z;

        if (v.x > max.x) max.x = v.x;
        if (v.y > max.y) max.y = v.y;
        if (v.z > max.z) max.z = v.z;
    }
    
    /*
     * combine two bounding boxes, if needed it has to be extended
     * 
     */
    public void mix(final BoundingBox bb){
    	if (bb != null) {
    		if (!bb.isValid){
    			return;
    		}
    		isValid = true;
    		Vector3f v = bb.min;
    		if (v.x < min.x) min.x = v.x;
    		if (v.y < min.y) min.y = v.y;
    		if (v.z < min.z) min.z = v.z;

    		v = bb.max;
    		if (v.x > max.x) max.x = v.x;
    		if (v.y > max.y) max.y = v.y;
    		if (v.z > max.z) max.z = v.z;
    	}
    }
        
    /*
     * draw bounding box
     */
    public void draw(final DisplayOptions dispOpt) {

		if (!isValid) {
			return;
		}
		float min_y = min.y;
		float min_z = min.z;
		float min_x = min.x;

		float max_y = max.y;
		float max_z = max.z;
		float max_x = max.x;

		if (min_y > max_y){
			return;
		}

		v[0].set(min_x, min_y, min_z);
		v[1].set(min_x, min_y, max_z);
		v[2].set(min_x, max_y, min_z);
		v[3].set(min_x, max_y, max_z);
		v[4].set(max_x, min_y, min_z);
		v[5].set(max_x, min_y, max_z);
		v[6].set(max_x, max_y, min_z);
		v[7].set(max_x, max_y, max_z);
		float[] ls = new float[1];
		GL gl = dispOpt.gl;
		gl.glGetFloatv(GL.GL_LINE_WIDTH, ls, 0);
		gl.glLineWidth(1);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glColor3d(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255);
		gl.glBegin(GL.GL_LINES);
		for (int i = 0; i != 7; i++){
			for (int j = i + 1; j != 8; j++) {
				gl.glVertex3f(v[i].x, v[i].y, v[i].z);
				gl.glVertex3f(v[j].x, v[j].y, v[j].z);
			}
		}
		gl.glEnd();
		gl.glLineWidth(ls[0]);
		gl.glEnable(GL.GL_LIGHTING);
	}

	public boolean isValid() {
		return isValid;
	}

	public Vector3f getMax() {
		return max;
	}

	public Vector3f getMin() {
		return min;
	}
}
