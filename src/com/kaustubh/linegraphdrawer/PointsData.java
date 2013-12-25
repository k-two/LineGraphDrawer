package com.kaustubh.linegraphdrawer;

import java.util.ArrayList;

public class PointsData {
	public class Point {
		private float x, y;
		
		protected Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public void setY(float y) {
			this.y = y;
		}

		public float getY() {
			return y;
		}
	}
	
	public enum ScaleOptions {
		SCALE_EQUALLY, SCALE_ALONG_AXES;
	}
	
	private ArrayList<Point> pointsArray = null;
	private ArrayList<Point> processedPointsArray = null;
	private float minX = 0;
	private float minY = 0;
	private float maxX = Integer.MIN_VALUE;
	private float maxY = Integer.MIN_VALUE;
	
	private float viewHeight, viewWidth;
	private float xForYAxis, yForXAxis;
	private boolean isPointsArrayListNormalized = false;
	private ScaleOptions scaleOption;
	
	public PointsData() {
		pointsArray = new ArrayList<Point>();
		pointsArray.add(new Point(0, 0));
	}
	
	public void addToArray(float x, float y) {
		Point p = new Point(x, y);
		pointsArray.add(p);
		
		if (x < minX)
			minX = x;
		if (x > maxX)
			maxX = x;
		
		if (y < minY)
			minY = y;
		if (y > maxY)
			maxY = y;
	}

	public void setViewDimensions(float width, float height) {
		viewWidth = width;
		viewHeight = height;
	}
	
	private void normalizePoints() {
		if (isPointsArrayListNormalized == false) {
			float x, y;
			for (Point p : pointsArray) {
				x = (p.getX() - minX) / (maxX - minX);
				y = (p.getY() - minY) / (maxY - minY);
				p.setX(x);
				p.setY(y);
			}
			isPointsArrayListNormalized = true;
		}
	}
	
	private void processArrayScaledEqually() {
		float x, y;
		float scaleFactor = (viewWidth < viewHeight) ? viewWidth : viewHeight;
		
		for (Point p : pointsArray) {
			x = p.getX() * scaleFactor;
			y = viewHeight - (p.getY() * scaleFactor);
			processedPointsArray.add(new Point(x, y));
		}
		
		xForYAxis = (-minX) / (maxX - minX) * scaleFactor;
		yForXAxis = viewHeight - ((-minY) / (maxY - minY) * scaleFactor);
	}
	
	private void processArrayScaledAlongAxes() {
		float x, y;
		
		for (Point p : pointsArray) {
			x = p.getX() * viewWidth;
			y = (1 - p.getY()) * viewHeight;
			processedPointsArray.add(new Point(x, y));
		}
		
		xForYAxis = (-minX) / (maxX - minX) * viewWidth;
		yForXAxis = (1 - ((-minY) / (maxY - minY))) * viewHeight;
	}
	
	public ArrayList<Point> getProcessedArray() {
		processedPointsArray = null;
		processedPointsArray = new ArrayList<PointsData.Point>();
		
		System.out.println("X: " + minX + " " + maxX);
		System.out.println("Y: " + minY + " " + maxY);
		
		normalizePoints();
		
		if (scaleOption == ScaleOptions.SCALE_EQUALLY)
			processArrayScaledEqually();
		else
			processArrayScaledAlongAxes();
		
		return processedPointsArray;

	}
	
	public ArrayList<Point> getPointsArray() {
		return pointsArray;
	}
	
	public float getXForYAxis() {
		return xForYAxis;
	}
	
	public float getYForXAxis() {
		return yForXAxis;
	}
	
	public void setScaleOption(ScaleOptions scaleOpt) {
		scaleOption = scaleOpt;
	}
}
