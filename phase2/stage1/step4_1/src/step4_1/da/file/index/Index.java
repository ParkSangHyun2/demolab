package step4_1.da.file.index;

public interface Index {
	//
	public void runIndex();
	
	void sortIndexFile();
	
	public int searchIndexedFile(String searchValue);
}
