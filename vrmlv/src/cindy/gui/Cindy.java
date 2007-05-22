package cindy.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import cindy.core.GLDisplay;
import cindy.core.LoggerHelper;
import cindy.core.NativesHelper;
import cindy.core.VRMLRenderer;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.drawable.nodes.VRDNodeFactory;
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
			is = new ProgressMonitorInputStream(Cindy.this, "Reading file " + fileChosen, new FileInputStream(new File(fileChosen)));
/*			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					is.getProgressMonitor().setMillisToDecideToPopup(0);
					is.getProgressMonitor().setMillisToPopup(0);
					is.getProgressMonitor().setProgress(1);					
				}				
			});
*/			
			model.readModel(is, new VRDNodeFactory());
			renderer.setModel(model);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Cindy.this.setTitle(appName + "  /" + fileChosen);
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
	
	private JComboBox renderingModeChanger;
	
	private boolean guiBeingUpdate = false;
	
	public Cindy(final String inputWRL){
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
		
		final LinkedList<IDrawable> selected = new LinkedList<IDrawable>();
		
		tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				guiBeingUpdate = true;
				TreePath[] paths = ((JTree)e.getSource()).getSelectionPaths();
				if (paths!=null){
					for (int i=0; i!=paths.length; i++){
						Object obj = paths[i].getLastPathComponent();
						_LOG.info(""+obj.toString());
						for (Iterator<IDrawable> iter = selected.iterator(); iter.hasNext(); ){
							iter.next().getNodeSeetings().drawBBox = false;
						}
						selected.clear();
						NodeSettings ns = ((IDrawable)obj).getNodeSeetings();
						if (ns!=null){
							ns.drawBBox = true;
							switch(ns.rendMode){
								case -1:
									renderingModeChanger.setSelectedItem("NONE");
									break;
								case GL.GL_FILL:
									renderingModeChanger.setSelectedItem("GL_FILL");
									break;
								case GL.GL_LINE:
									renderingModeChanger.setSelectedItem("GL_LINE");
									break;
								case GL.GL_POINT:
									renderingModeChanger.setSelectedItem("GL_POINT");
									break;
								default:
									_LOG.warn("wrong rendering type");									
							}
							selected.add((IDrawable)obj);
						}else{
							renderingModeChanger.setSelectedIndex(-1);
						}
					}
				}
				guiBeingUpdate = false;
			}});
		//tree.setRootVisible(false);
	    JScrollPane objectsChangePanel = new JScrollPane(tree);
		
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileDialog = new JFileChooser();
				fileDialog.setFileFilter(new WRLFileFilter());
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
		
		
		JPanel nodeSettingsPanel = new JPanel();
		nodeSettingsPanel.setLayout(new BoxLayout(nodeSettingsPanel,BoxLayout.Y_AXIS));
		nodeSettingsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Node settings"));
		
		
		renderingModeChanger = new JComboBox(new String[]{"NONE", "GL_FILL","GL_LINE","GL_POINT"});
		renderingModeChanger.setSelectedIndex(-1);
		renderingModeChanger.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if (guiBeingUpdate){
					return ;
				}
				JComboBox src = (JComboBox)e.getSource();
				if (src.getSelectedIndex()<0){
		    		return;
		    	}
		    	_LOG.info("updating renderig mode");		    	
		    	String str = (String)src.getSelectedItem();
		    	int flag = 0;
		    	if (str.equals("NONE")) flag = -1;
		    	if (str.equals("GL_FILL")) flag = GL.GL_FILL;
		    	if (str.equals("GL_LINE")) flag = GL.GL_LINE;
		    	if (str.equals("GL_POINT")) flag = GL.GL_POINT;
		    	
		    	if (!selected.isEmpty()){
			    	IDrawable first = selected.getFirst(); 
			    	if (first!=null){
			    		first.getNodeSeetings().rendMode = flag;		    	
			    	}
		    	}
			}
			
		});
		JPanel renderingMode = new JPanel(new BorderLayout());
		renderingMode.setBorder(BorderFactory.createTitledBorder("rendering mode"));		
		renderingMode.add(renderingModeChanger);	
		
		
		//JCheckBox showBoundingBoxes = new JCheckBox("Show bounding box", true);
		
		nodeSettingsPanel.add(renderingMode);
		JPanel tmp1 = new JPanel(new BorderLayout());
		//tmp1.add(showBoundingBoxes);
		nodeSettingsPanel.add(tmp1);
		leftPanel.add(nodeSettingsPanel, BorderLayout.NORTH);
		leftPanel.add(objectsChangePanel, BorderLayout.CENTER);
		add(splitPane);
		
		setSize(640+170,480);
		setVisible(true);
		renderingWindow.start();
		
		//TODO: 
		new Thread(){
			public void run(){
				//String inputWRL = "c:\\__vrml\\2006_01_16\\problem1\\problem1.wrl";
				//inputWRL = "C:\\__vrml\\2006_01_16\\coil_2.wrl"; 
				//outputWRL = "C:\\__vrml\\2006_01_16\\CT_res_2.wrl";
				if (inputWRL != null) {
					//_LOG.info("input file: " + inputWRL);
					readInFile(inputWRL);
				} else {
					_LOG.info("no input file specified");
				}
			}
		}.start();
	}
	
	static public void main(String args[]){
		String inputFile = null;
		if (args.length > 0) inputFile = args[0];
		new Cindy(inputFile);
	}
}

class WRLFileFilter extends FileFilter {

	@Override
	public boolean accept(File arg0) {
		String name = arg0.getName().toLowerCase();
		return (arg0.isDirectory() || name.endsWith(".wrl"));
	}

	@Override
	public String getDescription() {
		return "WRL files";
	}
	
}
