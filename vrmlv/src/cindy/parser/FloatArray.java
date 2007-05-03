package cindy.parser;

import java.io.IOException;
import java.io.StreamTokenizer;

public class FloatArray{
	float[]	arr;
	float[]	arrTemp;
	
	int		size;
	int		maxSize;
	public FloatArray(int p){
		arr=new float [p];
		size=0;
	}
	public void push_back(float item){
		if (size==arr.length){
			arrTemp=new float[arr.length*2];
			System.arraycopy(arr,0,arrTemp,0,size);
			arr=arrTemp;
			arrTemp=null;
			//System.out.println("reallocating");
		}
		arr[size]=item;
		size++;
	}
	public static float[] read(VRMLNodeParser parser) throws IOException{
		FloatArray arr=new FloatArray(1024);
		StreamTokenizer st=parser.st;
		st.nextToken();//[
		for(;;){
			st.nextToken();
			if(st.ttype!=StreamTokenizer.TT_WORD){					
				break; //it was ]
			}			
			arr.push_back(parser.getFloat());
		}
		arr.arrTemp=new float[arr.size];
		System.arraycopy(arr.arr,0,arr.arrTemp,0,arr.size);
		arr.arr=arr.arrTemp;
		arr.arrTemp=null;		
		return arr.arr;
	}
}
