package cindy.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.SwingUtilities;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.log4j.Logger;

import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.parser.VRMLModel;

/**
 * Main class for Renderer Window, perform all graphic issues
 * 
 */

public class VRMLRenderer implements GLEventListener, MouseListener, MouseMotionListener {
	
	private static Logger _LOG = Logger.getLogger(VRMLRenderer.class);
	
	private GL gl;
	private GLU glu;
	private VRMLModel model;

	private boolean inited = false;
	private boolean shutdowned = false;
	
	//arcball
	//////////////////////////////////////////////////////////////////////////////////////
	private ArcBall arcBall = new ArcBall(640.0f, 480.0f);
	private Vector3f arcBallPos=new Vector3f(0,0,0);
	private javax.vecmath.Matrix4f transform = new javax.vecmath.Matrix4f();
	private javax.vecmath.Matrix3f lastRot = new javax.vecmath.Matrix3f();
	private javax.vecmath.Matrix3f thisRot = new javax.vecmath.Matrix3f();
	private Vector2f mousePt=new Vector2f();
	private boolean isClicked  = false;
	private boolean isDragging = false;	
	
	private void clearArcBall(){
		lastRot.setIdentity();
        thisRot.setIdentity();
        transform.setIdentity();
        arcBallPos.set(0, 0, 0);
	}
	
//	is called to update position when rotating by mouse
	private void updatePosition(){
		
	    if (!isDragging){
	        if (isClicked){
				isDragging = true;
				lastRot.m00 = thisRot.m00;
				lastRot.m01 = thisRot.m01;
				lastRot.m02 = thisRot.m02;
				lastRot.m10 = thisRot.m10;
				lastRot.m11 = thisRot.m11;
				lastRot.m12 = thisRot.m12;
				lastRot.m20 = thisRot.m20;
				lastRot.m21 = thisRot.m21;
				lastRot.m22 = thisRot.m22;
				arcBall.click(mousePt);
	        }
	    }else{
	        if (isClicked){
	        	
	            Vector4f ThisQuat=new Vector4f();
	            arcBall.drag(mousePt, ThisQuat);
	            MathUtils.Matrix3fSetRotationFromQuat4f(thisRot, ThisQuat);
	            thisRot.mul(lastRot);
	            MathUtils.Matrix4fSetRotationFromMatrix3f(transform, thisRot);
	        }
	        else
	            isDragging = false;
	    }
	}
	
	 //update the opengl matrixes by arcball matrix 
    private void updateArcBallPos(GL gl){    	
    	gl.glTranslatef(arcBallPos.x,arcBallPos.y,arcBallPos.z);		
		float[] M={
				transform.m00,transform.m10,transform.m20,transform.m30,
				transform.m01,transform.m11,transform.m21,transform.m31,
				transform.m02,transform.m12,transform.m22,transform.m32,
				transform.m03,transform.m13,transform.m23,transform.m33
		};
	    gl.glMultMatrixf(M,0);
    }
	//////////////////////////////////////////////////////////////////////////////////////
	
    public synchronized void setModel(VRMLModel m) {
    	clearArcBall();
		model = m;
	}
        
    //is called when opengl context changes
	public void init(GLAutoDrawable drawable) {
		clearArcBall();
		gl = drawable.getGL();
		glu	= new GLU();
			
	   	_LOG.info("-------------------------------------------------------\n");
	   	_LOG.info("OpenGl initializing");
    	_LOG.info("GL_VENDOR:\t" + gl.glGetString(GL.GL_VENDOR));
    	_LOG.info("GL_RENDERER:\t" + gl.glGetString(GL.GL_RENDERER));
    	_LOG.info("GL_VERSION:\t" + gl.glGetString(GL.GL_VERSION));    
    	
    	//if openglcontex changes, but inited alredy we not add the same listeners    	 
    	if (!inited){
    		drawable.addMouseListener(this);
      		drawable.addMouseMotionListener(this);
    	}
      	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
    	gl.glClearDepth(1.0f);
    	gl.glDepthFunc(GL.GL_LEQUAL);
    	gl.glEnable(GL.GL_DEPTH_TEST);
    	gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);    	    	
    	_LOG.info("-------------------------------------------------------\n");
    	inited = true;
    }
	
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    	if (height <= 0) {
			height = 1;
		}
    	float h = (float)width / (float)height;
    	gl.glMatrixMode(GL.GL_PROJECTION);
    	gl.glViewport(0,0,width,height);
    	gl.glLoadIdentity();
    	glu.gluPerspective(45.0f, h, .1f ,600.0f);
    	gl.glMatrixMode(GL.GL_MODELVIEW);
    	gl.glLoadIdentity();
    	arcBall.setBounds((float) width, (float) height);
    }   
    
    public synchronized void shutdown(){
    	shutdowned = true;
    	_LOG.info("renderer ask to stop thread");
    }
                     
	public void display(GLAutoDrawable drawable) {
		if (shutdowned) return;
		
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);		
		if (model!=null){
			updateArcBallPos(gl);
			((IDrawable)model.getMainGroup()).draw(new DisplayOptions(gl,glu));
		}
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		_LOG.info("displayChanged");
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public synchronized void mousePressed(MouseEvent e){
		if (SwingUtilities.isLeftMouseButton(e)) {
			isClicked = true;
		}
	}
	
	public synchronized void mouseReleased(MouseEvent e){
		if (SwingUtilities.isLeftMouseButton(e)){
			isDragging	= false;
			isClicked	= false;
		}
	} 
	public synchronized void mouseClicked(MouseEvent e){
    }
	
	public synchronized void mouseDragged(MouseEvent e){
		if (isClicked){
			mousePt.x = e.getX();
	        mousePt.y = e.getY();
			updatePosition();
		}		
	}
	
	public synchronized void mouseMoved(MouseEvent e) {
	}
}