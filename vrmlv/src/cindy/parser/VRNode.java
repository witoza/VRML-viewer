package cindy.parser;

import java.io.IOException;
import java.util.Iterator;

public abstract class VRNode implements Iterable{
			
	public VRNode	parent		= null;
	public String	name		= null;
	
	abstract public Iterator iterator();
	abstract public VRNode read(VRMLNodeParser parser) throws IOException;
	abstract public String getNodeInternalName();
	
	public String toString(){
		if (name!=null)
			return name;
		return getNodeInternalName();
	}

}