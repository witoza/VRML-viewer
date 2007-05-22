package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeParser;
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
		}
		return this;
	}
}
