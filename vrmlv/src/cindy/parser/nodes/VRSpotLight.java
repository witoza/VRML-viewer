package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import javax.vecmath.Vector3f;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRMLParserException;
import cindy.parser.VRNode;

public class VRSpotLight extends VRNode{
	
	static public final String VRNODENAME = "SpotLight";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	
	public float ambientIntensity		= 0;
	public Vector3f attenuation			= new Vector3f(1,0,0);
	public float beamWidth				= 1.570796f;
	public Vector3f color				= new Vector3f();
	public float cutOffAngle			= 0.785398f;
	public Vector3f direction			= new Vector3f(0,0,-1);
	public float intensity				= 1;
	public Vector3f location			= new Vector3f(0,0,0);
	public boolean on					= true;
	public float radius					= 100;
	
	public VRNode clone(VRMLNodeFactory nf) {
		VRSpotLight nd = (VRSpotLight)nf.createSpotLight();
		nd.model = model;
		nd.name = name;
		
		nd.ambientIntensity = ambientIntensity;
		nd.attenuation = attenuation;
		nd.beamWidth = beamWidth;
		nd.color = color;
		nd.cutOffAngle = cutOffAngle;
		nd.direction = direction;
		nd.intensity = intensity;
		nd.location = location;
		nd.on = on;
		nd.radius = radius;
		return nd;
	}
	
	
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
			
			if (s.equals("ambientIntensity"))	ambientIntensity=parser.readFloat();
			else if (s.equals("attenuation"))	attenuation=parser.readVector3f();	
			else if (s.equals("beamWidth"))		beamWidth=parser.readFloat();			
			else if (s.equals("color")) 		color=parser.readVector3f();
			else if (s.equals("cutOffAngle"))	cutOffAngle=parser.readFloat();
			else if (s.equals("direction"))		direction=parser.readVector3f();			
			else if (s.equals("intensity"))		intensity=parser.readFloat();
			else if (s.equals("location"))		location=parser.readVector3f();
			else if (s.equals("on"))			on=parser.readBoolean();
			else if (s.equals("radius"))		radius=parser.readFloat();
			else {
				throw new VRMLParserException(s + " phrase not possible in "+ getNodeInternalName() + " node! ");
			}
		}
		return this;
	}


}
