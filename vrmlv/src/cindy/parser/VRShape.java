package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

public class VRShape extends VRNode{
	
	public VRAppearance		appearance;
	public VRNode			geometry;
	
	public Iterator iterator(){
		LinkedList args=new LinkedList();
		if (appearance!=null) args.add(appearance);
		if (geometry!=null) args.add(geometry);
		if (args.size()==0)
			args=null;
		return new VRMLDefaultTreeDFSIterator(args,this);
	}
	
	public String toString(){
		if (name!=null)
			return name;
		if (geometry!=null && geometry.name!=null)
			return geometry.name;
		return "Shape";
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
			if (s.equals("appearance"))		appearance	=( VRAppearance)parser.readNode(this);
			else if (s.equals("geometry"))	geometry	= parser.readNode(this);
		}
		return this;
	}
}