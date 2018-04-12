package sort.merge;

public class MergeSort {
	//
	
	public void mergeSort(int start, int end, int[] array) {
		//
		int mid = (start+ end) / 2;
		
		if(end-start>1) {
		mergeSort(start, mid, array);
		mergeSort(mid+1, end, array);
		merge(start, mid, end, array);
		}
	}

	private void merge(int start, int mid, int end, int[] array) {
		// 
		
	}
}
