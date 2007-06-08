package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import javax.vecmath.Vector3f;

import cindy.parser.FloatArray;
import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRLOD extends VRNode{
	
	static public final String VRNODENAME = "LOD";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public Vector3f		center=new Vector3f(0,0,0);
	public float[]		range;
	public LinkedList	level;
	
	public VRNode clone(VRMLNodeFactory nf) {
		VRLOD nd = (VRLOD)nf.createLOD();
		nd.model = model;
		nd.name = name;
		
		nd.center = center;
		nd.range = range;
		nd.level = level;
		return nd;
	}
	
	public Iterator iterator(){		
		return new VRMLDefaultTreeDFSIterator(level,this);
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("center"))			center=parser.readVector3f();
			else if (s.equals("range"))		range=FloatArray.read(parser);
			else if (s.equals("level"))		level=parser.readNodes(this);
		}
		return this;
	}


}