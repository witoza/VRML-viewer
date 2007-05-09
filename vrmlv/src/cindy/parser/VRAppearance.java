package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

public class VRAppearance extends VRNode{
	
	static public final String VRNODENAME = "Apperance";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public VRMaterial material;
	
	public Iterator iterator(){
		LinkedList args = new LinkedList();
		args.add(material);
		return new VRMLDefaultTreeDFSIterator(args,this);
	}	

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("material")) material=(VRMaterial) parser.readNode(this);
		}
		return this;
	}

}