package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditStudentActivity extends Activity {
	private SharedPreferences sharedPref;
	private String token;
	private EditText firstName, lastName, code, modules;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_student);
		
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		
		Intent in = getIntent();
		String name1 = in.getStringExtra("firstname");
		String name2 = in.getStringExtra("lastname");
		String modulesList = in.getStringExtra("modules");
		String id = in.getStringExtra("id");
		
		firstName = (EditText)findViewById(R.id.newStudentFirstName);
		firstName.setText(name1);
		lastName = (EditText)findViewById(R.id.newStudentLastName);
		lastName.setText(name2);
		code = (EditText)findViewById(R.id.newStudentCode);
		code.setText(id);
		code.setEnabled(false);
		modules = (EditText)findViewById(R.id.newStudentModules);
		modules.setText(modulesList);
		
		Button submit = (Button)findViewById(R.id.newStudent);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				
				AsyncTask<String,String,Integer> db = new DatabaseConnector(EditStudentActivity.this, token, "Editing student details...");
				db.execute("editStudent",firstName.getText().toString(),lastName.getText().toString(),code.getText().toString(),modules.getText().toString());
			}
			
		});
	}
}
