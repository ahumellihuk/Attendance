package middlesex.attendance.business;

import java.util.ArrayList;

public class Student {

	private String id;
	private String name;
	private ArrayList<String> modules;
	
	public Student(String id, String name) {
		this.id = id;
		this.name = name;
		modules = new ArrayList<String>();
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
	
	public ArrayList<String> getModuleIds() {
		return modules;
	}
	
	public String getModules() {
		String output = "";
		for (int i=0; i<modules.size(); i++) {
			output+=modules.get(i)+";";
		}
		return output;		
	}
	
	public void addModule(String id) {
		modules.add(id);
	}
	
 	public boolean removeModule(int id) {
 		if (modules.contains(id)) {
 			int index = modules.indexOf(id);
 			modules.remove(index);
 			return true;
 		}
 		return false;
 	}
}
