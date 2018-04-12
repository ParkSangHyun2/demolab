package sort.compare;

public class TestThread extends Thread{
	//
	CustomSort sort;
	public TestThread(CustomSort sort) {
		//
		this.sort = sort;
	}
	
	@Override
	public void run() {
		//
		sort.run();
	}
}
