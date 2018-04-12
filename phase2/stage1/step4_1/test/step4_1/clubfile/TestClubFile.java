package step4_1.clubfile;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.club.TravelClub;
import step4_1.da.file.ClubFileStore;

class TestClubFile {
	//
	private final int DATACOUNT = 100; 
	private List<TravelClub> lists;
	private TravelClub travelClub;
	
	public List<TravelClub> createList(){
		lists = new ArrayList<TravelClub>();
		StringBuilder builder = new StringBuilder();
		String name;
		String intro;
		
		
		for(int i=0; i<DATACOUNT; i++) {
			builder.setLength(0);
			builder.append("TravelClub").append(i);
			name = builder.toString();
			builder.setLength(0);
			builder.append("Introduce").append(i);
			intro = builder.toString();
			
			travelClub = new TravelClub(name, intro);
			lists.add(travelClub);
			if(i%10000 == 0) {
				System.out.println("Creating..."+i);
			}
		}
		System.out.print("Writing File...");
		return lists;
	}
	@Test
	void testWrite() {
		//
		long time = System.currentTimeMillis();
		
		this.createList();
		
		ClubFileStore clubFile = new ClubFileStore();
		int i=0;
		int size = lists.size();
		for(TravelClub club : lists) {
			clubFile.create(club);
			i++;
			if(i%1000 == 0) {
				System.out.println("Writing..." + i + " / " + size);
			}
		}
		System.out.println("Taken time --> " + (System.currentTimeMillis()-time));
	}

}
