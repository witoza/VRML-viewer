package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.LinkedList;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.log4j.Logger;

import cindy.parser.nodes.VRAppearance;
import cindy.parser.nodes.VRBox;
import cindy.parser.nodes.VRColor;
import cindy.parser.nodes.VRCone;
import cindy.parser.nodes.VRCoordinate;
import cindy.parser.nodes.VRCylinder;
import cindy.parser.nodes.VRDirectionalLight;
import cindy.parser.nodes.VRGroup;
import cindy.parser.nodes.VRIndexedFaceSet;
import cindy.parser.nodes.VRIndexedLineSet;
import cindy.parser.nodes.VRLOD;
import cindy.parser.nodes.VRMaterial;
import cindy.parser.nodes.VRPointLight;
import cindy.parser.nodes.VRPointSet;
import cindy.parser.nodes.VRShape;
import cindy.parser.nodes.VRSphere;
import cindy.parser.nodes.VRSpotLight;
import cindy.parser.nodes.VRText;
import cindy.parser.nodes.VRTextureCoordinate;
import cindy.parser.nodes.VRTransform;
import cindy.parser.nodes.VRViewpoint;
import cindy.parser.nodes.VRWorldInfo;

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
		
		 if (s.equals(VRTransform.VRNODENAME))		return nodeFactory.createTransform();
		 if (s.equals(VRShape.VRNODENAME))			return nodeFactory.createShape();
		 if (s.equals(VRViewpoint.VRNODENAME))		return nodeFactory.createViewpoint();
		 if (s.equals(VRLOD.VRNODENAME))			return nodeFactory.createLOD();
		 if (s.equals(VRGroup.VRNODENAME))			return nodeFactory.createGroup();
		 if (s.equals(VRText.VRNODENAME))			return nodeFactory.createText();
		 if (s.equals(VRIndexedFaceSet.VRNODENAME)) return nodeFactory.createIndexedFaceSet();
		 if (s.equals(VRIndexedLineSet.VRNODENAME)) return nodeFactory.createIndexedLineSet();
		 if (s.equals(VRPointSet.VRNODENAME))		return nodeFactory.createPointSet();
		 if (s.equals(VRCoordinate.VRNODENAME))		return nodeFactory.createCoordinate();
		 if (s.equals(VRColor.VRNODENAME))			return nodeFactory.createColor();
		 if (s.equals(VRMaterial.VRNODENAME))		return nodeFactory.createMaterial();
		 if (s.equals(VRAppearance.VRNODENAME))		return nodeFactory.createAppearance();
		 if (s.equals(VRCone.VRNODENAME))			return nodeFactory.createCone();
		 if (s.equals(VRSphere.VRNODENAME))			return nodeFactory.createShpere();
		 if (s.equals(VRBox.VRNODENAME))			return nodeFactory.createBox();
		 if (s.equals(VRCylinder.VRNODENAME))		return nodeFactory.createCylinder();
		 if (s.equals(VRWorldInfo.VRNODENAME))		return nodeFactory.createWorldInfo();
		 if (s.equals(VRDirectionalLight.VRNODENAME))	return nodeFactory.createDirectionalLight();		 
		 if (s.equals(VRTextureCoordinate.VRNODENAME))	return nodeFactory.createTextureCoordinate();
		 if (s.equals(VRPointLight.VRNODENAME))		return nodeFactory.createPointLight();
		 if (s.equals(VRSpotLight.VRNODENAME))		return nodeFactory.createSpotLight();
		 
		 return null;
	}
	
	public void setTokenizer(StreamTokenizer str){
		st = str;
	}
	
	public void skip(char openingBrack, char closingBrack) throws IOException{
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
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	private LinkedList<String> readStringList(char quoteChar) throws IOException{
		LinkedList<String> ll = new LinkedList<String>();
		
		while(st.nextToken()!=StreamTokenizer.TT_EOF){
			char zn=(char)st.ttype;
			
			if (zn==']'){			
				return ll;
			}
			ll.add(st.sval);
			print(quoteChar+st.sval+quoteChar);
		}
		return ll;
	}	
	
	public LinkedList<String> readStrings(char quoteChar) throws IOException{
		if (isNextBracket('[')){			
			return readStringList(quoteChar);
		}
		else{
			LinkedList<String> ll = new LinkedList<String>();
			ll.add(readString(quoteChar));
			return ll;
		}
	}
	
	public String readString(char quoteChar) throws IOException{
		st.quoteChar(quoteChar);
		String text=readString();
		print(quoteChar+text+quoteChar);
		st.quoteChar((char)0);
		return text;		
	}
	
////////////////////////////////////////////////////////////////////////////////////////
	
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
				}else if (s.equals("NULL")) {
					//st.nextToken();	//name
					n--;
					nd = null;
					//print("... NULL");
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
	
	public LinkedList<VRNode> readNodeList(VRNode parent) throws IOException{
		LinkedList<VRNode> ll = new LinkedList<VRNode>();
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
	public LinkedList<VRNode> readNodes(VRNode parent) throws IOException{
		LinkedList<VRNode> linkedList=null;
		if (isNextBracket('['))
			linkedList = readNodeList(parent);
		else{
			VRNode node = readNode(parent);
			if (node!=null){
				linkedList = new LinkedList<VRNode>();
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
