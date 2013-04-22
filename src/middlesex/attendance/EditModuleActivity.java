package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditModuleActivity extends Activity {

	private SharedPreferences sharedPref;
	private String token;
	private EditText name, code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_module);
		
		sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		
		Intent in = getIntent();
		String moduleName = in.getStringExtra("name");
		String moduleID = in.getStringExtra("id");
		
		name = (EditText)findViewById(R.id.newModuleName);
		name.setText(moduleName);
		code = (EditText)findViewById(R.id.newModuleCode);
		code.setText(moduleID);
		code.setEnabled(false);
		
		Button submit = (Button)findViewById(R.id.addModule);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				
				AsyncTask<String,String,Integer> db = new DatabaseConnector(EditModuleActivity.this, token, "Editing module details...");
				db.execute("editModule",code.getText().toString(),name.getText().toString());
			}
			
		});
	}
}
