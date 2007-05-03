package cindy.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


import org.apache.log4j.Logger;

import cindy.core.GLDisplay;
import cindy.core.LoggerHelper;
import cindy.core.NativesHelper;
import cindy.core.VRMLRenderer;

public class Malcoln extends JFrame{
	
	private static final Logger _LOG = Logger.getLogger(Malcoln.class);
	
	static public String appName = "Malcoln 0.01";
	private GLDisplay renderingWindow;
	private VRMLRenderer renderer = new VRMLRenderer();

	private boolean exited = false;

	// need to be called when app exits
	public synchronized void shutdown() {
		if (exited){
			return;
		}
		exited = true;
		_LOG.info("exiting...");
		renderer.shutdown();
		new Thread(new Runnable() {
			public void run() {
				while (!renderingWindow.isThreadFinished()) {
					try {
						Thread.sleep(250);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					_LOG.info("waiting for shutdown");
				}
				_LOG.info("VrmlViewer closed");
				_LOG.info("------------------------------------------------------------");
			}
		}, "closing thread").start();
	}
	
	public Malcoln(){
		super(appName);
		LoggerHelper.initializeLoggingFacility();
		Object ob[] = NativesHelper.checkNativeFiles();
		if (((Boolean)ob[0]== false)){
			System.out.println(ob[1]);
			return ;
		}
		renderingWindow = new GLDisplay(renderer);
		add(renderingWindow.getDrawable());
		renderingWindow.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				shutdown();
			}
		});
		
		setSize(640,480);
		setVisible(true);
	}
	
	static public void main(String args[]){
		new Malcoln();
	}
}
