package cindy.drawable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;

import cindy.parser.VRMLModel;
import cindy.parser.VRMLNodeFactory;

public class VRMLDrawableModel extends VRMLModel{
	
	private static Logger _LOG = Logger.getLogger(VRMLDrawableModel.class);
	
	protected HashMap<String, BufferedImage> fileNameToPixmap = new HashMap<String, BufferedImage>();
	
	public VRMLDrawableModel(){
		super();
	}
	
	public void readModel(InputStream inputStream, VRMLNodeFactory nf) throws IOException{
		super.readModel(inputStream, nf);
		for (String s: fileNameToRead){
			s = s.substring(1, s.length()-1);
			BufferedImage bi = BitmapLoader.loadBitmap(s);
			if (bi==null){
				_LOG.warn("can't read file: "+s);
			}else{
				_LOG.warn("file: "+s+" loaded");
				fileNameToPixmap.put(s, bi);
			}
		}
	}
}
