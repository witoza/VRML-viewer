package cindy.parser;

public interface VRMLNodeFactory {

	
	public VRNode createTransform();
	
	public VRNode createShape();
	
	public VRNode createViewpoint();
	
	public VRNode createLOD();
	
	public VRNode createGroup();
	
	public VRNode createText();
	
	public VRNode createIndexedFaceSet();
	
	public VRNode createIndexedLineSet();
	
	public VRNode createPointSet();
	
	public VRNode createCoordinate();
	
	public VRNode createColor();
	
	public VRNode createMaterial();
	
	public VRNode createAppearance();
}
