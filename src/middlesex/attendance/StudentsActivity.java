package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class StudentsActivity extends ListActivity {

	private SharedPreferences sharedPref;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.students_list);
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		updateList();
		
		// Get listview
        ListView lv = getListView();
        registerForContextMenu(lv);
	}

	public void updateList() {
		AsyncTask<String, String, Integer> db = new DatabaseConnector(this, token, "Fetching students...");
		db.execute("getStudents");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.add_item:
                Intent in = new Intent(getApplicationContext(),NewStudentActivity.class);
                startActivityForResult(in, 101);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 101) {
			updateList();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.students_list, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.add("Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  TextView text = (TextView)info.targetView.findViewById(R.id.itemDetails);
	  String id = text.getText().toString();
	  AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Removing student...");
	  db.execute("removeStudent",id);
	  return true;
	}

}
