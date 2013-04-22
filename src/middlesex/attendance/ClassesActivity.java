package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Classes Activity displays a list of all classes for a selected Module.
 * @author Dmitri Samoilov
 *
 */
public class ClassesActivity extends ListActivity {
	/**
	 * Class ID code
	 */
	private String classID;
	/**
	 * Module ID code
	 */
	private String module;
	private SharedPreferences sharedPref;
	/**
	 * User authentication token
	 */
	private String token;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classes_list);
		module = getIntent().getStringExtra("module");
		this.setTitle(module);
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		updateList();		
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				TextView hidden = (TextView)view.findViewById(R.id.hidden);
				classID = hidden.getText().toString();
				TextView name = (TextView)view.findViewById(R.id.itemName);
				String className = name.getText().toString();
				TextView date = (TextView)view.findViewById(R.id.itemDetails);
				String classDate = date.getText().toString();
				TextView time = (TextView)view.findViewById(R.id.itemDetails2);
				String classTime = time.getText().toString();
				
				Intent in = new Intent(getApplicationContext(), ClassActivity.class);
				in.putExtra("id", classID);
				in.putExtra("module", module);
				in.putExtra("name", className);
				in.putExtra("date", classDate);
				in.putExtra("time", classTime);
				startActivity(in);
			}
			
		});
	registerForContextMenu(lv);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 101) {
			updateList();
		} 
	}
	/**
	 * Creates and executes new Database query, to get classes details.
	 */
	public void updateList() {
		AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Fetching classes...");
		db.execute("getClasses",module);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.classes_list, menu);
	    return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.add("Edit");
	    menu.add("Delete");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.add_item:
                Intent in = new Intent(getApplicationContext(),NewClassActivity.class);
                in.putExtra("module", module);
                startActivityForResult(in, 101);
	            return true;
	        case R.id.students:
                in = new Intent(getApplicationContext(),StudentsActivity.class);
                startActivity(in);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  TextView hidden = (TextView)info.targetView.findViewById(R.id.hidden);
	  String id = hidden.getText().toString();
	  if (item.getTitle().equals("Edit")) {
			TextView name = (TextView)info.targetView.findViewById(R.id.itemName);
			String className = name.getText().toString();
			TextView date = (TextView)info.targetView.findViewById(R.id.itemDetails);
			String classDate = date.getText().toString();
			TextView time = (TextView)info.targetView.findViewById(R.id.itemDetails2);
			String classTime = time.getText().toString();
			
			Intent in = new Intent(getApplicationContext(), EditClassActivity.class);
			in.putExtra("id", id);
			in.putExtra("name", className);
			in.putExtra("date", classDate);
			in.putExtra("time", classTime);
			startActivity(in);
	  }
	  else {		  
		  AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Removing class...");
		  db.execute("removeClass",id);
	  }
	  return true;
	}

}
