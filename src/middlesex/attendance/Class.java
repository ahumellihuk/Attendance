package middlesex.attendance;

import java.util.Date;
import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

public class Class implements Parcelable {

	private int id;
	private int moduleId;
	private Date date;
	private String name;
	private HashMap<Integer, Boolean> attendance;
	
	public Class(int id, int module, String name) {
		this.id = id;
		this.moduleId = module;
		this.name = name;
	}
	
	public void setAttended(int student) {
		attendance.put(student, true);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public int getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(int module) {
		moduleId = module;
	}
	
	@SuppressWarnings("deprecation")
	public void setDateTime(int day, int month, int year, int hours, int minutes) {
		this.date = new Date();
		date.setDate(day);
		date.setMonth(month);
		date.setYear(year);
		date.setHours(hours);
		date.setMinutes(minutes);
	}
	
	@SuppressWarnings("deprecation")
	public String getDate() {
		String output = "";
		output += date.getDate();
		output += "/";
		output += date.getMonth();
		output += "/";
		output += date.getYear();
		return output;
	}
	
	@SuppressWarnings("deprecation")
	public String getTime() {
		String output = "";
		output += date.getHours();
		output += ":";
		output += date.getMinutes();
		return output;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeInt(moduleId);
		parcel.writeSerializable(date);
		parcel.writeString(name);
		parcel.writeMap(attendance);
	}
	
	
}
