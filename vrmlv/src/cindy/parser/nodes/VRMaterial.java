package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import javax.vecmath.Vector3f;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRMaterial extends VRNode{
		
	static public final String VRNODENAME = "Material";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public float		ambientIntensity	= 0.2f;
	public Vector3f		diffuseColor		= new Vector3f(0.8f,0.8f,0.8f);
	public Vector3f		emissiveColor		= new Vector3f(0,0,0);
	public float		shininess			= 0.2f;
	public Vector3f		specularColor		= new Vector3f(0,0,0);
	public float		transparency		= 0;
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(null,this);
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken(); //{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("ambientIntensity"))	ambientIntensity = parser.readFloat();
			else if (s.equals("diffuseColor"))	diffuseColor = parser.readVector3f();
			else if (s.equals("emissiveColor"))	emissiveColor = parser.readVector3f();
			else if (s.equals("shininess")) 	shininess = parser.readFloat();
			else if (s.equals("specularColor"))	specularColor = parser.readVector3f();
			else if (s.equals("transparency"))	transparency = parser.readFloat();
		}
		return this;
	}

	public VRNode clone(VRMLNodeFactory nf) {
		VRMaterial mt = (VRMaterial)nf.createMaterial();
		mt.ambientIntensity = ambientIntensity;
		mt.diffuseColor = diffuseColor;
		mt.emissiveColor = emissiveColor;
		mt.shininess = shininess;
		mt.specularColor = specularColor;
		mt.transparency = transparency;
		return mt;
	}
}