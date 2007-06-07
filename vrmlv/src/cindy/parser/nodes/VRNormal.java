package cindy.parser.nodes;

import java.io.IOException;
import java.util.Iterator;

import javax.vecmath.Vector3f;

import cindy.parser.CVector3fArray;
import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRNormal extends VRNode{

	static public final String VRNODENAME = "Normal";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public Vector3f[]	vector;
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken();//{			
		parser.st.nextToken();//vector
		vector = CVector3fArray.read(parser);
		parser.st.nextToken();//}
		return this;
	}
	
	public VRNode clone(VRMLNodeFactory nf) {
		VRNormal nl = (VRNormal)nf.createNormal();
		nl.vector = vector;
		return nl;
	}
}
