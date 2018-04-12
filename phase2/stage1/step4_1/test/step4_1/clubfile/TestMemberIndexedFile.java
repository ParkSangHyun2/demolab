package step4_1.clubfile;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import step4_1.da.file.index.MemberIndexFile;

class TestMemberIndexedFile {

	@Test
	void testMemberIndexFile() {
		MemberIndexFile memberFile = new MemberIndexFile();
		memberFile.runIndex();
	}

	@Test
	void testRunIndex() {
		fail("Not yet implemented");
	}

	@Test
	void testSortIndexFile() {
		fail("Not yet implemented");
	}

	@Test
	void testSearchIndexedFile() {
		//
		MemberIndexFile memberFile = new MemberIndexFile();
		int pointer = memberFile.searchIndexedFile("ppp5684@naver.com");
		System.out.print("POINTER --> " + pointer);
	}
	
	@Test
	void testDelete() {
		//
		MemberIndexFile memberFile = new MemberIndexFile();
		memberFile.delete("ppppp@ppppp.ppppp");
	}

}
