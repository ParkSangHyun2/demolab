package step1.share.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
	//
	public static File createFile(String folderName,String stepName, String fileName) {
		//
		File resourceFile = null; 
		
		try {
		    String fileSeparator = System.getProperty("file.separator"); 
			String pathName = getPathName(folderName, stepName); 
		    String fullFileName = pathName + fileSeparator + fileName; 

			Path path = Paths.get(pathName); 
	        if (!Files.exists(path)) {
                Files.createDirectories(path);
	        }
	        
			resourceFile = new File(fullFileName);
			resourceFile.createNewFile();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resourceFile; 
	}
	
	public static File findFile(String folderName, String stepName, String fileName) {
		//
		File resourceFile = null; 
		
		try {
		    String fileSeparator = System.getProperty("file.separator"); 
			String pathName = getPathName(folderName, stepName); 
		    String fullFileName = pathName + fileSeparator + fileName; 

			Path path = Paths.get(pathName); 
	        if (!Files.exists(path)) {
                Files.createDirectories(path);
	        }
	        
			resourceFile = new File(fullFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resourceFile; 
	}

	public static void deleteFile(String folderName, String stepName, String fileName) {
		//
		File resourceFile = null; 
		
		try {
		    String fileSeparator = System.getProperty("file.separator"); 
			String pathName = getPathName(folderName, stepName); 
		    String fullFileName = pathName + fileSeparator + fileName; 

			Path path = Paths.get(pathName); 
	        if (!Files.exists(path)) {
                Files.createDirectories(path);
	        }
	        
			resourceFile = new File(fullFileName);
			if (resourceFile.exists()) {
				// 
				resourceFile.delete(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getPathName(String folderName, String stepName) throws IOException {
		//
		String cannonicalPath = (new File(".")).getCanonicalPath();
	    String fileSeparator = System.getProperty("file.separator"); 

	    StringBuilder builder = new StringBuilder(); 
	    builder.append(cannonicalPath).append(fileSeparator);
	    builder.append("resource").append(fileSeparator); 
    	builder.append(folderName).append(fileSeparator);
		builder.append(stepName); 
		
	    return  builder.toString();
	}
	
	public static List<String> findFileList(String folderName, String stepName) {
		//
		List<String> fileNameList = new ArrayList<String>(); 
		
		try {
			String pathName = getPathName(folderName, stepName); 
			Path path = Paths.get(pathName); 
	        if (!Files.exists(path)) {
                Files.createDirectories(path);
	        } else {
		        File folder = new File(pathName); 
		        String[] fileNames = folder.list();
		        fileNameList = Arrays.asList(fileNames);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileNameList; 
	}
	
	public static boolean write(File file, char[] contents) {
		//
		FileWriter fileWriter = null;
		boolean successful = false;
		
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(contents);
			
			successful = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { 
				if (fileWriter != null) {
					fileWriter.close(); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return successful;
	}
	
	public static char[] read(File file) {
		// 
        BufferedReader reader = null; 
        CharArrayWriter contents = new CharArrayWriter((int)file.length());
        
		try {
	        reader = new BufferedReader(new FileReader(file));
	        String line = null;
	        
			while (true) {
				if((line = reader.readLine()) == null) {
					break;
				}
				contents.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return contents.toCharArray(); 
	}
}