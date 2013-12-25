package com.kaustubh.linegraphdrawer;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GraphDrawerView extends View {

	private Paint paint, axisPaint;
	private PointsData pd;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (pd == null)
			return;
		
		pd.setViewDimensions(this.getWidth(), this.getHeight());
		
		pd.setScaleOption(PointsData.ScaleOptions.SCALE_EQUALLY);
		
		final ArrayList<PointsData.Point> list = pd.getProcessedArray();
		PointsData.Point startPoint, endPoint;

		for (int i = 0; i < list.size() - 1; i++) {
			startPoint = list.get(i);
			endPoint = list.get(i + 1);
			canvas.drawLine(startPoint.getX(), startPoint.getY(),
					endPoint.getX(), endPoint.getY(), paint);
		}
		
		canvas.drawLine(0, pd.getYForXAxis(), this.getWidth(), pd.getYForXAxis(), axisPaint);
		canvas.drawLine(pd.getXForYAxis(), 0, pd.getXForYAxis(), this.getHeight(), axisPaint);
	}

	public GraphDrawerView(Context context) {
		super(context);

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2f);
		paint.setStyle(Paint.Style.STROKE);
		
		axisPaint = new Paint();
		axisPaint.setAntiAlias(true);
		axisPaint.setColor(Color.RED);
		axisPaint.setStrokeWidth(3f);
		axisPaint.setStyle(Paint.Style.STROKE);
	}

	public void setPointsData(PointsData pointsData) {
		pd = pointsData;
		invalidate();
	}
}
