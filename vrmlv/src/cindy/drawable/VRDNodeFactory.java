package cindy.drawable;

import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRNode;

public class VRDNodeFactory implements VRMLNodeFactory{

	
	public VRNode createTransform(){
		return new VRDTransform();
	}
	
	public VRNode createShape(){
		return new VRDShape();
	}
	
	public VRNode createViewpoint(){
		return new VRDViewpoint();
	}
	
	public VRNode createLOD(){
		return new VRDLOD();
	}
	
	public VRNode createGroup(){
		return new VRDGroup();
	}
	
	public VRNode createText(){
		return new VRDText();
	}
	
	public VRNode createIndexedFaceSet(){
		return new VRDIndexedFaceSet();
	}
	
	public VRNode createIndexedLineSet(){
		return new VRDIndexedLineSet();
	}
	
	public VRNode createPointSet(){
		return new VRDPointSet();
	}
	
	public VRNode createCoordinate(){
		return new VRDCoordinate();
	}
	
	public VRNode createColor(){
		return new VRDCoordinate();
	}
	
	public VRNode createMaterial(){
		return new VRDMaterial();
	}
	
	public VRNode createAppearance(){
		return new VRDAppearance();
	}

	public VRNode createBox() {
		return new VRDBox();
	}

	public VRNode createCone() {
		return new VRDCone();
	}

	public VRNode createCylinder() {
		return new VRDCylinder();
	}

	public VRNode createShpere() {
		return new VRDSphere();
	}	
}
