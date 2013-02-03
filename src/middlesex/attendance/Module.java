package middlesex.attendance;

import java.util.ArrayList;

public class Module {

	private int id;
	private String name;
	private ArrayList<Integer> classes;
	
	public Module(int id, String name) {
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
	
	public ArrayList<Integer> getClassIds() {
		return classes;
	}
	
	public void addModule(int id) {
		classes.add(id);
	}
	
 	public boolean removeModule(int id) {
 		if (classes.contains(id)) {
 			int index = classes.indexOf(id);
 			classes.remove(index);
 			return true;
 		}
 		return false;
 	}
}
