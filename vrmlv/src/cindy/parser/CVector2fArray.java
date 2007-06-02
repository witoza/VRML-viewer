package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;

import javax.vecmath.Vector2f;

public class CVector2fArray{
	Vector2f []arr;
	Vector2f []arrTemp;
	
	int size;
	int maxSize;
	public CVector2fArray(int p){
		arr=new Vector2f [p];
		size=0;
	}
	public void push_back(Vector2f item){
		if (size==arr.length){
			arrTemp=new Vector2f[arr.length*2];
			System.arraycopy(arr,0,arrTemp,0,size);
			arr=arrTemp;
			arrTemp=null;
			//System.out.println("reallocating");
		}
		arr[size]=item;
		size++;
	}
	public static Vector2f[] read(VRMLNodeParser parser) throws IOException{
		CVector2fArray arr=new CVector2fArray(1024);
		StreamTokenizer st=parser.st;
		st.nextToken();//[
		for(;;){
			st.nextToken();
			if(st.ttype!=StreamTokenizer.TT_WORD){					
				break; //it was ]
			}
			float x=parser.getFloat();
			float y=parser.readFloat();
			arr.push_back(new Vector2f(x,y));
		}
		arr.arrTemp=new Vector2f[arr.size];
		System.arraycopy(arr.arr,0,arr.arrTemp,0,arr.size);
		arr.arr=arr.arrTemp;
		arr.arrTemp=null;
		return arr.arr;
	}
}

