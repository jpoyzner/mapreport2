package mapreport.view.map;

import mapreport.front.option.DBWhereOption;
import mapreport.front.option.Options;
import mapreport.util.Log;

public class Rectangle {
	double left;
	double right;
	double top;
	double bottom;
	  
	public Rectangle(
		double xCenter,
		double yCenter,
		double xSpan,
		double ySpan) {
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.ySpan = ySpan;
		this.xSpan = xSpan;
		
		init(xCenter, yCenter, xSpan, ySpan);
	}
	
	public Rectangle(Bounds bounds) {
		this.left = bounds.getLeft();
		this.right = bounds.getRight();
		this.top = bounds.getTop();
		this.bottom = bounds.getBottom();
		
		this.xCenter = (this.left + this.right) / 2;
		this.yCenter = (this.top + this.bottom) / 2;
		this.xSpan = Math.abs(this.left - this.right);
		this.ySpan = Math.abs(this.top - this.bottom);
	}

	private void init(double xCenter, double yCenter, double xSpan, double ySpan) {
		left = xCenter - xSpan / 2;
		right = xCenter + xSpan / 2;
		top = yCenter + ySpan / 2;
		bottom = yCenter - ySpan / 2;
	}
	
	public Rectangle(Options options) {
		left = options.getLeft().getNumberValue();
		right = options.getRight().getNumberValue();
		top = options.getTop().getNumberValue();
		bottom = options.getBottom().getNumberValue();
		
		this.xCenter = (this.left + this.right) / 2;
		this.yCenter = (this.top + this.bottom) / 2;
		this.xSpan = Math.abs(this.left - this.right);
		this.ySpan = Math.abs(this.top - this.bottom);

		Log.log("Rectangle xCenter=" + xCenter + " yCenter=" + yCenter + " xSpan=" + xSpan + " ySpan=" + ySpan 
		        		 + "  left=" + left + " right=" + right + " top =" + top + " bottom=" + bottom); 
		init(xCenter, yCenter, xSpan, ySpan);
	}

	public static Rectangle getRectangle(Options options) {
		Rectangle ret = null;
		if ((options.getLeft() != null && options.getRight() != null && options.getTop() != null && options.getBottom() != null)) {
			ret = new Rectangle(options);
		}
		return ret;
	}

	private double getValue(DBWhereOption option) {
		double ret = 0;
		if (option != null) {
			String value = option.getValue();
			Log.log("Rectangle getValue value=" + value);
			ret = new Double(value);
		}
		return ret;
	}
	
	public String buildLink() {
		StringBuilder ret = new StringBuilder("");
		ret.append("&xCenter=");
		ret.append(xCenter);
		ret.append("&yCenter=");
		ret.append(yCenter);
		ret.append("&xSpan=");
		ret.append(xSpan);
		ret.append("&ySpan=");
		ret.append(ySpan);
			Log.log("Rectangle buildLink() ret.toString()=" + ret.toString());
		return ret.toString();
	}
	
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public double getRight() {
		return right;
	}
	public void setRight(double right) {
		this.right = right;
	}
	public double getTop() {
		return top;
	}
	public void setTop(double top) {
		this.top = top;
	}
	public double getBottom() {
		return bottom;
	}
	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getxSpan() {
		return xSpan;
	}

	public void setxSpan(double xSpan) {
		this.xSpan = xSpan;
	}

	public double getySpan() {
		return ySpan;
	}

	public void setySpan(double ySpan) {
		this.ySpan = ySpan;
	}
	double xSpan;
	double ySpan;
	public double getXCenter() {
		return xCenter;
	}

	public void setxCenter(double xCenter) {
		this.xCenter = xCenter;
	}

	public double getYCenter() {
		return yCenter;
	}

	public void setyCenter(double yCenter) {
		this.yCenter = yCenter;
	}
	double xCenter;
	double yCenter;
	public String toString() {
		String ret = "Rectangle left:" + left + " right:" + right + " top:" + top + " bottom:" + bottom + " xSpan:" + xSpan + " ySpan:" + ySpan 
				 + " xCenter:" + xCenter + " yCenter:" + yCenter;
		return ret;
	}
	
	public static class Bounds {
		final double left, right, top, bottom;
		
		public Bounds(double left, double right, double top, double bottom) {
			this.left = left;
			this.right = right;
			this.top = top;
			this.bottom = bottom;
		}

		public double getLeft() {
			return left;
		}

		public double getRight() {
			return right;
		}

		public double getTop() {
			return top;
		}

		public double getBottom() {
			return bottom;
		}
	}
}
