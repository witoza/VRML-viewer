package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

public class VRCylinder extends VRNode{
	
	public boolean bottom	= true;
	public float height		= 2.0f;
	public float radius		= 1.0f;
	public boolean side		= true;
	public boolean top		= true;
	
	public Iterator iterator() {
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
	
	public String toString(){
		if (name!=null)
			return name;
		return "Cylinder";
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
		}
		return this;
	}
}
