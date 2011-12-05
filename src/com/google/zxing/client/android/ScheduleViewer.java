package com.google.zxing.client.android;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.google.zxing.client.android.Cosmetic;
import com.google.zxing.client.android.FileIO;

import java.text.ParseException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ScheduleViewer extends ListActivity {
	
	private ArrayList<Cosmetic> m_orders;
	private CosmeticAdapter m_adapter;
	private FileIO fileHandler;
	private SimpleDateFormat dateFormatter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileHandler = new FileIO();
        dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        ShowList();
    }
    
    private class CosmeticAdapter extends ArrayAdapter<Cosmetic> {
 
        private ArrayList<Cosmetic> items;
 
        public CosmeticAdapter(Context context, int textViewResourceId, ArrayList<Cosmetic> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.schedule_list, null);
            }
            Cosmetic p = items.get(position);
            if (p != null) {
                TextView tt = (TextView) v.findViewById(R.id.toptext);
                TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                Button btn = (Button) v.findViewById(R.id.ButtonDeleteCos);
                
                
                if (tt != null){
                	tt.setText("< " + p.getBrand() + " > " +p.getName());                            
                }
                if(bt != null){
                	Date d = p.getEndDate();
                	Date now = Calendar.getInstance().getTime();
                	int difInDays = (int)((now.getTime() - d.getTime())/(1000*60*60*24));
                	
                	String outStr;
                	if( difInDays < 0 )	// will expire
                		outStr = "    will expire after "+ -difInDays + " days";
                	else
                		outStr = "    expired "+ difInDays + " days ago";
                	
                	outStr += " (date:"+dateFormatter.format(p.getEndDate())+")";
                	bt.setText(outStr);
                }
            }
            return v;
        }
    }
    
    private void ShowCalendar()
    {   
        setContentView(R.layout.schedule);
        Button b1 = (Button)findViewById(R.id.Button01);
        
        b1.setText(R.string.schGoListBtn);
        
        b1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  ShowList();
          }
        });

        try {
			m_orders = fileHandler.loadCosData();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        //m_orders.clear();
        
    }
    
    private void ShowList()
    {   
        setContentView(R.layout.schedule);
        Button b1 = (Button)findViewById(R.id.Button01);
        b1.setText(R.string.schGoCalBtn);
        
        b1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  Intent intent = new Intent(ScheduleViewer.this, CalenderActivity.class);
        	  startActivity(intent);
          }
        });

        try {
			m_orders = fileHandler.loadCosData();
	        m_adapter = new CosmeticAdapter(this, R.layout.schedule_list, m_orders);
	        
	        setListAdapter(m_adapter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

}
