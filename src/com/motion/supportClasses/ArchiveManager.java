package com.motion.supportClasses; 	

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArchiveManager 
{
	private String path;
	
	public ArchiveManager(String archivesPath)
	{
		path = archivesPath;
	}
	
	public ArrayList<String> openArchive() throws IOException{
		FileReader charReader = new FileReader(path);
		BufferedReader textReader = new BufferedReader(charReader);
	
		ArrayList<String> textData = new ArrayList<String>();
	
		String line;
		while((line = textReader.readLine()) != null)
		{
			if(line.startsWith("//") == false 		&& 
					line.startsWith("***") == false && 
					line.trim().equals("") == false && 
					line.equals("\n") == false		&&
					line.isEmpty() == false)
				textData.add(line);
		}
		
		textReader.close();
		
//		/*TEST CODE*/for(int i=0; i<textData.size(); i++)
//			/*TEST PRINT*/System.out.print("textData ="+textData.get(i)+"\n");
		
		return textData;		
	}
}
