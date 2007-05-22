package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRViewpoint extends VRNode{
	
	static public final String VRNODENAME = "Viewpoint";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public float		fieldOfView		= 0.785398f;
	public String		description		= "";
	public Vector3f		position		= new Vector3f(0,0,10);
	public Vector4f		orientation		= new Vector4f(0,0,1,0);
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(null,this);
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s = parser.st.sval;
			parser.print(s);
			if (s.equals("orientation"))		orientation	= parser.readVector4f();
			else if (s.equals("position"))		position	= parser.readVector3f();
			else if (s.equals("description"))	description	= parser.readString();
			else if (s.equals("fieldOfView"))	fieldOfView	= parser.readFloat();			
		}
		return this;
	}
}