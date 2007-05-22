package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRGroup extends VRNode{
	
	static public final String VRNODENAME = "Group";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public LinkedList children=new LinkedList();
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(children,this);
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