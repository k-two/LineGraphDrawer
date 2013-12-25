package com.kaustubh.linegraphdrawer;

import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

// Stolen from: http://www.androiddesignpatterns.com/2013/04/retaining-objects-across-config-changes.html
public class TaskFragment extends Fragment {
	
	static interface TaskCallbacks {
		void onPreExecute();

		//void onProgressUpdate(int percent);

		//void onCancelled();

		void onPostExecute();
	}

	private TaskCallbacks taskCallbacks;
	private DummyTask mTask;
	private InputStream fileInputStream;
	private PointsData pointsData;
	
	public PointsData getPointsData() {
		return pointsData;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		taskCallbacks = (TaskCallbacks) activity;
	}
	
	public void setFileInputStreamToBeRead(InputStream inputStream) {
		fileInputStream = inputStream;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Retain this fragment across configuration changes.
		setRetainInstance(true);

		// Create and execute the background task.
		mTask = new DummyTask();
		mTask.execute();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		taskCallbacks = null;
	}

	private class DummyTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				InputFileReader ifr = new InputFileReader(fileInputStream);
				pointsData = ifr.parseFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void ignore) {
			if (taskCallbacks != null) {
				taskCallbacks.onPostExecute();
			}
		}
	}
}
