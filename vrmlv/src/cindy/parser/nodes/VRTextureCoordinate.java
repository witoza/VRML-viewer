package cindy.parser.nodes;

import java.io.IOException;
import java.util.Iterator;

import javax.vecmath.Vector2f;

import cindy.parser.CVector2fArray;
import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRTextureCoordinate extends VRNode{
	
	static public final String VRNODENAME = "TextureCoordinate";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public Vector2f[]	point;
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken();//{			
		parser.st.nextToken();//point
		point = CVector2fArray.read(parser);
		parser.st.nextToken();//}
		return this;
	}
}
