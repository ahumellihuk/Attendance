package middlesex.attendance;

import middlesex.attendance.db.DatabaseConnector;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private SharedPreferences sharedPref;
	private EditText userInput,passInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		sharedPref = getSharedPreferences("user", this.MODE_PRIVATE);
		
		if (sharedPref.contains("token")) {
			mainActivity();
		}
		else {
			setContentView(R.layout.login_my);
			userInput = (EditText)findViewById(R.id.userid);
			passInput = (EditText)findViewById(R.id.password);
			Button login = (Button)findViewById(R.id.login);
			login.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					AsyncTask<String, String, Integer> db = new DatabaseConnector(LoginActivity.this, null, "Logging in...");
					db.execute("login", userInput.getText().toString(), passInput.getText().toString());		
				}			
			});
		}		
	}
	
	public void storeUserToken(String token) {		
		Editor edit = sharedPref.edit();
		edit.putString("token", token);
		edit.commit();	
		mainActivity();
	}
	
	private void mainActivity() {
		Intent in = new Intent(getApplicationContext(), ModulesActivity.class);
		startActivity(in);
		finish();
	}
}
