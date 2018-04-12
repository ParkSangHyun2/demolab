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

import entity.club.CommunityMember;
import step4_1.da.file.io.FileConfig;
import util.FileDbWrapper;

public class MemberIndexFile implements Index {
	//
	private static Map<String, Integer> keyIndexMap;

	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("id", 0);
		keyIndexMap.put("name", 1);
	}

	private FileDbWrapper memberFile;
	private FileDbWrapper memberIndexFile;
	private FileDbWrapper memberTempFile;

	public MemberIndexFile() {
		//
		this.memberFile = new FileDbWrapper("step41", FileConfig.getFileName("Member"), FileConfig.getDelimiter());

		this.memberIndexFile = new FileDbWrapper("step41", FileConfig.getFileName("MemberIndex"),
				FileConfig.getDelimiter());
		this.memberTempFile = new FileDbWrapper("step41", FileConfig.getFileName("MemberTemp"),
				FileConfig.getDelimiter());

		this.memberFile.setKeyIndexMap(keyIndexMap);
		this.memberIndexFile.setKeyIndexMap(keyIndexMap);
		this.memberTempFile.setKeyIndexMap(keyIndexMap);
	}

	public boolean exists(String memberId) {
		//
		boolean found = false;
		int pointer = this.searchIndexedFile(memberId);

		if (pointer == -1) {
			found = false;
		} else {
			found = true;
		}
		return found;
	}

	public void write(CommunityMember member) {
		//
		if (this.exists(member.getId())) {
			return;
		}

		BufferedWriter fileWriter;
		try {
			fileWriter = memberFile.requestBufferedFileWriter();
			fileWriter.write(convertToStr(member));
			fileWriter.write("\r\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CommunityMember read(String memberId) {
		//
		CommunityMember member = null;
		RandomAccessFile reader = null;
		int pointer = -1;
		String line = null;

		try {
			reader = memberFile.requestAccessReader();
			pointer = this.searchIndexedFile(memberId);
			reader.seek(pointer);
			line = reader.readLine();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		member = convertToMember(line);
		return member;
	}

	public void update(CommunityMember member) {
		//
		this.delete(member.getEmail());
		this.write(member);
		this.runIndex();
	}

	public void delete(String memberId) {
		//
		RandomAccessFile reader;
		int startPointer = -1;
		int endPointer = -1;

		try {
			reader = memberFile.requestAccessReader();
			startPointer = this.searchIndexedFile(memberId);
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
		StringBuilder builder = new StringBuilder();

		try {
			accessReader = memberFile.requestAccessReader();
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
				builder.setLength(0);

				builder.append(lineId).append(memberFile.getDelimiter()).append(this.convertLongToString(filePointer));
				while (builder.toString().length() < 35) {
					builder.append("|");
				}
				indexedLines.add(builder.toString());
				filePointer = accessReader.getFilePointer();
			}

			System.out.println("UP:" + indexedLines);
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
		List<String> sortedLine = new ArrayList<>();
		Map<String, String> indexedValues = new TreeMap<>();

		try {
			reader = memberIndexFile.requestReader();
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
				while (builder.toString().length() < 35) {
					builder.append("|");
				}
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
			RandomAccessFile reader = memberIndexFile.requestAccessReader();

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

				if (searchValue.compareTo(searchLineValue) == 0) {
					return Integer.parseInt(searchLineResult);
				} else if (searchValue.compareTo(searchLineValue) > 0) {
					startPointer = midPointer + 1;
					midPointer = (startPointer + endPointer) / 2;
				} else if (searchValue.compareTo(searchLineValue) < 0) {
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

	private void writeIndexedFile(List<String> lines) {
		//
		FileWriter fileWriter;
		memberIndexFile.delete();
		try {
			fileWriter = memberIndexFile.requestFileWriter();
			for (String line : lines) {
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

	private String convertToStr(CommunityMember member) {
		//
		return memberFile.convertFrom(member);
	}

	private CommunityMember convertToMember(String readLine) {
		//
		return (CommunityMember) memberFile.convertTo(readLine, CommunityMember.class);
	}
}
