package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRMLParserException;
import cindy.parser.VRNode;

public class VRTransform extends VRNode{
	

	static public final String VRNODENAME = "Transform";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	/**
	 * Returns Transformation Matrix that is to be applied to all children of Transform node
	 * @return Transformation Matrix 
	 */
	public cindy.core.Matrix4f getTransformMatrix(){
				
		cindy.core.Matrix4f m = new cindy.core.Matrix4f();	
		m.LoadIdent();
		m.Translate(-center.x,-center.y,-center.z);
		m.Rotate(-scaleOrientation.w*180/(float)Math.PI,scaleOrientation.x,scaleOrientation.y,scaleOrientation.z);
		m.Scale(scale.x,scale.y,scale.z);
		m.Rotate(scaleOrientation.w*180/(float)Math.PI,scaleOrientation.x,scaleOrientation.y,scaleOrientation.z);
		m.Rotate(rotation.w*180.0f/(float)Math.PI,rotation.x,rotation.y,rotation.z);
		m.Translate(center.x,center.y,center.z);
		m.Translate(translation.x,translation.y,translation.z);
		return m;
	}
	
	public Vector3f center				= new Vector3f(0,0,0);
	public Vector3f scale				= new Vector3f(1,1,1);
	public Vector3f translation			= new Vector3f(0,0,0);
	public Vector4f rotation			= new Vector4f(0,0,1,0);
	public Vector4f scaleOrientation	= new Vector4f(0,0,1,0);
	
	public LinkedList children;
	

	public VRNode clone(VRMLNodeFactory nf) {
		VRTransform nd = (VRTransform)nf.createTransform();
		nd.model = model;
		nd.name = name;
		
		nd.center = center;
		nd.scale = scale;
		nd.translation = translation;
		nd.rotation = rotation;
		nd.scaleOrientation = scaleOrientation;
		nd.children = children;
		return nd;
	}
	
		
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(children, this);
	}
	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		boolean anyTrans = false;
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("center"))					{anyTrans=true; center = parser.readVector3f();}
			else if (s.equals("scale"))				{anyTrans=true; scale = parser.readVector3f();}
			else if (s.equals("rotation"))			{anyTrans=true; rotation = parser.readVector4f();}
			else if (s.equals("scaleOrientation"))	{anyTrans=true; scaleOrientation = parser.readVector4f();}
			else if (s.equals("translation"))		{anyTrans=true; translation = parser.readVector3f();}
			else if (s.equals("children"))			children = parser.readNodes(this);
			else if (s.equals("ROUTE"))				parser.skipRoute();
			else {
				throw new VRMLParserException(s + " phrase not possible in "+ getNodeInternalName() + " node! ");
			}
		}
		if (!anyTrans && children!=null && children.size()==1){
			return (VRNode)children.get(0);
		}
		return this;
	}
}