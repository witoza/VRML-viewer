package cindy.parser;

public interface VRMLNodeFactory {

	public VRNode createCone();	
	public VRNode createShpere();	
	public VRNode createBox();	
	public VRNode createCylinder();	
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
	public VRNode createWorldInfo();	
	public VRNode createDirectionalLight(); 	
	public VRNode createTextureCoordinate();		
	public VRNode createNormal();	
	public VRNode createPointLight();	
	public VRNode createSpotLight();
}
