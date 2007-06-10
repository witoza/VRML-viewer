package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRMLParserException;
import cindy.parser.VRNode;

public class VRShape extends VRNode{
	
	static public final String VRNODENAME = "Shape";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public VRAppearance		appearance = new VRAppearance();
	public VRNode			geometry;
	
	public Iterator iterator(){
		LinkedList args=new LinkedList();
		if (appearance!=null) args.add(appearance);
		if (geometry!=null) args.add(geometry);
		if (args.size()==0)
			args=null;
		return new VRMLDefaultTreeDFSIterator(args,this);
	}
	
	public VRNode getNthDrawableNode(int index){
		if (index<0 || index>1){
			throw new RuntimeException("child index out of range");
		}
		return geometry;
	}	

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s = parser.st.sval;
			parser.print(s);
			if (s.equals("appearance"))		appearance	= (VRAppearance)parser.readNode(this);
			else if (s.equals("geometry"))	geometry	= parser.readNode(this);
			else {
				throw new VRMLParserException(s + " phrase not possible in "+ getNodeInternalName() + " node! ");
			}
		}
		return this;
	}

	public VRNode clone(VRMLNodeFactory nf) {
		VRShape nd = (VRShape)nf.createShape();
		nd.model = model;
		nd.name = name;
		
		nd.appearance = appearance;
		nd.geometry = geometry;
		return nd;
	}
}