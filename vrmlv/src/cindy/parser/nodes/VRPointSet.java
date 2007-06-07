package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRPointSet extends VRNode{

	static public final String VRNODENAME = "PointSet";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
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

	public VRNode clone(VRMLNodeFactory nf) {
		VRPointSet ps = (VRPointSet)nf.createPointSet();
		ps.coord = coord;
		ps.color = color;
		return ps;
	}
}
