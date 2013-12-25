package com.kaustubh.linegraphdrawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputFileReader {

	private InputStream is;
	private PointsData pd = null;
	
	public InputFileReader(InputStream inputStream) {
		if (inputStream != null)
			is = inputStream;
		
		pd = new PointsData();
	}
	
	public PointsData parseFile() {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String[] strArr;
		
		try {
			while ((line = br.readLine()) != null) {
				strArr = line.trim().split(" ");
				pd.addToArray(Float.parseFloat(strArr[0]), Float.parseFloat(strArr[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			br.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pd;
	}
}
