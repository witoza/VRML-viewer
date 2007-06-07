package cindy.drawable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.apache.log4j.Logger;

import cindy.parser.VRMLModel;
import cindy.parser.VRMLNodeFactory;

public class VRMLDrawableModel extends VRMLModel{
	
	private static Logger _LOG = Logger.getLogger(VRMLDrawableModel.class);
	
	protected HashMap<String, Integer> fileNameToPixmap = new HashMap<String, Integer>();
	
	public VRMLDrawableModel(){
		super();
	}
	
	private String dir="";
	
	public int getOGLTextureId(String s, GL gl, GLU glu){
		if (file_name!=null){
			File fin = new File(file_name);
			dir = fin.getParent()+File.separator;
			_LOG.info("dir = "+dir);
		}
		
		if (!fileNameToPixmap.containsKey(s)){
			
			int tex = DrawableHelper.genTexture(gl);
			gl.glBindTexture(GL.GL_TEXTURE_2D, tex);
			TextureReader.Texture texture = null;
			try {
				if (new File(s).isAbsolute()){
					texture = TextureReader.readTexture(s);
				}else{
					texture = TextureReader.readTexture(dir+s);
				}
			} catch (IOException e) {
				_LOG.error("error while loading file: "+s+" : "+e);
				return -1;
			}
			DrawableHelper.makeRGBTexture(gl, glu, texture, GL.GL_TEXTURE_2D, false);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			fileNameToPixmap.put(s, tex);
		}
		return fileNameToPixmap.get(s);
	}
	
	public void readModel(InputStream inputStream, VRMLNodeFactory nf) throws IOException{
		super.readModel(inputStream, nf);
		/*		
		for (String s: fileNameToRead){
			s = s.substring(1, s.length()-1);
			BufferedImage bi = BitmapLoader.loadBitmap(s);
			if (bi==null){
				_LOG.warn("can't read file: "+s);
			}else{
				_LOG.warn("file: "+s+" loaded");
				fileNameToPixmap.put(s, bi);
			}
		}*/
	}
}
