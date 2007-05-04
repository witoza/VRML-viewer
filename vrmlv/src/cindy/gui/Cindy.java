package cindy.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;

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
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				shutdown();
			}
		});

		new Thread(){
			public void run(){
				try {
					String outputWRL = "c:\\__vrml\\2006_01_16\\problem1\\problem1.wrl";
					outputWRL = "C:\\__vrml\\2006_01_16\\coil_2.wrl"; 
					//outputWRL = "C:\\__vrml\\2006_01_16\\CT_res_2.wrl";
					
					VRMLModel model = new VRMLModel();
					model.readModel(outputWRL, new VRDNodeFactory());
					renderer.setModel(model);
					renderingWindow.start();
					
					JPanel leftPanel = new JPanel(new BorderLayout());
					JPanel centerPanel = new JPanel(new BorderLayout());
					
					
					
					JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);		    		   
					splitPane.setOneTouchExpandable(true);
					splitPane.setDividerLocation(170);
					centerPanel.add(renderingWindow.getDrawable());
					
					JTree tree = new JTree(new JTreeModelFromVrmlModel(model.getMainGroup()));
					tree.setCellRenderer(new VRMLObjectsTreeCellRenderer());
					leftPanel.add(tree);
					
					add(splitPane);
					setSize(640+170,480);
					setVisible(true);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}	   
			}
		}.start();
	   	
	}
	
	static public void main(String args[]){
		new Cindy();
	}
}
