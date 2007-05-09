package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

public class VRSphere extends VRNode{
	
	public float		radius=1.0f;
	
	public Iterator iterator() {
		return new VRMLDefaultTreeDFSIterator(null,this);
	}
	
	public String toString(){
		if (name!=null)
			return name;
		return "Sphere";
	}
	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("radius"))			radius=parser.readFloat();
		}
		return this;
	}
}
