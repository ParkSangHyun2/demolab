package step1.share.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	//
	public static String today(String format) {
		//
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		return dateFormat.format((Calendar.getInstance().getTime()));
	}
	
	public static String today() {
		//
		return today("yyyy.MM.dd");
	}
	
	public static String CurrentTime() {
		//
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss");
		long time = System.currentTimeMillis();
		return dateFormat.format(new Date(time));
	}
}
