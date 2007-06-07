package cindy.parser.nodes;

import java.io.IOException;
import java.util.Iterator;

import javax.vecmath.Vector3f;

import cindy.parser.CVector3fArray;
import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRCoordinate extends VRNode{
	
	static public final String VRNODENAME = "Coordinate";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public Vector3f[]	coord;
	
	public VRNode clone(VRMLNodeFactory nf) {
		VRCoordinate wc = (VRCoordinate)nf.createCoordinate();
		wc.coord = coord;
		return wc;
	}
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken();//{			
		parser.st.nextToken();//point or color
		coord = CVector3fArray.read(parser);
		parser.st.nextToken();//}
		return this;
	}	
}