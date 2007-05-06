package cindy.drawable;

import java.util.Iterator;

import cindy.core.BoundingBox;
import cindy.parser.VRGroup;
import cindy.parser.VRNode;

public class VRDGroup extends VRGroup implements IDrawable{

	NodeSettings ns;
	
	public void draw(DisplayOptions dispOpt) {
		if (getNodeSeetings().drawBBox){
			getNodeSeetings().boundingBox.draw(dispOpt);
		}
		
		Iterator<IDrawable> iter = (Iterator<IDrawable>) children.iterator();
		while(iter.hasNext()){
			iter.next().draw(dispOpt);
		}
	}

	public int numOfDrawableChildren() {
		return children.size();
	}
	
	public VRNode getNthChild(int n) {
		return (VRNode)children.get(n);
	}

	public NodeSettings getNodeSeetings() {
		if (ns == null){
			ns = new NodeSettings();
			//create proper bounding box
			ns.boundingBox = new BoundingBox();
			Iterator<IDrawable> iter = (Iterator<IDrawable>) children.iterator();
			while(iter.hasNext()){
				NodeSettings chilNodeSeetings = iter.next().getNodeSeetings();
				if (chilNodeSeetings!=null){
					ns.boundingBox.mix(chilNodeSeetings.boundingBox);
				}
			}
		}
		return ns;
	}

}
