package cindy.parser;

import java.io.IOException;
import java.util.Iterator;

public abstract class VRNode implements Iterable{
			
	public VRNode	parent		= null;
	public String	name		= null;
	
	abstract public Iterator iterator();
	abstract public VRNode read(VRMLNodeParser parser) throws IOException;	
}