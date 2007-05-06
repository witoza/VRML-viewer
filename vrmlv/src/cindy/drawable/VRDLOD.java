package cindy.drawable;

import java.util.Iterator;

import javax.media.opengl.GL;

import cindy.core.BoundingBox;
import cindy.parser.VRLOD;
import cindy.parser.VRNode;

public class VRDLOD extends VRLOD implements IDrawable{

	public void draw(DisplayOptions dispOpt) {		
		GL gl = dispOpt.gl;
		if (getNodeSeetings().drawBBox){
			getNodeSeetings().boundingBox.draw(dispOpt);
		}

		gl.glPushMatrix();
	
		//TODO: check if this should be transformed ex: gl.glTranslatef(center.x,center.y,center.z);			
			Iterator<IDrawable> iter = (Iterator<IDrawable>) level.iterator();
			while(iter.hasNext()){
				iter.next().draw(dispOpt);
			}
		gl.glPopMatrix();
	}

	public int numOfDrawableChildren() {
		return level.size();
	}
	
	public VRNode getNthChild(int n) {
		return (VRNode)level.get(n);
	}

	NodeSettings ns;
	public NodeSettings getNodeSeetings() {
		if (ns == null){
			ns = new NodeSettings();
			ns.boundingBox = new BoundingBox();
			//compute bounding box
			Iterator<IDrawable> iter = (Iterator<IDrawable>) level.iterator();
			while(iter.hasNext()){
				NodeSettings chilNodeSeetings = iter.next().getNodeSeetings();
				if (chilNodeSeetings!=null && chilNodeSeetings.boundingBox.isValid()){
					ns.boundingBox.mix(chilNodeSeetings.boundingBox);
				}
			}
		}
		return ns;
	}
}
