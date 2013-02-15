package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewStudentActivity extends Activity {
	private SharedPreferences sharedPref;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_student);
		
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		
		Button submit = (Button)findViewById(R.id.newStudent);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				EditText firstName = (EditText)findViewById(R.id.newStudentFirstName);
				EditText lastName = (EditText)findViewById(R.id.newStudentLastName);
				EditText code = (EditText)findViewById(R.id.newStudentCode);
				EditText modules = (EditText)findViewById(R.id.newStudentModules);
				
				AsyncTask<String,String,Integer> db = new DatabaseConnector(NewStudentActivity.this, token, "Creating new student...");
				db.execute("newStudent",firstName.getText().toString(),lastName.getText().toString(),code.getText().toString(),modules.getText().toString());
			}
			
		});
	}
}
