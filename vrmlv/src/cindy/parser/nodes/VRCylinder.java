package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRMLParserException;
import cindy.parser.VRNode;

public class VRCylinder extends VRNode{
	
	static public final String VRNODENAME = "Cylinder";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public boolean bottom	= true;
	public float height		= 2.0f;
	public float radius		= 1.0f;
	public boolean side		= true;
	public boolean top		= true;
	
	public VRNode clone(VRMLNodeFactory nf) {
		VRCylinder nd = (VRCylinder)nf.createCylinder();
		nd.model = model;
		nd.name = name;
		
		nd.bottom = bottom;
		nd.height = height;
		nd.radius = radius;
		nd.side = side;
		nd.top = top;
		return nd;
	}

	
	public Iterator iterator() {
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("bottom"))			bottom = parser.readBoolean();
			else if (s.equals("height"))	height = parser.readFloat();
			else if (s.equals("radius"))	radius = parser.readFloat();			
			else if (s.equals("side"))		side = parser.readBoolean();
			else if (s.equals("top"))		top = parser.readBoolean();
			else {
				throw new VRMLParserException(s + " phrase not possible in "+ getNodeInternalName() + " node! ");
			}
		}
		return this;
	}
}
