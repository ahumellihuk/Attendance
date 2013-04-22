package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
/**
 * Class Activity displays a single class details, along with attendance data for it.
 * @author Dmitri Samoilov
 *
 */
public class ClassActivity extends ListActivity {
	/**
	 * Class ID code
	 */
	private String classID;
	private SharedPreferences sharedPref;
	/**
	 * User authentication token
	 */
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);
		Intent in = getIntent();
		String className = in.getStringExtra("name");
		String moduleCode = in.getStringExtra("module");
		String classDate = in.getStringExtra("date");
		String classTime = in.getStringExtra("time");
		classID = in.getStringExtra("id");
		
		TextView name = (TextView)findViewById(R.id.className);
		TextView code = (TextView)findViewById(R.id.moduleCode);
		TextView date = (TextView)findViewById(R.id.classDate);
		TextView time = (TextView)findViewById(R.id.classTime);
		
		name.setText(className);
		code.setText(moduleCode);
		date.setText(classDate);
		time.setText(classTime);
		
		sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		updateList();		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
		  String student = scanResult.getContents();
		  AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Recording attendance...");
		  db.execute("mark",student,classID);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_class, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.recordAttendance:
	        	IntentIntegrator integrator = new IntentIntegrator(ClassActivity.this);
				integrator.initiateScan();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	/**
	 * Creates and executes new Database query, to get attendance data.
	 */
	public void updateList() {
		AsyncTask<String,String,Integer> db = new DatabaseConnector(this, token, "Fetching student names...");
		db.execute("getAttendance",classID);		
	}

}
