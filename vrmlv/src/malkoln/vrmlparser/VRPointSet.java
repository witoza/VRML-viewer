package malkoln.vrmlparser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

public class VRPointSet extends VRNode{

	public VRCoordinate coord;
	public VRCoordinate color;
	
	public Iterator iterator(){
		LinkedList args = new LinkedList();
		if (coord!=null) args.add(coord);
		if (color!=null) args.add(color);
		if (args.size()==0){
			args = null;
		}
		return new VRMLDefaultTreeDFSIterator(args,this);
	}
	
	public String toString(){
		if (name!=null)
			return name;
		return "PointSet";		
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("coord"))		coord = (VRCoordinate)parser.readNode(this);
			else if (s.equals("color"))	color = (VRCoordinate)parser.readNode(this);
		}
		return this;
	}
}
