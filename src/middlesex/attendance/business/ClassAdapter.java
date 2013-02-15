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

public class ClassAdapter extends ArrayAdapter<Class> {
	
	Context context; 
    int layoutResourceId;    
    ArrayList<Class> classes = null;

	public ClassAdapter(Context context, int layoutResourceId,
			ArrayList<Class> classes) {
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
	            holder.details2 = (TextView)row.findViewById(R.id.itemDetails2);
	            holder.hidden = (TextView)row.findViewById(R.id.hidden);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ClassHolder)row.getTag();
	        }
	        
	        Class item = classes.get(position);
	        holder.name.setText(item.getName());
	        try {
	        holder.details.setText(item.getDate());
	        holder.details2.setText(item.getTime());
	        } catch (NullPointerException npe) {}
	        holder.hidden.setText(item.getId());
	        
	        return row;
	    }

	 static class ClassHolder
	    {
		 	TextView name,details,details2,hidden;
	    }
}
