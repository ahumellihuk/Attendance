package middlesex.attendance;

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
    Module modules[] = null;

	public ModuleAdapter(Context context, int layoutResourceId,
			Module[] modules) {
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
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ClassHolder)row.getTag();
	        }
	        
	        Module item = modules[position];
	        holder.name.setText(item.getName());
	        holder.details.setText(item.getId());

	        
	        return row;
	    }

	 static class ClassHolder
	    {
		 	TextView name,details;
	    }
}
