package project_Calendar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Comparable<Event>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name, desc;
	private Date sDate, eDate;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public String getSDate() {return sdf.format(sDate);}
	public void setSDate(Date sDate) {this.sDate = sDate;}
	public String getEDate() {return sdf.format(eDate);}
	public void setEDate(Date eDate) {this.eDate = eDate;}
	
	Event(String name, String desc, Date sDate, Date eDate) {
		this.name = name;
		this.desc = desc;
		this.sDate = sDate;
		this.eDate = eDate;
	}
	String period() {
		return sdf.format(sDate) + "~" + sdf.format(eDate);
	}
	@Override
	public int compareTo(Event e) {
		return (int)(this.sDate.getTime() - e.sDate.getTime());
	}
	@Override
	public String toString() {
		return String.format("[제목] : %s\n[기간] : %s\n[상세] : %s\n", name, period(), desc);
	}
	
}
