package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import javax.vecmath.Vector3f;

public class VRLOD extends VRNode{
	
	static public final String VRNODENAME = "LOD";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public Vector3f		center=new Vector3f(0,0,0);
	public float[]		range;
	public LinkedList	level;
	
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