package cindy.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.nio.IntBuffer;

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
import cindy.drawable.DrawableHelper;
import cindy.drawable.IDrawable;
import cindy.drawable.TextureReader;
import cindy.parser.VRMLModel;

import com.sun.opengl.util.BufferUtil;

/**
 * Main class for Renderer Window, perform all graphic issues
 * 
 */

public class VRMLRenderer implements GLEventListener, MouseListener, MouseMotionListener {
	
	private static Logger _LOG = Logger.getLogger(VRMLRenderer.class);
	
	private boolean addSelecting = false;
	private DisplayOptions displayOptions = new DisplayOptions(null, null);
	
	public DisplayOptions.SelectingOptions getSelectedNodes(){
		return displayOptions.selectedNodes;
	}
	
	private GL gl;
	private GLU glu;
	private VRMLModel model;

	private boolean inited = false;
	private boolean shutdowned = false;
	
	public VRMLRenderer(IParentListener par){
		parent = par;
	}
	
	//arcball
	//////////////////////////////////////////////////////////////////////////////////////
	private ArcBall arcBall = new ArcBall(640.0f, 480.0f);
	private Vector3f arcBallPos=new Vector3f(0,0,0);
	private javax.vecmath.Matrix4f transform = new javax.vecmath.Matrix4f();
	private javax.vecmath.Matrix3f lastRot = new javax.vecmath.Matrix3f();
	private javax.vecmath.Matrix3f thisRot = new javax.vecmath.Matrix3f();
	private Vector2f mousePt=new Vector2f();
	
	IParentListener parent;
	
	private boolean isMouseClicked=false; //for picking object
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
    
    //  handle picking options
    //see http://www.lighthouse3d.com/opengl/picking/index.php?openglway3
    private int processHits(int hits, IntBuffer buffer) {
        //System.out.println("---------------------------------");
        //System.out.println(" HITS: " + hits);
        int offset = 0;
        int names;
        int z1, z2;
        
        int minz=0;
        int minNumber=-1;
        
        for (int i = 0; i < hits; i++) {
            //System.out.println("- - - - - - - - - - - -");
            //System.out.println(" hit: " + (i + 1));
            names = buffer.get(offset);
            offset++;
            z1 = buffer.get(offset);
            offset++;
            z2 = buffer.get(offset) ;
            offset++;
            //System.out.println(" number of names: " + names);
            //System.out.println(" z1: " + z1);
            //System.out.println(" z2: " + z2);
            //System.out.println(" names: ");

            for (int j = 0; j < names; j++) {
                //System.out.print("  " + buffer.get(offset));
                if (j == (names - 1)) {
                	int res=buffer.get(offset);
                	if (minNumber==-1 || z1<minz){
                    	minNumber=res;
                    	minz=z2;
                    }
                    //System.out.println("<-");
                } else {
                    //System.out.println();
                }
                offset++;
            }
            //System.out.println("- - - - - - - - - - - -");
            
            
        }
        //System.out.println("--------------------------------- ");
        return minNumber;
    }
	
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
    	   gl.glEnable(GL.GL_TEXTURE_2D);
    	
    	gl.glEnable(GL.GL_LIGHT0);
    	gl.glEnable(GL.GL_LIGHTING);    	
    	gl.glEnable(GL.GL_COLOR_MATERIAL);
    	
    	gl.glFrontFace(GL.GL_CCW);    	
		gl.glDisable(GL.GL_CULL_FACE);
		
		gl.glColor3f(1,1,1);
		gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);			
		//gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
    	
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
    
    public boolean secondArcballMode=false;
    
    public synchronized void shutdown(){
    	shutdowned = true;
    	_LOG.info("renderer ask to stop thread");
    }
    
    public void resetPos(){
    	clearArcBall();
    }   
    
    float[] getFormMatrix(javax.vecmath.Matrix4f transform){
    	float[] M={
				transform.m00,transform.m10,transform.m20,transform.m30,
				transform.m01,transform.m11,transform.m21,transform.m31,
				transform.m02,transform.m12,transform.m22,transform.m32,
				transform.m03,transform.m13,transform.m23,transform.m33
		};
    	return M;
    }
 
    
    void drawModel(){
    	gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
		
		
	gl.glPushMatrix();
			gl.glTranslatef(arcBallPos.x,arcBallPos.y,arcBallPos.z);	
			
			float[] M={
					transform.m00,transform.m10,transform.m20,transform.m30,
					transform.m01,transform.m11,transform.m21,transform.m31,
					transform.m02,transform.m12,transform.m22,transform.m32,
					transform.m03,transform.m13,transform.m23,transform.m33
			};
		    gl.glMultMatrixf(M,0);
		    
		    
	/*		
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
			GLUquadric quadric = glu.gluNewQuadric();
			glu.gluSphere(quadric, 0.2, 6, 6);
	*/
	    	if (model!=null){
				gl.glInitNames();
				((IDrawable)model.getMainGroup()).draw(displayOptions);
				
			}
	    	
	    	gl.glPopMatrix();
    }
                     
	public void display(GLAutoDrawable drawable) {
		displayOptions.gl = gl;
		displayOptions.glu = glu;
		displayOptions.pickingOptions.clear();
		if (shutdowned) return;
		
		if (isMouseClicked){			
			//picking objects
			int x=(int)actualMousePos.x;
			int y=(int)actualMousePos.y;
			int nbRecords = 0;
			IntBuffer selectBuffer=BufferUtil.newIntBuffer(32);

			gl.glSelectBuffer(selectBuffer.capacity(), selectBuffer);
			gl.glRenderMode(GL.GL_SELECT);
			int[] viewport = new int[4];
			gl.glGetIntegerv(GL.GL_VIEWPORT, viewport,0);
			
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPushMatrix();
				gl.glLoadIdentity();
				glu.gluPickMatrix(x, viewport[3]-y, 2, 2, viewport,0);
				glu.gluPerspective(45.0f, (float)viewport[2]/(float)viewport[3], 0.1f, 100.0f);
				gl.glMatrixMode(GL.GL_MODELVIEW);
				gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
				
				drawModel();
				
				gl.glFlush();
				nbRecords = gl.glRenderMode(GL.GL_RENDER);
				gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPopMatrix();
			gl.glMatrixMode(GL.GL_MODELVIEW);
			if(nbRecords > 0){				
				int objectNumber=processHits(nbRecords,selectBuffer);
				if (objectNumber>=0){
					IDrawable node = (IDrawable)displayOptions.pickingOptions.get(objectNumber);
					_LOG.debug("picking: " + node);
					if (addSelecting){
						displayOptions.selectedNodes.selectAnotherNode(node);
					}else{
						displayOptions.selectedNodes.selectSingleNode(node);
					}
				}
			}else{
				if (!addSelecting){
					displayOptions.selectedNodes.clearSelectedNodes();
				}
				_LOG.debug("picking: none");
			}
			parent.objectClicked(displayOptions.selectedNodes);			
			
		}
		isMouseClicked = false;
				
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT);
				
		drawModel();
		if (isCenterClicked){
			float dy=(lastMouseY-actualMousePos.y)/50.0f;
//			System.out.println(dy);
			if (Math.abs(lastMouseY-actualMousePos.y)>1){
				lastDelta=dy;
			}else{
				dy=lastDelta;
			}
			arcBallPos.z-=dy;
			lastMouseY = (int) actualMousePos.y;
		}
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		_LOG.info("displayChanged");
	}

	private boolean isRightClicked=false;
	private boolean isCenterClicked=false;
	private int lastMouseX=-1;
	private int lastMouseY=-1;
	private float lastDelta;
	private Vector2f actualMousePos=new Vector2f();
	    
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public synchronized void mousePressed(MouseEvent e){
		lastMouseX=-1;
		lastMouseY=-1;
		if (SwingUtilities.isLeftMouseButton(e)) {
			isClicked = true;
		}
		else if (SwingUtilities.isMiddleMouseButton(e)){
			isCenterClicked = true; 
			lastMouseY=(int)actualMousePos.y; 
			lastDelta=0.0f;
		}
		else if (SwingUtilities.isRightMouseButton(e)){
			isRightClicked = true;
		}
	}
	
	public synchronized void mouseReleased(MouseEvent e){
		if (SwingUtilities.isLeftMouseButton(e)){
			isDragging	= false;
			isClicked	= false;
		}
		else if (SwingUtilities.isRightMouseButton(e)) isRightClicked = false;
		else if (SwingUtilities.isMiddleMouseButton(e)) isCenterClicked = false;
	} 
	public synchronized void mouseClicked(MouseEvent e){
		isMouseClicked=true;
		if (e.isControlDown()){
			addSelecting=true;
		}else{
			addSelecting=false;			
		}
    }
	
	public synchronized void mouseDragged(MouseEvent e){
		if (isClicked){
			mousePt.x = e.getX();
	        mousePt.y = e.getY();
			updatePosition();
		}
		if (isRightClicked){
			if (lastMouseX==-1){
				lastMouseX=e.getX();
				lastMouseY=e.getY();
			}
			
			float dx=-(lastMouseX-e.getX())/200.0f;
			float dy=(lastMouseY-e.getY())/200.0f;
			
			arcBallPos.x+=dx;
			arcBallPos.y+=dy;
			
			
			lastMouseX=e.getX();
			lastMouseY=e.getY();
		}		
		actualMousePos.x=e.getX();
		actualMousePos.y=e.getY();	
	}
	
	public synchronized void mouseMoved(MouseEvent e) {
		actualMousePos.x=e.getX();
		actualMousePos.y=e.getY();
	}
}