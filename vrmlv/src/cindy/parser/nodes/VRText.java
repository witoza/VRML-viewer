package cindy.parser.nodes;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

import cindy.parser.VRMLDefaultTreeDFSIterator;
import cindy.parser.VRMLNodeFactory;
import cindy.parser.VRMLNodeParser;
import cindy.parser.VRNode;

public class VRText extends VRNode{
	
	static public final String VRNODENAME = "Text";
	public String getNodeInternalName(){
		return VRNODENAME;
	}
	
	public String text;
	
	public Iterator iterator(){
		return new VRMLDefaultTreeDFSIterator(null,this);
	}

	public VRNode read(VRMLNodeParser parser) throws IOException {
		parser.st.nextToken();//{
		while (parser.st.nextToken()!=StreamTokenizer.TT_EOF){
			if (parser.st.ttype!=StreamTokenizer.TT_WORD)
				break;		
			String s=parser.st.sval;
			parser.print(s);
			if (s.equals("string")){
				parser.st.quoteChar('"');
				text=parser.readString();
				parser.print(text+"\n");
				parser.st.quoteChar((char)0);
			}else if (s.equals("fontStyle")){
				parser.st.nextToken();//FontStyle
				parser.skip('{','}');
			}				
		}
		if (text==null || text=="")
			return null;
		return this;
	}

	public VRNode clone(VRMLNodeFactory nf) {
		VRText txt = (VRText)nf.createText();
		txt.text = text;
		return txt;
	}
}