package step4_1.clubfile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import entity.club.TravelClub;
import step4_1.da.file.index.ClubIndexFile;
import step4_1.da.file.io.ClubFile;
import step4_1.da.file.io.FileConfig;
import util.FileDbWrapper;

public class TestClubIndexedFile {
	//
	private static FileDbWrapper clubFile;
	private static Map<String,Integer> keyIndexMap; 
	
	static {
		clubFile = new FileDbWrapper("step41", FileConfig.getFileName("Club"), FileConfig.getDelimiter());
		keyIndexMap = new LinkedHashMap<String,Integer>(); 
		keyIndexMap.put("id", 0); 
		keyIndexMap.put("name", 1); 
		clubFile.setKeyIndexMap(keyIndexMap);
	}
	@Test
	public void testRunIndex() {
		//
		ClubIndexFile clubIndex = new ClubIndexFile();
		long time = System.currentTimeMillis();
		clubIndex.runIndex();
		System.out.print("indexedbase Taken time --> " + (System.currentTimeMillis() - time));
	}

	@Test
	public void testSortIndexFile() {
		ClubIndexFile clubIndex = new ClubIndexFile();
		long time = System.currentTimeMillis();
		clubIndex.sortIndexFile();
		System.out.print("sort Taken time --> " + (System.currentTimeMillis() - time));
	}

	@Test
	public void testSearchIndexedFile() {
		ClubIndexFile clubIndex = new ClubIndexFile();
		long time = System.currentTimeMillis();
		int value = clubIndex.searchIndexedFile("700000");
		System.out.println("searched Value =>" + value);
		System.out.println("indexing Taken time --> " + (System.currentTimeMillis() - time));
	}
	
	public int testSearchIndexedFile2() {
		ClubIndexFile clubIndex = new ClubIndexFile();
		long time = System.currentTimeMillis();
		int value = clubIndex.searchIndexedFile("100000");
		System.out.println("searched Value =>" + value);
		System.out.println("indexing Taken time --> " + (System.currentTimeMillis() - time));
		
		return value;
	}
	
	@Test
	public void testSearchIndexedClub() {
		long time = System.currentTimeMillis();
		int linePointer = testSearchIndexedFile2();
		try {
			RandomAccessFile reader = clubFile.requestAccessReader();
			reader.seek(linePointer);
			String line = reader.readLine();
			TravelClub club = (TravelClub) clubFile.convertTo(line, TravelClub.class);
			System.out.print("CLUB -->>" + club + ", TIME->" + (System.currentTimeMillis()-time));
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSequentialSearchFile() {
		long time = System.currentTimeMillis();
		ClubFile fileStore = new ClubFile();
		TravelClub club = fileStore.read("100000");
		System.out.print("CLUB -->>" + club + ", TIME->" + (System.currentTimeMillis()-time));
	}

}
