package cindy.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import org.apache.log4j.Logger;

public class VRMLModel{
	
	private static Logger logger = Logger.getLogger(VRMLModel.class);
	
	public VRGroup mainGroup = new VRGroup();
			
	public void readModel(String file) throws IOException{
		readModel(new FileInputStream(file));
	}
	
	public void readModel(InputStream inputStream) throws IOException{
		
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
		logger.info("model reading: starded");
		VRMLNodeParser parser = new VRMLNodeParser(this, new NodeFactory());
		parser.setTokenizer(st);
		mainGroup.name="VRML WORLD";
		mainGroup.children=parser.readNodeList(mainGroup);
		fin.close();
		logger.info("model reading: finished ok");						
		logger.info("---------------------------------------------------------------------");
	}
}