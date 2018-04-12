package search.sequential;

public class SequentialSearch {
	//
	private long time;
	private int searchValue;
	
	public SequentialSearch(long time, int searchValue) {
		//
		this.time = time;
		this.searchValue = searchValue;
	}
	
	public void sequentialSearch(int[] array) {
		//
		for(int i=0; i<array.length; i++) {
			if(searchValue == array[i]) {
				System.out.println("SequentialSearch --> " + (System.currentTimeMillis() - time));
				return;
			}
		}
	}
	
	private static int[] createArray(int amount) {
		//
		int[] sequentialArray = new int[amount];
		for (int i = 0; i < amount; i++) {
			sequentialArray[i] = i;
		}
		return sequentialArray;
	}

	public static void main(String[] args) {
		//
		SequentialSearch sequentialSearch = new SequentialSearch(System.currentTimeMillis(), 99999999);
		int[] array = createArray(100000000);

		sequentialSearch.sequentialSearch(array);
	}
}
