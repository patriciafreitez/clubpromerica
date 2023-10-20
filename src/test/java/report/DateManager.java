package report;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
	private static Date CURRENT_DATE;
	private static String FORMAT_DATE = "YYYY-MM-DD HH:mm:ss";
	private static String FORMAT_FULL = "YYYY-MM-DD hh:mm aaa";
	private static String FORMAT_DIT = "YYYY-MM-DD-HH-mm-ss";
	
	public void init() {
		CURRENT_DATE = getNow();
	}
	
	public String getFullDate() {
		return new SimpleDateFormat(FORMAT_FULL).format(CURRENT_DATE);
	}
	
	public String getDate() {
		return new SimpleDateFormat(FORMAT_DATE).format(CURRENT_DATE);
	}
	
	public Date getNow() {
		return Calendar.getInstance().getTime();
	}
	
	public String getDirDate() {
		return new SimpleDateFormat(FORMAT_DIT).format(getNow());
	}
	
	public String getNowStr() {
		return new SimpleDateFormat(FORMAT_DATE).format(getNow());
	}
	
	public long getTimeFrom(String dateCompare) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		
		long diff = 0;
		try {
			Date date1 = format.parse(dateCompare);
		    Date date2 = format.parse(getNowStr());
		    diff = date2.getTime() - date1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}
	
	public String timeFormatByMilis(long diff) {
		BigDecimal secondsValue = BigDecimal.valueOf(diff);
        Duration dur = Duration.ofMillis(secondsValue.longValueExact());
        int hours = (int) dur.toHours();
        int minutes = (int) dur.toMinutes();
        int seconds = (int) dur.getSeconds();	

	    return hourFormat(hours) + ":" + hourFormat(minutes) + ":" + hourFormat(seconds);
	}
	
	private String hourFormat(int value) {
		return value < 10 ? "0" + value : String.valueOf(value);
	}
}
