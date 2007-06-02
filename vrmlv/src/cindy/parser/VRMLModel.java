package cindy.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import cindy.parser.nodes.VRGroup;

public class VRMLModel{
	
	private static Logger _LOG = Logger.getLogger(VRMLModel.class);
	
	protected VRGroup mainGroup;	
	
	protected LinkedList<String> fileNameToRead = new LinkedList<String>();
	
	public void addPixmap(String fileName){
		_LOG.info("pixmap: " + fileName);
		fileNameToRead.add(fileName);
	}
	
	public VRGroup getMainGroup(){
		return mainGroup;
	}
			
	public void readModel(String file, VRMLNodeFactory nf) throws IOException{
		readModel(new FileInputStream(file), nf);
	}
	
	public void readModel(InputStream inputStream, VRMLNodeFactory nf) throws IOException{
		
		BufferedReader fin = new BufferedReader(
								new InputStreamReader(inputStream));
		StreamTokenizer st = new StreamTokenizer(fin);
		st.resetSyntax();
		st.eolIsSignificant(false);
		st.wordChars( 0x20, 0x7E);
		st.commentChar('#');	
		st.whitespaceChars(',',',');
		st.whitespaceChars(0,' ');
		st.ordinaryChar('[');
		st.ordinaryChar(']');
		st.ordinaryChar('{');
		st.ordinaryChar('}');
		_LOG.info("model reading: starded");
		VRMLNodeParser parser = new VRMLNodeParser(this, nf);
		parser.setTokenizer(st);
		mainGroup = (VRGroup)nf.createGroup();
		mainGroup.name = "VRML WORLD";
		mainGroup.children = parser.readNodeList(mainGroup);
		fin.close();
		_LOG.info("model reading: finished");						
		_LOG.info("---------------------------------------------------------------------");
	}
}