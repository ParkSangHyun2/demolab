/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step4_1.da.file.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.club.TravelClub;
import util.FileDbWrapper;

public class ClubFile{
	//
	private static Map<String,Integer> keyIndexMap; 
	
	static {
		keyIndexMap = new LinkedHashMap<String,Integer>(); 
		keyIndexMap.put("id", 0); 
		keyIndexMap.put("name", 1); 
	}
	
	private FileDbWrapper clubFile; 
	private FileDbWrapper clubTempFile; 
	
	public ClubFile() {
		//
		this.clubFile = new FileDbWrapper(
				"step41",
				FileConfig.getFileName("Club"), 
				FileConfig.getDelimiter()); 
		
		this.clubTempFile = new FileDbWrapper(
				"step41", 
				FileConfig.getFileName("ClubTemp"), 
				FileConfig.getDelimiter());
		
		this.clubFile.setKeyIndexMap(keyIndexMap);
		this.clubTempFile.setKeyIndexMap(keyIndexMap);
	}
	
	public boolean exists(String clubId) {
		// 
        boolean found = false;
        BufferedReader reader; 
        
		try {
	        reader = clubFile.requestReader();			
	        String line = null; 
	        
			while(true) {
				if((line = reader.readLine()) == null) {
					break; 
				}
				
				if (clubFile.hasValueOf("id", clubId, line)) {
					found = true; 
					break; 
				}; 
			}
			reader.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return found; 
	}
	
	public void write(TravelClub club) {
		// 
		if (this.exists(club.getId())) {
			return;  
		}
		
		FileWriter fileWriter;

		try {
			fileWriter = clubFile.requestFileWriter();
			fileWriter.write(convertToStr(club)); 
			fileWriter.write("\r\n"); 
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<TravelClub> readAll(){
		//
		List<TravelClub> clubList = new ArrayList<>(); 
		BufferedReader reader = null;
		
		try {
	        reader = clubFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				 clubList.add(convertToClub(line)); 
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return clubList; 
	}
	
	public TravelClub read(String clubId) {
		//
		TravelClub club = null; 
		BufferedReader reader = null;
		
		try {
	        reader = clubFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				
				if (clubFile.hasValueOf("id", clubId, line)) {
					club = convertToClub(line); 
					break; 
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return club; 
	}

	public TravelClub readLast() {
		//
		String lastLine = null; 
		BufferedReader reader = null;
		
		try {
	        reader = clubFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				lastLine = line; 
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return convertToClub(lastLine);  
	}

	public TravelClub readByName(String name) {
		//
		TravelClub club = null; 
		BufferedReader reader = null;
		
		try {
	        reader = clubFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				if (clubFile.hasValueOf("name", name, line)) {
					club = convertToClub(line); 
					break; 
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return club; 
	}
	
	public void update(TravelClub club) {
		// 
        BufferedReader reader;
        PrintWriter writer;
		try {
	        reader = clubFile.requestReader();
	        writer = clubTempFile.requestPrintWriter(); 

	        String line = null;
	        String clubId = club.getId(); 
	        while ((line = reader.readLine()) != null) {

				if (clubFile.hasValueOf("id", clubId, line)) {
	            	line = convertToStr(club); 
	            }
	            writer.println(line);
	            writer.flush();
	        }
	        writer.close();
	        reader.close();
	        
	        if (!clubFile.delete()) {
	            System.out.println("Could not delete file");
	            return;
	        }

	        if (!clubTempFile.renameTo(clubFile)) {
	            System.out.println("Could not rename file");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String clubId) {
		// 
        BufferedReader reader;
        PrintWriter writer;
		try {
	        reader = clubFile.requestReader();
	        writer = clubTempFile.requestPrintWriter();
	        String line = null;

	        while ((line = reader.readLine()) != null) {

				if (clubFile.hasValueOf("id", clubId, line)) {
	            	continue; 
	            }
	            writer.println(line);
	            writer.flush();
	        }
	        
	        writer.close();
	        reader.close();
	        
	        if (!clubFile.delete()) {
	            System.out.println("Could not delete file");
	            return;
	        }

	        if (!clubTempFile.renameTo(clubFile)) {
	            System.out.println("Could not rename file");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private TravelClub convertToClub(String readLine) {
		// 
		return (TravelClub)clubFile.convertTo(readLine, TravelClub.class); 
	}
	
	private String convertToStr(TravelClub club) {
		//
		return clubFile.convertFrom(club); 
	}

//  Please don't remove. I left it for reference purpose
//	
//	private TravelClub convertToClub(String readLine) {
//		// 
//		StringTokenizer tokenizer = new StringTokenizer(readLine, clubFile.getDelimiter()); 
//		tokenizer.nextToken(); 
//		tokenizer.nextToken(); 
//		
//		String json = tokenizer.nextToken(); 
//		return ((new Gson()).fromJson(json, TravelClub.class)); 
//	}
//	
//	private String convertToStr(TravelClub club) {
//		//
//		StringBuilder builder = new StringBuilder(); 
//		
//		builder.append(club.getId()).append(clubFile.getDelimiter()); 
//		builder.append(club.getName()).append(clubFile.getDelimiter()); 
//		builder.append((new Gson()).toJson(club)); 
//		
//		return builder.toString(); 
//	}

	public static void main(String[] args) {
		// 
		ClubFile clubFile = new ClubFile(); 
		TravelClub clubOne = TravelClub.getSample(false); 
		String clubId1 = clubOne.getId(); 
		
		clubFile.write(clubOne);
		TravelClub readClubOne = clubFile.read(clubId1);
		System.out.println(" > read club 1: " + readClubOne); 
		
		readClubOne.setName("NewName");
		clubFile.update(readClubOne);
		TravelClub updatedClubOne = clubFile.read(readClubOne.getId()); 
		System.out.println(" > read club 1: " + updatedClubOne); 
		
		TravelClub clubTwo = new TravelClub("C++TravelClub", "This is a C++ programming travel club."); 
		clubTwo.setUsid("CTT01"); 

		clubFile.write(clubTwo); 
		TravelClub readClubTwo = clubFile.read(clubTwo.getId()); 
		System.out.println(" > read club 2: " + readClubTwo.toString()); 
		
		TravelClub readClubThree = clubFile.readLast(); 
		System.out.println(" > read club 3: " + readClubThree); 
	}

}