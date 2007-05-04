package cindy.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NativesHelper {
//	checks if proper library files exists
	
	final static String windowsLibs[] = {"jogl.dll","jogl_cg.dll","jogl_awt.dll"};
	final static String linuxLibs[] = {"libjogl.so","libjogl_cg.so","libjogl_awt.so"};
	final static String os = System.getProperty("os.name");

	static public Object[] checkNativeFiles(){
		boolean allExist = false;
		String result = new String();
		final Set<String> libs;
		if (os.equals("Linux")) libs = new HashSet<String>(Arrays.asList(linuxLibs)); 
		else if (os.startsWith("Windows")) libs = new HashSet<String>(Arrays.asList(windowsLibs));
		else throw new RuntimeException("OS not supported"); 

		System.out.println("Required libs: " + libs);
		
		Set<String> found = new HashSet<String>();
		Set<String> libPath = new HashSet<String>(Arrays.asList(System.getProperty("java.library.path").split(System.getProperty("path.separator"))));
		for (String p : libPath) {
			String[] files =  new File(p).list(new FilenameFilter() {
				public boolean accept(File dir, String name) { return libs.contains(name); }
			} );
			if (files != null) found.addAll(Arrays.asList(files));
			if (found.size() == libs.size()) { allExist = true; break; }
		}
		
		if (!allExist) {
			libs.removeAll(found);
			for (String missing : libs)
				result += "File " + missing + " is missing\n";
			result += "place these files in one of the following directory:\n" + libPath; 
		}
		
		return new Object[] {new Boolean(allExist), result};
	}
	
}
