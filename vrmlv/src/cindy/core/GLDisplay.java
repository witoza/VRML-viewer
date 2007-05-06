package cindy.core;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import org.apache.log4j.Logger;

import com.sun.opengl.util.FPSAnimator;

/**
 * GLDisplay holds all information needed to handle renderer panel, also staring and
 * shutting down an Animator
 *
 */
public class GLDisplay{

	static Logger _LOG = Logger.getLogger(GLDisplay.class);
	
    private FPSAnimator animator;
    //private GLJPanel renderingPanel;
    
    private GLCanvas renderingPanel = new GLCanvas();
     
    private boolean threadFinished = false;
    
    public boolean isThreadFinished(){
    	return threadFinished;   	
    }
   
    public GLCanvas getDrawable(){
    	return renderingPanel;
    }
    
    public GLDisplay(GLEventListener listener) {
    	GLCapabilities capabilities=new GLCapabilities();
    	_LOG.info(capabilities.toString());
        //renderingPanel = new GLJPanel(capabilities);
        renderingPanel.setIgnoreRepaint(true);        
        renderingPanel.addGLEventListener(listener);
        /*renderingPanel.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {
				forceToDraw();				
			}
			public void mouseMoved(MouseEvent e) {
				forceToDraw();
			}
        });
        renderingPanel.addMouseListener(new MouseAdapter(){
        	public synchronized void mouseClicked(MouseEvent e){
        		forceToDraw();
        	}
        	public synchronized void mouseReleased(MouseEvent e){
        		forceToDraw();
        	}
        	public synchronized void mousePressed(MouseEvent e){
        		forceToDraw();
        	}
        });*/
        //animator = new LateFPSAnimator(renderingPanel, 25);
        animator = new FPSAnimator(renderingPanel, 60);
    }
    
    public void forceToDraw(){
    	//animator.draw();
    }    
    
    public void start() {
    	animator.start();
    }

    public void stop() {
        animator.stop();
        threadFinished=true;
    }
}
