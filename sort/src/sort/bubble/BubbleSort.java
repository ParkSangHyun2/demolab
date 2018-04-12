package sort.bubble;

import sort.compare.CustomSort;

public class BubbleSort implements CustomSort {
	//
	private int[] data;
	private long time;
	private int temp;

	public BubbleSort(int[] data, long time) {
		//
		this.data = data;
		this.time = time;
	}

	public void run() {
		//
		for (int i = 0; i < data.length; i++) {
			//
			for (int j = 0; j < data.length - i - 1; j++) {
				if (data[j] > data[j + 1]) {
					temp = data[j + 1];
					data[j + 1] = data[j];
					data[j] = temp;
				}
			}
		}
		// this.viewData(data);
		System.out.println("BUBBLE SORT ->>" + (System.currentTimeMillis() - time));
	}

	private void viewData(int[] data) {
		//
		for (int i : data) {
			System.out.print(i + " ");
		}
	}
}
