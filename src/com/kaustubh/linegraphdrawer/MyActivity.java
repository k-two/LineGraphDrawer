package com.kaustubh.linegraphdrawer;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;

public class MyActivity extends Activity implements TaskFragment.TaskCallbacks {

	GraphDrawerView gdView = null;
	private TaskFragment taskFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gdView = new GraphDrawerView(this);
		setContentView(gdView);

		// Logic stolen from: http://www.androiddesignpatterns.com/2013/04/retaining-objects-across-config-changes.html
		FragmentManager fm = getFragmentManager();
		taskFragment = (TaskFragment) fm.findFragmentByTag("task");

		// If the Fragment is non-null, then it is currently being
		// retained across a configuration change.
		if (taskFragment == null) {
			taskFragment = new TaskFragment();
			try {
				taskFragment.setFileInputStreamToBeRead(getAssets().open("points.txt"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			fm.beginTransaction().add(taskFragment, "task").commit();
		}
		else {
			if (taskFragment.getPointsData() != null)
				gdView.setPointsData(taskFragment.getPointsData());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostExecute() {
		gdView.setPointsData(taskFragment.getPointsData());
	}
}
