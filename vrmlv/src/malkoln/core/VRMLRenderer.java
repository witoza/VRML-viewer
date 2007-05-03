package malkoln.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import malkoln.vrmlparser.VRMLModel;

import org.apache.log4j.Logger;

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
	
    public void setModel(VRMLModel m) {
		model = m;
	}
        
    //is called when opengl context changes
	public void init(GLAutoDrawable drawable) {
		
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
    }   
    
    public synchronized void shutdown(){
    	shutdowned = true;
    	_LOG.info("renderer ask to stop thread");
    }
    
    private float rotateT = 0.0f;
                     
	public synchronized void display(GLAutoDrawable drawable) {
		if (shutdowned) return;
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);

		gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

		gl.glBegin(GL.GL_TRIANGLES);

		// Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Right Side Facing Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);

		// Left Side Facing Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		// Bottom
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.1f, 0.1f, 0.1f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);

		gl.glEnd();

		rotateT += 0.2f;
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		_LOG.info("displayChanged");
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public synchronized void mousePressed(MouseEvent e) {
		
	}

	public synchronized void mouseReleased(MouseEvent e) {
		
	}

	public synchronized void mouseClicked(MouseEvent e) {
		
	}

	public synchronized void mouseDragged(MouseEvent e) {

	}

	public synchronized void mouseMoved(MouseEvent e) {

	}
}