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

public class NewModuleActivity extends Activity {

	private SharedPreferences sharedPref;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_module);
		
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		
		Button submit = (Button)findViewById(R.id.addModule);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				EditText name = (EditText)findViewById(R.id.newModuleName);
				EditText code = (EditText)findViewById(R.id.newModuleCode);
				
				AsyncTask<String,String,Integer> db = new DatabaseConnector(NewModuleActivity.this, token, "Creating new module...");
				db.execute("newModule",code.getText().toString(),name.getText().toString());
			}
			
		});
	}
}
