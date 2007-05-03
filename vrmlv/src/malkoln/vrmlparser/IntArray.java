package malkoln.vrmlparser;

import java.io.IOException;
import java.io.StreamTokenizer;

public class IntArray{
	int []arr;
	int []arrTemp;
	
	int size;
	int maxSize;
	public IntArray(int p){
		arr=new int [p];
		size=0;
	}
	public void push_back(int item){
		if (size==arr.length){
			arrTemp=new int[arr.length*2];
			System.arraycopy(arr,0,arrTemp,0,size);
			arr=arrTemp;
			arrTemp=null;
			//System.out.println("reallocating");
		}
		arr[size]=item;
		size++;
	}
	public static int[] read(VRMLNodeParser parser) throws IOException{
		IntArray arr=new IntArray(1024);
		StreamTokenizer st=parser.st;	
		st.nextToken();//[
		for(;;){
			st.nextToken();
			if(st.ttype!=StreamTokenizer.TT_WORD){					
				break; //it was ]
			}
			arr.push_back((int)parser.getFloat());
		}
		if (arr.size==0) return null;
		arr.arrTemp=new int[arr.size];
		System.arraycopy(arr.arr,0,arr.arrTemp,0,arr.size);
		arr.arr=arr.arrTemp;
		arr.arrTemp=null;
		return arr.arr;
	}
}