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

import entity.board.Posting;
import step4_1.da.file.io.FileConfig;
import util.FileDbWrapper;

public class PostingIndexFile implements Index {
	private static Map<String, Integer> keyIndexMap;

	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("id", 0);
		keyIndexMap.put("title", 1);
		keyIndexMap.put("writerEmail", 2);
		keyIndexMap.put("boardId", 3);
	}

	private FileDbWrapper postingFile;
	private FileDbWrapper postingIndexFile;
	private FileDbWrapper postingTempFile;

	public PostingIndexFile() {
		//
		this.postingFile = new FileDbWrapper("step41", FileConfig.getFileName("Posting"), FileConfig.getDelimiter());

		this.postingIndexFile = new FileDbWrapper("step41", FileConfig.getFileName("PostingIndex"),
				FileConfig.getDelimiter());
		this.postingTempFile = new FileDbWrapper("step41", FileConfig.getFileName("PostingTemp"),
				FileConfig.getDelimiter());

		this.postingFile.setKeyIndexMap(keyIndexMap);
		this.postingIndexFile.setKeyIndexMap(keyIndexMap);
		this.postingTempFile.setKeyIndexMap(keyIndexMap);
	}
	
	public boolean exists(String postingId) {
		// 
		boolean found = false;
		int pointer = this.searchIndexedFile(postingId);

		if (pointer == -1) {
			found = false;
		} else {
			found = true;
		}
		return found;
	}
	
	public void write(Posting posting) {
		// 
		if (this.exists(posting.getId())) {
			return;  
		}
		
		BufferedWriter fileWriter;
		
		try {
			fileWriter = postingFile.requestBufferedFileWriter();
			fileWriter.write(convertToStr(posting)); 
			fileWriter.write("\r\n"); 
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Posting read(String postingId) {
		//
		Posting posting = null;
		RandomAccessFile reader = null;
		int pointer = -1;
		String line = null;

		try {
			reader = postingFile.requestAccessReader();
			pointer = this.searchIndexedFile(postingId);
			reader.seek(pointer);
			line = reader.readLine();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		posting = convertToPosting(line);
		return posting;
	}

	//unused Function
	public List<Posting> readByWriterEmail(String writerEmail) {
		//
		List<Posting> postings = new ArrayList<>(); 
		BufferedReader reader = null;
		
		try {
	        reader = postingFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				if (postingFile.hasValueOf("writerEmail", writerEmail, line)) {
					Posting club = convertToPosting(line); 
					postings.add(club); 
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return postings; 
	}

	//unused Function.
	public List<Posting> readByBoardId(String boardId) {
		//
		List<Posting> postings = new ArrayList<>(); 
		BufferedReader reader = null;
		
		try {
	        reader = postingFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				if (postingFile.hasValueOf("boardId", boardId, line)) {
					Posting club = convertToPosting(line); 
					postings.add(club); 
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return postings; 
	}

	public List<Posting> readByTitle(String title) {
		//
		List<Posting> postings = new ArrayList<>(); 
		BufferedReader reader = null;
		
		try {
	        reader = postingFile.requestReader();
			String line = null; 
			
			while((line = reader.readLine()) != null) {
				if (postingFile.hasValueOf("title", title, line)) {
					Posting club = convertToPosting(line); 
					postings.add(club); 
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return postings; 
	}

	public void update(Posting posting) {
		// 
		this.delete(posting.getUsid());
		this.write(posting);
		this.runIndex();
	}
	
	public void delete(String postingId) {
		// 
		RandomAccessFile reader;
		int startPointer = -1;
		int endPointer = -1;

		try {
			reader = postingFile.requestAccessReader();
			startPointer = this.searchIndexedFile(postingId);
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
			accessReader = postingFile.requestAccessReader();
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

				builder.append(lineId).append(postingFile.getDelimiter()).append(this.convertLongToString(filePointer));

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

	@Override
	public void sortIndexFile() {
		//
		BufferedReader reader = null;
		String value;
		StringBuilder builder = new StringBuilder();
		;
		List<String> sortedLine = new ArrayList<>();
		Map<String, String> indexedValues = new TreeMap<>();

		try {
			reader = postingIndexFile.requestReader();
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
			RandomAccessFile reader = postingIndexFile.requestAccessReader();

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
		postingIndexFile.delete();
		try {
			fileWriter = postingIndexFile.requestFileWriter();
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
	
	private Posting convertToPosting(String readLine) {
		// 
		return (Posting)postingFile.convertTo(readLine, Posting.class); 
	}
	
	private String convertToStr(Posting posting) {
		//
		return postingFile.convertFrom(posting); 
	}

	public static void main(String[] args) {
		//
		PostingIndexFile postingFile = new PostingIndexFile();
		postingFile.runIndex();
	}
}
