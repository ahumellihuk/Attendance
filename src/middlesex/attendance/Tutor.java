package middlesex.attendance;

import java.util.ArrayList;

public class Tutor {

	private int id;
	private String name;
	private ArrayList<Integer> modules;
	
	public Tutor(int id, String name) {
		this.id = id;
		this.name = name;
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
	
	public ArrayList<Integer> getModuleIds() {
		return modules;
	}
	
	public void addModule(int id) {
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
