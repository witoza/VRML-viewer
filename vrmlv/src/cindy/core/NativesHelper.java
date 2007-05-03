package cindy.core;

import java.io.File;

public class NativesHelper {
//	checks if proper dll files exists
	static public Object[] checkNativeFiles(){
		//TODO: change 'joglNativeFiles' files depending on os. this could be done via checking properties
		
		final String joglNativeFiles[]={"jogl.dll","jogl_cg.dll","jogl_awt.dll"};
		if (joglNativeFiles==null || joglNativeFiles.length==0) 
			return new Object[]{new Boolean(true),null};
		
		boolean exists[]=new boolean[joglNativeFiles.length];
		for (int i=0; i!=exists.length; i++)
			exists[i]=false;
		String libPath=System.getProperty("java.library.path");
		System.out.println(libPath);
		String paths[]=libPath.split(";");
		if (paths!=null && paths.length>0){
			for (int i=0; i!=paths.length-1; i++){
				if (paths[i]==null) continue;
				for (int j=i+1; j!=paths.length; j++){
					if (paths[i].equals(paths[j]))
						paths[j]=null;
				}
			}
			for (int i=0; i!=paths.length; i++){
				if (paths[i]==null) continue;				
				if (paths[i].equals(".")) paths[i]="";
				paths[i]=new File(paths[i]).getAbsolutePath();
				for (int j=0; j!=joglNativeFiles.length; j++){
					File fin=new File(paths[i]+"/"+joglNativeFiles[j]);
					if (fin.exists())
						exists[j]=true;
				}
			}
		}
		StringBuffer result=new StringBuffer();
		boolean allExists=true;
		for (int i=0; i!=exists.length; i++){
			if (!exists[i]){
				result.append("File ").append(joglNativeFiles[i]).append(" is missing\n");
				allExists=false;
			}
		}
		if (!allExists && paths!=null && paths.length>0){
			result.append("place these files in one of the following directory:\n");
			for (int i=0; i!=paths.length; i++){
				if (paths[i]==null) continue;
				result.append(paths[i]).append("\n");
			}
		}
		return new Object[]{new Boolean(allExists),result.toString()};
	}
	
}
