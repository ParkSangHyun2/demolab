package sort.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sort.bubble.BubbleSort;
import sort.insertion.InsertionSort;
import sort.quick.QuickSort;
import sort.selection.SelectionSort;

public class SortTest {
	//
	private final static int DATASIZE = 1000000;
	public static void main(String[] args) {
		//
		SortTest test = new SortTest();

		long time = System.currentTimeMillis();
		CustomSort bubble = new BubbleSort(test.createArray(DATASIZE), time);
		QuickSort quick = new QuickSort(test.createArray(DATASIZE),time);
		CustomSort insertion = new InsertionSort(test.createArray(DATASIZE),time);
		CustomSort selection = new SelectionSort(test.createArray(DATASIZE), time);
		List<CustomSort> sorts = new ArrayList<>();
		
		sorts.add(bubble);
		sorts.add(quick);
		sorts.add(insertion);
		sorts.add(selection);
		
		for(CustomSort sort : sorts) {
			TestThread thread = new TestThread(sort);
			thread.start();
		}

	}
	
	private int[] createArray(int dataSize) {
		//
		int[] array = new int[dataSize];
		for(int i=0; i<dataSize; i++) {

			array[i]=(new Random().nextInt(dataSize));
		}
		return array;
	}
}
