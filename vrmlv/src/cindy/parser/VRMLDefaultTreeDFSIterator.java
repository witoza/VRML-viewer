package cindy.parser;

import java.util.Iterator;
import java.util.LinkedList;

public class VRMLDefaultTreeDFSIterator implements Iterator{
		
	private boolean firstRun = true;	
	private VRMLDefaultTreeDFSIterator innerIter;
	
	private final VRNode thisNode;
	private final Iterator pointerToNextNode;
	
	public VRMLDefaultTreeDFSIterator(LinkedList listOfVRNodes,VRNode callBack){
		this.thisNode=callBack;

		if (listOfVRNodes!=null){
			pointerToNextNode=listOfVRNodes.iterator();
		}else{
			pointerToNextNode=new LinkedList().iterator();
		}
	}

	private void setNextNode(){		
		if (pointerToNextNode.hasNext()){
			innerIter=(VRMLDefaultTreeDFSIterator)((VRNode)pointerToNextNode.next()).iterator();
		}
	}
	
	public boolean hasNext() {
		if (firstRun)
			return true;
		if (pointerToNextNode.hasNext())
			return true;
		if (innerIter!=null && innerIter.hasNext())
			return true;
		return false;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();			
	}
	
	public Object next() {
		if (firstRun){
			firstRun=false;
			if (hasNext())
				setNextNode();			
			return thisNode;
		}		
		if (innerIter.hasNext())
			return innerIter.next();
		else{
			setNextNode();
			return next();
		}
	}	
}
