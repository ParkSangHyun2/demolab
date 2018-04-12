package sort.quick;

import java.util.Random;

import sort.compare.CustomSort;

public class QuickSort implements CustomSort {
	//
	private int[] array;
	private long time;

	public QuickSort(int[] array, long time) {
		//
		this.array = array;
		this.time = time;
	}

	public int[] quickSort(int start, int end, int[] array) {
		//
		if (array == null || array.length < 0) {
			return null;
		}

		if (start >= end) {
			return null;
		}

		int temp;
		int pivot = ((end + start) / 2);
		int left = start;
		int right = end;

		while (end - start >= 1) {
			//
			while (left < pivot && array[left] <= array[pivot]) {
				left++;
			}
			while (right > pivot && array[right] >= array[pivot]) {
				right--;
			}

			if (left == pivot && pivot == right) {
				quickSort(start, pivot - 1, array);
				quickSort(pivot + 1, end, array);
				break;
			}

			if (left == pivot && right != left) {
				temp = array[right];
				array[right] = array[pivot];
				array[pivot] = temp;
				left = start;
				right = end;
				continue;
			}
			if (right == pivot && right != left) {
				temp = array[left];
				array[left] = array[pivot];
				array[pivot] = temp;
				left = start;
				right = end;
				continue;
			}

			if (left < pivot && pivot < right) {
				temp = array[left];
				array[left] = array[right];
				array[right] = temp;
			}
		}

		return array;
	}

	@Override
	public void run() {
		//
		quickSort(0, array.length - 1, array);
		System.out.println("Quick SORT ->>" + (System.currentTimeMillis() - time));
	}

	public static void main(String[] args) {
		//
		int dataSize = 100000;
		int[] numbers = new int[dataSize];

		for (int i = 0; i < dataSize; i++) {
			numbers[i] = (new Random().nextInt(dataSize));
		}

		QuickSort quick = new QuickSort(numbers, System.currentTimeMillis());

		int[] result = quick.quickSort(0, numbers.length - 1, numbers);
		quick.viewData(result);
		System.out.println("Time : " + (System.currentTimeMillis() - quick.time));
	}

	private void viewData(int[] data) {
		//
		for (int i : data) {
			System.out.print(i + " ");
		}
	}
}
