package cindy.parser;

public class VRNodeFactory extends VRMLNodeFactory{
	
	
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
	
	
	public VRNode createNode(String s){
		 if (s.equals("Transform"))		return createTransform();
		 if (s.equals("Shape"))			return createShape();
		 if (s.equals("Viewpoint"))		return createViewpoint();
		 if (s.equals("LOD"))			return createLOD();
		 if (s.equals("Group"))			return createGroup();
		 if (s.equals("Text"))			return new VRText();
		 if (s.equals("IndexedFaceSet"))return new VRIndexedFaceSet();
		 if (s.equals("IndexedLineSet"))return new VRIndexedLineSet();
		 if (s.equals("PointSet"))		return new VRPointSet();
		 if (s.equals("Coordinate"))	return new VRCoordinate();
		 if (s.equals("Color"))			return new VRCoordinate();
		 if (s.equals("Material"))		return new VRMaterial();
		 if (s.equals("Appearance"))	return new VRAppearance();
		 return null;
	}	
}
