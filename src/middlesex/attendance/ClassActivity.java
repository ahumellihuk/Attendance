package middlesex.attendance;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class ClassActivity extends ListActivity {

	private ClassAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Class[] classes = (Class[]) intent.getParcelableArrayExtra("classes");
		adapter = new ClassAdapter(this, R.layout.list_item, classes);		
		setListAdapter(adapter);
	}
}
