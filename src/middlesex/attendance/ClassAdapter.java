package middlesex.attendance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClassAdapter extends ArrayAdapter<Class> {
	
	Context context; 
    int layoutResourceId;    
    Class classes[] = null;

	public ClassAdapter(Context context, int layoutResourceId,
			Class[] classes) {
		super(context, layoutResourceId, classes);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.classes = classes;
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
	        
	        Class item = classes[position];
	        holder.name.setText(item.getName());
	        holder.details.setText(item.getDate()+" - "+item.getTime());

	        
	        return row;
	    }

	 static class ClassHolder
	    {
		 	TextView name,details;
	    }
}
