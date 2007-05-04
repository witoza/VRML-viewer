package cindy.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;


import org.apache.log4j.Logger;

import cindy.core.GLDisplay;
import cindy.core.LoggerHelper;
import cindy.core.NativesHelper;
import cindy.core.VRMLRenderer;
import cindy.drawable.VRDNodeFactory;
import cindy.parser.VRMLModel;

public class Cindy extends JFrame{
	
	private static final Logger _LOG = Logger.getLogger(Cindy.class);
	
	static public String appName = "'Cindy' VRML Browser 0.01";
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
	
	public Cindy(){
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
		
		String outputWRL = "c:\\__vrml\\2006_01_16\\problem1\\problem1.wrl";
		outputWRL = "C:\\__vrml\\2006_01_16\\coil_2.wrl"; 
		outputWRL = "C:\\__vrml\\2006_01_16\\CT_res_2.wrl";
		
		VRMLModel model = new VRMLModel();
	   	try {
			model.readModel(outputWRL, new VRDNodeFactory());
			renderer.setModel(model);
		} catch (IOException e1) {
			e1.printStackTrace();
		}	   	
	}
	
	static public void main(String args[]){
		new Cindy();
	}
}
