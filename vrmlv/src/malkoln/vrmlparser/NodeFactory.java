package malkoln.vrmlparser;

public class NodeFactory {
	
	public VRNode createNode(String s){
		 if (s.equals("Transform"))		return new VRTransform();
		 if (s.equals("Shape"))			return new VRShape();
		 if (s.equals("Viewpoint"))		return new VRViewpoint();
		 if (s.equals("LOD"))			return new VRLOD();
		 if (s.equals("Group"))			return new VRGroup();
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
