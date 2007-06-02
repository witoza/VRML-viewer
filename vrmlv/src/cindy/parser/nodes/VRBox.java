package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import javax.vecmath.Vector3f;

import org.apache.log4j.Logger;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRBox extends VRNode{

	private static Logger _LOG = Logger.getLogger(VRBox.class);
	
	static public final String VRNODENAME = "Box";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public Vector3f		size = new Vector3f(2,2,2);
//	public VRCoordinate coord;
//	public VRCoordinate color;
	
	public Iterator iterator() {
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
		
	public VRNode read(VRMLNodeParser parser) throws IOException {
		_LOG.info("read started");
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("size"))			size=parser.readVector3f();
		}
		return this;
	}

}
