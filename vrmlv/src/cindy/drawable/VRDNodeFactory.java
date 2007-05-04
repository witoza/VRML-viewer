package cindy.drawable;

import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRNode;

public class VRDNodeFactory extends VRMLNodeFactory{
	public VRNode createNode(String s){
		 if (s.equals("Transform"))		return new VRDTransform();
		 if (s.equals("Shape"))			return new VRDShape();
		 if (s.equals("Viewpoint"))		return new VRDViewpoint();
		 if (s.equals("LOD"))			return new VRDLOD();
		 if (s.equals("Group"))			return new VRDGroup();
		 if (s.equals("Text"))			return new VRDText();
		 if (s.equals("IndexedFaceSet"))return new VRDIndexedFaceSet();
		 if (s.equals("IndexedLineSet"))return new VRDIndexedLineSet();
		 if (s.equals("PointSet"))		return new VRDPointSet();
		 if (s.equals("Coordinate"))	return new VRDCoordinate();
		 if (s.equals("Color"))			return new VRDCoordinate();
		 if (s.equals("Material"))		return new VRDMaterial();
		 if (s.equals("Appearance"))	return new VRDAppearance();
		 return null;
	}	
}
