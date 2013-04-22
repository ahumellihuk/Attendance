package middlesex.attendance.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Class {

	private String id;
	private String moduleId;
	private Calendar date;
	private String name;
	
	public Class(String id, String module, String name) {
		this.id = id;
		this.moduleId = module;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(String module) {
		moduleId = module;
	}
	
	public void setDate(Date date) {
		this.date = Calendar.getInstance();
		this.date.setTime(date);
		
	}
	
	public String getDate() {
		String output = "";
		output += date.get(Calendar.DAY_OF_MONTH);
		output += "/";
		output += (date.get(Calendar.MONTH)+1);
		output += "/";
		output += date.get(Calendar.YEAR);
		return output;
	}
	
	public String getTime() {
		String output = "";
		output += date.get(Calendar.HOUR_OF_DAY);
		output += ":";
		if (date.get(Calendar.MINUTE) < 10)
		output += "0" + date.get(Calendar.MINUTE);
		else output += date.get(Calendar.MINUTE);
		return output;
	}	
	
}
