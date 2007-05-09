package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

public class VRWorldInfo extends VRNode{

	static public final String VRNODENAME = "WorldInfo";
	
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public String []info ;
	public String title = ""; 
	
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
			/*if (s.equals("center"))			center=parser.readVector3f();
			else if (s.equals("range"))		range=FloatArray.read(parser);
			else if (s.equals("level"))		level=parser.readNodes(this);*/
		}
		return this;	
	}
	
}
