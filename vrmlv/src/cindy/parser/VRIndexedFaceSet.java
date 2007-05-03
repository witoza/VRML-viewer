package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import cindy.parser.IntArray;


public class VRIndexedFaceSet extends VRNode{
	
	public boolean 			ccw				= true;
	public VRCoordinate		color;
	public int[]			colorIndex;
	public boolean 			colorPerVertex	= true;
	public boolean 			convex			= true;
	public int[]			coordIndex;
	public boolean 			normalPerVertex	= true;
	public boolean			solid			= true;
	public VRCoordinate		coord;
		
	public Iterator iterator(){
		LinkedList args=new LinkedList();
		if (color!=null) args.add(color);
		if (coord!=null) args.add(coord);
		if (args.size()==0)
			args=null;
		return new VRMLDefaultTreeDFSIterator(args,this);
	}	
	
	public String toString(){
		if (name!=null)
			return name;
		return "IndexedFaceSet";		
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		boolean isCordIndex=false;
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("ccw"))					ccw=parser.readBoolean();
			else if (s.equals("colorIndex"))		colorIndex=IntArray.read(parser);
			else if (s.equals("colorPerVertex"))	colorPerVertex=parser.readBoolean();
			else if (s.equals("convex"))			convex=parser.readBoolean();
			else if (s.equals("coordIndex"))		{isCordIndex=true; coordIndex=IntArray.read(parser); }
			else if (s.equals("normalPerVertex"))	normalPerVertex=parser.readBoolean();
			else if (s.equals("solid"))				solid=parser.readBoolean();
			else if (s.equals("coord"))				coord=(VRCoordinate)parser.readNode(this);
			else if (s.equals("color"))				color=(VRCoordinate)parser.readNode(this);
		}
		if (isCordIndex && coordIndex==null)
			return null;
		return this;
	}
}