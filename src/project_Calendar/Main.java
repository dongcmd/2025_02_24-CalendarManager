package project_Calendar;

import java.io.IOException;
import java.text.ParseException;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
//		2025/02/27 10:00:00
		CalendarManager cm = new CalendarManager();
		cm.run();
	}
}