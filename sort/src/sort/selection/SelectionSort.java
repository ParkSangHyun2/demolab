package sort.selection;

import java.util.Random;

import sort.compare.CustomSort;

public class SelectionSort implements CustomSort {
	//
	private long time;
	private int[] data;
	private int temp;

	public SelectionSort(int[] data, long time) {
		//
		this.data = data;
		this.time = time;

	}

	public void run() {
		//

		for (int i = 0; i < data.length; i++) {
			int min = i;
			for (int j = i + 1; j < data.length; j++) {
				if (data[min] > data[j]) {
					min = j;
				}
			}
			if (min != i) {
				temp = data[min];
				data[min] = data[i];
				data[i] = temp;
			}
		}
		// viewData(data);
		System.out.println("SELECTION SORT ->>" + (System.currentTimeMillis() - time));

	}

	private void viewData(int[] data) {
		//
		for (int i : data) {
			System.out.print(i + " ");
		}
	}
}
