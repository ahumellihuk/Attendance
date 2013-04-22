package middlesex.attendance;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import middlesex.attendance.db.DatabaseConnector;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ModulesActivity extends ListActivity {

	private SharedPreferences sharedPref;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.module_list);
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		updateList();	
		
		// Get listview
        ListView lv = getListView();
 
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String module = ((TextView) view.findViewById(R.id.itemDetails)).getText().toString();
 
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ClassesActivity.class);
                in.putExtra("module", module);
 
                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });
        registerForContextMenu(lv);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.modules_list, menu);
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
		Intent in;
	    switch (item.getItemId()) {
	        case R.id.add_item:
                in = new Intent(getApplicationContext(),NewModuleActivity.class);
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
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 101) {
			updateList();
		}
		else {
			IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			if (scanResult != null) {
			  String student = scanResult.getContents();
			  //AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Recording attendance...");
			  //db.execute("mark",student,classID);
			}
		}
	}

	public void updateList() {
		AsyncTask<String, String, Integer> db = new DatabaseConnector(this, token, "Fetching modules...");
		db.execute("getModules");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  	TextView text = (TextView)info.targetView.findViewById(R.id.itemDetails);
	  	String id = text.getText().toString();
	  	
	  	if (item.getTitle().equals("Edit")) {
	  		TextView text2 = (TextView)info.targetView.findViewById(R.id.itemName);
		  	String name = text2.getText().toString();
		  	
	  		Intent in = new Intent(getApplicationContext(), EditModuleActivity.class);
			in.putExtra("id", id);
			in.putExtra("name", name);
			startActivity(in);
	  	}
	  	else {
	  		AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Removing module...");
		  	db.execute("removeModule",id);
	  	}	  
	  return true;
	}

}
