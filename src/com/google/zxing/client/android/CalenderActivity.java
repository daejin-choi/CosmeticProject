package com.google.zxing.client.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class CalenderActivity extends Activity implements OnClickListener {

	Button listView, pre, next;
	ArrayList<TextView> list;
	TextView today, clickedCosmetic;
	private SimpleDateFormat dateFormatter;
	int setYear;
	int setMonth;
	int setMonth2;
	int setDay;
	int clickedDay;

	ArrayList<FinalDate> finalDates = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
		setContentView(R.layout.calender);
		listView = (Button)findViewById(R.id.listView);
		pre = (Button) findViewById(R.id.pre);
		next = (Button) findViewById(R.id.next);
		today = (TextView) findViewById(R.id.today);
		clickedCosmetic = (TextView) findViewById(R.id.clickedCosmetic);
		Calendar calendar = Calendar.getInstance();
		setYear = calendar.get(Calendar.YEAR);
		setMonth = calendar.get(Calendar.MONTH);
		setMonth2 = calendar.get(Calendar.MONTH) + 1;
		setDay = calendar.get(Calendar.DAY_OF_MONTH);
		list = new ArrayList<TextView>();
		TableLayout table = (TableLayout) findViewById(R.id.table);
		for (int i = 0; i < 6; i++) {
			TableRow tr = new TableRow(this);
			for (int j = 0; j < 7; j++) {
				TextView tv = new TextView(this);
				tv.setHeight(60);
				if (j == 0) {
					tv.setTextColor(Color.RED);
					tv.setTextSize(20);
				} else if (j == 6) {
					tv.setTextColor(Color.BLUE);
					tv.setTextSize(20);
				} else {
					tv.setTextColor(Color.WHITE);
					tv.setTextSize(20);
				}
				tv.setGravity(Gravity.CENTER_HORIZONTAL);
				tr.addView(tv);
				list.add(tv);
			}
			table.addView(tr);

		}

		table.setStretchAllColumns(true);
		table = (TableLayout) findViewById(R.id.week);

		setCalendar(setYear, setMonth);

		listView.setOnClickListener(this);
		pre.setOnClickListener(this);
		next.setOnClickListener(this);

	}

	private void setCalendar(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		int todayYear = calendar.get(Calendar.YEAR);
		int todayMonth = calendar.get(Calendar.MONTH);
		int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int finalYear, finalMonth;
		FinalDate finalDate = null;
		boolean checked = false;
		
		FileIO fileIO = new FileIO();
		ArrayList<Cosmetic> cosmetics = new ArrayList<Cosmetic>();
		try {
			cosmetics = fileIO.loadCosData();
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		finalDates = new ArrayList<FinalDate>();
		for (int i=0; i<cosmetics.size(); i++)
		{
			Cosmetic cos = cosmetics.get(i);
			Date d = cos.getEndDate();
			finalYear = d.getYear()+1900;
			finalMonth = d.getMonth();
			if (finalYear == year && finalMonth == month)
			{
				finalDate = new FinalDate();
				finalDate.setFinalDay(d.getDate());
				finalDate.cosmetic = cosmetics.get(i);
				finalDates.add(finalDate);				
			}
		}
		
		today.setText(year + "년" + (month + 1) + "월");

		int getDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		int j = 1;
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setText("");
			list.get(i).setBackgroundColor(Color.BLACK);
		}
		
		for (int i = getDay-1; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + getDay-1; i++) {
			if (finalDates.size() != 0){
				int k=0;
				checked = false;
					while (true){
						if (k == finalDates.size()) { break;}
						if (j == finalDates.get(k).getFinalDay())
						{
							list.get(i).setBackgroundColor(Color.GREEN);
							list.get(i).setText(j + "");
							list.get(i).setClickable(true);
							list.get(i).setId(j);
							list.get(i).setOnClickListener(this);
							j++;
							checked = true;
							break;
						}
						k++;
					}
					if (year == todayYear && month == todayMonth && j == todayDay && checked == false) // Today Print
					{
						list.get(i).setBackgroundColor(Color.CYAN); // TODAY print
						list.get(i).setText(j++ + "");
						checked = true;
					} 
					else if (checked == false){
						list.get(i).setBackgroundColor(Color.BLACK);
						list.get(i).setText(j++ + "");
						
					}
				
			}
			else {
				if (year == todayYear && month == todayMonth && j == todayDay)	//Today Print
				{
					list.get(i).setBackgroundColor(Color.CYAN);		//TODAY print
					list.get(i).setText(j++ + "");
				}
				else{
					list.get(i).setBackgroundColor(Color.BLACK); 
					list.get(i).setText(j++ + "");
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.pre) {
			setMonth--;
			if (setMonth <= 0) {
				setYear--;
				setMonth = 11;
			}
			setCalendar(setYear, setMonth);
		} else if (v.getId() == R.id.next) {
			setMonth++;
			if (setMonth >= 12) {
				setMonth = 0;
				setYear++;
			}
			setCalendar(setYear, setMonth);
		} else if (v.getId() == R.id.listView) {
	      	  Intent intent = new Intent(CalenderActivity.this, SETermActivity.class);
	      	  startActivity(intent);
		} else
		{
			//clickedCosmetic.setText("test");
			for (int i=0; i<finalDates.size(); i++)
			{
				if (v.getId() == finalDates.get(i).getFinalDay())
				{
                	Date d = finalDates.get(i).cosmetic.getEndDate();
                	Date now = Calendar.getInstance().getTime();
                	int difInDays = (int)((now.getTime() - d.getTime())/(1000*60*60*24));
                	
                	String outStr = "< " + finalDates.get(i).cosmetic.getBrand() + " > " + finalDates.get(i).cosmetic.getName() + "\n";
                	if( difInDays < 0 )	// will expire
                		outStr += "    will expire after "+ -difInDays + " days";
                	else
                		outStr += "    expired "+ difInDays + " days ago";
                	
                	outStr += " (date:"+dateFormatter.format(d)+")";
					clickedCosmetic.setText(outStr);
				}
			}
		}
	}
	
	class FinalDate{
		private int finalDay;
		public Cosmetic cosmetic;
		
		public int getFinalDay() {
			return finalDay;
		}
		public void setFinalDay(int finalDay) {
			this.finalDay = finalDay;
		}	
	}
}
