package step4_1.da.file.index;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import entity.club.TravelClub;
import step4_1.da.file.io.FileConfig;
import util.FileDbWrapper;

public class ClubIndexFile implements Index {
	//
	private static Map<String, Integer> keyIndexMap;

	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("id", 0);
		keyIndexMap.put("name", 1);
	}

	private FileDbWrapper clubFile;
	private FileDbWrapper clubIndexFile;
	private FileDbWrapper clubTempFile; 

	public ClubIndexFile() {
		//
		this.clubFile = new FileDbWrapper("step41", FileConfig.getFileName("Club"), FileConfig.getDelimiter());
		this.clubIndexFile = new FileDbWrapper("step41", FileConfig.getFileName("ClubIndex"),
				FileConfig.getDelimiter());
		this.clubTempFile = new FileDbWrapper(
				"step41", 
				FileConfig.getFileName("ClubTemp"), 
				FileConfig.getDelimiter());

		this.clubFile.setKeyIndexMap(keyIndexMap);
		this.clubIndexFile.setKeyIndexMap(keyIndexMap);
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
		BufferedWriter bufferedFileWriter;
		try {
			bufferedFileWriter = clubFile.requestBufferedFileWriter();
			bufferedFileWriter.write(convertToStr(club));
			bufferedFileWriter.write("\r\n");
			bufferedFileWriter.close();
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
		RandomAccessFile reader = null;
		int pointer = -1;
		String line = null;

		try {
			reader = clubFile.requestAccessReader();
			pointer = this.searchIndexedFile(clubId);
			reader.seek(pointer);
			line = reader.readLine();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		club = convertToClub(line);
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
		this.delete(club.getUsid());
		this.write(club);
		this.runIndex();
	}
	
	public void delete(String clubId) {
		// 
		RandomAccessFile reader;
		int startPointer = -1;
		int endPointer = -1;

		try {
			reader = clubFile.requestAccessReader();
			startPointer = this.searchIndexedFile(clubId);
			reader.seek(startPointer);
			String line = reader.readLine();
			long lineLength = line.length() + 2;
			endPointer = (int) (startPointer + lineLength);

			for (int i = endPointer; i >= startPointer; i--) {
				reader.seek(i);
				reader.writeByte('\b');
			}
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		this.runIndex();
	}

	@Override
	public void runIndex() {
		//
		RandomAccessFile accessReader = null;
		List<String> indexedLines = new ArrayList<>();

		try {
			accessReader = clubFile.requestAccessReader();
			// reader = clubFile.requestReader();
			String line;
			String lineId;
			long filePointer = 0;
			long fileLength = accessReader.length();

			while (fileLength > filePointer) {
				//
				filePointer = accessReader.getFilePointer();
				line = accessReader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "||");
				lineId = tokenizer.nextToken();

				StringBuilder builder = new StringBuilder();

				builder.append(lineId).append(clubFile.getDelimiter()).append(this.convertLongToString(filePointer));

				indexedLines.add(builder.toString());
				filePointer = accessReader.getFilePointer();
			}
			accessReader.close();
			this.writeIndexedFile(indexedLines);

			this.sortIndexFile();
		} catch (IOException e) {
			//
			System.out.println("Indexed File indexing ERROR --> " + e.getMessage());
		}
	}

	public void sortIndexFile() {
		//
		BufferedReader reader = null;
		String value;
		StringBuilder builder = new StringBuilder();
		;
		List<String> sortedLine = new ArrayList<>();
		Map<String, String> indexedValues = new TreeMap<>();

		try {
			reader = clubIndexFile.requestReader();
			String line = null;

			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, "||");
				String key = tokenizer.nextToken();
				String pointer = tokenizer.nextToken();
				indexedValues.put(key, pointer);
			}
			reader.close();
			for (String key : indexedValues.keySet()) {
				//
				value = indexedValues.get(key);
				builder.setLength(0);
				builder.append(key).append("||").append(value);
				sortedLine.add(builder.toString());
			}
			this.writeIndexedFile(sortedLine);
		} catch (IOException e) {
			System.out.println("Sorted File indexing ERROR --> " + e.getMessage());
		}
	}

	@Override
	public int searchIndexedFile(String searchValue) {
		//
		try {
			RandomAccessFile reader = clubIndexFile.requestAccessReader();

			long length = reader.length();
			long lineLength = reader.readLine().length() + 2;

			long endPointer = (length / lineLength);
			long startPointer = 0;
			long midPointer = endPointer / 2;

			while (startPointer <= endPointer) {
				reader.seek(midPointer * lineLength);
				String searchLine = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(searchLine, "||");
				String searchLineValue = tokenizer.nextToken();
				String searchLineResult = tokenizer.nextToken();

				if (Integer.parseInt(searchValue) == Integer.parseInt(searchLineValue)) {
					return Integer.parseInt(searchLineResult);
				} else if (Integer.parseInt(searchValue) > Integer.parseInt(searchLineValue)) {
					startPointer = midPointer + 1;
					midPointer = (startPointer + endPointer) / 2;
				} else if (Integer.parseInt(searchValue) < Integer.parseInt(searchLineValue)) {
					endPointer = midPointer - 1;
					midPointer = (startPointer + endPointer) / 2;
				}
			}
		} catch (IOException | NullPointerException e) {
			//
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	private void writeIndexedFile(List<String> indexedLines) {
		//
		FileWriter fileWriter;
		clubIndexFile.delete();
		try {
			fileWriter = clubIndexFile.requestFileWriter();
			for (String line : indexedLines) {
				fileWriter.write(line);
				fileWriter.write("\r\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String convertLongToString(Long pointer) {
		//
		return String.format("%08d", pointer.intValue());
	}
	private TravelClub convertToClub(String readLine) {
		// 
		return (TravelClub)clubFile.convertTo(readLine, TravelClub.class); 
	}
	
	private String convertToStr(TravelClub club) {
		//
		return clubFile.convertFrom(club); 
	}
}
