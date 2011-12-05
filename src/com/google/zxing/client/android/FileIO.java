package com.google.zxing.client.android;

import com.google.zxing.client.android.Cosmetic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;

public class FileIO {
	
	private String localUrl = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mfilefolder";
	BufferedReader reader = null;
	String line = null;	
	private SimpleDateFormat dateFormatter;
	
	private int totalCosNum;
	
	public FileIO()
	{
		totalCosNum = getTotalCosNum();
		dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
	}
	
	private int getTotalCosNum(){
		int totalNum = 0;
		try{	
			reader = new BufferedReader(new FileReader(localUrl + "/myCosmetics.txt"));
			while((line = reader.readLine()) != null) {
				totalNum++; }
		}catch (IOException ioe) {
			System.out.println("file IO error");
			ioe.printStackTrace();
		}finally {
			if(reader != null)
				try { reader.close(); }
				catch(Exception e) {}
		}
		return totalNum;	
	}
	
	
	public boolean addCosmetic(String data){			
		try{
			FileOutputStream fos = null;
			File file = new File(localUrl);
			if (totalCosNum == 0){
				file.mkdirs();
				fos = new FileOutputStream(localUrl + "/myCosmetics.txt");
			}
			else {
				fos = new FileOutputStream(localUrl + "/myCosmetics.txt", true); 
			}
			
			String parsed[] = data.split(":");
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, Integer.valueOf(parsed[parsed.length-1]));
			Date d = c.getTime();
			
			totalCosNum++;
			data = totalCosNum + ":" + data + ":" + dateFormatter.format(d) + "\n";
			fos.write(data.getBytes());
			fos.close();
			System.out.println("addByString success data : "+ data);
			return true;
		}catch(Exception e){e.printStackTrace();
			System.out.println("addByString fail");
			return false; }		
	}

	
	public boolean addCosmetic(Cosmetic newData){			
		try{
			FileOutputStream fos = null;
			File file = new File(localUrl);
			if (totalCosNum == 0){
				file.mkdirs();
				fos = new FileOutputStream(localUrl + "/myCosmetics.txt");
			}
			else {
				fos = new FileOutputStream(localUrl + "/myCosmetics.txt", true); 
			}
			
			totalCosNum++;

			newData.setId(totalCosNum);
			fos.write((String.valueOf(newData.getId()) + ":").getBytes());
			fos.write((newData.getBrand()+":").getBytes());
			fos.write((newData.getName()+":").getBytes());
			fos.write((newData.getExpiredDate()+"\n").getBytes());
			fos.close();
			System.out.println("add success");
			return true;
		}catch(Exception e){e.printStackTrace();
			System.out.println("add fail");
			return false; }		
	}	
	
	public ArrayList<Cosmetic> loadCosData() throws ParseException{

		ArrayList<Cosmetic> cosmetics = new ArrayList<Cosmetic>();
		if (totalCosNum ==0)
		{
			System.out.println("empty file");
			return cosmetics;
		}
		String[] parsedLine = null;
		try{
			
			reader = new BufferedReader(new FileReader(localUrl + "/myCosmetics.txt"));
			while ((line = reader.readLine()) != null){
				parsedLine = line.split(":");
				Cosmetic cosmetic = new Cosmetic();
				cosmetic.setId(Integer.valueOf(parsedLine[0]));
				cosmetic.setBrand(parsedLine[1]);
				cosmetic.setName(parsedLine[2]);
				cosmetic.setExpiredDate(Integer.parseInt(parsedLine[3]));
				
				Date d = (Date) dateFormatter.parse(parsedLine[4]);
				cosmetic.setEndDate(d);
				
				cosmetics.add(cosmetic);				
			}
		}catch(IOException ioe){
			System.out.println("file load error");
			ioe.printStackTrace();
		}
		return cosmetics;
	}
	

	public boolean saveCosmetics(ArrayList<Cosmetic> list){
		Cosmetic cosmetic = new Cosmetic();
		try{
			FileOutputStream fos = new FileOutputStream(localUrl + "/myCosmetics.txt");
			String data = "";
			for (int i=0; i<list.size(); i++)
			{
				cosmetic = list.get(i);
				data = cosmetic.getId() + ":" + cosmetic.getBrand() + ":" + cosmetic.getName() + ":"	+ cosmetic.getExpiredDate() + "\n";
				fos.write(data.getBytes());
			}
			fos.close();
			System.out.println("save success");
			return true;
		}catch(Exception e){e.printStackTrace();}
		System.out.println("save fail");
		return false;		
	}
	
}