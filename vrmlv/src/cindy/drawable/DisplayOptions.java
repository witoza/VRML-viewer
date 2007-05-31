package cindy.drawable;

import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class DisplayOptions {
	
	public static class SelectingOptions{	
		public LinkedList<IDrawable> selectedNodes = new LinkedList<IDrawable>();
		
		public void clearSelectedNodes(){
			for (IDrawable node : selectedNodes){
				NodeSettings ns = node.getNodeSettings();
				if (ns!=null){
					ns.drawBBox = false;
				}
			}
			selectedNodes.clear();
		}
		public void selectSingleNode(IDrawable node){
			clearSelectedNodes();
			selectAnotherNode(node);				
		}
		
		public void addAnotherNode(IDrawable node){
			NodeSettings ns = node.getNodeSettings();
			if (ns!=null){
				ns.drawBBox = true;
			}
			if (!selectedNodes.contains(node)){
				selectedNodes.add(node);
			}
		}	
		
		public void selectAnotherNode(IDrawable node){
			NodeSettings ns = node.getNodeSettings();
			if (ns!=null){
				ns.drawBBox = true;
			}
			if (selectedNodes.contains(node)){
				if (ns!=null){
					ns.drawBBox = false;
				}
				selectedNodes.remove(node);
			}else{
				selectedNodes.add(node);
			}
		}	
	}
	
	public SelectingOptions selectedNodes=new SelectingOptions();
	
	public static class PickingOptions{
		static final int MAX_OBJECT_COUNT=1000;
		
		private int i;
		private Object[] pickingObjects=new Object[MAX_OBJECT_COUNT];
		
		public int add(Object obj){		
			i++;
			if (i>=MAX_OBJECT_COUNT)
				throw new RuntimeException("too many object to handle picking");
			pickingObjects[i]=obj;
			return i;
		}
		public Object get(int i){
			return pickingObjects[i];
		}	
		public void clear(){
			for (;i!=-1; i--)
				pickingObjects[i]=null;
		}	
	}	
	public PickingOptions pickingOptions=new PickingOptions();	
	
	public GL gl;
	public GLU glu;
	
	public DisplayOptions(GL gl, GLU glu) {
		super();
		this.gl = gl;
		this.glu = glu;
	}
	
}
