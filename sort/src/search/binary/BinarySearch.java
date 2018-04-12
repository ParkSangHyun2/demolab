package search.binary;

public class BinarySearch {
	//
	private long time;
	private int searchValue;

	public BinarySearch(long time, int searchValue) {
		//
		this.time = time;
		this.searchValue = searchValue;
	}

	public void binarySearch(int[] array, int srcPos, int destPos) {
		//
		// for(int i : array) {
		// System.out.println(i);
		// }
		int midValue = (destPos + srcPos) / 2;
		if (midValue == 0) {
			return;
		}

		if (array[midValue] == searchValue) {
			System.out.println("BinarySearchTime("+ array[midValue] +") --> "+ (System.currentTimeMillis() - time));
			return;
		} else if (array[midValue] < searchValue) {
			binarySearch(array, midValue + 1, destPos);
		} else {
			binarySearch(array, srcPos, midValue - 1);
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
		BinarySearch binarySearch = new BinarySearch(System.currentTimeMillis(), 99999999);
		int[] array = createArray(100000000);

		binarySearch.binarySearch(array, 0, array.length - 1);
	}
}
