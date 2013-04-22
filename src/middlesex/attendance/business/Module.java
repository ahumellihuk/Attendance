package middlesex.attendance.business;

import java.util.ArrayList;

public class Module {

	private String id;
	private String name;
	
	public Module(String id, String name) {
		this.id = id;
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
}
