package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.LinkedList;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.log4j.Logger;

public class VRMLNodeParser{

	private Logger logger = Logger.getLogger(VRMLNodeParser.class);
	
	private final VRMLNodeFactory nodeFactory;
	public final VRMLModel parent;
	
	public VRMLNodeParser(VRMLModel parent, VRMLNodeFactory nodeFactory){
		this.parent = parent;
		this.nodeFactory = nodeFactory;
	}
	
	public StreamTokenizer st;
	public HashMap<String, VRNode> strToRef = new HashMap<String, VRNode>();
	
	private VRNode createNode(String s){
		 if (s.equals("Transform"))		return nodeFactory.createTransform();
		 if (s.equals("Shape"))			return nodeFactory.createShape();
		 if (s.equals("Viewpoint"))		return nodeFactory.createViewpoint();
		 if (s.equals("LOD"))			return nodeFactory.createLOD();
		 if (s.equals("Group"))			return nodeFactory.createGroup();
		 if (s.equals("Text"))			return nodeFactory.createText();
		 if (s.equals("IndexedFaceSet"))return nodeFactory.createIndexedFaceSet();
		 if (s.equals("IndexedLineSet"))return nodeFactory.createIndexedLineSet();
		 if (s.equals("PointSet"))		return nodeFactory.createPointSet();
		 if (s.equals("Coordinate"))	return nodeFactory.createCoordinate();
		 if (s.equals("Color"))			return nodeFactory.createColor();
		 if (s.equals("Material"))		return nodeFactory.createMaterial();
		 if (s.equals("Appearance"))	return nodeFactory.createAppearance();
		 return null;
	}
	
	public void setTokenizer(StreamTokenizer str){
		st = str;
	}
	
	void skip(char openingBrack, char closingBrack) throws IOException{
		int hm=0;
		while(st.nextToken()!=StreamTokenizer.TT_EOF){
			switch(st.ttype){
				case StreamTokenizer.TT_NUMBER:
				case StreamTokenizer.TT_WORD:
					break;
				default:
					char zn=(char)st.ttype;
					if (zn==openingBrack)
						hm++;
					if (zn==closingBrack)
						hm--;
					if (hm==0)
						return;
					break;
			}
		}
	}
	
	public Vector3f readVector3f() throws IOException {
		Vector3f v = new Vector3f();
		v.x = readFloat();
		v.y = readFloat();
		v.z = readFloat();
		return v;
	}

	public Vector3d readVector3d() throws IOException {
		Vector3d v = new Vector3d();
		v.x = readDouble();
		v.y = readDouble();
		v.z = readDouble();
		return v;
	}

	public Vector4f readVector4f() throws IOException {
		Vector4f v = new Vector4f();
		v.x = readFloat();
		v.y = readFloat();
		v.z = readFloat();
		v.w = readFloat();
		return v;
	}
	
	public String readString() throws IOException {
		st.nextToken();
		return st.sval;
	}

	public boolean readBoolean() throws IOException {
		st.nextToken();
		if (st.sval.toLowerCase().equals("true"))
			return true;
		return false;
	}

	public float getFloat() throws IOException {
		return java.lang.Float.parseFloat(st.sval);
	}

	public double getDouble() throws Exception {
		return Double.parseDouble(st.sval);
	}

	public int readInt() throws IOException {
		st.nextToken();
		return Integer.parseInt(st.sval);
	}

	public float readFloat() throws IOException {
		st.nextToken();
		return java.lang.Float.parseFloat(st.sval);
	}

	public double readDouble() throws IOException {
		st.nextToken();
		return Double.parseDouble(st.sval);
	}
	
	boolean isNextBracket(char bracketType) throws IOException{
		st.nextToken();
		if (st.ttype==StreamTokenizer.TT_NUMBER || st.ttype==StreamTokenizer.TT_WORD){
			st.pushBack();
			return false;
		}
		char zn=(char)st.ttype;
		if (zn==bracketType)
			return true;
		return false;
	}
		
	public VRNode readNode(VRNode parent) throws IOException{
		n++;		
		VRNode nd = null;
		String s = readString();
		String name = null;
		if (s.equals("DEF")){
			name = readString();
			s = readString();
			print("DEF " + name + " " + s);
		}else if (s.equals("USE")){
			name = readString();
			n--;
			print("USE " + name);
			nd = strToRef.get(name);
			return nd;
		}else
			print(s);
			n++;
			nd = createNode(s);
			if (nd!=null){
				nd.read(this);
			}else{
				if (s.equals("PROTO")){
					st.nextToken();	//name
					skip('[',']');	//dec
					skip('{','}');	//def
					n--;
					print("... skipping");
					n++;
				}else{
					skip('{','}');
					n--;
					print("... skipping");
					n++;
				}
			}
			if (nd!=null){	
				if (name!=null){
					nd.name=name;
					
					print("putting "+name+" to hashmap");
					strToRef.put(name, nd);
				}
				nd.parent = parent;
			}
			n--;
			
		n--;
		return nd;
	}
	
	public LinkedList readNodeList(VRNode parent) throws IOException{
		LinkedList ll = new LinkedList();
		while(st.nextToken()!=StreamTokenizer.TT_EOF){
			switch(st.ttype){
				case StreamTokenizer.TT_WORD:
					st.pushBack();
					VRNode node = readNode(parent);
					if (node!=null){
						ll.add(node);
					}
					break;
				default:
					char zn=(char)st.ttype;
					if (zn==']'){
						return ll;
					}
					logger.warn("unknown token: "+st.toString());
					break;
				}
		}
		return ll;
	}
	public LinkedList readNodes(VRNode parent) throws IOException{
		LinkedList linkedList=null;
		if (isNextBracket('['))
			linkedList=readNodeList(parent);
		else{
			VRNode node=readNode(parent);
			if (node!=null){
				linkedList=new LinkedList();
				linkedList.add(node);
			}
		}
		return linkedList;
	}
	
	private int n = 0;
	
	public void print(String s){
		String ws="";
		for (int i=0;i!=n*3; i++){
			ws=ws+" ";
		}
		logger.debug(ws+s);
	}
}
