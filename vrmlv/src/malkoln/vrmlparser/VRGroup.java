package malkoln.vrmlparser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

public class VRGroup extends VRNode{
	
	public LinkedList children=new LinkedList();
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(children,this);
	}

	public String toString(){
		if (name!=null)
			return name;
		return "Group";		
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s+"\n");
			if (s.equals("children"))	children=parser.readNodes(this);
		}
		return this;
	}


}