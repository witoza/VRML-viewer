package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRMLParserException;
import cindy.parser.VRNode;

public class VRCone extends VRNode{

	static public final String VRNODENAME = "Cone";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public float bottomRadius	 	= 1;
	public float height				= 2;
	public boolean side				= true;
	public boolean bottom 			= true;
	
	
	public VRNode clone(VRMLNodeFactory nf) {
		VRCone nd = (VRCone) nf.createCone();
		nd.model = model;
		nd.name = name;
		
		nd.bottomRadius = bottomRadius;
		nd.height = height;
		nd.side = side;
		nd.bottom = bottom;
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
			if (s.equals("bottomRadius"))	bottomRadius = parser.readFloat();
			else if (s.equals("height"))	height = parser.readFloat();		
			else if (s.equals("side"))		side = parser.readBoolean();
			else if (s.equals("bottom"))	bottom = parser.readBoolean();
			else {
				throw new VRMLParserException(s + " phrase not possible in "+ getNodeInternalName() + " node! ");
			}
		}
		return this;
	}


}
