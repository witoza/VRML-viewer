package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRSphere extends VRNode{
	
	static public final String VRNODENAME = "Sphere";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public float		radius=1.0f;
	
	public VRNode clone(VRMLNodeFactory nf){
		VRSphere nd = (VRSphere)nf.createShpere();
		nd.model = model;
		nd.name = name;
		
		nd.radius = radius;
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
			if (s.equals("radius"))			radius = parser.readFloat();
		}
		return this;
	}
}
