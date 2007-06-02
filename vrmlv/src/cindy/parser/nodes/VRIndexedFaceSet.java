package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;

import cindy.parser.IntArray;
import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;


public class VRIndexedFaceSet extends VRNode{
	
	static public final String VRNODENAME = "IndexedFaceSet";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	
	public VRCoordinate		color;
	public VRCoordinate		coord;
	public VRNormal			normal;//TODO
	public VRTextureCoordinate texCoord;//TODO	
	public boolean 			ccw				= true;	
	public int[]			colorIndex;	
	public boolean 			colorPerVertex	= true;
	public boolean 			convex			= true;	
	public int[]			coordIndex;
	public float 			creaseAngle		= 0;//TODO
	public int[]			normalIndex;//TODO
	public boolean 			normalPerVertex	= true;
	public boolean			solid			= true;
	public int[]			texCoordIndex;//TODO
	

	
	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		boolean isCordIndex=false;
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			
			if (s.equals("color"))				color=(VRCoordinate)parser.readNode(this);
			else if (s.equals("coord"))			coord=(VRCoordinate)parser.readNode(this);
			else if (s.equals("normal"))		normal=(VRNormal)parser.readNode(this);
			else if (s.equals("texCoord"))		texCoord=(VRTextureCoordinate)parser.readNode(this);			
			else if (s.equals("ccw"))			ccw=parser.readBoolean();
			else if (s.equals("colorIndex"))	colorIndex=IntArray.read(parser);
			else if (s.equals("colorPerVertex"))colorPerVertex=parser.readBoolean();
			else if (s.equals("convex"))		convex=parser.readBoolean();
			else if (s.equals("coordIndex"))	coordIndex=IntArray.read(parser);			
			else if (s.equals("creaseAngle"))	creaseAngle=parser.readFloat();			
			else if (s.equals("normalIndex"))	normalIndex=IntArray.read(parser);			
			else if (s.equals("normalPerVertex"))normalPerVertex=parser.readBoolean();
			else if (s.equals("solid"))			solid=parser.readBoolean();
			else if (s.equals("texCoordIndex"))	texCoordIndex=IntArray.read(parser);
		}
		if (isCordIndex && coordIndex==null)
			return null;
		return this;
	}
	
	
	public Iterator iterator(){
		LinkedList args=new LinkedList();
		if (color!=null) args.add(color);
		if (coord!=null) args.add(coord);		
		if (normal!=null) args.add(normal);
		if (texCoord!=null) args.add(texCoord);
		if (args.isEmpty()){
			args=null;
		}
		return new VRMLDefaultTreeDFSIterator(args,this);
	}	
}