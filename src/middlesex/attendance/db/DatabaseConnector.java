package middlesex.attendance.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import middlesex.attendance.ClassActivity;
import middlesex.attendance.ClassesActivity;
import middlesex.attendance.LoginActivity;
import middlesex.attendance.ModulesActivity;
import middlesex.attendance.R;
import middlesex.attendance.StudentsActivity;
import middlesex.attendance.business.Class;
import middlesex.attendance.business.ClassAdapter;
import middlesex.attendance.business.Module;
import middlesex.attendance.business.ModuleAdapter;
import middlesex.attendance.business.Student;
import middlesex.attendance.business.StudentAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class DatabaseConnector extends AsyncTask<String, String, Integer> {
	
	private static final String GET_ATTENDANCE_URL = "http://mdxapi.azurewebsites.net/getClassAttendance.php";
	private static final String MARK_URL = "http://mdxapi.azurewebsites.net/markAttended.php";
	private static final String GET_CLASSES_URL = "http://mdxapi.azurewebsites.net/getClasses.php";
	private static final String GET_MODULES_URL = "http://mdxapi.azurewebsites.net/getModules.php";
	private static final String GET_STUDENTS_URL = "http://mdxapi.azurewebsites.net/getStudents.php";
	private static final String NEW_CLASS_URL = "http://mdxapi.azurewebsites.net/newClass.php";
	private static final String NEW_MODULE_URL = "http://mdxapi.azurewebsites.net/newModule.php";
	private static final String NEW_STUDENT_URL = "http://mdxapi.azurewebsites.net/newStudent.php";
	private static final String EDIT_CLASS_URL = "http://mdxapi.azurewebsites.net/editClass.php";
	private static final String EDIT_MODULE_URL = "http://mdxapi.azurewebsites.net/editModule.php";
	private static final String EDIT_STUDENT_URL = "http://mdxapi.azurewebsites.net/editStudent.php";
	private static final String REMOVE_CLASS_URL = "http://mdxapi.azurewebsites.net/removeClass.php";
	private static final String REMOVE_MODULE_URL = "http://mdxapi.azurewebsites.net/removeModule.php";
	private static final String REMOVE_STUDENT_URL = "http://mdxapi.azurewebsites.net/removeStudent.php";
	private static final String LOGIN_URL = "http://mdxapi.azurewebsites.net/login.php";
	private static final int STUDENT_MARKED = 1;
	private static final int STUDENT_NOT_MARKED = 0;
	private static final int CLASS_STUDENTS_LOADED = 3;
	private static final int CLASS_STUDENTS_NOT_LOADED = 2;
	private static final int MODULES_NOT_LOADED = 4;
	private static final int MODULES_LOADED = 5;
	private static final int NEW_CLASS_NOT_CREATED = 6;
	private static final int NEW_CLASS_CREATED = 7;
	private static final int NEW_MODULE_NOT_CREATED = 8;
	private static final int NEW_MODULE_CREATED = 9;
	private static final int CLASSES_LOADED = 11;
	private static final int CLASSES_NOT_LOADED = 10;
	private static final int CLASS_NOT_REMOVED = 12;
	private static final int CLASS_REMOVED = 13;
	private static final int MODULE_NOT_REMOVED = 14;
	private static final int MODULE_REMOVED = 15;
	private static final int STUDENTS_NOT_LOADED = 16;
	private static final int STUDENTS_LOADED = 17;
	private static final int NEW_STUDENT_CREATED = 19;
	private static final int NEW_STUDENT_NOT_CREATED = 18;
	private static final int LOGIN_SUCCESSFUL = -1;
	private static final int LOGIN_DENIED = -2;
	private static final int STUDENT_REMOVED = 21;
	private static final int STUDENT_NOT_REMOVED = 20;
	private static final int CLASS_EDITED = 23;
	private static final int CLASS_NOT_EDITED = 22;
	private static final int MODULE_EDITED = 25;
	private static final int MODULE_NOT_EDITED = 24;
	private static final int STUDENT_EDITED = 27;
	private static final int STUDENT_NOT_EDITED = 26;
	private String waitMessage;
	private Activity parent;
	private ProgressDialog pDialog; 
	private JSONParser jParser;
	private String message;
	private ArrayList<String> classStudents;
	private ArrayList<Class> classes;
	private ArrayList<Module> modules;
	private String token;
	private ArrayList<Student> students;

	public DatabaseConnector(Activity act, String token, String arg) {
		super();
		waitMessage = arg;
		this.token = token;
		parent = act;		
	}
	
	@Override
	protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(parent);
        pDialog.setMessage(waitMessage);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... args) {
		Integer result = null;
		jParser = new JSONParser();
		if (args[0] == "getAttendance") {
			result = getClassAttendance(args[1]);
		}
		else if (args[0] == "mark") {
			result = markAttended(args[1],args[2]);
		}	
		else if (args[0] == "getClasses") {
			result = getClasses(args[1]);
		}
		else if (args[0] == "getModules") {
			result = getModules();
		}
		else if (args[0] == "getStudents") {
			result = getStudents();
		}
		else if (args[0] == "newClass") {
			result = newClass(args[1],args[2],args[3],args[4]);
		}
		else if (args[0] == "newStudent") {
			result = newStudent(args[1],args[2],args[3],args[4]);
		}
		else if (args[0] == "editStudent") {
			result = editStudent(args[1],args[2],args[3],args[4]);
		}
		else if (args[0] == "newModule") {
			result = newModule(args[1],args[2]);
		}
		else if (args[0] == "editModule") {
			result = editModule(args[1],args[2]);
		}
		else if (args[0] == "editClass") {
			result = editClass(args[1],args[2],args[3],args[4]);
		}
		else if (args[0] == "removeClass") {
			result = removeClass(args[1]);
		}
		else if (args[0] == "removeModule") {
			result = removeModule(args[1]);
		}
		else if (args[0] == "removeStudent") {
			result = removeStudent(args[1]);
		}
		else if (args[0] == "login") {
			result = login(args[1],args[2]);
		}
        return result;
	}
	
	private Integer editModule(String code, String name) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(EDIT_MODULE_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return MODULE_EDITED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MODULE_NOT_EDITED;
	}

	private Integer editStudent(String firstname, String lastname, String code,
			String moduleids) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("firstname", firstname));
        params.add(new BasicNameValuePair("lastname", lastname));
        params.add(new BasicNameValuePair("modules", moduleids));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(EDIT_STUDENT_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return STUDENT_EDITED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return STUDENT_NOT_EDITED;
	}

	private Integer editClass(String id, String name, String date,
			String time) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        
		params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("date", date));
        params.add(new BasicNameValuePair("time", time));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(EDIT_CLASS_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return CLASS_EDITED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return CLASS_NOT_EDITED;
	}

	private Integer removeStudent(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("token", token));
		JSONObject json = jParser.makeHttpRequest(REMOVE_STUDENT_URL, "POST", params);
		
		try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return STUDENT_REMOVED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return STUDENT_NOT_REMOVED;
	}

	private Integer newStudent(String first, String last, String code, String moduleids) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("firstname", first));
        params.add(new BasicNameValuePair("lastname", last));
        params.add(new BasicNameValuePair("modules", moduleids));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(NEW_STUDENT_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return NEW_STUDENT_CREATED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return NEW_STUDENT_NOT_CREATED;
	}

	private Integer getStudents() {
		students = new ArrayList<Student>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", token));
        JSONObject json = jParser.makeHttpRequest(GET_STUDENTS_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                JSONArray array = json.getJSONArray("students");

                //looping through all Students
                for (int i = 0; i < array.length(); i++) {
                    JSONObject c = array.getJSONObject(i);
                    
                    //Getting the fields
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String studentModules = c.getString("modules");
                    String [] moduleids = studentModules.split(";");
                    
                    //Creating Student Object
                    Student item = new Student(id,name);
                    for (int x=0; x<moduleids.length; x++)
                    	item.addModule(moduleids[x]);
                    students.add(item);	                    
                }	                
                return STUDENTS_LOADED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return STUDENTS_NOT_LOADED;
	}

	private Integer login(String userid, String password) {
		//Encode password
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
			password = null;
		} catch (NoSuchAlgorithmException nsae) {}
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("password", hashword));
		JSONObject json = jParser.makeHttpRequest(LOGIN_URL, "POST", params);
		
		try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
            	token = json.getString("token");
                return LOGIN_SUCCESSFUL;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return LOGIN_DENIED;
	}

	private Integer removeModule(String moduleID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", moduleID));
		params.add(new BasicNameValuePair("token", token));
		JSONObject json = jParser.makeHttpRequest(REMOVE_MODULE_URL, "POST", params);
		
		try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return MODULE_REMOVED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return MODULE_NOT_REMOVED;
	}

	private Integer removeClass(String classID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", classID));
		params.add(new BasicNameValuePair("token", token));
		JSONObject json = jParser.makeHttpRequest(REMOVE_CLASS_URL, "POST", params);
		
		try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return CLASS_REMOVED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return CLASS_NOT_REMOVED;
	}

	protected Integer getClassAttendance(String classID) {
		classStudents = new ArrayList<String>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", classID));
        params.add(new BasicNameValuePair("token", token));
        JSONObject json = jParser.makeHttpRequest(GET_ATTENDANCE_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                JSONArray array = json.getJSONArray("students");

                //looping through all Students
                for (int i = 0; i < array.length(); i++) {
                    String name = array.getString(i);
                    classStudents.add(name);	                    
                }	
                return CLASS_STUDENTS_LOADED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return CLASS_STUDENTS_NOT_LOADED;
	}
	
	protected Integer markAttended(String student, String classID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("student", student));
		params.add(new BasicNameValuePair("class", classID));
		params.add(new BasicNameValuePair("token", token));
		
		JSONObject json = jParser.makeHttpRequest(MARK_URL, "POST", params);
		
		try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {	            	
                return STUDENT_MARKED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return STUDENT_NOT_MARKED;
	}
	
	protected Integer getClasses(String moduleID) {
		classes = new ArrayList<Class>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("module",moduleID));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(GET_CLASSES_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                JSONArray array = json.getJSONArray("classes");

                //looping through all Classes
                for (int i = 0; i < array.length(); i++) {
                    JSONObject c = array.getJSONObject(i);
                    
                    //Getting the fields
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String module = c.getString("module");
                    String obj = c.getString("datetime");
                    obj = obj.substring(9, 29);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = df.parse(obj,new ParsePosition(0));
                    
                    //Creating Class Object
                    Class item = new Class(id,module,name);
                    item.setDate(date);
                    classes.add(item);	
                }
                return CLASSES_LOADED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return CLASSES_NOT_LOADED;
	}
	
	protected Integer getModules() {
		modules = new ArrayList<Module>();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", token));
        JSONObject json = jParser.makeHttpRequest(GET_MODULES_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                JSONArray array = json.getJSONArray("modules");

                //looping through all Modules
                for (int i = 0; i < array.length(); i++) {
                    JSONObject c = array.getJSONObject(i);
                    
                    //Getting the fields
                    String id = c.getString("id");
                    String name = c.getString("name");
                    
                    //Creating Module Object
                    Module item = new Module(id,name);
                    modules.add(item);	                    
                }	                
                return MODULES_LOADED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MODULES_NOT_LOADED;
	}
	
	protected Integer newClass(String name, String date, String time, String module) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("date", date));
        params.add(new BasicNameValuePair("time", time));
        params.add(new BasicNameValuePair("module", module));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(NEW_CLASS_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return NEW_CLASS_CREATED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return NEW_CLASS_NOT_CREATED;
	}
	
	protected Integer newModule(String code, String name) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("token", token));
        
        JSONObject json = jParser.makeHttpRequest(NEW_MODULE_URL, "POST", params);

        try {
            int success = json.getInt("success");
            message = json.getString("message");
            if (success == 1) {
                return NEW_MODULE_CREATED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return NEW_MODULE_NOT_CREATED;
	}
	
	
	
	@Override
	protected void onPostExecute(Integer result) {
		pDialog.dismiss();
		Toast toast;
		switch (result) {
		case CLASS_STUDENTS_LOADED:
			if (parent instanceof ClassActivity) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>((ClassActivity)parent, R.layout.attended, R.id.attendedName, classStudents);		
				((ClassActivity) parent).setListAdapter(adapter);
			}
			break;
		case CLASS_STUDENTS_NOT_LOADED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case STUDENT_MARKED:
			if (parent instanceof ClassActivity) {
				toast = Toast.makeText(((ClassActivity)parent).getApplicationContext(), message, Toast.LENGTH_SHORT);
				toast.show();
				((ClassActivity)parent).updateList();
			}
			break;
		case STUDENT_NOT_MARKED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case CLASSES_NOT_LOADED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			if (parent instanceof ClassesActivity) {
				ClassAdapter adapter = new ClassAdapter((ClassesActivity)parent, R.layout.list_item, classes);		
				((ClassesActivity)parent).setListAdapter(adapter);
			}
			break;
		case CLASSES_LOADED:
			if (parent instanceof ClassesActivity) {
				ClassAdapter adapter = new ClassAdapter((ClassesActivity)parent, R.layout.list_item, classes);		
				((ClassesActivity)parent).setListAdapter(adapter);
			}
			break;
		case MODULES_LOADED:
			if (parent instanceof ModulesActivity) {
				ModuleAdapter adapter = new ModuleAdapter((ModulesActivity)parent, R.layout.list_item, modules);		
				((ModulesActivity)parent).setListAdapter(adapter);
			}
			break;
		case MODULES_NOT_LOADED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			if (parent instanceof ModulesActivity) {
				ModuleAdapter adapter = new ModuleAdapter((ModulesActivity)parent, R.layout.list_item, modules);		
				((ModulesActivity)parent).setListAdapter(adapter);
			}
			break;
		case STUDENTS_LOADED:
			if (parent instanceof StudentsActivity) {
				StudentAdapter adapter = new StudentAdapter((StudentsActivity)parent, R.layout.list_item, students);		
				((StudentsActivity)parent).setListAdapter(adapter);
			}
			break;
		case STUDENTS_NOT_LOADED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			if (parent instanceof StudentsActivity) {
				StudentAdapter adapter = new StudentAdapter((StudentsActivity)parent, R.layout.list_item, students);		
				((StudentsActivity)parent).setListAdapter(adapter);
			}
			break;
		case NEW_CLASS_NOT_CREATED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case NEW_CLASS_CREATED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			parent.finish();
			break;
		case CLASS_NOT_EDITED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case CLASS_EDITED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			parent.finish();
			break;
		case MODULE_NOT_EDITED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case MODULE_EDITED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			parent.finish();
			break;
		case STUDENT_NOT_EDITED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case STUDENT_EDITED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			parent.finish();
			break;
		case NEW_MODULE_NOT_CREATED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case NEW_MODULE_CREATED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			parent.finish();
			break;
		case NEW_STUDENT_NOT_CREATED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case NEW_STUDENT_CREATED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			parent.finish();
			break;
		case CLASS_REMOVED:
			if (parent instanceof ClassesActivity) {
				toast = Toast.makeText(((ClassesActivity)parent).getApplicationContext(), message, Toast.LENGTH_SHORT);
				toast.show();
				((ClassesActivity)parent).updateList();
			}
			break;
		case CLASS_NOT_REMOVED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case MODULE_REMOVED:
			if (parent instanceof ModulesActivity) {
				toast = Toast.makeText(((ModulesActivity)parent).getApplicationContext(), message, Toast.LENGTH_SHORT);
				toast.show();
				((ModulesActivity)parent).updateList();
			}
			break;
		case MODULE_NOT_REMOVED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case STUDENT_REMOVED:
			if (parent instanceof StudentsActivity) {
				toast = Toast.makeText(((StudentsActivity)parent).getApplicationContext(), message, Toast.LENGTH_SHORT);
				toast.show();
				((StudentsActivity)parent).updateList();
			}
			break;
		case STUDENT_NOT_REMOVED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		case LOGIN_SUCCESSFUL:
			if (parent instanceof LoginActivity) {
				toast = Toast.makeText(((LoginActivity)parent).getApplicationContext(), message, Toast.LENGTH_SHORT);
				toast.show();
				((LoginActivity)parent).storeUserToken(token);
			}
			break;
		case LOGIN_DENIED:
			toast = Toast.makeText(parent.getApplicationContext(), message, Toast.LENGTH_SHORT);
			toast.show();
			break;
		}
	}

}
