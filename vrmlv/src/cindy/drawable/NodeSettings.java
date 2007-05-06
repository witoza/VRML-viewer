package cindy.drawable;

import javax.media.opengl.GL;

import cindy.core.BoundingBox;

public class NodeSettings {
	public BoundingBox boundingBox = null;

	public boolean drawBBox = false;

	public int rendMode = GL.GL_FILL;

	public int shadeModel = GL.GL_SMOOTH;

	public float lineWidth = 1.0f;

}
