package sort.insertion;

import sort.compare.CustomSort;

public class InsertionSort implements CustomSort {
	//
	private long time;
	private int[] data;
	private int temp;

	public InsertionSort(int[] data, long time) {
		//
		this.time = time;
		this.data = data;
	}

	public void run() {
		//
		for (int i = 1; i < data.length; i++) {
			temp = data[i];
			for (int j = i - 1; j > 0; j--) {
				if (temp > data[j]) {
					data[j + 1] = data[j];
				} else {
					data[j] = temp;
					break;
				}
			}
		}
		// this.viewData(data);
		System.out.println("INSERTION SORT ->>" + (System.currentTimeMillis() - time));
	}

	private void viewData(int[] data) {
		//
		for (int i : data) {
			System.out.print(i + " ");
		}
	}
}
