package cindy.drawable.nodes;

import java.util.Iterator;

import javax.media.opengl.GL;

import cindy.core.BoundingBox;
import cindy.drawable.DisplayOptions;
import cindy.drawable.IDrawable;
import cindy.drawable.NodeSettings;
import cindy.parser.VRNode;
import cindy.parser.nodes.VRTransform;

public class VRDTransform extends VRTransform implements IDrawable{

	public void draw(DisplayOptions dispOpt) {
		if (children==null) return ;
		GL gl = dispOpt.gl;
		if (getNodeSettings()!=null && getNodeSettings().drawBBox){
			getNodeSettings().boundingBox.draw(dispOpt);
		}
		
		gl.glPushMatrix();
		
			//in ogl terms:
			gl.glTranslatef(translation.x,translation.y,translation.z);
			gl.glTranslatef(center.x,center.y,center.z);
			gl.glRotatef(rotation.w*180/(float)Math.PI,rotation.x,rotation.y,rotation.z);	
			gl.glRotatef(scaleOrientation.w*180/(float)Math.PI,scaleOrientation.x,scaleOrientation.y,scaleOrientation.z);
			gl.glScalef(scale.x,scale.y,scale.z);
			gl.glRotatef(-scaleOrientation.w*180/(float)Math.PI,scaleOrientation.x,scaleOrientation.y,scaleOrientation.z);
			gl.glTranslatef(-center.x,-center.y,-center.z);
								
			
		
		//	gl.glMultMatrixf(getTransformMatrix().getMatrix(),0);			
			Iterator<IDrawable> iter = (Iterator<IDrawable>) children.iterator();
			while(iter.hasNext()){
				iter.next().draw(dispOpt);
			}
		gl.glPopMatrix();		
	}

	public int numOfDrawableChildren() {
		return children.size();
	}
	
	public VRNode getNthChild(int n) {
		return (VRNode)children.get(n);
	}
	
	NodeSettings ns;
	public NodeSettings getNodeSettings() {
		if (children==null) return null;
		if (ns == null){
			ns = new NodeSettings();
			ns.boundingBox = new BoundingBox();
			//compute bounding box
			
			Iterator<IDrawable> iter = (Iterator<IDrawable>) children.iterator();
			while(iter.hasNext()){
				NodeSettings chilNodeSeetings = iter.next().getNodeSettings();
				if (chilNodeSeetings!=null){
					if (chilNodeSeetings.boundingBox.isValid()){
						ns.boundingBox.mix(getTransformMatrix().VMultiply(chilNodeSeetings.boundingBox.getMin()));
						ns.boundingBox.mix(getTransformMatrix().VMultiply(chilNodeSeetings.boundingBox.getMax()));
					}
				}
			}
		}
		return ns;
	}

}
