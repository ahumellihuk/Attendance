package middlesex.attendance;

import java.util.Calendar;

import middlesex.attendance.db.DatabaseConnector;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class EditClassActivity extends Activity {

	String classID;
	EditText date,time,name;
	private SharedPreferences sharedPref;
	private String token;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_class);
		Intent in = getIntent();
		String className = in.getStringExtra("name");
		String classDate = in.getStringExtra("date");
		String classTime = in.getStringExtra("time");
		classID = in.getStringExtra("id");
		
		sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
		token = sharedPref.getString("token", null);
		
		date = (EditText)findViewById(R.id.new_class_date);
		date.setText(classDate);
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Calendar cal = Calendar.getInstance();
				DatePickerDialog dateDlg = new DatePickerDialog(EditClassActivity.this,
				         new DatePickerDialog.OnDateSetListener() {
				         	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				         		String m,d;
				         		if (monthOfYear < 10)
				         			m = "0"+monthOfYear;
				         		else m = ""+monthOfYear;
				         		if (dayOfMonth < 10)
				         			d = "0"+dayOfMonth;
				         		else d = ""+dayOfMonth;
				         		date.setText(year+"-"+m+"-"+d);				            
				         	}
				         }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
				dateDlg.show();				
			}
			
		});
		time = (EditText)findViewById(R.id.new_class_time);
		time.setText(classTime);
		time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Calendar cal = Calendar.getInstance();
				TimePickerDialog timeDlg = new TimePickerDialog(EditClassActivity.this, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						String m,h;
		         		if (hourOfDay < 10)
		         			h = "0"+hourOfDay;
		         		else h = ""+hourOfDay;
		         		if (minute < 10)
		         			m = "0"+minute;
		         		else m = ""+minute;
		         		time.setText(h+":"+m);							
					}
				}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
				timeDlg.show();
				
			}
			
		});
		
		name = (EditText)findViewById(R.id.new_class_name);
		name.setText(className);
		
		Button submit = (Button)findViewById(R.id.addClass);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				
				
				AsyncTask<String,String,Integer> db = new DatabaseConnector(EditClassActivity.this, token, "Editing class details...");
				db.execute("editClass",classID, name.getText().toString(),date.getText().toString(),time.getText().toString());
			}
			
		});
	}
}
