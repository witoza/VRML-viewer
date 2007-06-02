package cindy.parser;

import cindy.parser.nodes.VRAppearance;
import cindy.parser.nodes.VRBox;
import cindy.parser.nodes.VRCone;
import cindy.parser.nodes.VRCoordinate;
import cindy.parser.nodes.VRCylinder;
import cindy.parser.nodes.VRDirectionalLight;
import cindy.parser.nodes.VRGroup;
import cindy.parser.nodes.VRIndexedFaceSet;
import cindy.parser.nodes.VRIndexedLineSet;
import cindy.parser.nodes.VRLOD;
import cindy.parser.nodes.VRMaterial;
import cindy.parser.nodes.VRNormal;
import cindy.parser.nodes.VRPointSet;
import cindy.parser.nodes.VRShape;
import cindy.parser.nodes.VRSphere;
import cindy.parser.nodes.VRText;
import cindy.parser.nodes.VRTextureCoordinate;
import cindy.parser.nodes.VRTransform;
import cindy.parser.nodes.VRViewpoint;
import cindy.parser.nodes.VRWorldInfo;

public class VRNodeFactory implements VRMLNodeFactory{
	
	public VRNode createTransform(){
		return new VRTransform();
	}
	
	public VRNode createShape(){
		return new VRShape();
	}
	
	public VRNode createViewpoint(){
		return new VRViewpoint();
	}
	
	public VRNode createLOD(){
		return new VRLOD();
	}
	
	public VRNode createGroup(){
		return new VRGroup();
	}
	
	public VRNode createText(){
		return new VRText();
	}
	
	public VRNode createIndexedFaceSet(){
		return new VRIndexedFaceSet();
	}
	
	public VRNode createIndexedLineSet(){
		return new VRIndexedLineSet();
	}
	
	public VRNode createPointSet(){
		return new VRPointSet();
	}
	
	public VRNode createCoordinate(){
		return new VRCoordinate();
	}
	
	public VRNode createColor(){
		return new VRCoordinate();
	}
	
	public VRNode createMaterial(){
		return new VRMaterial();
	}
	
	public VRNode createAppearance(){
		return new VRAppearance();
	}

	public VRNode createBox() {
		return new VRBox();
	}

	public VRNode createCone() {
		return new VRCone();
	}

	public VRNode createCylinder() {
		return new VRCylinder();
	}

	public VRNode createShpere() {
		return new VRSphere();
	}

	public VRNode createWorldInfo() {
		return new VRWorldInfo();
	}

	public VRNode createDirectionalLight() {
		return new VRDirectionalLight();
	}

	public VRNode createTextureCoordinate() {
		return new VRTextureCoordinate();
	}

	public VRNode createNormal() {
		return new VRNormal();
	}	
}
