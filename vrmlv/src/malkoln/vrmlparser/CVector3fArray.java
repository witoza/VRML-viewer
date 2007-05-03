package malkoln.vrmlparser;

import java.io.IOException;
import java.io.StreamTokenizer;

import javax.vecmath.Vector3f;

public class CVector3fArray{
	Vector3f []arr;
	Vector3f []arrTemp;
	
	int size;
	int maxSize;
	public CVector3fArray(int p){
		arr=new Vector3f [p];
		size=0;
	}
	public void push_back(Vector3f item){
		if (size==arr.length){
			arrTemp=new Vector3f[arr.length*2];
			System.arraycopy(arr,0,arrTemp,0,size);
			arr=arrTemp;
			arrTemp=null;
			//System.out.println("reallocating");
		}
		arr[size]=item;
		size++;
	}
	public static Vector3f[] read(VRMLNodeParser parser) throws IOException{
		CVector3fArray arr=new CVector3fArray(1024);
		StreamTokenizer st=parser.st;
		st.nextToken();//[
		for(;;){
			st.nextToken();
			if(st.ttype!=StreamTokenizer.TT_WORD){					
				break; //it was ]
			}
			float x=parser.getFloat();
			float y=parser.readFloat();
			float z=parser.readFloat();
			arr.push_back(new Vector3f(x,y,z));
		}
		arr.arrTemp=new Vector3f[arr.size];
		System.arraycopy(arr.arr,0,arr.arrTemp,0,arr.size);
		arr.arr=arr.arrTemp;
		arr.arrTemp=null;
		return arr.arr;
	}
}

