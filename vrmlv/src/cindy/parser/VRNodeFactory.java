package cindy.parser;

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
}
