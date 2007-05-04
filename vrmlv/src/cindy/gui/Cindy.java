package cindy.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;

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
	
	private void readInFile(final String fileChosen){
		_LOG.info("reading file: " + fileChosen);
		final VRMLModel model = new VRMLModel();
		try{
			is = new ProgressMonitorInputStream(Cindy.this, "Reading data", new FileInputStream(new File(fileChosen)));
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					is.getProgressMonitor().setMillisToDecideToPopup(0);
					is.getProgressMonitor().setMillisToPopup(0);
					is.getProgressMonitor().setProgress(1);					
				}				
			});
			
			model.readModel(is, new VRDNodeFactory());
			renderer.setModel(model);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Cindy.this.setTitle(appName + "  /" + fileChosen + "/");
					tree.setModel(new JTreeModelFromVrmlModel(model.getMainGroup()));
				}
			});
		} catch (IOException e1) {
			_LOG.warn("error while reading data");
			e1.printStackTrace();
		}
	}
	private JTree tree = new JTree();
	private ProgressMonitorInputStream is;	
	
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
		
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		tree.setModel(null);	
		tree.setCellRenderer(new VRMLObjectsTreeCellRenderer());
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileDialog = new JFileChooser();
				fileDialog.setDialogType(JFileChooser.OPEN_DIALOG);
				fileDialog.setDialogTitle("Select .wrl file to open");
				int fd = fileDialog.showOpenDialog(Cindy.this);
				if (fd != JFileChooser.APPROVE_OPTION){
					return ;
				}
				String fileChosen = fileDialog.getCurrentDirectory()
						.getAbsolutePath()
						+ File.separator
						+ fileDialog.getSelectedFile().getName();
				readInFile(fileChosen);				
			}
		});
		fileMenu.add(openMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				shutdown();
				System.exit(0);
			}
			
		});
		fileMenu.add(exitMenuItem);
		
		menubar.add(fileMenu);
		setJMenuBar(menubar);
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new BorderLayout());				
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);		    		   
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(170);
		centerPanel.add(renderingWindow.getDrawable());
		leftPanel.add(tree);
		add(splitPane);
		setSize(640+170,480);
		setVisible(true);
		renderingWindow.start();
		
		//TODO: 
		new Thread(){
			public void run(){
				String outputWRL = "c:\\__vrml\\2006_01_16\\problem1\\problem1.wrl";
				outputWRL = "C:\\__vrml\\2006_01_16\\coil_2.wrl"; 
				//outputWRL = "C:\\__vrml\\2006_01_16\\CT_res_2.wrl";
				readInFile(outputWRL);
			}
		}.start();
	}
	
	static public void main(String args[]){
		new Cindy();
	}
}
