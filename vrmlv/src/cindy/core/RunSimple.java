package cindy.core;

import java.io.IOException;

import cindy.parser.VRMLModel;


public class RunSimple {

	public static void main(String[] args) throws IOException{
		
		LoggerHelper.initializeLoggingFacility();
		String outputWRL = "c:\\__vrml\\2006_01_16\\problem1\\problem1.wrl";
		VRMLModel model=new VRMLModel();
	   	model.readModel(outputWRL);
	}
	
}
