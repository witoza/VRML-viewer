package cindy.parser;

import java.io.IOException;
import java.util.Iterator;

import javax.vecmath.Vector3f;

import cindy.parser.CVector3fArray;


public class VRCoordinate extends VRNode{
	
	public Vector3f[]	coord;
	
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