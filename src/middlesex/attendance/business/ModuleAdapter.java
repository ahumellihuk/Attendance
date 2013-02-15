package middlesex.attendance.business;

import java.util.ArrayList;

import middlesex.attendance.R;
import middlesex.attendance.R.id;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ModuleAdapter extends ArrayAdapter<Module> {
	
	Context context; 
    int layoutResourceId;    
    ArrayList<Module> modules = null;

	public ModuleAdapter(Context context, int layoutResourceId,
			ArrayList<Module> modules) {
		super(context, layoutResourceId, modules);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.modules = modules;
	}
	
	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ClassHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new ClassHolder();
	            holder.name = (TextView)row.findViewById(R.id.itemName);
	            holder.details = (TextView)row.findViewById(R.id.itemDetails);
	            holder.details2 = (TextView)row.findViewById(R.id.itemDetails2);
	            
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ClassHolder)row.getTag();
	        }
	        
	        Module item = modules.get(position);
	        holder.name.setText(item.getName());
	        holder.details.setText(item.getId());
	        holder.details2.setVisibility(TextView.INVISIBLE);

	        
	        return row;
	    }

	 static class ClassHolder
	    {
		 	TextView name,details,details2;
	    }
}
