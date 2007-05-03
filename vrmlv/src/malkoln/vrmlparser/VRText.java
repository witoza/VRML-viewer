package malkoln.vrmlparser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Iterator;

public class VRText extends VRNode{
	
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
		if (text=="" || text==null)
			return null;
		return this;
	}
}